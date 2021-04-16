package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest
{
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup()
    {
        User user1 = new User(), user2 = new User(), user3;
        user1.setUserId(0l);
        user1.setForename("testForename");
        user1.setSurname("testSurname");
        user1.setEmail("testMail");
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
    }

    //Format: methodName_StateUnderTest_ExpectedBehavior
    
    @Test
    public void getUser_IdExists_UserIsCorrect()
    {
        long userId = 0l;
        User user = userService.getUser(userId);
        assertThat(user.getUserId()).isEqualTo(0l);
        assertThat(user.getForename()).isEqualTo("testForename");
        assertThat(user.getSurname()).isEqualTo("testSurname");
        assertThat(user.getEmail()).isEqualTo("testMail");
        assertThat(user.getHash()).isEqualTo("testHash");
        assertThat(user.getSalt()).isEqualTo("testSalt");
        assertThat(user.getScore()).isEqualTo(10);
        assertThat(user.getRating()).isEqualTo(4);
    }

    @Test
    public void getUser_IdDoesNotExists_ReturnsNull()
    {
        long userId = -1l;
        User user = userService.getUser(userId);
        assertThat(user).isNull();
    }

    @Test
    public void createUser_userGetsAdded_ReturnsTrue()
    {
        User user = userService.getUser(0l);
        Mockito.lenient()
                .when(userRepository.save(user))
                .thenReturn(user);


        assertThat(userService.createUser(user)).isTrue();
    }

    @Test
    public void editUser_updatesUser_ReturnsUpdatedUser()
    {
        User tempUser = new User();
        tempUser.setUserId(5l);

        Mockito.lenient()
                .when(userRepository.save(tempUser))
                .thenReturn(tempUser);

        User user = userService.editUser(0l, tempUser);

        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(0l);
        assertThat(user.getForename()).isEqualTo(tempUser.getForename());
        assertThat(user.getSurname()).isEqualTo(tempUser.getSurname());
        assertThat(user.getEmail()).isEqualTo(tempUser.getEmail());
        assertThat(user.getHash()).isEqualTo(tempUser.getHash());
        assertThat(user.getSalt()).isEqualTo(tempUser.getSalt());
        assertThat(user.getScore()).isEqualTo(tempUser.getScore());
        assertThat(user.getRating()).isEqualTo(tempUser.getRating());
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
