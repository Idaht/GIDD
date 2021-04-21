package idatt2106.group3.backend.Model.DTO.User;

import java.time.LocalDate;

/**
 * This is the superclass for User DTO's.
 */
public class UserSuperclassDTO {
    private String forename;
    private String surname;
    private String email;
    private LocalDate dateOfBirth;

    public UserSuperclassDTO(String forename, String surname, String email, LocalDate dateOfBirth) {
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString()
    {
        return "User{" +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth;
    }
}
