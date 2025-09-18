package com.camp_us.security;

import com.camp_us.service.MemberService;
import com.camp_us.dto.MemberVO;

import java.sql.SQLException;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private MemberService memberService;

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id  = authentication.getName();
        String raw = (String) authentication.getCredentials();

        try {
            MemberVO member = memberService.getMemberById(id);
            if (member == null) {
                throw new UsernameNotFoundException("입력하신 아이디가 존재하지 않습니다.");
            }

            String dbPw = member.getMem_pass();
            if (dbPw == null || !raw.equals(dbPw)) {
                throw new BadCredentialsException("비밀번호가 틀립니다.");
            }

            User principal = new User(member);
            return new UsernamePasswordAuthenticationToken(principal, raw, principal.getAuthorities());

        } catch (SQLException e) {
            throw new AuthenticationServiceException("서버 장애로 서비스가 불가합니다.", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
