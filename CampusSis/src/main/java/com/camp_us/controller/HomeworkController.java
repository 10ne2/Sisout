package com.camp_us.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.camp_us.command.PageMaker;
import com.camp_us.dto.HomeworkSubmitVO;
import com.camp_us.dto.HomeworkVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.ProLecVO;
import com.camp_us.service.HomeworkService;

@Controller
@RequestMapping("/homework")
public class HomeworkController {

	private final HomeworkService homeworkService;

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	public HomeworkController(HomeworkService homeworkService) {
		this.homeworkService = homeworkService;
	}

	// ✅ 학생/교수 공용 과제 목록
	@GetMapping("/list")
	public String homeworkList(@ModelAttribute PageMaker pagemaker,
	                           @RequestParam(value = "lec_id", required = false) String lecId,
	                           Model model, HttpSession session) throws Exception {
	    String url = "homework/studentslist";

	    // 검색어 공백 처리
	    if (pagemaker.getKeyword() != null && pagemaker.getKeyword().trim().isEmpty()) {
	        pagemaker.setKeyword(null);
	    }

	    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
	    String userId = loginUser.getMem_id();
	    String auth   = loginUser.getMem_auth();

	    pagemaker.setLecId(lecId);
	    
	    // 과목명 조회해서 모델에 추가
	    String lecName = null;
	    if (lecId != null && !lecId.isBlank()) {
	        lecName = sqlSession.selectOne("LecClass-Mapper.selectLectureNameById", lecId);
	    }
	    model.addAttribute("lecName", lecName);

	    // 목록/전체건수
	    int totalCount = homeworkService.getHomeworkTotalCount(pagemaker);
	    pagemaker.setTotalCount(totalCount);
	    List<HomeworkVO> homeworkList = homeworkService.getHomeworkList(pagemaker);

	    Map<Integer, Boolean> submitStatusMap = new HashMap<>();
	    Map<Integer, Boolean> feedbackMap     = new HashMap<>();

	    boolean isStudent = (auth != null && auth.contains("ROLE01"));

	    for (HomeworkVO hw : homeworkList) {
	        if (isStudent) {
	            // 학생: 내 제출/피드백 여부
	            Map<String, Object> p = Map.of("hwNo", hw.getHwNo(), "stuId", userId);
	            HomeworkSubmitVO submit = sqlSession.selectOne(
	                "HomeworkSubmit-Mapper.selectSubmitByStuIdAndHwNo", p);

	            boolean submitted = (submit != null);
	            submitStatusMap.put(hw.getHwNo(), submitted);

	            boolean hasFeedback = submitted &&
	                                  submit.getHwsubFeedback() != null &&
	                                  !submit.getHwsubFeedback().trim().isEmpty();
	            feedbackMap.put(hw.getHwNo(), hasFeedback);

	        } else {
	            // 교수: 모든 제출건에 피드백이 달렸을 때만 '평가완료'
	            Integer total    = sqlSession.selectOne("HomeworkSubmit-Mapper.countSubmitByHwNo", hw.getHwNo());
	            Integer ungraded = sqlSession.selectOne("HomeworkSubmit-Mapper.countUngradedByHwNo", hw.getHwNo());

	            boolean allGraded = (total != null && total > 0) && (ungraded != null && ungraded == 0);
	            feedbackMap.put(hw.getHwNo(), allGraded);

	            // 교수 화면에서 '제출여부' 컬럼은 학생 전용이므로 의미 없음
	            submitStatusMap.put(hw.getHwNo(), false);
	        }
	    }

	    model.addAttribute("homeworklist", homeworkList);
	    model.addAttribute("submitStatusMap", submitStatusMap);
	    model.addAttribute("feedbackMap", feedbackMap);
	    model.addAttribute("pageMaker", pagemaker);

	    return url;
	}


	@GetMapping("/professordetail")
	// ✅ 교수용 과제 상세 페이지
	public String professordetail(@RequestParam("hwNo") int hwNo, Model model) throws Exception {
		HomeworkVO homework = homeworkService.getHomeworkByNo(hwNo);
		model.addAttribute("homework", homework);
		List<HomeworkSubmitVO> submitList = sqlSession.selectList("HomeworkSubmit-Mapper.selectSubmitListByHwNo", hwNo);
		model.addAttribute("submitList", submitList);

		return "homework/professordetail";
	}

	@GetMapping("/edit")
	public String editHomeworkForm(@RequestParam("hwNo") int hwNo, Model model) throws Exception {
		HomeworkVO homework = homeworkService.getHomeworkByNo(hwNo); // ✅ 과제 정보 가져오기
		model.addAttribute("homework", homework);

		// ✅ 날짜 및 시간 분리해서 전달
		if (homework.getHwStartDate() != null && homework.getHwEndDate() != null) {
			LocalDateTime start = homework.getHwStartDate().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDateTime();
			LocalDateTime end = homework.getHwEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

			model.addAttribute("startDate", start.format(dateFormatter));
			model.addAttribute("startTime", start.format(timeFormatter));
			model.addAttribute("endDate", end.format(dateFormatter));
			model.addAttribute("endTime", end.format(timeFormatter));
		}

		return "homework/editform";
	}

	@PostMapping("/edit")
	public String updateHomework(@RequestParam String hwName, @RequestParam String lecsId,
			@RequestParam String startDate, @RequestParam String startTime, @RequestParam String endDate,
			@RequestParam String endTime, @RequestParam int hwNo, @RequestParam String hwDesc) {
		HomeworkVO vo = new HomeworkVO();
		vo.setHwNo(hwNo);
		vo.setHwName(hwName);
		vo.setLecsId(lecsId);
		vo.setHwDesc(hwDesc);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		vo.setHwStartDate(Timestamp.valueOf(LocalDateTime.parse(startDate + " " + startTime, formatter)));
		vo.setHwEndDate(Timestamp.valueOf(LocalDateTime.parse(endDate + " " + endTime, formatter)));

		homeworkService.updateHomework(vo);

		return "homework/closepopup";
	}

