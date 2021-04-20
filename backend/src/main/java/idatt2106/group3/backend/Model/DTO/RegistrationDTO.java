package idatt2106.group3.backend.Model.DTO;

/**
 * DTO class for sending User and token back to client when creating a user
 */
public class RegistrationDTO {
    private String token;
    private UserDTO user;

    public RegistrationDTO(String token, long userId, UserPasswordDTO userPasswordDTO) {
        this.token = token;
        this.user = new UserDTO();
        this.user.setUserId(userId);
        this.user.setForename(userPasswordDTO.getForename());
        this.user.setSurname(userPasswordDTO.getSurname());
        this.user.setEmail(userPasswordDTO.getEmail());
        this.user.setScore(userPasswordDTO.getScore());
        this.user.setRating(userPasswordDTO.getRating());
        this.user.setRole(userPasswordDTO.getRole());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "RegistrationDTO{" +
                ", token='" + token + '\'' +
                ", user='" + user.toString() + '\'';
    }
}


