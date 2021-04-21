package idatt2106.group3.backend.Model.DTO.Activity;

import java.time.LocalDateTime;

import idatt2106.group3.backend.Model.Difficulty;

public class ActivityRegistrationDTO extends ActivitySuperclassDTO{

    public ActivityRegistrationDTO(String title, String type, String description, String equipment, Difficulty difficulty, String city, String place, double longitude, double latitude, LocalDateTime startTime, int durationMinutes, boolean isPrivateActivity, int maxParticipants) {
        super(title, type, description, equipment, difficulty, city, place, longitude, latitude, startTime, durationMinutes, isPrivateActivity, maxParticipants);
    }

    public ActivityRegistrationDTO(){
        super();
    }

    @Override
    public String toString()
    {
        return super.toString() + '\'' +
        '}';
    }
}
