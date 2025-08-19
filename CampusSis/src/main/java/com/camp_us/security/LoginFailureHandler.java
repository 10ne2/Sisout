package com.camp_us.security;

import java.io.IOException;
import javax.servlet.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public LoginFailureHandler() {
        setDefaultFailureUrl("/camp_us/login/index?error=true");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        String errorMessage;

        if (exception instanceof UsernameNotFoundException)
            errorMessage = "아이디가 존재하지 않습니다.";
        else if (exception instanceof BadCredentialsException)
            errorMessage = "비밀번호가 틀립니다.";
        else if (exception instanceof InsufficientAuthenticationException)
            errorMessage = "추가 인증이 필요합니다.";
        else
            errorMessage = "로그인에 실패했습니다.";

        request.getSession().setAttribute("loginFailMsg", errorMessage);
        System.out.println("로그인 실패");
        response.sendRedirect(request.getContextPath() + "/login/index");
    }
}