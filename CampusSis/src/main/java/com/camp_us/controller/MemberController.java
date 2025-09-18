package com.camp_us.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camp_us.dto.MemberVO;
import com.camp_us.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @Resource(name = "picturePath")
    private String picturePath;

    @GetMapping("/getPicture")
    @ResponseBody
    public ResponseEntity<byte[]> getPicture(String id) {
        try {
            MemberVO member = service.getMember(id);
            if (member == null || member.getPicture() == null) {
                return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
            }

            Path img = Paths.get(picturePath, member.getPicture());
            if (!Files.exists(img)) {
                return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
            }

            byte[] bytes = Files.readAllBytes(img);
            String ct = Files.probeContentType(img);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(ct != null ? MediaType.parseMediaType(ct)
                                              : MediaType.APPLICATION_OCTET_STREAM);
            headers.setCacheControl("no-cache, no-store, must-revalidate");

            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profile")
    public String updateProfile(String mem_id,
                                MultipartFile pictureFile,
                                String pictureChanged,
                                HttpSession session,
                                RedirectAttributes rttr) throws IOException {
        MemberVO login = (MemberVO) session.getAttribute("loginUser");
        if (login == null) return "redirect:/login";

        if ("true".equals(pictureChanged) && pictureFile != null && !pictureFile.isEmpty()) {
            Files.createDirectories(Paths.get(picturePath));

            String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
            String newName = login.getMem_id() + "_" + System.currentTimeMillis() + (ext.isEmpty() ? "" : "." + ext);

            Path target = Paths.get(picturePath, newName);
            pictureFile.transferTo(target.toFile());

            if (login.getPicture() != null && !login.getPicture().isBlank()) {
                try { Files.deleteIfExists(Paths.get(picturePath, login.getPicture())); } catch (Exception ignore) {}
            }

            service.updatePicture(login.getMem_id(), newName);

            login.setPicture(newName);
            session.setAttribute("loginUser", login);
        }

        rttr.addFlashAttribute("msg", "saved");
        return "redirect:/mypage";
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String,String> body,
                                            HttpSession session) throws Exception {
        String currentPw = body.getOrDefault("currentPw", "");
        String newPw     = body.getOrDefault("newPw", "");

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.ok(Map.of("success", false, "message", "로그인이 필요합니다."));
        }

        boolean ok = service.changePassword(loginUser.getMem_id(), currentPw, newPw); // ★ encoder 제거
        if (ok) {
            session.invalidate(); // 선택
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.ok(Map.of("success", false, "message", "현재 비밀번호가 일치하지 않습니다."));
        }
    }
}