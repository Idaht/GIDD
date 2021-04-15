package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public User getUser(long userId)
    {
        return userRepository.getOne(userId);
    }

    public boolean createUser(User user)
    {
        User createdUser = userRepository.save(user);

        return userRepository.existsById(createdUser.getUserId());
    }

    public User editUser(long userId, User user)
    {
        user.setUserId(userId);
        return userRepository.save(user);
    }

    public boolean deleteUser(long userId)
    {
        userRepository.deleteById(userId);
        return !userRepository.existsById(userId);

    }

    public Set<Activity> getUserActivities(long userId)
    {
        return userRepository.getOne(userId).getActivities();
    }

    /**
     * Removes a user from an activity. Returns true if successful, else false
     * @param userId
     * @param activity
     * @return
     */
    public boolean removeUserFromActivity(long userId, Activity activity)
    {
        return userRepository.getOne(userId).getActivities().remove(activity);
    }
}
