package idatt2106.group3.backend.Model.DTO.User;

import java.time.LocalDate;

public class UserEditDTO extends UserSuperclassDTO {
    private String newPassword;
    private String oldPassword;
    private String profilePicture;
    
    public UserEditDTO(String forename, String surname, String email, LocalDate dateOfBirth, String newPassword, String oldPassword, String profilePicture) {
        super(forename, surname, email, dateOfBirth);
        this.profilePicture = profilePicture;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword  ;
    }

    public UserEditDTO(){
        super();
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
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
