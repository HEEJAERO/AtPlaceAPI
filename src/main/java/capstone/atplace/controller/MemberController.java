package capstone.atplace.controller;

import capstone.atplace.domain.Member;
import capstone.atplace.domain.Place;
import capstone.atplace.form.JoinForm;
import capstone.atplace.form.LoginForm;
import capstone.atplace.response.Result;
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



    // 반환타입을 오브젝트로 반환

}
