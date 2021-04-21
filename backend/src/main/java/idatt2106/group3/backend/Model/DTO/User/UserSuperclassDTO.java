package idatt2106.group3.backend.Model.DTO.User;

/**
 * This is the superclass for User DTO's.
 */
public class UserSuperclassDTO {
    private String forename;
    private String surname;
    private String email;
    private int score;
    private int rating;

    public UserSuperclassDTO(String forename, String surname, String email, int score, int rating) {
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.score = score;
        this.rating = rating;
    }

    public UserSuperclassDTO() {
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

    @Override
    public String toString()
    {
        return "User{" +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", score=" + score + '\'' +
                ", rating=" + rating;
    }
}
