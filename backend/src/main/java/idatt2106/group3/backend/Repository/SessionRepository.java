package idatt2106.group3.backend.Repository;

import idatt2106.group3.backend.Model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>
{
}
