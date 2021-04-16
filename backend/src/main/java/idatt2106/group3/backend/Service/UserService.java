package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Repository.ActivityRepository;
import idatt2106.group3.backend.Repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityRepository activityRepository;

    public User getUser(long userId)
    {
        LOGGER.info("getUser(long userId) called with userId: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) return user.get();
        return null;
    }

    public boolean createUser(User user)
    {
        LOGGER.info("createUser(User user) called with userId: {}", user.getUserId());
        User createdUser = userRepository.save(user);
        return userRepository.existsById(createdUser.getUserId());
    }

    public User editUser(long userId, User user)
    {
        LOGGER.info("editUser(long userId, User user) called with userId: {}", userId);
        user.setUserId(userId);
        return userRepository.save(user);
    }

    public boolean deleteUser(long userId)
    {
        LOGGER.info("deleteUser(long userId) called with userId: {}", userId);
        userRepository.deleteById(userId);
        return !userRepository.existsById(userId);
    }

    public Set<Activity> getUserActivities(long userId)
    {
        LOGGER.info("getUserActivities(long userId) called with userId: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) return user.get().getActivities();
        return null;
    }

    /**
     * Removes a user from an activity. Returns true if successful, else false
     * @param userId
     * @param activity
     * @return
     */
    public boolean removeUserFromActivity(long userId, long activityId)
    {
        LOGGER.info("removeUserFromActivity(long userId, long activityId) called with userId: {}, and activityId: {}", userId, activityId);
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            for(Activity activity : user.get().getActivities()) {
                if(activity.getActivityId() == activityId) return user.get().getActivities().remove(activity);
            }
        }
        return false;
    }

    /**
     * Adds a user to an activity. Returns true if successful, else false
     * @param userId
     * @param activityId
     * @return
     */
    public Activity addUserToActivity(long userId, long activityId){
        LOGGER.info("addUserToActivity(long userId, long activityId) called with userId: {}, and activityId: {}", userId, activityId);
        Optional<User> user = userRepository.findById(userId);
        Optional<Activity> activity = activityRepository.findById(activityId);
        if(user.isPresent() && activity.isPresent()){
            User temp = user.get();
            temp.getActivities().add(activity.get());
            userRepository.save(temp);
            return activity.get();
        }
        return null;
    }
}
