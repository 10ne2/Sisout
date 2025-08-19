package com.camp_us.controller;


import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.camp_us.dto.MemberVO;
import com.camp_us.dto.StuLecVO;
import com.camp_us.service.StuLecService;

@Controller
@RequestMapping("")
public class AdminLTEController {
	@Autowired
	private StuLecService stuLecService;
	@GetMapping("/student")
	public void starter(HttpSession session, Model model) {
        MemberVO member = (MemberVO) session.getAttribute("loginUser");
        model.addAttribute("member", member);
		String stu_id = member.getMem_id();
		System.out.println(stu_id);// mimi 학생 id 하드코딩
        try {
            List<StuLecVO> lectureList = stuLecService.selectLectureListByStudentId(stu_id);
            model.addAttribute("lectureList", lectureList);
            System.out.println(lectureList);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "강의 목록 조회 중 오류가 발생했습니다.");
        }
	}
	@GetMapping("/adminmenu")
		public void adminmenu() {}
	@GetMapping("/empmenu")
		public void empmenu() {}
	@GetMapping("/mypage")
	public ModelAndView mypage(ModelAndView mnv) throws Exception {
		String url="/mypage";
		mnv.setViewName(url);
		return mnv;
	}
}