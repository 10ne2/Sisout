package com.camp_us.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camp_us.dao.LectureListDAO;
import com.camp_us.dto.AttendanceVO;
import com.camp_us.dto.LecVideoVO;
import com.camp_us.dto.LectureListVO;
import com.camp_us.service.LecVideoService;
import com.camp_us.service.VidAttendanceService;

@Controller
@RequestMapping("/lecture")
public class LectureVideoController {

	@Autowired
	private LecVideoService lecVideoService;
	
	@Autowired
	private LectureListDAO lectureListDAO;

	@Value("c:/uploads/final")
	private String uploadPath;

	@Autowired
	private VidAttendanceService attendanceService;

	@GetMapping("/vidlist")
	public String vidlist(@RequestParam("lec_id") String lecId,
	                      @RequestParam(value = "week", defaultValue = "1주차") String week,
	                      Model model,
	                      Principal principal,
	                      Authentication authentication) {

	    String memId = principal.getName();
	    boolean isStudent = false;
	    boolean isProfessor = false;

	    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	    for (GrantedAuthority auth : authorities) {
	        String role = auth.getAuthority();
	        if ("ROLE_ROLE01".equals(role)) isStudent = true;
	        else if ("ROLE_ROLE02".equals(role)) isProfessor = true;
	    }

	    List<LecVideoVO> list = lecVideoService.getVideosByWeek(lecId, week);

	    if (isStudent) {
	        for (LecVideoVO video : list) {
	            String aNo = lecId + memId + video.getLecvidId();
	            AttendanceVO att = attendanceService.getAttendanceInfo(aNo);
	            if (att != null) {
	                video.setProgress(Integer.parseInt(att.getProgress()));
	                model.addAttribute("vidprogress", Integer.parseInt(att.getProgress()));
	                // VO에 주입
	            } else {
	                video.setProgress(0); // 없으면 0%
	                model.addAttribute("vidprogress", 0);
	            }
	        }
	    }

	    model.addAttribute("videoList", list);
	    model.addAttribute("lecId", lecId);
	    model.addAttribute("week", week);

	    return "lecture/vidleclist";
	}

	@GetMapping("/register")
	public String showRegisterForm(@RequestParam("lec_id") String lecId, Model model) {
		model.addAttribute("lecId", lecId);
		return "lecture/vidadd"; // /WEB-INF/views/lecture/vidadd.jsp
	}

	@PostMapping("/register")
	public String registerVideo(@ModelAttribute LecVideoVO vo, BindingResult result,
			@RequestParam("videoFile") MultipartFile videoFile, @RequestParam("thumbFile") MultipartFile thumbFile,
			RedirectAttributes rttr) throws IOException {

		vo.setLecvidId(UUID.randomUUID().toString());

	    // [1] 디렉토리 확인 및 생성
	    File dir = new File(uploadPath);
	    if (!dir.exists()) dir.mkdirs();

	    // [2] 비디오 파일 저장
	    if (videoFile != null && !videoFile.isEmpty()) {
	        String originalName = videoFile.getOriginalFilename();
	        String saveName = UUID.randomUUID() + "_" + originalName;
	        File saveFile = new File(uploadPath, saveName);
	        System.out.println(">>> 저장 경로: " + saveFile.getAbsolutePath());
	        videoFile.transferTo(saveFile);

	        vo.setLecvidVidname(originalName);
	        vo.setLecvidVidpath("/uploads/final/" + saveName);
	    }

	    // [3] 썸네일 저장
	    if (thumbFile != null && !thumbFile.isEmpty()) {
	        String originalThumb = thumbFile.getOriginalFilename();
	        String saveThumb = UUID.randomUUID() + "_" + originalThumb;
	        File thumbSaveFile = new File(uploadPath, saveThumb);
	        thumbFile.transferTo(thumbSaveFile);
	        System.out.println(">>> 썸네일 저장 경로: " + thumbSaveFile.getAbsolutePath());

	        vo.setLecvidThumbnail("/uploads/final/" + saveThumb);
	    }

		// ✅ DB에 저장
		lecVideoService.addVideo(vo);
		
	    List<LectureListVO> studentList = lectureListDAO.getStudentsByLecture(vo.getLecId());
	    System.out.println(studentList);
	    for (LectureListVO student : studentList) {
	        AttendanceVO att = new AttendanceVO();
	        att.setaNo(vo.getLecId() + student.getMemId() + vo.getLecvidId()); // 출석 번호
	        att.setLecsId(student.getLecsId()); // 수강 ID
	        att.setaDetail("결석"); // 초기 출석 상태
	        att.setaCat("동영상"); // 출석 종류
	        att.setModPending("NOTSEND"); // 출석 정정 상태
	        
	        System.out.println("== INSERT_ATTENDANCE VO ==");
	        System.out.println("aNo: " + att.getaNo());
	        System.out.println("aDetail: " + att.getaDetail());
	        System.out.println("lecsId: " + att.getLecsId());
	        System.out.println("aCat: " + att.getaCat());
	        System.out.println("modPending: " + att.getModPending());

	        attendanceService.saveInitialAttendance(att);
	    }

		// ✅ 목록으로 이동
		rttr.addAttribute("lec_id", vo.getLecId());
		return "redirect:/lecture/vidlist";
	}
	
