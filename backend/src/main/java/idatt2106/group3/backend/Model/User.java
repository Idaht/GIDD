package idatt2106.group3.backend.Model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String username;
    private String forename;
    private String surname;
    private String email;
    private String hash;
    private String salt;
    private int score;
    private int rating;
    private String role;
    private int faults;

    @OneToOne(mappedBy = "user")
    private Session session;

    @ManyToMany
    @JoinTable(
        name = "User_Activity",
        joinColumns = @JoinColumn(name = "userId"),
        inverseJoinColumns = @JoinColumn(name = "activityId")
    )
    Set<Activity> activities;

    @OneToMany(mappedBy = "organizer")
    Set<Activity> organizedActivities;


    public User(String username,
                String forename,
                String surname,
                String email, String hash, String salt, int score, int rating, String role, int faults) {
        this.username = username;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.hash = hash;
        this.salt = salt;
        this.score = score;
        this.rating = rating;
        this.role = role;
        this.faults = faults;
    }

    public User(){}

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public int getFaults() {
        return faults;
    }

    public void setFaults(int faults) {
        this.faults = faults;
    }
}
