package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Configuration.Jwt.JwtSigningKey;
import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Model.DTO.User.UserRegistrationCallbackDTO;
import idatt2106.group3.backend.Model.DTO.User.UserDTO;
import idatt2106.group3.backend.Model.DTO.User.UserEditDTO;
import idatt2106.group3.backend.Model.DTO.User.UserWithPasswordDTO;
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

    public UserDTO getUser(long userId)
    {
        LOGGER.info("getUser(long userId) called with userId: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) return new UserDTO(user.get());
        return null;
    }

    public UserRegistrationCallbackDTO createUser(UserWithPasswordDTO user)
    {
        //TODO: sende en melding at email eksisterer til frontend
        LOGGER.info("createUser(UserPasswordDTO user) called with email: {}", user.getEmail());
        User createdUser = new User();
        createdUser.setForename(user.getForename());
        createdUser.setSurname(user.getSurname());
        createdUser.setEmail(user.getEmail());
        createdUser.setDateOfBirth(user.getDateOfBirth());
        createdUser.setTrainingLevel(user.getTrainingLevel());
        createdUser.setHash(passwordEncoder.encode(user.getPassword())); //encodes password on registration
        if(user.getProfilePicture() != null)createdUser.setProfilePicture(user.getProfilePicture().getBytes());    
        createdUser.setRole("ROLE_USER");
        createdUser = userRepository.save(createdUser);
        String token = createJWtToken(createdUser);

        
        return new UserRegistrationCallbackDTO(token, createdUser.getUserId(), user);
    }

    public UserDTO editUser(long userId, UserEditDTO userDTO)
    {
        LOGGER.info("editUser(long userId, UserDTO userDTO) called with userId: {}", userId);
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if(userDTO.getForename() != null) user.setForename(userDTO.getForename());
            if(userDTO.getSurname() != null) user.setSurname(userDTO.getSurname());
            if(userDTO.getTrainingLevel() != null) user.setTrainingLevel(userDTO.getTrainingLevel());
            if(userDTO.getEmail() != null)user.setEmail(userDTO.getEmail());
            if(userDTO.getProfilePicture() != null)user.setProfilePicture(userDTO.getProfilePicture().getBytes());
            if(userDTO.getProfilePicture().equals("null")) user.setProfilePicture(null);
            // Already checked that oldPassword is correct
            if(userDTO.getNewPassword() != null && userDTO.getOldPassword() != null)user.setHash(passwordEncoder.encode(userDTO.getNewPassword()));
            
            return new UserDTO(userRepository.save(user));
            
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


    public boolean doesEmailAlreadyExist(String email){
        boolean existsEmail = userRepository.findUserByEmail(email).isPresent();
        if(existsEmail) LOGGER.info("Email: {} already exists", email);
        return existsEmail;
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

    public boolean isOldPasswordCorrect(String oldPassword, long userId){
        Optional<User> optionalUsers = userRepository.findById(userId);
        if(optionalUsers.isPresent()){
            return passwordEncoder.matches(oldPassword, optionalUsers.get().getHash());
        }
        return false;
    }


}
