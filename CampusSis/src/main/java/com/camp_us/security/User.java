package com.camp_us.security;

import com.camp_us.dto.MemberVO;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
    private final com.camp_us.dto.MemberVO member;

    public User(MemberVO member) {
        this.member = member;
    }

    public MemberVO getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + member.getMem_auth()));
    }

    @Override
    public String getPassword() {
        return member.getMem_pass();
    }

    @Override
    public String getUsername() {
        return member.getMem_id();
    }

    @Override
    public boolean isAccountNonExpired() { return member.getEnabled()!=4; }

    @Override
    public boolean isAccountNonLocked() { return member.getEnabled()!=3; }

    @Override
    public boolean isCredentialsNonExpired() { return member.getEnabled()!=2; }

    @Override
    public boolean isEnabled() { return member.getEnabled()!=0; }
}