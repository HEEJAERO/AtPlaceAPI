package capstone.atplace.repository;

import capstone.atplace.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
