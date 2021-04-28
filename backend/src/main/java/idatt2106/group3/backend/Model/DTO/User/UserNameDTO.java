package idatt2106.group3.backend.Model.DTO.User;

import idatt2106.group3.backend.Model.User;

public class UserNameDTO {
    private String forename;
    private String surname;

    public UserNameDTO(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
    }

    public UserNameDTO(User user) {
        this.forename = user.getForename();
        this.surname = user.getSurname();
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

    @Override
    public String toString() {
        return "User{" +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
    
}
