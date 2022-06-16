package capstone.atplace.repository;

import capstone.atplace.domain.Member;
import capstone.atplace.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findPlacesByMember(Member member);

    //@Query("select p from Place p where p.member =: member")
    Place findPlaceByMember(Member member);
}

