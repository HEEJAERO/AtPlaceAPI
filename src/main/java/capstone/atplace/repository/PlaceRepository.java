package capstone.atplace.repository;

import capstone.atplace.domain.Member;
import capstone.atplace.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findPlacesByMember(Member member);
}

