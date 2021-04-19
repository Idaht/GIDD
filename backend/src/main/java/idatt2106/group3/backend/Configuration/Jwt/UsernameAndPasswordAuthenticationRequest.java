package idatt2106.group3.backend.Configuration.Jwt;


/**
 * Mapper file
 */
public class UsernameAndPasswordAuthenticationRequest {
    private String email;
    private String password;

    public UsernameAndPasswordAuthenticationRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
