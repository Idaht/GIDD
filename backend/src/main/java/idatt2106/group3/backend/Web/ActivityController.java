package idatt2106.group3.backend.Web;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.Chat;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Service.ActivityService;
import idatt2106.group3.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController
{
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @GetMapping("/{activity_id}")
    public ResponseEntity<Activity> getActivity(@PathVariable("activity_id") long activityId) {
        Activity returnActivity = activityService.getActivity(activityId);
        if (returnActivity == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(returnActivity, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getActivities() {
        List<Activity> activities = activityService.getActivities();
        if (activities == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        if (activityService.createActivity(activity)) {
            return new ResponseEntity<>(activity, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{activity_id}")
    public ResponseEntity<Activity> editActivity(@PathVariable("activity_id") long activityId, @RequestBody Activity activity) {
        Activity returnActivity = activityService.editActivity(activityId, activity);
        if (returnActivity == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(returnActivity,HttpStatus.OK);
    }

    @DeleteMapping("/{activity_id}")
    public ResponseEntity<String> deleteActivity(@PathVariable("activity_id") long activityId) {
        if (activityService.deleteActivity(activityId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/{activity_id}/users/{user_id}")
    public ResponseEntity<User> addUserToActivity(@PathVariable("activity_id") long activityId, @PathVariable("user_id") long userId) {
        if (activityService.addUserToActivity(activityId, userService.getUser(userId))) {
            return new ResponseEntity<>(userService.getUser(userId),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{activity_id}/chat")
    public ResponseEntity<Chat> getActivityChat(@PathVariable("activity_id") long activityId) {
        Chat returnChat = activityService.getActivity(activityId).getChat();
        if (returnChat == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(returnChat, HttpStatus.OK);
    }
}
