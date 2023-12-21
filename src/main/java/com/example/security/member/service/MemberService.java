package com.example.security.member.service;

import com.example.security.member.model.entity.Member;
import com.example.security.member.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {
    private MemberRepository memberRepository;

    private HttpServletRequest httpServletRequest;

    private PasswordEncoder passwordEncoder;


    public MemberService(MemberRepository memberRepository, HttpServletRequest httpServletRequest, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.httpServletRequest = httpServletRequest; // 세션 저장을 위한 것인데 스프링 시큐리티에서 세션도 알아서 저장해준다...
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(String username, String password) {

        // 좋지 않은 방식의 비밀번호 암호화
        // 좋은 방식은 config 를 생성하여 빈으로 등록한다.
//        String encodedPassword =
//                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password);

        if(!memberRepository.findByUsername(username).isPresent()) {
            memberRepository.save(Member.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
//                    .password(password)
                    .authority("ROLE_USER")
                    .build());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByUsername(username);
        Member member = null;
        if(result.isPresent()) {
            member = result.get();
            httpServletRequest.getSession().setAttribute("isLogined", true);   // 스프링 시큐리티에서 알아서 해주기 떄문에 필요가 없다...세션 저장부분
        }
        return member; // UserDetails 전달 AuthenticationProvider << 여기가 만남의 광장 느낌
    }
}
