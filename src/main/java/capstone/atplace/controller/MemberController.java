package capstone.atplace.controller;

import capstone.atplace.domain.Member;
import capstone.atplace.domain.Place;
import capstone.atplace.form.JoinForm;
import capstone.atplace.form.LoginForm;
import capstone.atplace.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 요청
    @PostMapping("/member/new")
    public String create(@Valid @ModelAttribute JoinForm form) {
        // memberForm 추가
        Member member = new Member(form.getName(), form.getPassword(),
                form.getUsername(), form.getPhoneNumber(), LocalDateTime.now());
        try {
            memberService.join(member);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "";
    }

    //로그인 요청
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form) {
        try {
            memberService.login(form.getUsername(), form.getPassword());
        } catch (Exception e) {
            return e.getMessage();
        }
        return "login";
    }

    //로그아웃
    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {

    }

    private void expiredCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @GetMapping("/recommend")
    public String recommend() {
        return "recommend";
    }

    // 즐겨찾기 목록 가져오기
    @GetMapping("/places")
    public Result places(@Valid @RequestParam String username) {
        Long findMemberId = memberService.findByUsername(username);

        List<Place> places = memberService.getPlaces(findMemberId);

        return new Result(places.size(), places);

    }
    // 즐겨찾기 추가
    @PostMapping("/add-place")
    public void addPlace(){

    }
    // 즐겨찾기 변경
    @PostMapping("/update-palce")
    public void updatePlace(){

    }
    // 즐겨찾기 삭제

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
    // 반환타입을 오브젝트로 반환

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private  T data;
    }


}
