package com.camp_us.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp_us.service.MemberService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private MemberService memberService;
	
	@Autowired
	public LoginController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/index")
	public String loginGet() {		
		String url = "/login/index";
		return url;
	}
	
	@GetMapping("/accessDenied")
    public String accessDenied() {
		String url = "/login/accessDenied";
		return url;
	}

    @GetMapping("/loginTimeOut")
    public String loginTimeOut(Model model) throws Exception {
    	System.out.println("LoginController.loginTimeOut() 호출됨");
        String url = "/login/sessionOut";
        model.addAttribute("message", "세션이 만료되었습니다. \\n 다시 로그인 해주세요");
        return url;
    }

    @GetMapping("/loginExpired")
    public String loginExpired(Model model) throws Exception {
        String url = "/login/sessionOut";
        model.addAttribute("message", "다른 기기에서의 중복 로그인이 감지되었습니다. \\n 다시 로그인 해주세요");
        return url;
    }
	
	
//	@PostMapping("/index")
//	public String loginPost(String id, String pwd,HttpSession session)throws Exception{
//		String url="redirect:/main";
//		
//		MemberVO member=null;
//		member = memberService.getMember(id);			
//				
//		if(member!=null && pwd.equals(member.getMem_pass())) { //로그인 성공.
//				session.setAttribute("loginUser",member);
//		}else {  //아이디 불일치
//			url="redirect://login/index";
//		}	
//		
//		return url;
//	}
//	@GetMapping("/logout")
//	public String logout(HttpSession session) {
//		String url="redirect:/";
//		
//		session.invalidate(); //세션 갱신
//		
//		return url;
//	}

}