package idatt2106.group3.backend.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;

/**
 * Component for sending mails
 */
@Component
public class EmailComponent {
    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
      String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("team.gidd@gmail.com");
        message.setTo(to);
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }

    /**
     * Used when a activity gets deleted/canceled
     * Sends all participants a cancellation mail
     * @param user 
     * @param activity
     */
    public void sendCancelationMail(User user, Activity activity){
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("team.gidd@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Aktivitet " + activity.getTitle() + " i " + activity.getCity() + " er avlyst");
        message.setText("Aktivitet " + activity.getTitle() + " som skulle foregå " +
        getTimeAndDateString(activity.getStartTime(), activity.getDurationMinutes()) +
        " er blitt avlyst");
        emailSender.send(message);
    }

    /**
     * Creates a formatted "from-to" string with actual date and times
     * @param dateTime start time
     * @param durationMinutes duration of the activity
     * @return formatted String
     */
    private String getTimeAndDateString(LocalDateTime dateTime, int durationMinutes){
        DateTimeFormatter initialDateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy 'fra' HH:mm 'til' ");
        DateTimeFormatter endingDateTimeFormat = DateTimeFormatter.ofPattern("HH:mm");

        String format1 = dateTime.format(initialDateTimeFormat);
        String format2 = dateTime.plusMinutes(durationMinutes).format(endingDateTimeFormat);

        return format1 + format2;
    }
}
