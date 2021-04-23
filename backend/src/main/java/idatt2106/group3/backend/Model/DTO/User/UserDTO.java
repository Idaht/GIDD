package idatt2106.group3.backend.Model.DTO.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import idatt2106.group3.backend.Model.Difficulty;
import idatt2106.group3.backend.Model.User;

/**
 * DTO class for sending User information to frontend
 */
public class UserDTO extends UserSuperclassDTO {
    private long userId;
    private String role;
    private String profilePicture;


    public UserDTO(long userId, String forename, String surname, String email, LocalDate dateOfBirth, Difficulty trainingLevel, String role, String profilePicture) {
        super(forename, surname, email, dateOfBirth, trainingLevel);
        this.userId = userId;
        this.role = role;
        this.profilePicture = profilePicture;
    }

    public UserDTO() {
        super();
    }

    public UserDTO(User user){
        super(user.getForename(), user.getSurname(), user.getEmail(), user.getDateOfBirth(), user.getTrainingLevel());
        this.userId = user.getUserId();
        this.role = user.getRole();
        if(user.getProfilePicture()!= null)this.profilePicture = new String(user.getProfilePicture(), StandardCharsets.UTF_8);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString()
    {
        return super.toString() + '\'' +
                ", userId='" + userId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
