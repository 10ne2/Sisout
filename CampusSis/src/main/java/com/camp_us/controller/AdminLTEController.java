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

import com.camp_us.dto.AcademicVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.ProLecVO;
import com.camp_us.dto.StuLecVO;
import com.camp_us.service.AcademicService;
import com.camp_us.service.DashboardService;
import com.camp_us.service.LecClassService;
import com.camp_us.service.NoticeService;
import com.camp_us.service.StuLecService;

@Controller
@RequestMapping("")
public class AdminLTEController {

	@Autowired
	private StuLecService stuLecService;
	@Autowired
	private LecClassService lecClassService;
	@Autowired
	private AcademicService academicService;
	@Autowired 
	private NoticeService noticeService;        // 최근 공지 5건
	@Autowired 
	private DashboardService dashboardService;  // 과제 마감 이벤트/달력 점
	

	@GetMapping("/student")
    public String studentLayout(HttpSession session, Model model) throws Exception {
        MemberVO member = (MemberVO) session.getAttribute("loginUser");
        if (member == null) return "redirect:/login";
        model.addAttribute("member", member);

        String memId = member.getMem_id();
        String auth  = member.getMem_auth();
        boolean isStudent   = auth != null && (auth.contains("ROLE01") || auth.contains("ROLE_ROLE01"));
        boolean isProfessor = auth != null && (auth.contains("ROLE02") || auth.contains("ROLE_ROLE02"));

        if (isStudent)   model.addAttribute("stulectureList", stuLecService.selectLectureListByStudentId(memId));
        else             model.addAttribute("stulectureList", java.util.Collections.emptyList());
        if (isProfessor) model.addAttribute("prolectureList", lecClassService.selectLecClassByProfessorId(memId));
        else             model.addAttribute("prolectureList", java.util.Collections.emptyList());

        return "student";  // => /WEB-INF/views/student.jsp
    }

    // ✅ 대시보드 콘텐츠 (iframe이 이걸 로드)
	@GetMapping("/student/home")
	public String studentHome(HttpSession session, Model model) throws Exception {
	    MemberVO member = (MemberVO) session.getAttribute("loginUser");
	    if (member == null) return "redirect:/login";
	    model.addAttribute("member", member);

	    String memId = member.getMem_id();
	    String auth  = member.getMem_auth();
	    boolean isStudent   = auth != null && (auth.contains("ROLE01") || auth.contains("ROLE_ROLE01"));
	    boolean isProfessor = auth != null && (auth.contains("ROLE02") || auth.contains("ROLE_ROLE02"));

	    // === 강의 카드용 공통 리스트 채우기 ===
	    if (isStudent) {
	        // 원래대로
	        model.addAttribute("stulectureList",
	            stuLecService.selectLectureListByStudentId(memId));
	    } else if (isProfessor) {
	        // 교수 강의 목록을 'stulectureList' 라는 키로 넣어 JSP 재사용
	        model.addAttribute("stulectureList",
	            lecClassService.selectLecClassByProfessorId(memId));
	    } else {
	        model.addAttribute("stulectureList", java.util.Collections.emptyList());
	    }

	    // === 공지 ===
	    model.addAttribute("noticeList", noticeService.getRecentNotices());

	    // === 이벤트/달력 점: 역할별로 다른 쿼리 호출 ===
	    if (isProfessor) {
	        model.addAttribute("eventList", dashboardService.getUpcomingHwEventsForProfessor(memId));
	        model.addAttribute("hwDots",    dashboardService.getHwDotsForProfessor(memId));
	    } else {
	        model.addAttribute("eventList", dashboardService.getUpcomingHwEvents(memId)); // 학생용
	        model.addAttribute("hwDots",    dashboardService.getHwDots(memId));           // 학생용
	    }

	    return "dashboard/studentsdashboard";
	}


	@GetMapping("/mail")
	public ModelAndView mail(ModelAndView mnv) {
		mnv.setViewName("/mail");
		return mnv;
	}

	@GetMapping("/adminmenu")
	public void adminmenu() {
	}

	@GetMapping("/empmenu")
	public void empmenu() {
	}

	@GetMapping("/mypage")
	public ModelAndView mypage(ModelAndView mnv, HttpSession session) throws SQLException {
	    MemberVO member = (MemberVO) session.getAttribute("loginUser");
	    if (member == null) {
	        mnv.setViewName("redirect:/login");
	        return mnv;
	    }

	    String memId = member.getMem_id();
	    AcademicVO academic = academicService.getByMemId(memId);
	    if (academic == null) academic = new AcademicVO();

	    mnv.addObject("member", member);
	    mnv.addObject("student", academic);
	    mnv.setViewName("mypage/mypage");
	    return mnv;
	}

}
