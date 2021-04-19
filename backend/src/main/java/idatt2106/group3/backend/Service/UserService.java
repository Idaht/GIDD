package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Model.DTO.UserDTO;
import idatt2106.group3.backend.Model.DTO.UserPasswordDTO;
import idatt2106.group3.backend.Repository.ActivityRepository;
import idatt2106.group3.backend.Repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ActivityRepository activityRepository;

    public User getUser(long userId)
    {
        LOGGER.info("getUser(long userId) called with userId: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) return user.get();
        return null;
    }

    public User createUser(UserPasswordDTO user)
    {
        //TODO: sende en melding at email eksisterer til frontend
        LOGGER.info("createUser(UserPasswordDTO user) called with email: {}", user.getEmail());
        if(userRepository.findUserByEmail(user.getEmail()).isPresent()) return null;
        User createdUser = new User();
        createdUser.setForename(user.getForename());
        createdUser.setSurname(user.getSurname());
        createdUser.setEmail(user.getEmail());
        createdUser.setHash(passwordEncoder.encode(user.getHash())); //encodes password on registration
        createdUser.setRole("ROLE_USER");
        return userRepository.save(createdUser);
    }

    public User editUser(long userId, UserDTO userDTO)
    {
        LOGGER.info("editUser(long userId, UserDTO userDTO) called with userId: {}", userId);
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setForename(userDTO.getForename());
            user.setSurname(userDTO.getSurname());
            user.setEmail(userDTO.getEmail());
            user.setScore(userDTO.getScore());
            user.setRating(userDTO.getRating());
            user.setRole(userDTO.getRole());
            return userRepository.save(user);
        }
        LOGGER.warn("Did not find user with userId: {}, while running editUser(long userId, UserDTO userDTO). Returning null", userId);
        return null;
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
        LOGGER.warn("Did not find user with userId: {}, while running getUserActivities(long userId). Returning empty set", userId);
        return Collections.emptySet();
    }

    /**
     * Removes a user from an activity. Returns true if successful, else false
     * @param userId
     * @param activityId
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
