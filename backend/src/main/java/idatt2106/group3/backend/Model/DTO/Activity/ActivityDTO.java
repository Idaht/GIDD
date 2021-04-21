package idatt2106.group3.backend.Model.DTO.Activity;

import java.time.LocalDateTime;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.Difficulty;

public class ActivityDTO {
    private long activityId;
    private String title;
    private String description;
    private String equipment;
    private Difficulty difficulty;
    private String city;
    private String place;
    private double longitude;
    private double latitude;
    private LocalDateTime startTime;
    private int durationMinutes;
    private boolean isPrivateActivity;
    private long organizerId;

    public ActivityDTO(Activity activity){
        this.activityId = activity.getActivityId();
        this.title = activity.getTitle();
        this.description = activity.getDescription();
        this.equipment = activity.getEquipment();
        this.difficulty = activity.getDifficulty();
        this.city = activity.getCity();
        this.place = activity.getPlace();
        this.longitude = activity.getLongitude();
        this.latitude = activity.getLatitude();
        this.startTime = activity.getStartTime();
        this.durationMinutes = activity.getDurationMinutes();
        this.isPrivateActivity = activity.isPrivateActivity();
        this.organizerId = activity.getOrganizer().getUserId();
    }

    public ActivityDTO(){}

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isPrivateActivity() {
        return isPrivateActivity;
    }

    public void setPrivateActivity(boolean privateActivity) {
        isPrivateActivity = privateActivity;
    }

    public long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(long organizerId) {
        this.organizerId = organizerId;
    }

    @Override
    public String toString()
    {
        return "Activity{" +
                "activityId=" + activityId +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", equipment='" + equipment + '\'' +
                ", difficulty=" + difficulty +
                ", city='" + city + '\'' +
                ", place='" + place + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", startTime=" + startTime +
                ", durationMinutes=" + durationMinutes +
                ", isPrivateActivity=" + isPrivateActivity +
                '}';
    }
}
