package idatt2106.group3.backend.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import idatt2106.group3.backend.Model.Notification;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Repository.NotificationRepository;
import idatt2106.group3.backend.Repository.UserRepository;

@Service
public class NotificationService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Find a notification by a given notificationId
     * @param notificationId
     * @return Notification
     */
    public Notification getNotification(long notificationId) {
        LOGGER.info("getNotification(long notificationId) called with notificationId: {}", notificationId); 
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if(notificationOptional.isPresent()) return notificationOptional.get();
        return null;
    }

    /**
     * Deletes all notifications of a specific user given by userId
     * @param userId
     */
    public void deleteNotifications(long userId) {
        LOGGER.info("deleteNotifications(long userId) called with userId: {}", userId); 
        List<Notification> userNotifications = notificationRepository.findAllNotificationsByUserId(userId);
        notificationRepository.deleteAll(userNotifications);
    }

    /**
     * Creates new notification for a specific user given userId
     * @param userId
     * @param notificationText
     * @return
     */
    public Notification createNotification(User user, String notificationText) {
        LOGGER.info("createNotification(User user, String notificationText) called"); 
        return notificationRepository.save(new Notification(notificationText, user));
    }

}
