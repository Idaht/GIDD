package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Configuration.Jwt.JwtSigningKey;
import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Model.DTO.RegistrationDTO;
import idatt2106.group3.backend.Model.DTO.UserDTO;
import idatt2106.group3.backend.Model.DTO.UserPasswordDTO;
import idatt2106.group3.backend.Repository.ActivityRepository;
import idatt2106.group3.backend.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
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

    public RegistrationDTO createUser(UserPasswordDTO user)
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
        createdUser = userRepository.save(createdUser);
        String token = createJWtToken(createdUser);

        
        return new RegistrationDTO(token, createdUser.getUserId(), user);
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
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            for(Activity activity : userOptional.get().getActivities()) {
                if(activity.getActivityId() == activityId) {
                    User user = userOptional.get();
                    user.getActivities().remove(activity);
                    userRepository.save(user);
                    return true;
                } 
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

    /**
     * Creates a JWT token for registration of user, POST api/v1/users
     * @return JWT token
     */
    private String createJWtToken(User user){
        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getRole()));
        return Jwts.builder().setSubject(user.getEmail()).claim("authorities", grantedAuthorities)
                .claim("userId", user.getUserId())
                .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(Keys.hmacShaKeyFor(JwtSigningKey.getInstance())).compact();
    }
}
