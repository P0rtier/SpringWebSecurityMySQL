package pl.uzi.psspringbootstarttutorial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.uzi.psspringbootstarttutorial.models.Speaker;

@Repository
public interface SpeakerRepository extends JpaRepository<Speaker,Long> {
}
