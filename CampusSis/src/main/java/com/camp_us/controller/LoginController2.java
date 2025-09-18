package com.camp_us.controller;


import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camp_us.dto.MemberVO;
import com.camp_us.service.MemberService;

@RestController
@RequestMapping("/api/login")
public class LoginController2 {
	
	private MemberService memberService;
	
	@Autowired
	public LoginController2(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@PostMapping(value = "/index", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> loginPost(@RequestBody Map<String, String> param, HttpSession session)throws Exception {
        String id = param.get("id");
        String pwd = param.get("pwd");
        
        MemberVO member = memberService.getMember(id);
        System.out.println("입력 ID: " + id + ", 입력 PW: " + pwd);
        System.out.println("DB에서 조회된 PW: " + (member != null ? member.getMem_pass() : "null"));
        if (member != null && pwd.equals(member.getMem_pass())) {
            // 세션에 로그인 사용자 정보 저장
            session.setAttribute("loginUser", member);
            System.out.println(session.getId());
            return ResponseEntity.ok(member); // 로그인 성공 시 사용자 정보 반환
        } else {
            // 로그인 실패
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }

    // 세션 만료
    @GetMapping("/loginTimeOut")
    public ResponseEntity<?> loginTimeOut() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("세션이 만료되었습니다. 다시 로그인 해주세요.");
    }

    // 중복 로그인 감지
    @GetMapping("/loginExpired")
    public ResponseEntity<?> loginExpired() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("다른 기기에서의 중복 로그인이 감지되었습니다. 다시 로그인 해주세요.");
    }

    // 접근 권한 없음
    @GetMapping("/accessDenied")
    public ResponseEntity<?> accessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("접근 권한이 없습니다.");
    }
    @GetMapping("/check")
    public ResponseEntity<?> checkSession(HttpSession session) {
        MemberVO member = (MemberVO) session.getAttribute("loginUser");
        if(member != null) {
            return ResponseEntity.ok(member); // 세션 존재 시 사용자 정보 반환
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body("세션 없음");
        }
    }
}