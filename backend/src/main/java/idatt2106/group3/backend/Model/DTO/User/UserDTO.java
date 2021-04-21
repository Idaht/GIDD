package idatt2106.group3.backend.Model.DTO.User;

import java.sql.Blob;

import idatt2106.group3.backend.Model.User;

/**
 * DTO class for sending User information to frontend
 */
public class UserDTO {
    private long userId;
    private String forename;
    private String surname;
    private String email;
    private int score;
    private int rating;
    private String role;
    private Blob profilePicture;


    public UserDTO(long userId, String forename, String surname, String email, int score, int rating, String role, Blob profilePicture) {
        this.userId = userId;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.score = score;
        this.rating = rating;
        this.role = role;
        this.profilePicture = profilePicture;
    }

    public UserDTO() {
    }

    public UserDTO(User user){
        this.userId = user.getUserId();
        this.forename = user.getForename();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.score = user.getScore();
        this.rating = user.getRating();
        this.role = user.getRole();
        this.profilePicture = user.getProfilePicture();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString()
    {
        return "User{" +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", score=" + score +
                ", rating=" + rating +
                ", role='" + role + '\'' +
                '}';
    }
}
