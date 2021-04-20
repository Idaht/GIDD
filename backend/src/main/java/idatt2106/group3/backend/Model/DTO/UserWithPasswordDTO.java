package idatt2106.group3.backend.Model.DTO;

import java.sql.Blob;

/**
 * DTO class for User POST-requests: POST api/v1/users
 */
public class UserWithPasswordDTO {
    private String forename;
    private String surname;
    private String email;
    private String hash;
    private int score;
    private int rating;
    private String role;
    private Blob profilePicture;


    public UserWithPasswordDTO(String forename, String surname, String email, String hash, int score, int rating, String role, Blob profilePicture) {
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.hash = hash;
        this.score = score;
        this.rating = rating;
        this.role = role;
        this.profilePicture = profilePicture;
    }

    public UserWithPasswordDTO() {
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
                ", hash='" + hash + '\'' +
                ", score=" + score +
                ", rating=" + rating +
                ", role='" + role + '\'' +
                '}';
    }
}
