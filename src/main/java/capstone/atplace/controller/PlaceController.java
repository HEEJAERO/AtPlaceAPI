package capstone.atplace.controller;

import capstone.atplace.domain.Member;
import capstone.atplace.domain.Place;
import capstone.atplace.response.Result;
import capstone.atplace.service.MemberService;
import capstone.atplace.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceController {
    private final MemberService memberService;
    private final PlaceService placeService;
    // 즐겨찾기 목록 가져오기
    @GetMapping("/places")
    public Result places(@Valid @RequestParam String token) {
        Member member = memberService.getMemberByToken(token);
        List<Place> places = placeService.findPlaceByMember(member);
        return new Result( true, places);
    }
    // 즐겨찾기 추가
    @PostMapping("/add-place")
    public void addPlace(@RequestParam String token,@ModelAttribute Place place){
        Member member = memberService.getMemberByToken(token);
        placeService.addPlace(place);
        member.addPlace(place);
    }
    // 즐겨찾기 변경
    @PostMapping("/update-palce")
    public void updatePlace(@RequestParam String token,@ModelAttribute Place before,@ModelAttribute Place after){
        Member member = memberService.getMemberByToken(token);
        member.deletePlace(before);
        member.addPlace(after);
    }
    // 즐겨찾기 삭제
    @PostMapping("/delete-place")
    public void deletePlace(@RequestParam String token, @ModelAttribute Place place){
        Member member = memberService.getMemberByToken(token);
        member.deletePlace(place);
        placeService.deletePlace(place);
    }

}
