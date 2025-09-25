package com.camp_us.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.camp_us.dao.LectureListDAO;
import com.camp_us.dto.AttendanceVO;
import com.camp_us.dto.LecVideoVO;
import com.camp_us.dto.LectureListVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.service.LecVideoService;
import com.camp_us.service.MemberService;
import com.camp_us.service.VidAttendanceService;

@RestController
@RequestMapping("/api/lecture")
public class LectureVideoController2 {

    @Autowired private LecVideoService lecVideoService;
    @Autowired private LectureListDAO lectureListDAO;
    @Autowired private VidAttendanceService attendanceService;
    @Autowired private MemberService memberService;

    @Value("c:/uploads/final")
    private String uploadPath;

    /** ✅ 주차별 영상 목록 */
    @GetMapping("/vidlist")
    public ResponseEntity<?> vidlist(
            @RequestParam("lecId") String lecId,
            @RequestParam(value="week", defaultValue="1주차") String week,
            @RequestParam("memId") String memId
    ) throws Exception {
        MemberVO member = memberService.getMemberById(memId);
        if (member == null) {
            return ResponseEntity.status(401).body(Map.of("ok", false, "message", "INVALID_USER"));
        }

        boolean isStudent   = member.getMem_auth().contains("ROLE01");
        boolean isProfessor = member.getMem_auth().contains("ROLE02");

        // 선택한 주차 영상 가져오기
        List<LecVideoVO> list = lecVideoService.getVideosByWeek(lecId, week);

        if (isStudent) {
            for (LecVideoVO video : list) {
                String aNo = lecId + memId + video.getLecvidId();
                AttendanceVO att = attendanceService.getAttendanceInfo(aNo);
                if (att != null) {
                    video.setProgress(Integer.parseInt(att.getProgress()));
                } else {
                    video.setProgress(0);
                }
            }
        }

        // ✅ 강의에 등록된 모든 주차 가져오기
        List<String> allWeeks = lecVideoService.getWeeksByLecture(lecId);

        Map<String,Object> body = new HashMap<>();
        body.put("videoList", list);
        body.put("lecId", lecId);
        body.put("week", week);
        body.put("role", isStudent ? "student" : (isProfessor ? "professor" : "guest"));
        body.put("allWeeks", allWeeks); // ← 여기에 모든 주차 추가

        return ResponseEntity.ok(body);
    }

    /** ✅ 영상 등록 */
    @PostMapping("/register")
    public ResponseEntity<?> registerVideo(
    		@RequestParam("lecId") String lecId,
            @RequestParam("lecvidName") String lecvidName,
            @RequestParam("deadline") @DateTimeFormat(pattern = "yyyy-MM-dd") Date deadline,
            @RequestParam(value="detail", required=false) String detail,
            @RequestParam(value="week", required=false) String week,
            @RequestParam(value="videoFile", required=false) MultipartFile videoFile,
            @RequestParam(value="thumbFile", required=false) MultipartFile thumbFile
    ) throws IOException {

    	LecVideoVO vo = new LecVideoVO();
        vo.setLecId(lecId);
        vo.setLecvidId(UUID.randomUUID().toString());
        vo.setLecvidName(lecvidName);
        vo.setLecvidDetail(detail);
        vo.setLecvidWeek(week);
        vo.setLecvidDeadline(deadline);

        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        // 비디오 저장
        if (videoFile != null && !videoFile.isEmpty()) {
            String saveName = UUID.randomUUID() + "_" + videoFile.getOriginalFilename();
            File saveFile = new File(uploadPath, saveName);
            videoFile.transferTo(saveFile);

            vo.setLecvidVidname(videoFile.getOriginalFilename());
            vo.setLecvidVidpath("/uploads/final/" + saveName);
        }

        // 썸네일 저장
        if (thumbFile != null && !thumbFile.isEmpty()) {
            String saveThumb = UUID.randomUUID() + "_" + thumbFile.getOriginalFilename();
            File thumbSaveFile = new File(uploadPath, saveThumb);
            thumbFile.transferTo(thumbSaveFile);

            vo.setLecvidThumbnail("/uploads/final/" + saveThumb);
        }

        lecVideoService.addVideo(vo);

        // 출석 초기화
        List<LectureListVO> studentList = lectureListDAO.getStudentsByLecture(lecId);
        for (LectureListVO student : studentList) {
            AttendanceVO att = new AttendanceVO();
            att.setaNo(lecId + student.getMemId() + vo.getLecvidId());
            att.setLecsId(student.getLecsId());
            att.setaDetail("결석");
            att.setaCat("동영상");
            att.setModPending("NOTSEND");
            attendanceService.saveInitialAttendance(att);
        }

        return ResponseEntity.ok(Map.of("ok", true, "lecvidId", vo.getLecvidId()));
    }

