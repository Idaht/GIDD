package idatt2106.group3.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import idatt2106.group3.backend.Model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>
{
}
