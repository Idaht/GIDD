package idatt2106.group3.backend.Model.DTO.Activity;

import java.nio.charset.StandardCharsets;

import idatt2106.group3.backend.Model.Activity;

public class ActivityDTO extends ActivitySuperclassDTO {
    private long activityId;
    private long organizerId;

    public ActivityDTO(Activity activity){
        super(activity.getTitle(), activity.getType(), activity.getDescription(), activity.getEquipment(), activity.getDifficulty(), activity.getCity(), activity.getPlace(), activity.getLongitude(), activity.getLatitude(), activity.getStartTime(), activity.getDurationMinutes(), activity.isPrivateActivity(), activity.getMaxParticipants(), convertActivityPicture(activity.getActivityPicture()));
        this.activityId = activity.getActivityId();
        if(activity.getOrganizer() != null) this.organizerId = activity.getOrganizer().getUserId();
    }

    public ActivityDTO(){}

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(long organizerId) {
        this.organizerId = organizerId;
    }

    private static String convertActivityPicture(byte[] activityPicture){
        String activityPictureString = null;
        if(activityPicture != null)activityPictureString = new String(activityPicture, StandardCharsets.UTF_8);
        return activityPictureString;
    }

    @Override
    public String toString()
    {
        return super.toString() + '\'' +
                "activityId=" + activityId + '\'' +
                ", organizerId=" + organizerId + '\'' +
                '}';
    }
}
