package idatt2106.group3.backend.Model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String forename;
    private String surname;
    @Column(unique = true)
    private String email;
    private String hash;
    private String salt;
    private int score;
    private int rating;
    private String role;
    private int faults;

    @OneToOne(mappedBy = "user")
    private Session session;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_Activity",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "activityId")
    )
    Set<Activity> activities;

    @OneToMany(mappedBy = "organizer")
    Set<Activity> organizedActivities;

    @OneToMany(mappedBy = "reportWriter", targetEntity = Report.class)
    Set<Report> reportsSent;

    @OneToMany(mappedBy = "reportedUser", targetEntity = Report.class)
    Set<Report> reportsReceived;

    @OneToMany(mappedBy = "user", targetEntity = Message.class)
    Set<Message> messages;

    public User(String forename, String surname, String email, String hash, String salt, int score, int rating, String role, int faults, Session session, Set<Activity> activities, Set<Activity> organizedActivities, Set<Report> reportsSent, Set<Report> reportsReceived, Set<Message> messages) {
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.hash = hash;
        this.salt = salt;
        this.score = score;
        this.rating = rating;
        this.role = role;
        this.faults = faults;
        this.session = session;
        this.activities = activities;
        this.organizedActivities = organizedActivities;
        this.reportsSent = reportsSent;
        this.reportsReceived = reportsReceived;
        this.messages = messages;
    }

    public User(String forename, String surname, String email, String hash, String salt, int score, int rating, String role, int faults) {
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

    public User() {
    }

    public Set<Report> getReportsSent() {
        return reportsSent;
    }

    public void setReportsSent(Set<Report> reportsSent) {
        this.reportsSent = reportsSent;
    }

    public Set<Report> getReportsReceived() {
        return reportsReceived;
    }

    public void setReportsReceived(Set<Report> reportsReceived) {
        this.reportsReceived = reportsReceived;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Set<Activity> getOrganizedActivities() {
        return organizedActivities;
    }

    public void setOrganizedActivities(Set<Activity> organizedActivities) {
        this.organizedActivities = organizedActivities;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    @Override
    public String toString()
    {
        return "User{" +
                "userId=" + userId +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", hash='" + hash + '\'' +
                ", salt='" + salt + '\'' +
                ", score=" + score +
                ", rating=" + rating +
                ", role='" + role + '\'' +
                ", faults=" + faults +
                ", session=" + session +
                ", activities=" + activities +
                ", organizedActivities=" + organizedActivities +
                ", reportsSent=" + reportsSent +
                ", reportsReceived=" + reportsReceived +
                ", messages=" + messages +
                '}';
    }
}
