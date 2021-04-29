package idatt2106.group3.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import idatt2106.group3.backend.Model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT * FROM notification WHERE notification.user_id=?1", nativeQuery = true)
    public List<Notification> findAllNotificationsByUserId(long userId);

}
