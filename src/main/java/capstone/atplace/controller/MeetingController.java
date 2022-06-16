package capstone.atplace.controller;

import capstone.atplace.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeetingController {
    private final MemberService memberService;
    // 예약 목록 가져오기
    @GetMapping("/meetings")
    public void meetingList(){

    }

    // 예약 추가
    @PostMapping("/add-meeting")
    public void addMeeting(){

    }
    //예약 변경
    @PostMapping("/update-meeting")
    public void updateMeeting(){

    }
    //예약 삭제
    @PostMapping("/delete-meeting")
    public void deleteMeeting(){

    }
}