    /** ✅ 영상 상세 */
    @GetMapping("/detail")
    public ResponseEntity<?> showVideoDetail(
            @RequestParam("lecId") String lecId,
            @RequestParam("lecvidId") String lecvidId,
            @RequestParam("memId") String memId
    ) {
        LecVideoVO video = lecVideoService.getVideoById(lecvidId);
        if (video == null) {
            return ResponseEntity.status(404).body(Map.of("ok", false, "message", "NOT_FOUND"));
        }

        Map<String,Object> body = new HashMap<>();
        body.put("video", video);
        body.put("lecId", lecId);
        body.put("memId", memId);

        return ResponseEntity.ok(body);
    }

    /** ✅ 영상 시청 진행도 업데이트 */
    @PostMapping("/progress")
    public ResponseEntity<?> updateAttendanceProgress(@RequestBody Map<String, String> payload) {
        String aNo = payload.get("aNo");
        String progressStr = payload.get("progress");

        if (aNo == null || progressStr == null) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "message", "MISSING_PARAMETERS"));
        }

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

        attendanceService.updateAttendance(attendance);

        return ResponseEntity.ok(Map.of("ok", true));
    }

    /** ✅ 영상 수정 */
    @PostMapping("/modify")
    public ResponseEntity<?> modifyVideo(
            @RequestParam("lecvidId") String lecvidId,
            @RequestParam("title") String title,
            @RequestParam("detail") String detail,
            @RequestParam("week") String week,
            @RequestParam(value="videoFile", required=false) MultipartFile videoFile,
            @RequestParam(value="thumbFile", required=false) MultipartFile thumbFile
    ) throws IOException {
        LecVideoVO vo = lecVideoService.getVideoById(lecvidId);
        if (vo == null) {
            return ResponseEntity.status(404).body(Map.of("ok", false, "message", "NOT_FOUND"));
        }

        // ✅ 제목/내용/주차 업데이트
        vo.setLecvidName(title);
        vo.setLecvidDetail(detail);
        vo.setLecvidWeek(week);

        // ✅ 영상 교체
        if (videoFile != null && !videoFile.isEmpty()) {
            String saveName = UUID.randomUUID() + "_" + videoFile.getOriginalFilename();
            File saveFile = new File(uploadPath, saveName);
            videoFile.transferTo(saveFile);

            vo.setLecvidVidname(videoFile.getOriginalFilename());
            vo.setLecvidVidpath("/uploads/final/" + saveName);
        }

        // ✅ 썸네일 교체
        if (thumbFile != null && !thumbFile.isEmpty()) {
            String saveThumb = UUID.randomUUID() + "_" + thumbFile.getOriginalFilename();
            File thumbSaveFile = new File(uploadPath, saveThumb);
            thumbFile.transferTo(thumbSaveFile);

            vo.setLecvidThumbnail("/uploads/final/" + saveThumb);
        }

        lecVideoService.modifyVideo(vo);
        return ResponseEntity.ok(Map.of("ok", true));
    }
}