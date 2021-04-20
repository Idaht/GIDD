package idatt2106.group3.backend.Web;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Model.DTO.RegistrationDTO;
import idatt2106.group3.backend.Model.DTO.UserDTO;
import idatt2106.group3.backend.Model.DTO.UserPasswordDTO;
import idatt2106.group3.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.Set;


@RestController
@RequestMapping("/api/v1/users")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/{user_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<User> getUser(@PathVariable("user_id") long userId) {
        User returnUser = userService.getUser(userId);
        if (returnUser == null)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(returnUser, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RegistrationDTO> createUser(@RequestBody UserPasswordDTO user) {
        RegistrationDTO createdUser = userService.createUser(user);
        if (createdUser != null)
        {
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{user_id}")
    @PreAuthorize("#userId == principal.userId or hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> editUser( @PathVariable("user_id") long userId, @RequestBody UserDTO userDTO) {
        User returnUser = userService.editUser(userId, userDTO);
        if (returnUser == null)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(returnUser, HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    @PreAuthorize("#userId == principal.userId or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("user_id") long userId) {
        if (userService.deleteUser(userId))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{user_id}/activities")
    @PreAuthorize("#userId == principal.userId or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Activity>> getUserActivities(@PathVariable("user_id") long userId) {
        Set<Activity> activities = userService.getUserActivities(userId);
        if (activities == null) //Om vi ikke får et faktisk objekt tilbake fra Service, antar feil har skjedd
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}/activities/{activity_id}")
    @PreAuthorize("#userId == principal.userId or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> removeUserFromActivity(@PathVariable("user_id") long userId, @PathVariable("activity_id") long activityId) {
        if (userService.removeUserFromActivity(userId, activityId))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
