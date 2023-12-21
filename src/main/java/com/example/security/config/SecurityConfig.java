package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            // 설정을 하나씩 추가해준다.
            // csrf().disable() : 크로스 사이트 요청 변조 공격을 방어토록 하는 토큰 발급 기능을 꺼라
            // 왜? 프론트와 백엔드 서버를 나눴다고 생각하고 수업을 진행하기 때문에
            http.csrf().disable()
                    // 앞으로의 요청
                    .authorizeHttpRequests()
                    // 이건 허용해주고
                    .antMatchers("/login", "/member/signup").permitAll() // 여긴 로그인을 안해도 이 URL로 갈 수 있도록 하는것
                    .antMatchers("/member/mypage").hasRole("USER")  // 여긴 USER라는 권한을 가진 사람만이 갈 수 있는 URL ( 로그인 한 사용자만 )
                    // 나머지는 다 거부한다.
                    // 이것을 거부해서 막히면 디스패처 서블릿까지 가지도 못한다.
                    // 필터의 개념을 이해해야 한다.
                    //.anyRequest().denyAll();
                    .anyRequest().authenticated() // 나머지 모든 기능들은 인증받은 사용자만 갈 수 있도록 설정
                    .and()
                    .formLogin().loginProcessingUrl("/member/login")
                    .defaultSuccessUrl("/member/mypage");  // 로그인 성공 시 띄워 줄 화면 URL

            return  http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
