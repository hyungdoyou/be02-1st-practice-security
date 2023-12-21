package com.example.security.member.model.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    String username;
    String password;
    String authority;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한을 반환해줘야 사용자가 로그인 시 해당 권한을 확인해서 로그인을 승인해줄 수 있다.
        return Collections.singleton((GrantedAuthority) () -> authority);
    }

    @Override
    public boolean isAccountNonExpired() {
//        return false; // 기본값
        return true;    // 일단 테스트를 위해 다 true로 바꿔준다.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
