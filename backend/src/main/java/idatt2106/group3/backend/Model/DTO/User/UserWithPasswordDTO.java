package idatt2106.group3.backend.Model.DTO.User;

import java.sql.Blob;
import java.time.LocalDate;

/**
 * DTO class for User POST-requests: POST api/v1/users
 */
public class UserWithPasswordDTO extends UserSuperclassDTO{
    private String password;
    private Blob profilePicture;


    public UserWithPasswordDTO(String forename, String surname, String email, LocalDate dateOfBirth, String password, Blob profilePicture) {
        super(forename, surname, email, dateOfBirth);
        this.password = password;
        this.profilePicture = profilePicture;
    }

    public UserWithPasswordDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", password='" + password + '\'' +
                '}';
    }
}
