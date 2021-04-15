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

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ActivityControllerTest {
    @InjectMocks
    private ActivityController activityController;

    @Mock
    private ActivityService activityService;
    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        Activity activity = new Activity("Football", "A football", null, "Trondheim", "Dal", 50.30, 50.50, null, 60, false),
        activity1 = new Activity("Hockey", "A puck", null, "Trondheim", "Banen", 50.25, 50.78, null, 80, true);
        User user = new User("Username", "Forename", "Surname", "test@test.com", "test hash", "test salt", 100, 4, "Organizer", 2);

        List<Activity> activities = new ArrayList<>();
        activities.add(activity);
        activities.add(activity1);

        ArgumentCaptor<Activity> argumentCaptorActivity = ArgumentCaptor.forClass(Activity.class);
        ArgumentCaptor<Long> argumentCaptorId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<User> argumentCaptorUser = ArgumentCaptor.forClass(User.class);

        Mockito.lenient()
                .when(activityService.getActivity(argumentCaptorId.capture()))
                .thenReturn(activity);

        Mockito.lenient()
                .when(activityService.getActivities()).thenReturn(activities);

        Mockito.lenient()
                .when(activityService.createActivity(argumentCaptorActivity.capture()))
                .thenReturn(true);

        Mockito.lenient()
                .when(activityService.editActivity(argumentCaptorId.capture(), argumentCaptorActivity.capture()))
                .thenReturn(activity);

        Mockito.lenient()
                .when(activityService.deleteActivity(argumentCaptorId.capture()))
                .thenReturn(true);

        Mockito.lenient()
                .when(userService.getUser(argumentCaptorId.capture())).thenReturn(user);

        Mockito.lenient()
                .when(activityService.addUserToActivity(argumentCaptorId.capture(), argumentCaptorUser.capture()))
                .thenReturn(true);
    }

    @Test
    public void getActivity_ReturnActivityAndStatus_StatusOk() {
        ResponseEntity<Activity> activityResponseEntity = activityController.getActivity(10);
        Activity activity = activityResponseEntity.getBody();
        assertThat(activity).isNotNull();
        assertThat(activityResponseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(activity.getDescription()).isEqualTo("Football");
        assertThat(activity.getEquipment()).isEqualTo("A football");
        assertThat(activity.getDifficulty()).isEqualTo(null);
        assertThat(activity.getCity()).isEqualTo("Trondheim");
        assertThat(activity.getPlace()).isEqualTo("Dal");
        assertThat(activity.getLongitude()).isEqualTo(50.30);
        assertThat(activity.getLatitude()).isEqualTo(50.50);
        //assertThat(activity.getStartTime()).isEqualTo(null); //litt vanskelig å teste denne
        assertThat(activity.getDurationMinutes()).isEqualTo(60);
        assertThat(activity.isPrivateActivity()).isEqualTo(false);
    }

    @Test
    public void getActivities_ReturnListAndStatus_StatusOk() {
        ResponseEntity<List<Activity>> activities = activityController.getActivities();
        assertThat(activities.getStatusCodeValue()).isEqualTo(200);
        assertThat(activities.getBody()).isNotNull();
    }

    @Test
    public void createActivity_GetResponse_StatusCreated(@Mock Activity activity) {
        assertThat(activityController.createActivity(activity).getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void editActivity_GetResponse_StatusOk(@Mock Activity activity) {
        assertThat(activityController.editActivity(10, activity).getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteActivity_GetResponse_StatusOk() {
        assertThat(activityController.deleteActivity(10).getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void addUserToActivity_UserAdded_StatusCreated() {
        assertThat(activityController.addUserToActivity(10, 1).getStatusCodeValue()).isEqualTo(201);
    }
}