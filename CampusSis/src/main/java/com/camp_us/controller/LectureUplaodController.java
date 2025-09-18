package com.camp_us.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camp_us.dto.LectureVO;
import com.camp_us.service.LectureService;

@Controller
@RequestMapping("/lecture")
public class LectureUplaodController {

	
	@GetMapping("/list")
	public Object listOrStream(@RequestParam("lec_id") String lec_id,
	                           @RequestParam(value="stream", required=false) String stream,
	                           Model model) {
	    if ("1".equals(stream)) {
	        String path = lectureService.getPlanFilePathByLecId(lec_id);
	        if (path == null || path.trim().isEmpty()) return ResponseEntity.notFound().build();
	        File file = new File(path);
	        if (!file.exists() || !file.isFile()) return ResponseEntity.notFound().build();

	        FileSystemResource body = new FileSystemResource(file);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentLength(file.length());
	        headers.set(HttpHeaders.CONTENT_DISPOSITION,
	            "inline; filename*=UTF-8''" + URLEncoder.encode(file.getName(), StandardCharsets.UTF_8));
	        return new ResponseEntity<>(body, headers, HttpStatus.OK);
	    }

	    // 여기부터는 JSP 렌더
	    model.addAttribute("lec_id", lec_id);
	    System.out.println("리스트 강의 아이디 : " + lec_id);
	    String path = lectureService.getPlanFilePathByLecId(lec_id);
	    System.out.println("강의계획서 경로는 "  + path);
	    boolean hasPlan = (path != null && !path.trim().isEmpty() && new File(path).exists());
	    model.addAttribute("hasPlan", hasPlan);
	    if (hasPlan) {
	        String planUrl = "/lecture/list?lec_id=" + URLEncoder.encode(lec_id, StandardCharsets.UTF_8)
	                       + "&stream=1";
	        model.addAttribute("planUrl", planUrl);
	    }
	    return "lecture/list";
	}
 
    @GetMapping("/upload")
    public String uploadForm() {
        return "lecture/upload";
    }
    
    @Resource(name = "lectureService")
    private LectureService lectureService;

    @GetMapping("/finalupload")
    public String finalUploadForm(Model model) {
        return "lecture/final";
    }

    @PostMapping("/finalupload")
    public String uploadFinal(@RequestParam("planFile") MultipartFile planFile) {
        String courseId = "JAVA101";
        try {
            lectureService.saveFinalPlan(courseId, planFile);
        } catch (Exception e) {
            e.printStackTrace();
            return "errorPage";
        }
        return "redirect:/lecture/success";
    }
    
    @GetMapping("/success")
    public String success() {
        return "lecture/success";
    }
}