	@GetMapping("/detail")
	public String showVideoDetail(
	    @RequestParam("lec_id") String lecId,
	    @RequestParam("lecvid_id") String lecvidId,
	    Model model,
	    Principal principal
	) {
	    System.out.println("요청받은 lecvidId = " + lecvidId);
	    
	    LecVideoVO video = lecVideoService.getVideoById(lecvidId);
	    System.out.println("조회된 video = " + video);

	    String memId = principal.getName();

	    model.addAttribute("video", video);
	    model.addAttribute("lecId", lecId);
	    model.addAttribute("memId", memId);

	    return "lecture/viddetail";
	}
	
	@PostMapping("/progress")
	@ResponseBody
	public void updateAttendanceProgress(@RequestParam("aNo") String aNo,
	                                     @RequestParam("progress") String progressStr, Model model) {

	    AttendanceVO attendance = new AttendanceVO();
	    attendance.setaNo(aNo);
	    attendance.setProgress(progressStr);
	    

	    int progress = Integer.parseInt(progressStr);
	    if (progress == 0) {
	        attendance.setaDetail("결석");
	    } else if (progress >= 90) {
	        attendance.setaDetail("출석");
	    } else {
	        attendance.setaDetail("지각");
	    }
	    
	    System.out.println(progress);
	    System.out.println(attendance.getaNo());
	    System.out.println(attendance.getProgress());
	    System.out.println(attendance.getaDetail());

	    attendanceService.updateAttendance(attendance);
	    System.out.println("업데이트 요청됨 → aNo=" + aNo + ", progress=" + progressStr);
	    
	}
	
	@GetMapping("/modify")
	public String showModifyForm(@RequestParam("lec_id") String lecId,
	                              @RequestParam("lecvid_id") String lecvidId,
	                              Model model) {
	    LecVideoVO video = lecVideoService.getVideoById(lecvidId);

	    model.addAttribute("lecId", lecId);
	    model.addAttribute("video", video);

	    return "lecture/vidmodify";  // /WEB-INF/views/lecture/vidmodify.jsp
	}

	@PostMapping("/modify")
	public String modifyVideo(@ModelAttribute LecVideoVO vo,
	                          @RequestParam("videoFile") MultipartFile videoFile,
	                          @RequestParam("thumbFile") MultipartFile thumbFile,
	                          RedirectAttributes rttr) throws IOException {

	    // 기존 파일 유지, 새 파일이 있을 경우만 변경
	    if (videoFile != null && !videoFile.isEmpty()) {
	        String originalName = videoFile.getOriginalFilename();
	        String saveName = UUID.randomUUID() + "_" + originalName;
	        File saveFile = new File(uploadPath, saveName);
	        videoFile.transferTo(saveFile);

	        vo.setLecvidVidname(originalName);
	        vo.setLecvidVidpath("/uploads/final/" + saveName);
	    }

	    if (thumbFile != null && !thumbFile.isEmpty()) {
	        String originalThumb = thumbFile.getOriginalFilename();
	        String saveThumb = UUID.randomUUID() + "_" + originalThumb;
	        File thumbSaveFile = new File(uploadPath, saveThumb);
	        thumbFile.transferTo(thumbSaveFile);

	        vo.setLecvidThumbnail("/uploads/final/" + saveThumb);
	    }

	    lecVideoService.modifyVideo(vo);

	    rttr.addAttribute("lec_id", vo.getLecId());
	    return "redirect:/lecture/vidlist";
	}

}