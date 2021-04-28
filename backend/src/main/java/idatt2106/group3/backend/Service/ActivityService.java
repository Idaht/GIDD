package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Component.EmailComponent;
import idatt2106.group3.backend.Enum.SortingType;
import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.Chat;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Model.UserSecurityDetails;
import idatt2106.group3.backend.Model.DTO.SortFilterQueryDTO;
import idatt2106.group3.backend.Model.DTO.Activity.AbsenceDTO;
import idatt2106.group3.backend.Model.DTO.Activity.ActivityDTO;
import idatt2106.group3.backend.Model.DTO.Activity.ActivityRegistrationDTO;
import idatt2106.group3.backend.Model.DTO.User.UserDTO;
import idatt2106.group3.backend.Repository.ActivityRepository;
import idatt2106.group3.backend.Repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Service
public class ActivityService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired(required = false)
    private EmailComponent emailSender;

    /**
     * Returns activity from activityId stored in the database
     * @param activityId
     * @return ActivityDTO object
     */
    public ActivityDTO getActivity(long activityId)
    {
        LOGGER.info("getActivity(long activityId) called with activityId: {}", activityId); 
        Optional<Activity> activity = activityRepository.findById(activityId);
        if(activity.isPresent()) return new ActivityDTO(activity.get());
        return null;
    }

    /**
     * Returns all activities stored in the database
     * @return List of activities
     */
    public List<ActivityDTO> getActivities()
    {
        LOGGER.info("getActivities() called");
        return activityRepository.findAll().stream().map(activity -> new ActivityDTO(activity)).collect(Collectors.toList());
    }

    /**
     * Returns a sorted or filtered list from what the user wants
     * @param filter
     * @return List of ActivityDTO objects
     */
    public List<ActivityDTO> getActivitiesWithFilterAndSorting(SortFilterQueryDTO filter){
        LOGGER.info("getActivitiesWithFilterAndSorting(SortFilterQueryDTO) called with amount: {} sortQuery: {}",filter.getAmount(), filter.getSearchQuery());
        String searchQuery = "%" + filter.getSearchQuery() + "%";
        List<Activity> list;
        list = sortAndFilter(filter, searchQuery);
        return list.stream().map(activity -> new ActivityDTO(activity)).collect(Collectors.toList());
    }

    /**
     * Finds the user who created the activity, and sets it as the organizer
     * Saves the Activity object in the database
     * @param activity
     * @return ActivityDTO object
     */
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

    /**
     * Finds the activity from id and changes all given fields in activityRegDTO
     * @param activityId
     * @param activityRegDTO input DTO object
     * @return ActivityDTO object
     */
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

    /**
     * Finds activity from id, removes and sends cancelation mails to all participants, and deletes the activity from the database
     * @param activityId
     * @return if deletion was successful
     */
    public boolean deleteActivity(long activityId)
    {
        LOGGER.info("deleteActivity(long activityId) called with activityId: {}", activityId);
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        if(optionalActivity.isPresent()){
            Activity activity = optionalActivity.get();

            // Should not be able to delete activity after it is finished
            if(activity.getStartTime().plusMinutes(activity.getDurationMinutes()).isBefore(LocalDateTime.now())) {
                return false;
            }

            List<User> activityUsers = new ArrayList<>();
            for(User user : activity.getUsers()){
                user.getActivities().remove(activity);
                activityUsers.add(user);
                emailSender.sendCancelationMail(user, activity);
            }
            userRepository.saveAll(activityUsers);
            activityRepository.save(activity);
            activityRepository.deleteById(activityId);
            return !activityRepository.existsById(activityId);
        }
        return false;
    }

    /**
     * Adds a user to an activity, user becomes a participant
     * Checks if both are stored in database
     * @param activityId
     * @param userId
     * @return boolean
     */
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

    /**
     * Returns activity Chat object
     * @param activityId
     * @return Chat object
     */
    public Chat getChat(long activityId){
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        if(optionalActivity.isPresent()){
            return optionalActivity.get().getChat();
        }
        return null;
    }

    /**
     * Returns a boolean value if a user is the organizer of the activity
     * @param activityId
     * @param userId
     * @return boolean
     */
    public boolean checkIfOrganizerOfActivity(long activityId, long userId){
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        if(optionalActivity.isPresent()){
            return optionalActivity.get().getOrganizer().getUserId() == userId;
        }
        return false;
    }

    /**
     * Returns boolean based on if the user is a participant of the given AcitivityId
     * @param activityId
     * @param userId
     * @return true or false
     */
    public boolean checkIfParticipant(long activityId, long userId){
        Optional<Integer> optionalActivityId = activityRepository.findIfUserIsParticipantOfActivity(userId, activityId); 
        return optionalActivityId.isPresent();
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

    /**
     * Returns all participants of the given activity
     * @param activityId
     * @return Set of UsersDTO objects
     */
    @JsonIgnoreProperties(value = {"userId", "role", "profilePicture", "trusted", "email", "dateOfBirth", "trainingLevel", "role", "profilePicture"})
    public Set<UserDTO> getUsers(long activityId)
    {
        LOGGER.info("getUsers(long activityId) called with activityId: {}", activityId); 
        Optional<Activity> activityOptional = activityRepository.findById(activityId);
        if(activityOptional.isPresent()) {
            Activity activity = activityOptional.get();
            Set<User> users = activity.getUsers();
            Set<UserDTO> userDTOs = new HashSet<>();
            for(User user : users) {
                userDTOs.add(new UserDTO(user));
            }
            return userDTOs;
        }
        return new HashSet<>();
    }

    /**
     * Returns a list from Repository depending on DTO from frontend
     * @param filter SortFilterQueryDTO input
     * @param searchQuery title and description search substring
     * @return List of activities
     */
    private List<Activity> sortAndFilter(SortFilterQueryDTO filter, String searchQuery){
        
        if(filter.getDifficulty() == null && filter.getSortingType() == SortingType.DATE) 
            return activityRepository.findActivitiesOnDateWithoutFilter(searchQuery, filter.getAmount());
        else if(filter.getDifficulty() != null && filter.getSortingType() == SortingType.DATE) 
            return activityRepository.findActivitiesOnDateWithFilter(searchQuery, filter.getAmount(), filter.getDifficulty());
        else if(filter.getDifficulty() == null && filter.getSortingType() == SortingType.PARTICIPANT_AMOUNT) 
            return activityRepository.findActivitiesOnAmountWithoutFilter(searchQuery, filter.getAmount());
        else if(filter.getDifficulty() != null && filter.getSortingType() == SortingType.PARTICIPANT_AMOUNT) 
            return activityRepository.findActivitiesOnAmountWithFilter(searchQuery, filter.getAmount(), filter.getDifficulty());
        else if(filter.getDifficulty() == null && filter.getSortingType() == SortingType.DISTANCE)
            return activityRepository.findActivitiesOnDistanceWithoutFilter(searchQuery, filter.getAmount(), filter.getUserLongitude(), filter.getUserLatitude());
        else if(filter.getDifficulty() != null && filter.getSortingType() == SortingType.DISTANCE)
            return activityRepository.findActivitiesOnDistanceWithFilter(searchQuery, filter.getAmount(), filter.getUserLongitude(), filter.getUserLatitude(), filter.getDifficulty());

        return activityRepository.findAll();
    }
}
