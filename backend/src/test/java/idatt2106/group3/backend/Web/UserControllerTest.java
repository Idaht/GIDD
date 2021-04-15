package idatt2106.group3.backend.Web;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Service.ActivityService;
import idatt2106.group3.backend.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserControllerTest
{
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;
    @Mock
    private ActivityService activityService;

    @BeforeEach
    public void setUp() {
        User user = new User("Username", "Forename", "Surname", "test@test.com", "test hash", "test salt", 100, 4, "Organizer", 2);
        Activity activity = new Activity("Football", "A football", null, "Trondheim", "Dal", 50.30, 50.50, null, 60, false);

        List<Activity> activities = new ArrayList<>();
        activities.add(activity);

        ArgumentCaptor<User> argumentCaptorUser = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Long> argumentCaptorId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Activity> argumentCaptorActivity = ArgumentCaptor.forClass(Activity.class);

        Mockito.lenient()
                .when(userService.getUser(10))
                .thenReturn(user);

        Mockito.lenient()
                .when(userService.createUser(argumentCaptorUser.capture()))
                .thenReturn(true);

        Mockito.lenient()
                .when(userService.editUser(argumentCaptorId.capture(), argumentCaptorUser.capture()))
                .thenReturn(user);

        Mockito.lenient()
                .when(userService.deleteUser(argumentCaptorId.capture()))
                .thenReturn(true);

        Mockito.lenient()
                .when(activityService.getActivity(argumentCaptorId.capture()))
                .thenReturn(activity);

        Mockito.lenient()
                .when(userService.removeUserFromActivity(argumentCaptorId.capture(), argumentCaptorActivity.capture()))
                .thenReturn(true);
    }

    @Test
    public void getUser_ReturnUserAndStatus_StatusOk() {
        ResponseEntity<User> userResponseEntity = userController.getUser(10);
        User user = userResponseEntity.getBody();
        assertThat(user).isNotNull(); //nullpointer
        assertThat(userResponseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(user.getUsername()).isEqualTo("Username");
        assertThat(user.getForename()).isEqualTo("Forename");
        assertThat(user.getSurname()).isEqualTo("Surname");
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getHash()).isEqualTo("test hash");
        assertThat(user.getSalt()).isEqualTo("test salt");
        assertThat(user.getScore()).isEqualTo(100);
        assertThat(user.getRating()).isEqualTo(4);
        assertThat(user.getRole()).isEqualTo("Organizer");
        assertThat(user.getFaults()).isEqualTo(2);
    }

    @Test
    public void createUser_GetResponse_StatusCreated(@Mock User user) {
        assertThat(userController.createUser(user).getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void editUser_GetResponse_StatusOk(@Mock User user) {
        assertThat(userController.editUser(10, user).getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteUser_GetResponse_StatusOk() {
        assertThat(userController.deleteUser(10).getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getUserActivities_getActivitiesAndRespons_StatusOk() {
        ResponseEntity<Set<Activity>> activities = userController.getUserActivities(10);
        assertThat(activities.getStatusCodeValue()).isEqualTo(200);
        assertThat(activities.getBody()).isNotNull();
    }

    @Test
    public void removeUserFromActivity_GetResponse_StatusOk() {
        assertThat(userController.removeUserFromActivity(10, 1).getStatusCodeValue()).isEqualTo(200);
    }
}
