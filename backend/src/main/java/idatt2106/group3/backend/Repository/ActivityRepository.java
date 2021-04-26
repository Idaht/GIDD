package idatt2106.group3.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import idatt2106.group3.backend.Model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>
{
    @Query(value = "SELECT activity.* FROM activity JOIN user_activity ON (activity.activity_id = user_activity.activity_id AND user_activity.user_id=?1 AND activity.start_time > NOW())", nativeQuery = true)
    public List<Activity> findFutureUserActivities(Long userId);
}
