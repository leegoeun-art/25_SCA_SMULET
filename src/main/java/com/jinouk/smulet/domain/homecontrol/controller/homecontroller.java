package com.jinouk.smulet.domain.homecontrol.controller;

import com.jinouk.smulet.domain.homecontrol.dto.userdto;
import com.jinouk.smulet.domain.homecontrol.service.memberservice;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class homecontroller {
    private final memberservice mservice;

    // [세션 접근 제어 기능] 메인 페이지 접근 시 로그인 상태 확인 (담당: 고은)
    @GetMapping("/")
    public String website(HttpSession session) {
        if(session.getAttribute("loginEmail")==null) {
            return "redirect:/login_page";
        }
        return "user/My_page";
    }

    @GetMapping("/Register")
    public String register(){return "user/Register";}

    @GetMapping("/login_page")
    public String loginform(){return "user/login_Page";}

    @PostMapping("/do_Register")
    public String save(@ModelAttribute userdto userdto)
    {
        mservice.save(userdto);
        return "user/login_page";
    }

    // [로그인 시 세션 발급 기능] 로그인 성공 시 세션 저장 (담당: 고은)
    @PostMapping("/login_page")
    public String login(@ModelAttribute userdto userdto, HttpSession session)
    {
        System.out.println("1"+ userdto);
        userdto loginresult = mservice.login(userdto);
        if(loginresult!=null) {
            System.out.println(loginresult);
            session.setAttribute("loginEmail" , loginresult.getName());
            return "user/My_page";
        }
        else{
            System.out.println(loginresult);
            return "user/login_page";
        }
    }

    // [로그아웃 기능] 세션 무효화 처리 및 로그인 페이지로 리다이렉트 (담당: 고은)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 삭제
        return "redirect:/login_page"; // 로그인 페이지로 리다이렉트
    }

}
