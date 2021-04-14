package idatt2106.group3.backend.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.time.LocalDateTime;
import java.util.Set;

enum Difficulty {EASY, MEDIUM, HARD}

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long activityId;
    private String description;
    private String equipment;
    private Difficulty difficulty;
    private String city;
    private String place;
    private double longitude;
    private double latitude;
    private LocalDateTime startTime;
    private int durationMinutes;
    private long organizerId;
    private boolean isPrivateActivity;

    @ManyToMany(mappedBy = "activities")
    private Set<User> users;

    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "userId")
    private User organizer;

    @OneToOne
    @JoinColumn(name = "chatId", referencedColumnName = "chatId", nullable = false)
    private Chat chat;

    public Activity(String description, String equipment, Difficulty difficulty, String city, String place, double longitude, double latitude, LocalDateTime startTime, int durationMinutes, long organizerId, boolean isPrivateActivity) {
        this.description = description;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.city = city;
        this.place = place;
        this.longitude = longitude;
        this.latitude = latitude;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.organizerId = organizerId;
        this.isPrivateActivity = isPrivateActivity;
    }

    public Activity() {
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(long organizerId) {
        this.organizerId = organizerId;
    }

    public boolean isPrivateActivity() {
        return isPrivateActivity;
    }

    public void setPrivateActivity(boolean privateActivity) {
        isPrivateActivity = privateActivity;
    }
}
