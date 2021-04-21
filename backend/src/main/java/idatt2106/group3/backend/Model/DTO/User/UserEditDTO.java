package idatt2106.group3.backend.Model.DTO.User;

import java.sql.Blob;
import java.time.LocalDate;

public class UserEditDTO extends UserSuperclassDTO {
    private String newPassword;
    private String oldPassword;
    private Blob profilePicture;
    
    public UserEditDTO(String forename, String surname, String email, LocalDate dateOfBirth, String newHash, String oldHash, Blob profilePicture) {
        super(forename, surname, email, dateOfBirth);
        this.profilePicture = profilePicture;
        this.newPassword = newHash;
        this.oldPassword = oldHash;
    }

    public UserEditDTO(){
        super();
    }

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public String toString()
    {
        return super.toString() + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }
}
