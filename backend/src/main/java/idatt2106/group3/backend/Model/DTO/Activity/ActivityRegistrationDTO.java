package idatt2106.group3.backend.Model.DTO.Activity;

import java.time.LocalDateTime;

import idatt2106.group3.backend.Model.Difficulty;

public class ActivityRegistrationDTO {
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

    public ActivityRegistrationDTO(String title, String description, String equipment, Difficulty difficulty, String city, String place, double longitude, double latitude, LocalDateTime startTime, int durationMinutes, boolean isPrivateActivity) {
        this.description = description;
        this.title = title;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.city = city;
        this.place = place;
        this.longitude = longitude;
        this.latitude = latitude;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.isPrivateActivity = isPrivateActivity;
    }

    public ActivityRegistrationDTO(){}

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

    @Override
    public String toString()
    {
        return "Activity{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
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
