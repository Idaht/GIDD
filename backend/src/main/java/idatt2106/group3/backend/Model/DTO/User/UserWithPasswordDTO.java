package idatt2106.group3.backend.Model.DTO.User;

import java.sql.Blob;

/**
 * DTO class for User POST-requests: POST api/v1/users
 */
public class UserWithPasswordDTO extends UserSuperclassDTO{
    private String hash;
    private Blob profilePicture;


    public UserWithPasswordDTO(String forename, String surname, String email, String hash, int score, int rating, Blob profilePicture) {
        super(forename, surname, email, score, rating);
        this.hash = hash;
        this.profilePicture = profilePicture;
    }

    public UserWithPasswordDTO() {
        super();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
        return super.toString() + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