	@GetMapping("/delete")
	public String deleteHomework(@RequestParam("hwNo") int hwNo,
			@RequestParam(value = "lec_id", required = false) String lecId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "perPageNum", required = false) Integer perPageNum,
			@RequestParam(value = "keyword", required = false) String keyword) throws Exception {
		homeworkService.deleteHomework(hwNo);

		StringBuilder url = new StringBuilder("redirect:/homework/list");
		url.append("?lec_id=").append(lecId == null ? "" : lecId);
		if (page != null)
			url.append("&page=").append(page);
		if (perPageNum != null)
			url.append("&perPageNum=").append(perPageNum);
		if (keyword != null && !keyword.isBlank())
			url.append("&keyword=").append(keyword);
		return url.toString();
	}

	// ✅ 학생용 과제 상세 페이지 (제출 정보 포함)
	@GetMapping("/studentsdetail")
	public String studentHomeworkDetail(@RequestParam("hwNo") int hwNo,
			@RequestParam(value = "submitted", required = false) String submitted, Model model, HttpSession session) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		String stuId = loginUser.getMem_id();

		// 1. 과제 정보 조회
		HomeworkVO homework = sqlSession.selectOne("Homework-Mapper.selectHomeworkByHwNo", hwNo);
		model.addAttribute("homework", homework);

		// 2. 제출 정보 조회
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("stuId", stuId);
		paramMap.put("hwNo", hwNo);

		HomeworkSubmitVO submitVO = sqlSession.selectOne("HomeworkSubmit-Mapper.selectSubmitByStuIdAndHwNo", paramMap);
		model.addAttribute("submit", submitVO);

		// 3. alert 표시용 플래그
		if ("true".equals(submitted)) {
			model.addAttribute("submitted", true);
		}

		// 4. 디버깅 출력
		System.out.println("🔍 로그인된 학생 ID: " + stuId);
		System.out.println("🔍 과제 번호: " + hwNo);
		System.out.println("📦 제출 정보: " + (submitVO != null ? "있음" : "없음"));

		// ✅ JSP 렌더링
		return "homework/studentsdetail";
	}

	// ✅ 과제 작성 폼 (교수만)
	@PreAuthorize("hasAuthority('ROLE_ROLE02')")
	@GetMapping("/write")
	public String writeForm(HttpSession session, Model model,
			@RequestParam(value = "lec_id", required = false) String lecId) {

		MemberVO login = (MemberVO) session.getAttribute("loginUser");
		String memId = login.getMem_id();

		// ✅ VO로 받기
		List<ProLecVO> lectures = sqlSession.selectList("LecClass-Mapper.selectLecClassByProfessorId", memId);
		model.addAttribute("lectures", lectures);

		// ✅ 기본 lecId 세팅
		if (lecId != null && !lecId.isBlank()) {
			model.addAttribute("defaultLecId", lecId);
		} else if (lectures.size() == 1) {
			model.addAttribute("defaultLecId", lectures.get(0).getLec_id()); 
		}
		return "homework/register";
	}

	// ✅ 과제 등록 처리
	@PostMapping("/write")
	public String submitHomework(@RequestParam String hwName, @RequestParam String hwDesc,
			@RequestParam String startDate, @RequestParam String startTime, @RequestParam String endDate,
			@RequestParam String endTime, @RequestParam(required = false) String lecId, HttpSession session)
			throws Exception {

		MemberVO login = (MemberVO) session.getAttribute("loginUser");
		String memId = login.getMem_id();

		// ✅ VO로 받기 (GET과 동일하게)
		List<ProLecVO> lectures = sqlSession.selectList("LecClass-Mapper.selectLecClassByProfessorId", memId);
		Set<String> myLecIds = new HashSet<>();
		for (ProLecVO l : lectures)
			myLecIds.add(l.getLec_id());

		if (lecId == null || lecId.isBlank()) {
			if (myLecIds.size() == 1)
				lecId = myLecIds.iterator().next();
			else
				return "redirect:/homework/write?error=chooseLecture";
		}
		if (!myLecIds.contains(lecId))
			throw new SecurityException("담당 강의에만 등록할 수 있습니다.");

		HomeworkVO vo = new HomeworkVO();
		vo.setHwNo(homeworkService.getNextHwNo());
		vo.setHwName(hwName);
		vo.setHwDesc(hwDesc);
		vo.setLecId(lecId);
		vo.setLecsId(lecId);

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		vo.setHwStartDate(Timestamp.valueOf(LocalDateTime.parse(startDate + " " + startTime, fmt)));
		vo.setHwEndDate(Timestamp.valueOf(LocalDateTime.parse(endDate + " " + endTime, fmt)));

		homeworkService.insertHomework(vo);
		return "homework/registersuccess";
	}

	// ✅ 과제 제출 처리
	@PostMapping("/submit")
	public String submitHomework(HomeworkSubmitVO submitVO, HttpSession session) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		submitVO.setStuId(loginUser.getMem_id());

		// UUID로 과제 제출 번호 생성
		submitVO.setHwsubHsno(UUID.randomUUID().toString());

		sqlSession.insert("HomeworkSubmit-Mapper.insertHomeworkSubmit", submitVO);

		return "redirect:/homework/studentsdetail?hwNo=" + submitVO.getHwNo();
	}
}