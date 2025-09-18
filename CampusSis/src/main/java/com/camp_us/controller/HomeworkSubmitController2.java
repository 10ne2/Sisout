package com.camp_us.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.camp_us.dto.HomeworkSubmitVO;
import com.camp_us.dto.HomeworkVO;
import com.camp_us.service.HomeworkService;
import com.camp_us.service.HomeworkSubmitService;

@RestController
@RequestMapping("/api/homeworksubmit")
public class HomeworkSubmitController2 {

    @Autowired private HomeworkSubmitService submitService;
    @Autowired private HomeworkService homeworkService;

    private static final String UPLOAD_DIR = "C:/upload/homework/";

    private String saveFile(MultipartFile f) throws IOException {
        if (f == null || f.isEmpty()) return null;
        String fn = UUID.randomUUID() + "_" + f.getOriginalFilename();
        File dest = new File(UPLOAD_DIR + fn);
        dest.getParentFile().mkdirs();
        f.transferTo(dest);
        return fn;
    }

    /** ✅ 학생 과제 제출 (세션 제거, stuId를 RequestParam으로 받음) */
    @PostMapping(value="/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submit(@RequestParam("hwNo") int hwNo,
                                    @RequestParam("lecsId") String lecsId,
                                    @RequestParam("stuId") String stuId,
                                    @RequestParam("hwsubComment") String hwsubComment,
                                    @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile) {
        try {
            HomeworkSubmitVO vo = new HomeworkSubmitVO();
            vo.setHwsubHsno(UUID.randomUUID().toString());
            vo.setHwNo(hwNo);
            vo.setLecsId(lecsId);
            vo.setStuId(stuId);
            vo.setHwsubComment(hwsubComment);
            vo.setHwsubStatus("제출완료");
            vo.setHwsubFilename(saveFile(uploadFile));

            submitService.regist(vo);
            return ResponseEntity.ok(Map.of("ok", true, "submitId", vo.getHwsubHsno()));
        } catch (Exception e) {
        	
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("ok", false, "message", e.getMessage()));
        }
    }

    /** ✅ 학생 과제 수정 (세션 제거, stuId를 RequestParam으로 받음) */
    @PostMapping(value="/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> edit(@RequestParam("hwNo") int hwNo,
                                  @RequestParam("lecsId") String lecsId,
                                  @RequestParam("stuId") String stuId,
                                  @RequestParam("hwsubComment") String hwsubComment,
                                  @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile) {
        try {
            HomeworkSubmitVO vo = submitService.getSubmitByHwNoAndStdId(hwNo, stuId);
            if (vo == null) return ResponseEntity.status(404).body(Map.of("ok", false, "message", "제출 내역 없음"));

            vo.setLecsId(lecsId);
            vo.setHwsubComment(hwsubComment);
            String newFile = saveFile(uploadFile);
            if (newFile != null) vo.setHwsubFilename(newFile);

            submitService.updateSubmit(vo);
            return ResponseEntity.ok(Map.of("ok", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("ok", false, "message", e.getMessage()));
        }
    }

    /** ✅ 제출 상세(단건) */
    @GetMapping("/detail")
    public ResponseEntity<?> detail(@RequestParam("submitId") String submitId) {
        try {
            HomeworkSubmitVO submit = submitService.getSubmitById(submitId);
            if (submit == null) return ResponseEntity.status(404).body(Map.of("ok", false));
            return ResponseEntity.ok(Map.of("ok", true, "submit", submit));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("ok", false, "message", e.getMessage()));
        }
    }

    /** ✅ 특정 과제의 제출 목록 (교수) */
    @GetMapping("/listByHwNo")
    public ResponseEntity<?> listByHwNo(@RequestParam("hwNo") int hwNo) {
        try {
            List<HomeworkSubmitVO> list = submitService.getSubmitListByHwNo(hwNo);
            HomeworkVO homework = homeworkService.getHomeworkByNo(hwNo);
            return ResponseEntity.ok(Map.of("ok", true, "homework", homework, "submitList", list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("ok", false, "message", e.getMessage()));
        }
    }

    /** ✅ 파일 다운로드 */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("filename") String filename) throws Exception {
        File file = new File(UPLOAD_DIR + filename);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Path path = file.toPath();
        Resource resource = new UrlResource(path.toUri());

        String original = filename;
        int idx = filename.indexOf('_');
        if (idx >= 0 && idx + 1 < filename.length()) original = filename.substring(idx + 1);
        String encoded = UriUtils.encode(original, "UTF-8");

        String cd = "attachment; filename=\"" + encoded + "\"; filename*=UTF-8''" + encoded;
        String probed = null;
        try { probed = Files.probeContentType(path); } catch (IOException ignore) {}
        MediaType mediaType = (probed != null) ? MediaType.parseMediaType(probed) : MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.ok()
                .contentType(mediaType)
                .contentLength(file.length())
                .header(HttpHeaders.CONTENT_DISPOSITION, cd)
                .body(resource);
    }
    
    /** ✅ 교수 피드백 저장 */
    @PostMapping("/feedback")
    public ResponseEntity<?> saveFeedback(@RequestParam("submitId") String submitId,
                                          @RequestParam("profId") String profId,
                                          @RequestParam("feedbackText") String feedbackText) {
        try {
            HomeworkSubmitVO vo = submitService.getSubmitById(submitId);
            if (vo == null) {
                return ResponseEntity.status(404).body(Map.of("ok", false, "message", "제출 내역 없음"));
            }

            vo.setHwsubFeedback(feedbackText);
            // vo.setProfId(profId); ← VO에 필드 있으면 저장

            submitService.updateFeedback(vo);

            return ResponseEntity.ok(Map.of("ok", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("ok", false, "message", e.getMessage()));
        }
    }
}