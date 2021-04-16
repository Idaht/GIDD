package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Repository.ActivityRepository;
import idatt2106.group3.backend.Repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ActivityService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;

    public Activity getActivity(long activityId)
    {
        LOGGER.info("getActivity(long activityId) called with activityId: {}", activityId); 
        Optional<Activity> activity = activityRepository.findById(activityId);
        if(activity.isPresent()) return activity.get();
        return null;
    }

    public List<Activity> getActivities()
    {
        LOGGER.info("getActivities() called");
        return activityRepository.findAll();
    }

    public boolean createActivity(Activity activity)
    {
        LOGGER.info("createActivity(Activity activity) called. Activity Id: {}", activity.getActivityId());
        Activity createdActivity = activityRepository.save(activity);
        return activityRepository.existsById(createdActivity.getActivityId());
    }

    public Activity editActivity(long activityId, Activity activity)
    {
        LOGGER.info("editActivity(long activityId, Activity activity) called with activityId: {}", activityId);
        activity.setActivityId(activityId);
        return activityRepository.save(activity);
    }

    public boolean deleteActivity(long activityId)
    {
        LOGGER.info("deleteActivity(long activityId) called with activityId: {}", activityId);
        activityRepository.deleteById(activityId);
        return !activityRepository.existsById(activityId);
    }

    public boolean addUserToActivity(long activityId, User user)
    {
        LOGGER.info("addUserToActivity(long activityId, User user) called with activityId: {}, and userId: {}", activityId, user.getUserId());
        Optional<Activity> activity = activityRepository.findById(activityId);
        if(!activity.isPresent()) return false;
        Set<Activity> activities = user.getActivities();
        if (activities == null) {
            return false;
        }
        user.getActivities().add(activity.get());


        userRepository.save(user);
        return true;
    }
}
