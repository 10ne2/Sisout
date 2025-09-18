package com.camp_us.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.LecNoticeVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.service.LecNoticeService;
import com.camp_us.service.MemberService;

@RestController
@RequestMapping("/api/lecnotice")
public class LecNoticeController2 {

    private final LecNoticeService lecNoticeService;

    @Autowired
    private SqlSession sqlSession;
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    public LecNoticeController2(LecNoticeService lecNoticeService) {
        this.lecNoticeService = lecNoticeService;
    }

    // 전공(강의) 선택을 세션에 저장 (모바일에서 드롭다운 선택 시 호출)
    @PostMapping("/changeMajor")
    public Map<String, Object> changeMajor(@RequestParam("lec_id") String lecId, HttpSession session) {
        if (lecId == null || lecId.isBlank()) return Map.of("ok", false, "reason", "lec_id is blank");
        session.setAttribute("selectedLecId", lecId);
        return Map.of("ok", true, "lec_id", lecId);
    }

    // 목록 (JSON)
    @GetMapping("")
    public Map<String, Object> list(
    		@RequestParam String memId,
            @RequestParam  String lecId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "perPage", defaultValue = "10") int perPage,
            @RequestParam(value = "searchType", defaultValue = "") String searchType,
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            HttpSession session) throws Exception {

        if (lecId == null || lecId.isBlank()) {
            Object sel = session.getAttribute("selectedLecId");
            lecId = sel == null ? "" : String.valueOf(sel);
        }

        PageMakerMJ pm = new PageMakerMJ();
        pm.setPage(page);
        pm.setPerPageNum(perPage);
        pm.setSearchType(searchType);
        pm.setKeyword(keyword);
        pm.setLecId(lecId);

        int totalCount = lecNoticeService.getTotalCount(pm);
        List<LecNoticeVO> items = lecNoticeService.getLecNoticeList(pm);
        int totalPage = (int) Math.ceil((double) totalCount / perPage);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("page", page);
        pagination.put("perPage", perPage);
        pagination.put("totalCount", totalCount);
        pagination.put("totalPage", totalPage);

        Map<String, Object> res = new HashMap<>();
        res.put("ok", true);
        res.put("lec_id", lecId);
        res.put("items", items);
        res.put("page", pagination);
        return res;
    }

    // 상세 (조회수 쿠키로 1시간 중복방지)
    @GetMapping("/{id}")
    public Map<String, Object> detail(
            @PathVariable("id") String lecNoticeId,
            @RequestParam(value = "increaseView", defaultValue = "true") boolean increaseView,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        if (increaseView) {
            String cookieName = "lecnotice_viewed_" + lecNoticeId;
            boolean viewed = false;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (cookieName.equals(c.getName())) { viewed = true; break; }
                }
            }
            if (!viewed) {
                lecNoticeService.updateViewCount(lecNoticeId);
                Cookie ck = new Cookie(cookieName, "1");
                ck.setMaxAge(60 * 60); // 1시간
                ck.setPath(request.getContextPath());
                response.addCookie(ck);
            }
        }

        LecNoticeVO item = lecNoticeService.getLecNoticeById(lecNoticeId);
        return Map.of("ok", true, "item", item);
    }

    // 등록 (JSON 전송용) : 파일 없이 텍스트만
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> createJson(
    		@RequestParam String memId,
            @RequestBody LecNoticeVO body,
            HttpSession session) throws Exception {

        // lec_id 보정
        if (body.getLecId() == null || body.getLecId().isBlank()) {
            Object sel = session.getAttribute("selectedLecId");
            if (sel == null) return Map.of("ok", false, "reason", "lec_id is blank");
            body.setLecId(String.valueOf(sel));
        }

        MemberVO loginUser = memberService.getMemberById(memId);
        if (loginUser == null) return Map.of("ok", false, "reason", "로그인 필요");

        String profesId = sqlSession.selectOne("LecNoticeMapper.selectProfesIdByMemId", memId);
        if (profesId == null) return Map.of("ok", false, "reason", "교수 ID를 찾을 수 없습니다.");

        Integer owns = sqlSession.selectOne("LecNoticeMapper.countProLecByIds",
                Map.of("profesId", profesId, "lecId", body.getLecId()));
        if (owns == null || owns == 0) return Map.of("ok", false, "reason", "담당 강의가 아닙니다.");

        int nextId = lecNoticeService.getMaxLecNoticeId() + 1;
        body.setLecNoticeId(String.valueOf(nextId));
        body.setProfesId(profesId);
        body.setLecNoticeDate(new Timestamp(System.currentTimeMillis()));
        if (body.getFileName() == null || body.getFileName().isBlank()) body.setFileName("none.pdf");
        if (body.getFileDetail() == null || body.getFileDetail().isBlank()) body.setFileDetail("none.pdf");

        lecNoticeService.registLecNotice(body);
        return Map.of("ok", true, "lecNoticeId", body.getLecNoticeId());
    }

 // 등록 (Multipart 전송용) : 파일 업로드 포함
    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> createMultipart(
            @RequestParam String memId,
            @RequestParam(value = "lecId", required = false) String lecId,
            @RequestParam("lecNoticeName") String lecNoticeName,
            @RequestParam("lecNoticeDesc") String lecNoticeDesc,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            HttpServletRequest request,
            HttpSession session) throws Exception {

        if (lecId == null || lecId.isBlank()) {
            Object sel = session.getAttribute("selectedLecId");
            if (sel == null) return Map.of("ok", false, "reason", "lecId is blank");
            lecId = String.valueOf(sel);
        }

        // 1. memId → MemberVO 조회
        MemberVO loginUser = memberService.getMemberById(memId);
        if (loginUser == null) return Map.of("ok", false, "reason", "로그인 필요");

        // 2. memId → profesId 보정
        String profesId = sqlSession.selectOne("LecNoticeMapper.selectProfesIdByMemId", memId);
        if (profesId == null) return Map.of("ok", false, "reason", "profesId not found");

        // 3. 해당 교수(profesId)가 이 강의(lecId) 담당인지 확인
        Integer owns = sqlSession.selectOne("LecNoticeMapper.countProLecByIds",
                Map.of("profesId", profesId, "lecId", lecId));
        if (owns == null || owns == 0)
            return Map.of("ok", false, "reason", "담당 강의가 아닙니다.");

        // 4. 새 ID 생성
        int nextId = lecNoticeService.getMaxLecNoticeId() + 1;

        // 5. 파일 저장
        String uploadPath = request.getServletContext().getRealPath("/resources/upload/lecnotice");
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        String fileName = "none.pdf";
        String fileDetail = "none.pdf";
        if (files != null) {
            int idx = 0;
            for (MultipartFile mf : files) {
                if (mf != null && !mf.isEmpty()) {
                    String saved = UUID.randomUUID() + "_" + mf.getOriginalFilename();
                    mf.transferTo(new File(dir, saved));
                    if (idx == 0) fileName = saved;
                    else if (idx == 1) fileDetail = saved;
                    if (++idx >= 2) break;
                }
            }
        }

        // 6. LecNoticeVO 세팅
        LecNoticeVO vo = new LecNoticeVO();
        vo.setLecNoticeId(String.valueOf(nextId));
        vo.setLecId(lecId);
        vo.setProfesId(profesId); // ✅ 보정된 profesId 저장
        vo.setLecNoticeName(lecNoticeName);
        vo.setLecNoticeDesc(lecNoticeDesc);
        vo.setLecNoticeDate(new Timestamp(System.currentTimeMillis()));
        vo.setFileName(fileName);
        vo.setFileDetail(fileDetail);

        lecNoticeService.registLecNotice(vo);

        return Map.of("ok", true, "lecNoticeId", vo.getLecNoticeId(),
                "fileName", fileName, "fileDetail", fileDetail);
    }



    @PostMapping(
    	    path = "/{id}/update",
    	    produces = "application/json; charset=UTF-8"   // 응답 타입 JSON 명시
    	)
    	public Map<String, Object> updateMultipart(
    	        @PathVariable("id") String id,
    	        @RequestParam("lecNoticeName") String lecNoticeName,
    	        @RequestParam("lecNoticeDesc") String lecNoticeDesc,
    	        @RequestParam(value = "removeFile1", required = false) String remove1,
    	        @RequestParam(value = "removeFile2", required = false) String remove2,
    	        @RequestParam(value = "files", required = false) List<MultipartFile> files,
    	        HttpServletRequest request) throws Exception {

        LecNoticeVO origin = lecNoticeService.getLecNoticeById(id);
        if (origin == null) return Map.of("ok", false, "reason", "not found");

        String uploadPath = request.getServletContext().getRealPath("/resources/upload/lecnotice");
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        String fileName = origin.getFileName();
        String fileDetail = origin.getFileDetail();
        if ("on".equalsIgnoreCase(remove1)) fileName = "none.pdf";
        if ("on".equalsIgnoreCase(remove2)) fileDetail = "none.pdf";

        if (files != null) {
            int idx = 0;
            for (MultipartFile mf : files) {
                if (mf != null && !mf.isEmpty()) {
                    String saved = UUID.randomUUID() + "_" + mf.getOriginalFilename();
                    mf.transferTo(new File(dir, saved));
                    if (idx == 0) fileName = saved; else if (idx == 1) fileDetail = saved;
                    if (++idx >= 2) break;
                }
            }
        }

        origin.setLecNoticeName(lecNoticeName);
        origin.setLecNoticeDesc(lecNoticeDesc);
        origin.setFileName(fileName);
        origin.setFileDetail(fileDetail);

        lecNoticeService.modifyLecNotice(origin);
        return Map.of("ok", true);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") String id) throws Exception {
        lecNoticeService.removeLecNotice(id);
        return Map.of("ok", true);
    }
}