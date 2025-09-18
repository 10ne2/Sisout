package com.camp_us.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camp_us.command.PageMaker;
import com.camp_us.dto.HomeworkSubmitVO;
import com.camp_us.dto.HomeworkVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.ProLecVO;
import com.camp_us.service.HomeworkService;
import com.camp_us.service.MemberService;

@RestController
@RequestMapping("/api/homework")
public class HomeworkController2 {

    private final HomeworkService homeworkService;
    @Autowired private MemberService memberService;
    @Autowired private SqlSession sqlSession;

    @Autowired
    public HomeworkController2(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    // ✅ HTML 태그 제거 유틸 메서드
    private String stripHtml(String input) {
        if (input == null) return null;
        return input.replaceAll("<[^>]*>", "").trim();
    }

    /** ✅ 학생/교수 공용 과제 목록 */
    @GetMapping(value="/list", produces="application/json; charset=UTF-8")
    public ResponseEntity<Map<String,Object>> homeworkList(
            @RequestParam String memId,
            @ModelAttribute PageMaker pagemaker,
            @RequestParam String lecId,
            Model model) throws Exception {

        if (pagemaker.getKeyword() != null && pagemaker.getKeyword().trim().isEmpty()) {
            pagemaker.setKeyword(null);
        }

        MemberVO member = memberService.getMemberById(memId);
        if (member == null) {
            return ResponseEntity.status(401).body(Map.of("ok", false, "message", "INVALID_USER"));
        }

        boolean isStudent = member.getMem_auth().contains("ROLE01");
        boolean isProfessor = member.getMem_auth().contains("ROLE02");

        pagemaker.setLecId(lecId);

        String lecName = null;
        if (lecId != null && !lecId.isBlank()) {
            lecName = sqlSession.selectOne("LecClass-Mapper.selectLectureNameById", lecId);
        }

        int totalCount = homeworkService.getHomeworkTotalCount(pagemaker);
        pagemaker.setTotalCount(totalCount);

        List<HomeworkVO> homeworkList = homeworkService.getHomeworkList(pagemaker);

        Map<Integer, Boolean> submitStatusMap = new HashMap<>();
        Map<Integer, Boolean> feedbackMap     = new HashMap<>();

        for (HomeworkVO hw : homeworkList) {
            if (isStudent) {
                Map<String, Object> p = Map.of("hwNo", hw.getHwNo(), "stuId", memId);
                HomeworkSubmitVO submit = sqlSession.selectOne(
                        "HomeworkSubmit-Mapper.selectSubmitByStuIdAndHwNo", p);

                boolean submitted = (submit != null);
                submitStatusMap.put(hw.getHwNo(), submitted);

                boolean hasFeedback = submitted &&
                        submit.getHwsubFeedback() != null &&
                        !submit.getHwsubFeedback().trim().isEmpty();
                feedbackMap.put(hw.getHwNo(), hasFeedback);
            } else {
                Integer total    = sqlSession.selectOne("HomeworkSubmit-Mapper.countSubmitByHwNo", hw.getHwNo());
                Integer ungraded = sqlSession.selectOne("HomeworkSubmit-Mapper.countUngradedByHwNo", hw.getHwNo());
                boolean allGraded = (total != null && total > 0) && (ungraded != null && ungraded == 0);
                feedbackMap.put(hw.getHwNo(), allGraded);

                submitStatusMap.put(hw.getHwNo(), false); // 교수는 제출여부 의미 없음
            }
        }

        Map<String,Object> body = new HashMap<>();
        body.put("homeworkList", homeworkList);
        body.put("submitStatusMap", submitStatusMap);
        body.put("feedbackMap", feedbackMap);
        body.put("pageMaker", pagemaker);
        body.put("lecName", lecName);
        body.put("role", isStudent ? "student" : "professor");

        return ResponseEntity.ok(body);
    }

    /** ✅ 교수용 과제 상세 */
    @GetMapping(value="/professordetail", produces="application/json; charset=UTF-8")
    public ResponseEntity<Map<String,Object>> professordetail(@RequestParam("hwNo") int hwNo) throws Exception {
        HomeworkVO homework = homeworkService.getHomeworkByNo(hwNo);
        List<HomeworkSubmitVO> submitList =
                sqlSession.selectList("HomeworkSubmit-Mapper.selectSubmitListByHwNo", hwNo);

        Map<String,Object> body = new HashMap<>();
        body.put("homework", homework);
        body.put("submitList", submitList);

        return ResponseEntity.ok(body);
    }

    /** ✅ 과제 수정 폼 */
    @GetMapping("/edit")
    public String editHomeworkForm(@RequestParam("hwNo") int hwNo, Model model) throws Exception {
        HomeworkVO homework = homeworkService.getHomeworkByNo(hwNo);
        model.addAttribute("homework", homework);

        if (homework.getHwStartDate() != null && homework.getHwEndDate() != null) {
            LocalDateTime start = homework.getHwStartDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            LocalDateTime end = homework.getHwEndDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            model.addAttribute("startDate", start.format(dateFormatter));
            model.addAttribute("startTime", start.format(timeFormatter));
            model.addAttribute("endDate", end.format(dateFormatter));
            model.addAttribute("endTime", end.format(timeFormatter));
        }

        return "homework/editform";
    }

    /** ✅ 과제 수정 처리 */
    @PostMapping(value = "/edit", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> updateHomework(
            @RequestParam String hwName,
            @RequestParam(required = false) String lecsId,
            @RequestParam String startDate,
            @RequestParam String startTime,
            @RequestParam String endDate,
            @RequestParam String endTime,
            @RequestParam int hwNo,
            @RequestParam String hwDesc) {
        try {
            HomeworkVO origin = homeworkService.getHomeworkByNo(hwNo);

            HomeworkVO vo = new HomeworkVO();
            vo.setHwNo(hwNo);
            vo.setHwName(hwName);
            vo.setHwDesc(stripHtml(hwDesc)); // ⬅️ 태그 제거

            // 기존 lecId 보존
            String lecIdToUse  = (origin.getLecId()  != null && !origin.getLecId().isEmpty())
                    ? origin.getLecId()  : origin.getLecsId();
            String lecsIdToUse = (origin.getLecsId() != null && !origin.getLecsId().isEmpty())
                    ? origin.getLecsId() : origin.getLecId();

            if (lecsId != null && !lecsId.isBlank()) {
                lecIdToUse = lecsId;
                lecsIdToUse = lecsId;
            }

            vo.setLecId(lecIdToUse);
            vo.setLecsId(lecsIdToUse);

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            vo.setHwStartDate(Timestamp.valueOf(LocalDateTime.parse(startDate + " " + startTime, fmt)));
            vo.setHwEndDate(Timestamp.valueOf(LocalDateTime.parse(endDate + " " + endTime, fmt)));

            homeworkService.updateHomework(vo);

            return ResponseEntity.ok(Map.of("ok", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("ok", false, "message", e.getMessage()));
        }
    }

    /** ✅ 학생용 과제 상세 */
    @GetMapping(value="/student/detail", produces="application/json; charset=UTF-8")
    public ResponseEntity<Map<String,Object>> studentHomeworkDetail(
            @RequestParam("hwNo") int hwNo,
            @RequestParam("stuId") String stuId) {

        HomeworkVO homework = sqlSession.selectOne(
                "Homework-Mapper.selectHomeworkByHwNo", hwNo);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("stuId", stuId);
        paramMap.put("hwNo", hwNo);

        HomeworkSubmitVO submitVO = sqlSession.selectOne(
                "HomeworkSubmit-Mapper.selectSubmitByStuIdAndHwNo", paramMap);

        Map<String,Object> body = new HashMap<>();
        body.put("homework", homework);
        body.put("submit", submitVO);
        body.put("submittedFlag", submitVO != null);

        return ResponseEntity.ok(body);
    }

    /** ✅ 과제 작성 폼 */
    @PreAuthorize("hasAuthority('ROLE_ROLE02')")
    @GetMapping("/write")
    public String writeForm(@RequestParam("memId") String memId,
                            @RequestParam(value = "lec_id", required = false) String lecId,
                            Model model) {
        List<ProLecVO> lectures = sqlSession.selectList("LecClass-Mapper.selectLecClassByProfessorId", memId);
        model.addAttribute("lectures", lectures);

        if (lecId != null && !lecId.isBlank()) {
            model.addAttribute("defaultLecId", lecId);
        } else if (lectures.size() == 1) {
            model.addAttribute("defaultLecId", lectures.get(0).getLec_id());
        }
        return "homework/register";
    }

    /** ✅ 과제 등록 처리 */
    @PostMapping(value="/write", produces="application/json; charset=UTF-8")
    public ResponseEntity<?> writeJson(
            @RequestParam String hwName,
            @RequestParam String hwDesc,
            @RequestParam String startDate,
            @RequestParam String startTime,
            @RequestParam String endDate,
            @RequestParam String endTime,
            @RequestParam String lecId,
            @RequestParam String memId
    ) {
        try {
            MemberVO member = memberService.getMemberById(memId);
            if (member == null) {
                return ResponseEntity.status(401).body(Map.of("ok", false, "message", "INVALID_USER"));
            }

            HomeworkVO vo = new HomeworkVO();
            vo.setHwNo(homeworkService.getNextHwNo());
            vo.setHwName(hwName);
            vo.setHwDesc(stripHtml(hwDesc)); // ⬅️ 태그 제거
            vo.setLecId(lecId);
            vo.setLecsId(lecId);

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            vo.setHwStartDate(Timestamp.valueOf(LocalDateTime.parse(startDate + " " + startTime, fmt)));
            vo.setHwEndDate(Timestamp.valueOf(LocalDateTime.parse(endDate + " " + endTime, fmt)));

            homeworkService.insertHomework(vo);

            return ResponseEntity.ok(Map.of("ok", true, "hwNo", vo.getHwNo()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("ok", false, "message", e.getMessage()));
        }
    }
}