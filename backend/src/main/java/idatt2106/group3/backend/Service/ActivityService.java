package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ActivityService
{
    @Autowired
    private ActivityRepository activityRepository;

    public Activity getActivity(long activityId)
    {
        return activityRepository.getOne(activityId);
    }

    public List<Activity> getActivities()
    {
        return activityRepository.findAll();
    }

    public boolean createActivity(Activity activity)
    {
        Activity createdActivity = activityRepository.save(activity);
        return activityRepository.existsById(createdActivity.getActivityId());
    }

    public Activity editActivity(long activityId, Activity activity)
    {
        activity.setActivityId(activityId);
        return activityRepository.save(activity);
    }

    public boolean deleteActivity(long activityId)
    {
        activityRepository.deleteById(activityId);
        return !activityRepository.existsById(activityId);
    }

    public boolean addUserToActivity(long activityId, User user)
    {
        Set<User> users = activityRepository.getOne(activityId).getUsers();
        if (users == null) {
            return false;
        }
        users.add(user);

        activityRepository.getOne(activityId).setUsers(users);
        return true;

    }
}
