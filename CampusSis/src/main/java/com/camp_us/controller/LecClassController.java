package com.camp_us.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camp_us.dto.ComingLecVO;
import com.camp_us.dto.LecClassVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.OtherDashStuVO;
import com.camp_us.dto.UnsubmitHomeworkVO;
import com.camp_us.service.ComingLecService;
import com.camp_us.service.LecClassService;
import com.camp_us.service.OtherDashStuService;
import com.camp_us.service.UnsubmitHomeworkService;

@Controller
@RequestMapping("/lectureStu")
public class LecClassController {
    
    @Autowired
    private LecClassService lecClassService;
    
    @Autowired
    private UnsubmitHomeworkService unsubmitHomeworkService;
    
    @Autowired
	private ComingLecService comingLecService;
    
    @Autowired
	private OtherDashStuService otherDashStuService;
    
    @GetMapping("/main")
	public String main(HttpSession session, Model model) throws Exception {
    	
    	MemberVO member = (MemberVO) session.getAttribute("loginUser");
        if (member == null) {
            return "redirect:/login";
        }
        
        UnsubmitHomeworkVO vo = unsubmitHomeworkService.getStuIdbyMemId(member.getMem_id());
        String stu_id = vo.getStu_id();

		
		List<UnsubmitHomeworkVO> unsubmithwList = unsubmitHomeworkService.getUnsubmitHomeworkList(stu_id);
		model.addAttribute("unsubmitList", unsubmithwList);
		
		List<ComingLecVO> comingleclist = comingLecService.getComingLecList(stu_id);
		model.addAttribute("comingleclist", comingleclist);
		
		List<OtherDashStuVO> noticeList = otherDashStuService.getNoticeList(stu_id);
		model.addAttribute("noticeList", noticeList);
		
		List<OtherDashStuVO> fileList = otherDashStuService.getFileList(stu_id);
		model.addAttribute("fileList", fileList);
		
		List<OtherDashStuVO> attendenceList = otherDashStuService.getAttendenceList(stu_id);
		model.addAttribute("attendenceList", attendenceList);
		
		List<OtherDashStuVO> attendencePercent = otherDashStuService.getAttendencePercent(stu_id);
		model.addAttribute("attendencePercent", attendencePercent);
		
		return "lecture/main";
	}

    @GetMapping("/changeMajor")
    @ResponseBody
    public LecClassVO getLectureInfo(@RequestParam("lec_id") String lec_id) throws SQLException {
        return lecClassService.getLecClassById(lec_id);
    
    
    }
    @GetMapping("/syllabus")
    public String syllabus(@RequestParam("lec_id") String lec_id, Model model) {
        model.addAttribute("lec_id", lec_id);
        return "lecture/syllabus";  // views/lecture/syllabus.jsp
    }
}