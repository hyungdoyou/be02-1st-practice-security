package com.example.security.member.controller;

import com.example.security.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity signup (String username, String password) {
        memberService.signup(username, password);
        return ResponseEntity.ok().body("ok");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mypage")
    public ResponseEntity mypage() {
        return ResponseEntity.ok().body("마이 페이지입니다.");
    }
}
