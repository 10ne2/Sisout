package com.camp_us.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camp_us.dto.ComingLecVO;
import com.camp_us.dto.OtherDashStuVO;
import com.camp_us.dto.UnsubmitHomeworkVO;
import com.camp_us.service.ComingLecService;
import com.camp_us.service.LecClassService;
import com.camp_us.service.OtherDashStuService;
import com.camp_us.service.UnsubmitHomeworkService;

@RestController
@RequestMapping("/api/lecDashStu")
public class LecClassController2 {
    
    @Autowired
    private LecClassService lecClassService;
    
    @Autowired
    private UnsubmitHomeworkService unsubmitHomeworkService;
    
    @Autowired
	private ComingLecService comingLecService;                       
    
    @Autowired
	private OtherDashStuService otherDashStuService;
    
    
    @GetMapping("/main")
	public ResponseEntity<Map<String,Object>> main(@RequestParam String memId) throws Exception {
    	
    	ResponseEntity<Map<String,Object>> result=null;
    	
        UnsubmitHomeworkVO vo = unsubmitHomeworkService.getStuIdbyMemId(memId);
        String stu_id = vo.getStu_id();

		
		List<UnsubmitHomeworkVO> unsubmithwList = unsubmitHomeworkService.getUnsubmitHomeworkList(stu_id);
//		model.addAttribute("unsubmitList", unsubmithwList);
		
		List<ComingLecVO> comingleclist = comingLecService.getComingLecList(stu_id);
//		model.addAttribute("comingleclist", comingleclist);
		
		List<OtherDashStuVO> noticeList = otherDashStuService.getNoticeList(stu_id);
//		model.addAttribute("noticeList", noticeList);
		
		List<OtherDashStuVO> fileList = otherDashStuService.getFileList(stu_id);
//		model.addAttribute("fileList", fileList);
		
		List<OtherDashStuVO> attendenceList = otherDashStuService.getAttendenceList(stu_id);
//		model.addAttribute("attendenceList", attendenceList);
		
		List<OtherDashStuVO> attendencePercent = otherDashStuService.getAttendencePercent(stu_id);
//		model.addAttribute("attendencePercent", attendencePercent);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		dataMap.put("unsubmithwList", unsubmithwList);
		dataMap.put("comingleclist", comingleclist);
		dataMap.put("noticeList", noticeList);
		dataMap.put("fileList", fileList);
		dataMap.put("attendenceList", attendenceList);
		dataMap.put("attendencePercent", attendencePercent);
		
		result = new ResponseEntity<Map<String,Object>>(dataMap,HttpStatus.OK);
		
		return result;
	}

    
    
}