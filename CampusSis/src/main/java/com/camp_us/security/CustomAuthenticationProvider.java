package com.camp_us.security;

import com.camp_us.service.MemberService;
import com.camp_us.dto.MemberVO;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private MemberService memberService;

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String pwd = (String) authentication.getCredentials();

        MemberVO member;
		try {
			member = memberService.getMemberById(id);
			System.out.println(member);
	        if (member == null)
	            throw new UsernameNotFoundException("입력하신 아이디가 존재하지 않습니다.");

	        if (!pwd.equals(member.getMem_pass()))
	            throw new BadCredentialsException("비밀번호가 틀립니다.");

	        return new UsernamePasswordAuthenticationToken(new User(member), pwd, new User(member).getAuthorities());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AuthenticationServiceException("서버 장애로 서비스가 불가합니다.");
		}


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}