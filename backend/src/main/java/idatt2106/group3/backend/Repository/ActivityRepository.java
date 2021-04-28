package idatt2106.group3.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import idatt2106.group3.backend.Model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>
{
    @Query(value = "SELECT * FROM activity WHERE activity.start_time > NOW()", nativeQuery = true)
    public List<Activity> findAllFromNow();
    @Query(value = "SELECT user_activity.user_id FROM user_activity WHERE user_activity.user_id=?1 AND user_activity.activity_id=?2", nativeQuery = true)
    public Optional<Integer> findIfUserIsParticipantOfActivity(Long userId, Long activityId);
    // Returns your activities that will happen later
    @Query(value = "SELECT activity.* FROM activity JOIN user_activity ON (activity.activity_id = user_activity.activity_id AND user_activity.user_id=?1 AND activity.start_time > NOW())", nativeQuery = true)
    public List<Activity> findFutureUserActivities(Long userId);

    // Returns activities based on a search query and sorted by closest timewise
    @Query(value = "SELECT * FROM activity WHERE (activity.title LIKE ?1 OR activity.description LIKE ?1) AND activity.start_time > NOW() ORDER BY activity.start_time LIMIT ?2", nativeQuery = true)
    public List<Activity> findActivitiesOnDateWithoutFilter(String searchQuery, Integer amount);

    // Returns activities based on a search query and sorted by closest timewise, but filtered by difficulty
    @Query(value = "SELECT * FROM activity WHERE (activity.title LIKE ?1 OR activity.description LIKE ?1) AND activity.difficulty=?3 AND activity.start_time > NOW() ORDER BY activity.start_time LIMIT ?2", nativeQuery = true)
    public List<Activity> findActivitiesOnDateWithFilter(String searchQuery, Integer amount, Integer difficulty);

    @Query(value = "SELECT activity.* FROM activity LEFT JOIN user_activity ON (activity.activity_id = user_activity.activity_id ) WHERE (activity.title LIKE ?1 OR activity.description LIKE ?1) AND activity.start_time > NOW() GROUP BY activity.activity_id ORDER BY COUNT(user_activity.activity_id) DESC LIMIT ?2", nativeQuery = true)
    public List<Activity> findActivitiesOnAmountWithoutFilter(String searchQuery, Integer amount);

    @Query(value = "SELECT activity.* FROM activity LEFT JOIN user_activity ON (activity.activity_id = user_activity.activity_id ) WHERE (activity.title LIKE ?1 OR activity.description LIKE ?1) AND activity.difficulty = ?3 AND activity.start_time > NOW() GROUP BY activity.activity_id ORDER BY COUNT(user_activity.activity_id) DESC LIMIT ?2", nativeQuery = true)
    public List<Activity> findActivitiesOnAmountWithFilter(String searchQuery, Integer amount, Integer difficulty);

    @Query(value = "SELECT activity.* FROM activity WHERE (activity.title LIKE ?1 OR activity.description LIKE ?1) AND activity.start_time > NOW() ORDER BY SQRT(POW((?3-longitude),2)+POW((?4-latitude),2)) LIMIT ?2", nativeQuery = true)
    public List<Activity> findActivitiesOnDistanceWithoutFilter(String searchQuery, Integer amount, Double longitude, Double latitude);

    @Query(value = "SELECT activity.* FROM activity WHERE (activity.title LIKE ?1 OR activity.description LIKE ?1) AND activity.difficulty = ?5 AND activity.start_time > NOW() ORDER BY SQRT(POW((?3-longitude),2)+POW((?4-latitude),2)) LIMIT ?2", nativeQuery = true)
    public List<Activity> findActivitiesOnDistanceWithFilter(String searchQuery, Integer amount, Double longitude, Double latitude, Integer difficulty);
    
}
