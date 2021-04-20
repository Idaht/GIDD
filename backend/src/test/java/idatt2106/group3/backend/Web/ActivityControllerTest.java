package idatt2106.group3.backend.Web;

import com.fasterxml.jackson.databind.ObjectMapper;
import idatt2106.group3.backend.Model.Activity;

import idatt2106.group3.backend.Model.Difficulty;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Repository.ActivityRepository;
import idatt2106.group3.backend.Repository.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ActivityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        Activity activity = new Activity("Playing", "Football", "A football", Difficulty.EASY, "Trondheim", "Dal", 50.30, 50.50, LocalDateTime.now(), 60, false);
        Activity activity1 = new Activity("Playing", "Football", "A football", Difficulty.EASY, "Trondheim", "Dal", 50.30, 50.50, LocalDateTime.now(), 60, false);
        Activity activity2 = new Activity("Playing", "Football", "A football", Difficulty.EASY, "Trondheim", "Dal", 50.30, 50.50, LocalDateTime.now(), 60, false);
        activityRepository.save(activity2);
        activityRepository.save(activity1);
        activityRepository.save(activity);
        
    }

    @AfterEach
    public void teardown() {
        userRepository.deleteAll();
        activityRepository.deleteAll();
    }

    @Test
    public void getActivity_ReturnFirstActivityAndStatus_StatusOk() throws Exception {
        long id = activityRepository.findAll().get(0).getActivityId();
        this.mockMvc.perform(get("/api/v1/activities/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", containsStringIgnoringCase("Playing")))
                .andExpect(jsonPath("$.description", containsStringIgnoringCase("Football")))
                .andExpect(jsonPath("$.equipment", containsStringIgnoringCase("A football")))
                .andExpect(jsonPath("$.difficulty", is("EASY")))
                .andExpect(jsonPath("$.city", containsStringIgnoringCase("Trondheim")))
                .andExpect(jsonPath("$.place", containsStringIgnoringCase("Dal")))
                .andExpect(jsonPath("$.longitude", is(50.30)))
                .andExpect(jsonPath("$.latitude", is(50.50)))
                //.andExpect(jsonPath("$.startTime", is(null)))
                .andExpect(jsonPath("$.durationMinutes", is(60)))
                .andExpect(jsonPath("$.privateActivity", is(false)))
                .andReturn();
    }

    @Test
    public void getActivities_ReturnListAndStatus_StatusOk() throws Exception {
        this.mockMvc.perform(get("/api/v1/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))))
                .andReturn();
    }

    @Test
    public void createActivity_PostActivity_StatusCreated() throws Exception
    {
        Activity activity = new Activity("Playing","Football", "A football", Difficulty.EASY, "Trondheim", "Dal", 50.30, 50.50, LocalDateTime.now(), 60, false);
        String activityJson = objectMapper.writeValueAsString(activity);

        this.mockMvc.perform(post("/api/v1/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(activityJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", containsStringIgnoringCase("Playing")))
                .andExpect(jsonPath("$.description", containsStringIgnoringCase("Football")))
                .andExpect(jsonPath("$.equipment", containsStringIgnoringCase("A football")))
                .andExpect(jsonPath("$.difficulty", is("EASY")))
                .andExpect(jsonPath("$.city", containsStringIgnoringCase("Trondheim")))
                .andExpect(jsonPath("$.place", containsStringIgnoringCase("Dal")))
                .andExpect(jsonPath("$.longitude", is(50.30)))
                .andExpect(jsonPath("$.latitude", is(50.50)))
                .andExpect(jsonPath("$.durationMinutes", is(60)))
                .andExpect(jsonPath("$.privateActivity", is(false)));
    }

    @Test
    public void editActivity_UpdateActivity_StatusOk() throws Exception
    {
        Activity activity = new Activity("Title", "Football and games1", "Two footballs", Difficulty.EASY, "Trondheim", "Dal", 50.30, 50.50, null, 60, false);
        String activityJson = objectMapper.writeValueAsString(activity);

        long id = activityRepository.findAll().get(2).getActivityId();
        this.mockMvc.perform(put("/api/v1/activities/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(activityJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", containsStringIgnoringCase("title")))
                .andExpect(jsonPath("$.description", containsStringIgnoringCase("Football and games1")))
                .andExpect(jsonPath("$.equipment", containsStringIgnoringCase("Two footballs")))
                .andExpect(jsonPath("$.difficulty", is("EASY")))
                .andExpect(jsonPath("$.city", containsStringIgnoringCase("Trondheim")))
                .andExpect(jsonPath("$.place", containsStringIgnoringCase("Dal")))
                .andExpect(jsonPath("$.longitude", is(50.30)))
                .andExpect(jsonPath("$.latitude", is(50.50)))
                .andExpect(jsonPath("$.durationMinutes", is(60)))
                .andExpect(jsonPath("$.privateActivity", is(false)))
                .andReturn();

        this.mockMvc.perform(get("/api/v1/activities/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", containsStringIgnoringCase("title")))
                .andExpect(jsonPath("$.description", containsStringIgnoringCase("Football and games1")))
                .andExpect(jsonPath("$.equipment", containsStringIgnoringCase("Two footballs")))
                .andExpect(jsonPath("$.difficulty", is("EASY")))
                .andExpect(jsonPath("$.city", containsStringIgnoringCase("Trondheim")))
                .andExpect(jsonPath("$.place", containsStringIgnoringCase("Dal")))
                .andExpect(jsonPath("$.longitude", is(50.30)))
                .andExpect(jsonPath("$.latitude", is(50.50)))
                .andExpect(jsonPath("$.durationMinutes", is(60)))
                .andExpect(jsonPath("$.privateActivity", is(false)))
                .andReturn();
    }

    @Test
    public void deleteActivity_ShouldDeleteActivity_StatusOk() throws Exception //Brude fungere, men får ikke testet da ingen entitet med id 0 eksisterer
    {
        long id = activityRepository.findAll().get(1).getActivityId();
        this.mockMvc.perform(delete("/api/v1/activities/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void addUserToActivity_ExistingUserAdded_StatusCreated() throws Exception
    {
        User user1 = new User("Forename", "Surname", "test@test.com", "test hash", "test salt", 100, 4, "Organizer", 2, null);
        userRepository.save(user1);
        long userId = userRepository.findAll().get(0).getUserId();
        long activityId = activityRepository.findAll().get(0).getActivityId();
        this.mockMvc.perform(post("/api/v1/activities/" + activityId + "/users/" + userId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.forename", containsStringIgnoringCase("Forename")))
                .andExpect(jsonPath("$.surname", containsStringIgnoringCase("Surname")))
                .andExpect(jsonPath("$.email", containsStringIgnoringCase("test@test.com")))
                .andExpect(jsonPath("$.score", is(100)))
                .andExpect(jsonPath("$.rating", is(4)))
                .andExpect(jsonPath("$.role", containsStringIgnoringCase("Organizer")));
    }
}