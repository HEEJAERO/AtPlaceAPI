package capstone.atplace.service;

import capstone.atplace.domain.Member;
import capstone.atplace.domain.Place;
import capstone.atplace.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    public void addPlace(Place place){
        placeRepository.save(place);
    }
    public void deletePlace(Place place){
        placeRepository.delete(place);
    }
    public List<Place> findPlaceByMember(Member member){
        return placeRepository.findPlacesByMember(member);
    }

}
