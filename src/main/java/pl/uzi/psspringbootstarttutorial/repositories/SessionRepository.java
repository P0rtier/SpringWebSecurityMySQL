package pl.uzi.psspringbootstarttutorial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.uzi.psspringbootstarttutorial.models.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
}
