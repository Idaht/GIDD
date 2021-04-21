package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Model.DTO.User.UserDTO;
import idatt2106.group3.backend.Model.DTO.User.UserWithPasswordDTO;
import idatt2106.group3.backend.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest
{
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup()
    {
        User user1 = new User(), user2 = new User(), user3;
        user1.setUserId(0l);
        user1.setForename("testForename");
        user1.setSurname("testSurname");
        user1.setEmail("testMail");
        user1.setDateOfBirth(LocalDate.of(2005, 1, 1));
        user1.setHash("testHash");
        user1.setSalt("testSalt");
        user1.setScore(10);
        user1.setRating(4);
        user1.setRole("testRole");
        user1.setFaults(2);
        user1.setActivities(null);

        user2.setUserId(1l);
        user3 = user2;
        user3.setUserId(user1.getUserId());

        List<User> users = new ArrayList<>();
        users.add(user1);

        Collection<Activity> activitiesCollection = new ArrayList<>();
        Set<Activity> activities = Set.copyOf(activitiesCollection);
        user1.setActivities(activities);

        //ArgumentCaptor<User> argumentCaptorBook = ArgumentCaptor.forClass(User.class);


        Mockito.lenient()
                .when(userRepository.findById(user1.getUserId()))
                .thenReturn(java.util.Optional.of(user1));

        Mockito.lenient()
                .when(userRepository.existsById(user1.getUserId()))
                .thenReturn(true);
        Mockito.lenient()
        .when(passwordEncoder.encode(anyString()))
        .thenReturn("testHashed");
    }

    //Format: methodName_StateUnderTest_ExpectedBehavior
    
    @Test
    public void getUser_IdExists_UserIsCorrect()
    {
        long userId = 0l;
        UserDTO user = userService.getUser(userId);
        assertThat(user.getForename()).isEqualTo("testForename");
        assertThat(user.getSurname()).isEqualTo("testSurname");
        assertThat(user.getEmail()).isEqualTo("testMail");
        assertThat(user.getDateOfBirth()).isEqualTo(LocalDate.of(2005, 1, 1));
        assertThat(user.getScore()).isEqualTo(10);
        assertThat(user.getRating()).isEqualTo(4);
    }

    @Test
    public void getUser_IdDoesNotExists_ReturnsNull()
    {
        long userId = -1l;
        UserDTO user = userService.getUser(userId);
        assertThat(user).isNull();
    }

    @Test
    public void createUser_userGetsAdded_ReturnsTrue()
    {
        UserWithPasswordDTO user = new UserWithPasswordDTO("testForename", "testSurname", "testMail", LocalDate.of(2005, 1, 1), "hash", 0, 0, null);
        User returnUser = new User("test","test", "213", LocalDate.of(2005, 1, 1), "hash","salt",1,1,",",1, null);
        returnUser.setUserId(1);
        Mockito.lenient()
                .when(userRepository.save(any()))
                .thenReturn(returnUser);


        assertThat(userService.createUser(user)).isNotNull();
    }

    @Test
    public void editUser_updatesUser_ReturnsUpdatedUser() throws SerialException, SQLException
    {
        UserDTO tempUserDTO = new UserDTO(1,"Test", "Name", "email@email.com", LocalDate.of(2005, 1, 1), 10, 4, "Random Role", null);
        User tempUser = new User();
        tempUser.setForename(tempUserDTO.getForename());
        tempUser.setSurname(tempUserDTO.getSurname());
        tempUser.setEmail(tempUserDTO.getEmail());
        tempUser.setDateOfBirth(tempUserDTO.getDateOfBirth());
        tempUser.setScore(tempUserDTO.getScore());
        tempUser.setRating(tempUserDTO.getRating());
        tempUser.setRole(tempUserDTO.getRole());

        Mockito.lenient()
                .when(userRepository.save(any()))
                .thenReturn(tempUser);

        UserDTO user = userService.editUser(0l, tempUserDTO);

        assertThat(user).isNotNull();
        assertThat(user.getForename()).isEqualTo(tempUser.getForename());
        assertThat(user.getSurname()).isEqualTo(tempUser.getSurname());
        assertThat(user.getEmail()).isEqualTo(tempUser.getEmail());
        assertThat(user.getDateOfBirth()).isEqualTo(tempUser.getDateOfBirth());
        assertThat(user.getScore()).isEqualTo(tempUser.getScore());
        assertThat(user.getRating()).isEqualTo(tempUser.getRating());
        assertThat(user.getProfilePicture()).isEqualTo(tempUser.getProfilePicture());
    }

    @Test
    public void deleteUser_UserExists_ReturnsTrue()
    {
        Mockito.lenient()
                .when(userRepository.existsById(any(Long.class)))
                .thenReturn(false);
        assertThat(userService.deleteUser(1l)).isTrue();
    }

    @Test
    public void deleteUser_UserDoesNotExists_ReturnsFalse()
    {
        Mockito.lenient()
                .when(userRepository.existsById(any(Long.class)))
                .thenReturn(true);
        assertThat(userService.deleteUser(0l)).isFalse();
    }

    @Test
    public void getUserActivities_UserHasNoActivities_EmptySet()
    {
        Set<Activity> activities = userService.getUserActivities(0l);
        assertThat(activities).isEmpty();
    }
}
