package com.camp_us.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.camp_us.dto.MemberVO;
import com.camp_us.service.MemberService;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "*")
public class MemberController2 {

    private final MemberService service;

    public MemberController2(MemberService service) {
        this.service = service;
    }

    // 실제 파일 저장 위치 (환경에 맞게 수정)
    private final String picturePath = "C:/member/picture/upload/"; 

    /** ✅ 프로필 사진 가져오기 */
    @GetMapping("/getPicture")
    public ResponseEntity<?> getPicture(@RequestParam("memId") String memId) {
        try {
            MemberVO member = service.getMemberById(memId);
            if (member == null || member.getPicture() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("ok", false, "message", "picture not found"));
            }

            Path img = Paths.get(picturePath, member.getPicture());
            if (!Files.exists(img)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("ok", false, "message", "file not found"));
            }

            byte[] bytes = Files.readAllBytes(img);
            String ct = Files.probeContentType(img);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(ct != null ? MediaType.parseMediaType(ct)
                                              : MediaType.APPLICATION_OCTET_STREAM);
            headers.setCacheControl("no-cache, no-store, must-revalidate");

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("ok", false, "message", e.getMessage()));
        }
    }

    /** ✅ 프로필 사진 업로드 */
    @PostMapping("/profile")
    public ResponseEntity<?> uploadProfile(
            @RequestParam("memId") String memId,
            @RequestParam("pictureFile") MultipartFile pictureFile) throws IOException {

        if (memId == null || memId.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "message", "memId required"));
        }
        if (pictureFile == null || pictureFile.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "message", "pictureFile required"));
        }

        File dir = new File(picturePath);
        if (!dir.exists()) dir.mkdirs();

        // 저장 파일명: {memId}_{timestamp}.확장자
        String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
        String newName = memId + "_" + System.currentTimeMillis() + (ext.isEmpty() ? "" : "." + ext);

        File saveFile = new File(dir, newName);
        pictureFile.transferTo(saveFile);

        // DB에는 파일명만 저장
        service.updatePicture(memId, newName);

        return ResponseEntity.ok(Map.of(
            "ok", true,
            "message", "Profile updated",
            "file", newName
        ));
    }

    /** ✅ 비밀번호 변경 */
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String,String> body,
                                            @RequestParam("memId") String memId) throws Exception {
        String currentPw = body.getOrDefault("currentPw", "");
        String newPw     = body.getOrDefault("newPw", "");

        MemberVO member = service.getMemberById(memId);
        if (member == null) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "member not found"));
        }

        boolean ok = service.changePassword(memId, currentPw, newPw);
        if (ok) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.ok(Map.of("success", false, "message", "현재 비밀번호가 일치하지 않습니다."));
        }
    }
}