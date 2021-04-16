package idatt2106.group3.backend.Web;

import com.fasterxml.jackson.databind.ObjectMapper;
import idatt2106.group3.backend.Model.Activity;

import idatt2106.group3.backend.Model.Difficulty;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Repository.ActivityRepository;
import idatt2106.group3.backend.Repository.UserRepository;

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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ActivityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ActivityRepository activityRepo;
    @Autowired
    private UserRepository userRepo;

    @Before
    public void setup(){
        Activity activity = new Activity("Football", "A football", Difficulty.EASY, "Trondheim", "Dal", 50.30, 50.50, LocalDateTime.now(), 60, false);
        Activity activity1 = new Activity("Football", "A football", Difficulty.EASY, "Trondheim", "Dal", 50.30, 50.50, LocalDateTime.now(), 60, false);
        Activity activity2 = new Activity("Football", "A football", Difficulty.EASY, "Trondheim", "Dal", 50.30, 50.50, LocalDateTime.now(), 60, false);
        User user1 = new User("Forename", "Surname", "test@test.com", "test hash", "test salt", 100, 4, "Organizer", 2);
        activityRepo.save(activity2);
        activityRepo.save(activity1);
        activityRepo.save(activity);
        userRepo.save(user1);
    }

    @Test
    public void getActivity_ReturnFirstActivityAndStatus_StatusOk() throws Exception {
        this.mockMvc.perform(get("/api/v1/activities/1"))
                .andExpect(status().isOk())
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
        Activity activity = new Activity("Football", "A football", Difficulty.EASY, "Trondheim", "Dal", 50.30, 50.50, LocalDateTime.now(), 60, false);
        String activityJson = objectMapper.writeValueAsString(activity);

        this.mockMvc.perform(post("/api/v1/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(activityJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", containsStringIgnoringCase("Football")))
                .andExpect(jsonPath("$.equipment", containsStringIgnoringCase("A football")))
                // .andExpect(jsonPath("$.difficulty", is(Difficulty.EASY)))
                .andExpect(jsonPath("$.city", containsStringIgnoringCase("Trondheim")))
                .andExpect(jsonPath("$.place", containsStringIgnoringCase("Dal")))
                .andExpect(jsonPath("$.longitude", is(50.30)))
                .andExpect(jsonPath("$.latitude", is(50.50)))
                //.andExpect(jsonPath("$.startTime", is(null)))
                .andExpect(jsonPath("$.durationMinutes", is(60)))
                .andExpect(jsonPath("$.privateActivity", is(false)));
    }

    @Test
    public void editActivity_UpdateActivity_StatusOk() throws Exception
    {
        Activity activity = new Activity("Football and games1", "Two footballs", Difficulty.EASY, "Trondheim", "Dal", 50.30, 50.50, null, 60, false);
        String activityJson = objectMapper.writeValueAsString(activity);

        this.mockMvc.perform(put("/api/v1/activities/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(activityJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", containsStringIgnoringCase("Football and games1")))
                .andExpect(jsonPath("$.equipment", containsStringIgnoringCase("Two footballs")))
                .andExpect(jsonPath("$.difficulty", is("EASY")))
                .andExpect(jsonPath("$.city", containsStringIgnoringCase("Trondheim")))
                .andExpect(jsonPath("$.place", containsStringIgnoringCase("Dal")))
                .andExpect(jsonPath("$.longitude", is(50.30)))
                .andExpect(jsonPath("$.latitude", is(50.50)))
                //.andExpect(jsonPath("$.startTime", is(null)))
                .andExpect(jsonPath("$.durationMinutes", is(60)))
                .andExpect(jsonPath("$.privateActivity", is(false)))
                .andReturn();

        this.mockMvc.perform(get("/api/v1/activities/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", containsStringIgnoringCase("Football and games1")))
                .andExpect(jsonPath("$.equipment", containsStringIgnoringCase("Two footballs")))
                .andExpect(jsonPath("$.difficulty", is("EASY")))
                .andExpect(jsonPath("$.city", containsStringIgnoringCase("Trondheim")))
                .andExpect(jsonPath("$.place", containsStringIgnoringCase("Dal")))
                .andExpect(jsonPath("$.longitude", is(50.30)))
                .andExpect(jsonPath("$.latitude", is(50.50)))
                // .andExpect(jsonPath("$.startTime", is(null)))
                .andExpect(jsonPath("$.durationMinutes", is(60)))
                .andExpect(jsonPath("$.privateActivity", is(false)))
                .andReturn();
    }

    @Test
    public void deleteActivity_ShouldDeleteActivity_StatusOk() throws Exception //Brude fungere, men får ikke testet da ingen entitet med id 0 eksisterer
    {
        this.mockMvc.perform(delete("/api/v1/activities/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void addUserToActivity_ExistingUserAdded_StatusCreated() throws Exception
    {
        this.mockMvc.perform(post("/api/v1/activities/1/users/1"))
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
                .andExpect(jsonPath("$.faults", is(2)));
    }
}