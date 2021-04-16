package idatt2106.group3.backend.Web;

import com.fasterxml.jackson.databind.ObjectMapper;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.Difficulty;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Repository.ActivityRepository;
import idatt2106.group3.backend.Repository.UserRepository;
import idatt2106.group3.backend.Service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserService userService;

    @Before
    public void setup()
    {
        Activity activity1 = new Activity("Football", "A football", Difficulty.EASY, "Trondheim", "Dal", 50.30, 50.50, LocalDateTime.now(), 60, false);
        User user1 = new User("Username1", "1Forename", "1Surname", "test@test.com", "test hash", "test salt", 100, 4, "Organizer", 2);
        User user2 = new User("Username1", "1Forename", "1Surname", "test@test.com", "test hash", "test salt", 100, 4, "Organizer", 2);
        User user3 = new User("Username1", "1Forename", "1Surname", "test@test.com", "test hash", "test salt", 100, 4, "Organizer", 2);
        activityRepository.save(activity1);
        userRepository.save(user1);
        userService.addUserToActivity(1, 1);
        userRepository.save(user2);
        userRepository.save(user3);
    }


    @Test
    public void getUser_ReturnFirstUserAndStatus_StatusOk() throws Exception
    {
        this.mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", containsStringIgnoringCase("Username")))
                .andExpect(jsonPath("$.forename", containsStringIgnoringCase("Forename")))
                .andExpect(jsonPath("$.surname", containsStringIgnoringCase("Surname")))
                .andExpect(jsonPath("$.email", containsStringIgnoringCase("test@test.com")))
                .andExpect(jsonPath("$.hash", containsStringIgnoringCase("test hash")))
                .andExpect(jsonPath("$.salt", containsStringIgnoringCase("test salt")))
                .andExpect(jsonPath("$.score", is(100)))
                .andExpect(jsonPath("$.rating", is(4)))
                .andExpect(jsonPath("$.role", containsStringIgnoringCase("Organizer")))
                .andExpect(jsonPath("$.faults", is(2)))
                .andReturn();
    }

    @Test
    public void createUser_PostUserGetResponse_StatusCreated() throws Exception
    {

        User user = new User("Username", "Forename", "Surname", "test@test.com", "test hash", "test salt", 100, 4, "Organizer", 2);

        String userJson = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", containsStringIgnoringCase("Username")))
                .andExpect(jsonPath("$.forename", containsStringIgnoringCase("Forename")))
                .andExpect(jsonPath("$.surname", containsStringIgnoringCase("Surname")))
                .andExpect(jsonPath("$.email", containsStringIgnoringCase("test@test.com")))
                .andExpect(jsonPath("$.hash", containsStringIgnoringCase("test hash")))
                .andExpect(jsonPath("$.salt", containsStringIgnoringCase("test salt")))
                .andExpect(jsonPath("$.score", is(100)))
                .andExpect(jsonPath("$.rating", is(4)))
                .andExpect(jsonPath("$.role", containsStringIgnoringCase("Organizer")))
                .andExpect(jsonPath("$.faults", is(2)))
                .andReturn();
    }

    @Test
    public void editUser_UpdateUserGetResponse_StatusOk() throws Exception
    {
        User user = new User("Username1", "Forename1", "Surname1", "test1@test1.com", "test1 hash", "test1 salt", 100, 4, "Organizer1", 2);
        String userJson = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(put("/api/v1/users/2")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", containsStringIgnoringCase("Username1")))
                .andExpect(jsonPath("$.forename", containsStringIgnoringCase("Forename1")))
                .andExpect(jsonPath("$.surname", containsStringIgnoringCase("Surname1")))
                .andExpect(jsonPath("$.email", containsStringIgnoringCase("test1@test1.com")))
                .andExpect(jsonPath("$.hash", containsStringIgnoringCase("test1 hash")))
                .andExpect(jsonPath("$.salt", containsStringIgnoringCase("test1 salt")))
                .andExpect(jsonPath("$.score", is(100)))
                .andExpect(jsonPath("$.rating", is(4)))
                .andExpect(jsonPath("$.role", containsStringIgnoringCase("Organizer1")))
                .andExpect(jsonPath("$.faults", is(2)))
                .andReturn();

        this.mockMvc.perform(get("/api/v1/users/2"))
                .andExpect(jsonPath("$.username", containsStringIgnoringCase("Username1")))
                .andExpect(jsonPath("$.forename", containsStringIgnoringCase("Forename1")))
                .andExpect(jsonPath("$.surname", containsStringIgnoringCase("Surname1")))
                .andExpect(jsonPath("$.email", containsStringIgnoringCase("test1@test1.com")))
                .andExpect(jsonPath("$.hash", containsStringIgnoringCase("test1 hash")))
                .andExpect(jsonPath("$.salt", containsStringIgnoringCase("test1 salt")))
                .andExpect(jsonPath("$.score", is(100)))
                .andExpect(jsonPath("$.rating", is(4)))
                .andExpect(jsonPath("$.role", containsStringIgnoringCase("Organizer1")))
                .andExpect(jsonPath("$.faults", is(2)))
                .andReturn();
    }

    @Test
    public void deleteUser_ShouldDeleteUser_StatusOk() throws Exception
    {
        this.mockMvc.perform(delete("/api/v1/users/3"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserActivities_getActivitiesAndRespons_StatusOk() throws Exception
    {
        this.mockMvc.perform(get("/api/v1/users/1/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].description", containsStringIgnoringCase("Football")))
                .andExpect(jsonPath("$.[0].equipment", containsStringIgnoringCase("A football")))
                .andExpect(jsonPath("$.[0].difficulty", is("EASY")))
                .andExpect(jsonPath("$.[0].city", containsStringIgnoringCase("Trondheim")))
                .andExpect(jsonPath("$.[0].place", containsStringIgnoringCase("Dal")))
                .andExpect(jsonPath("$.[0].longitude", is(50.30)))
                .andExpect(jsonPath("$.[0].latitude", is(50.50)))
                // .andExpect(jsonPath("$.[0].startTime", is(null)))
                .andExpect(jsonPath("$.[0].durationMinutes", is(60)))
                .andExpect(jsonPath("$.[0].privateActivity", is(false)))
                .andReturn();
    }

    @Test
    public void removeUserFromActivity_ShouldRemoveUserAndGetResponse_StatusOk() throws Exception
    {
        this.mockMvc.perform(delete("/api/v1/users/1/activities/1"))
                .andExpect(status().isOk());
    }
}
