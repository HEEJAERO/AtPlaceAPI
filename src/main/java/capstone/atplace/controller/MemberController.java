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
    @PostMapping("/join")
    public Result create(@Valid @ModelAttribute JoinForm form) {
        // memberForm 추가
        Member member = new Member(form.getUsername(), form.getPassword(), form.getName());
        try {
            memberService.join(member);
        } catch (IllegalStateException e) {
            return new Result(false,e.getMessage());
        }
        return new Result(true, member);
    }

    //로그인 요청
    @PostMapping("/login")
    public Result login(@Valid @ModelAttribute LoginForm form) {
        try {
            Member member = memberService.login(form.getUsername(), form.getPassword());
            return new Result(true,member );
        } catch (Exception e) {
            return new Result(false,e.getMessage());
        }
    }
    @PostMapping("/validate")
    public Result login(@RequestParam String username){
        boolean b;
        try{b= memberService.duplicateUsername(username);}
        catch(Exception e){
            return new Result(false," 중복아이디");
        };
        return new Result(b," 사용가능한 아이디입니다.");

    }
    //로그아웃
    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {

    }




    // 반환타입을 오브젝트로 반환

}
