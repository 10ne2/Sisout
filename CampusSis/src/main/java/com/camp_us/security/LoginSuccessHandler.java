package com.camp_us.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.camp_us.dto.MemberVO;
import com.camp_us.service.MemberService;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private MemberService memberService;
	
	
	
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }



	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
	
		
		System.out.println("로그인 성공");
    	User user = (User) authentication.getPrincipal();
        MemberVO member = user.getMember();
        String userid = member.getMem_id();
        try {
			memberService.updateMemLastLogin(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}


        HttpSession session = request.getSession();
        session.setAttribute("loginUser", member);
        session.setMaxInactiveInterval(20*60);
//        response.sendRedirect(getDefaultTargetUrl());
        super.onAuthenticationSuccess(request, response, authentication);
	}

  
}