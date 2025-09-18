package com.camp_us.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.camp_us.command.HomeworkModifyCommand;
import com.camp_us.dto.HomeworkSubmitVO;
import com.camp_us.dto.HomeworkVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.service.HomeworkService;
import com.camp_us.service.HomeworkSubmitService;

@Controller
@RequestMapping("/homeworksubmit")
public class HomeworkSubmitController {

    @Autowired
    private HomeworkSubmitService submitService;

    @Autowired
    private HomeworkService homeworkService;

    private static final String UPLOAD_DIR = "C:/upload/homework/";

    /* =========================
       1) 과제 제출 (학생)
       - JSP 폼 name과 일치: hwNo, lecsId, hwsubComment, uploadFile
       - URL: POST /homeworksubmit/submit
       ========================= */
    @PostMapping("/submit")
    public String submitHomework(
            @RequestParam("hwNo") int hwNo,
            @RequestParam("lecsId") String lecsId,
            @RequestParam("hwsubComment") String hwsubComment,
            @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
            HttpSession session) throws Exception
    {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        String stuId = loginUser.getMem_id();

        HomeworkSubmitVO submitVO = new HomeworkSubmitVO();
        submitVO.setHwsubHsno(UUID.randomUUID().toString());
        submitVO.setHwNo(hwNo);
        submitVO.setLecsId(lecsId);
        submitVO.setStuId(stuId);
        submitVO.setHwsubComment(hwsubComment);
        submitVO.setHwsubStatus("제출완료");

        // 파일 저장 (선택)
        if (uploadFile != null && !uploadFile.isEmpty()) {
            String savedFileName = UUID.randomUUID() + "_" + uploadFile.getOriginalFilename();
            File dest = new File(UPLOAD_DIR + savedFileName);
            dest.getParentFile().mkdirs();
            uploadFile.transferTo(dest);
            submitVO.setHwsubFilename(savedFileName);
        } else {
            submitVO.setHwsubFilename(null); // Mapper에서 jdbcType=VARCHAR 지정 필요
        }

        submitService.regist(submitVO);

        // 새 경로로 리다이렉트
        return "redirect:/homework/studentsdetail?hwNo=" + hwNo + "&submitted=true";
    }

    /* =========================
       2) 과제 수정 폼 (페이지 방식)
       ========================= */
    @GetMapping("/modify")
    public String editSubmitForm(@RequestParam("hwNo") int hwNo, Model model, HttpSession session) throws Exception {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        String stdId = loginUser.getMem_id();

        HomeworkSubmitVO submit = submitService.getSubmitByHwNoAndStdId(hwNo, stdId);
        HomeworkVO homework = homeworkService.getHomeworkDetail(hwNo);

        model.addAttribute("submit", submit);
        model.addAttribute("homework", homework);

        return "homework/modify";
    }

    /* =========================
       3) 과제 수정 처리 (페이지 방식, POST /homeworksubmit/modify)
       - 폼 name: hwNo, lecsId, hwsubComment, file
       ========================= */
    @PostMapping("/modify")
    public String editSubmit(@ModelAttribute HomeworkModifyCommand command,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) throws Exception
    {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        String stdId = loginUser.getMem_id();

        HomeworkSubmitVO submit = command.toHomeworkSubmitVO();
        submit.setStuId(stdId);

        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(UPLOAD_DIR + fileName);
            dest.getParentFile().mkdirs();
            file.transferTo(dest);
            submit.setHwsubFilename(fileName);
        }

        submitService.updateSubmit(submit);
        redirectAttributes.addFlashAttribute("submitted", true);

        return "redirect:/homework/studentsdetail?hwNo=" + submit.getHwNo();
    }

    /* =========================
       4) 과제 수정 (AJAX, POST /homeworksubmit/edit)
       - JS fetch URL 맞추기: /homeworksubmit/edit
       - 폼 name: hwNo, lecsId, hwsubComment, uploadFile
       ========================= */
    @PostMapping("/edit")
    @ResponseBody
    public String editSubmitAjax(@ModelAttribute HomeworkSubmitVO submit,
                                 @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
                                 HttpSession session) {
        try {
            MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
            submit.setStuId(loginUser.getMem_id());

            if (uploadFile != null && !uploadFile.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + uploadFile.getOriginalFilename();
                File dest = new File(UPLOAD_DIR + fileName);
                dest.getParentFile().mkdirs();
                uploadFile.transferTo(dest);
                submit.setHwsubFilename(fileName);
            }
            submitService.updateSubmit(submit);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /* =========================
       5) 제출 상세(피드백 입력 폼)
       ========================= */
    @GetMapping("/detail")
    public String submissionDetail(@RequestParam("submitId") String submitId, Model model) throws Exception {
        HomeworkSubmitVO submit = submitService.getSubmitById(submitId);
        model.addAttribute("submit", submit);
        return "homework/feedbackform";
    }

    /* =========================
       6) 피드백 등록
       ========================= */
    @PostMapping("/feedback")
    public String registerFeedback(@ModelAttribute HomeworkSubmitVO submit) throws Exception {
        submitService.updateFeedback(submit);
        return "homework/close";
    }

    /* =========================
       7) 파일 다운로드
       ========================= */
    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@RequestParam("filename") String filename) throws IOException {
        File file = new File(UPLOAD_DIR + filename);
        if (!file.exists()) {
            // 바디 타입을 맞춰 줌
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body((Resource) null);
            // 또는: return ResponseEntity.status(HttpStatus.NOT_FOUND).<Resource>body(null);
        }

        Path path = file.toPath();
        Resource resource = new UrlResource(path.toUri());

        String original = filename;
        int idx = filename.indexOf('_');
        if (idx >= 0 && idx + 1 < filename.length()) original = filename.substring(idx + 1);

        String encoded = UriUtils.encode(original, "UTF-8"); // 스프링 5.x 호환

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
}
