package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.Chat;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Model.UserSecurityDetails;
import idatt2106.group3.backend.Model.DTO.Activity.AbsenceDTO;
import idatt2106.group3.backend.Model.DTO.Activity.ActivityDTO;
import idatt2106.group3.backend.Model.DTO.Activity.ActivityRegistrationDTO;
import idatt2106.group3.backend.Repository.ActivityRepository;
import idatt2106.group3.backend.Repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ActivityService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;

    public ActivityDTO getActivity(long activityId)
    {
        LOGGER.info("getActivity(long activityId) called with activityId: {}", activityId); 
        Optional<Activity> activity = activityRepository.findById(activityId);
        if(activity.isPresent()) return new ActivityDTO(activity.get());
        return null;
    }

    public List<ActivityDTO> getActivities()
    {
        LOGGER.info("getActivities() called");
        return activityRepository.findAll().stream().map(activity -> new ActivityDTO(activity)).collect(Collectors.toList());
    }

    public ActivityDTO createActivity(ActivityRegistrationDTO activity)
    {
        LOGGER.info("createActivity(Activity activity) called. Activity Title: {}", activity.getTitle());
        // To get currently logged in user
        UserSecurityDetails creatorUser = (UserSecurityDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Finds this user
        Optional<User> optionalUser = userRepository.findById(creatorUser.getUserId());
        // Saves User with organizer, and returns a ActivityDTO object
        if(optionalUser.isPresent()) return new ActivityDTO(activityRepository.save(new Activity(activity, optionalUser.get())));

        return null;
    }

    public ActivityDTO editActivity(long activityId, ActivityRegistrationDTO activityRegDTO)
    {
        LOGGER.info("editActivity(long activityId, Activity activity) called with activityId: {}", activityId);
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        if(optionalActivity.isPresent()){
            Activity activity = optionalActivity.get();
            activity.setTitle(activityRegDTO.getTitle());
            activity.setType(activityRegDTO.getType());
            activity.setDescription(activityRegDTO.getDescription());
            activity.setEquipment(activityRegDTO.getEquipment());
            activity.setDifficulty(activityRegDTO.getDifficulty());
            activity.setCity(activityRegDTO.getCity());
            activity.setPlace(activityRegDTO.getPlace());
            activity.setLongitude(activityRegDTO.getLongitude());
            activity.setLatitude(activityRegDTO.getLatitude());
            activity.setStartTime(activityRegDTO.getStartTime());
            activity.setDurationMinutes(activityRegDTO.getDurationMinutes());
            activity.setPrivateActivity(activityRegDTO.isPrivateActivity());
            activity.setMaxParticipants(activityRegDTO.getMaxParticipants());
            return new ActivityDTO(activityRepository.save(activity));
        }
        return null;
    }

    public boolean deleteActivity(long activityId)
    {
        LOGGER.info("deleteActivity(long activityId) called with activityId: {}", activityId);
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        if(optionalActivity.isPresent()){
            Activity activity = optionalActivity.get();
            List<User> activityUsers = new ArrayList<>();
            for(User user : activity.getUsers()){
                user.getActivities().remove(activity);
                activityUsers.add(user);
            }
            userRepository.saveAll(activityUsers);
            activityRepository.save(activity);
            activityRepository.deleteById(activityId);
            return !activityRepository.existsById(activityId);
        }
        return false;
    }

    public boolean addUserToActivity(long activityId, long userId)
    {
        LOGGER.info("addUserToActivity(long activityId) called with activityId: {}, and userId: {}", activityId, userId);

        Optional<Activity> activityOptional = activityRepository.findById(activityId);
        if(!activityOptional.isPresent()) {
            LOGGER.warn("Did not find activity with activityId: {}. Returning false", activityId);
            return false;
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()) {
            LOGGER.warn("Did not find user with userId: {}. Returning false", userId);
            return false;
        }
        User user = userOptional.get();

        Set<Activity> activities = user.getActivities();
        if (activities == null) {
            return false;
        }
        Activity activity = activityOptional.get();
        user.getActivities().add(activity);

        userRepository.save(user);
        return true;
    }

    public Chat getChat(long activityId){
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        if(optionalActivity.isPresent()){
            return optionalActivity.get().getChat();
        }
        return null;
    }

    public boolean checkIfOrganizerOfActivity(long activityId, long userId){
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        if(optionalActivity.isPresent()){
            return optionalActivity.get().getOrganizer().getUserId() == userId;
        }
        return false;
    }

    /**
     * Method that increases "absence" counter for users that were absent in an activity.
     * Also sets activity's markedAbsence boolean to true, so that we know that that activity
     * already has marked user absences.
     * @param activityId
     * @param absenceDTO
     * @return a set of UserIds of users that were absent. If it fails, returns an empty HashSet.
     */
    public Set<Long> markAbsent(long activityId, AbsenceDTO absenceDTO) {
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        if(optionalActivity.isPresent()){
            Set<Long> absentUsersId = absenceDTO.getAbsentUsersId();
            List<User> users = userRepository.findAllById(absentUsersId);
            for(User user : users) {
                user.setAbsence(user.getAbsence() + 1);
            }
            Activity activity = optionalActivity.get();
            activity.setMarkedAbsence(true);

            return absentUsersId;
        }
        return new HashSet<>();
    }
}
