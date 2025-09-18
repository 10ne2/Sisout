package com.camp_us.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.LecNoticeVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.service.LecNoticeService;

@Controller
@RequestMapping("/lecnotice")
public class LecNoticeController {

    private final LecNoticeService lecNoticeService;

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    public LecNoticeController(LecNoticeService lecNoticeService) {
        this.lecNoticeService = lecNoticeService;
    }

    // ▼ 전공 변경(세션 저장) — /lecnotice/changeMajor
    @GetMapping("/changeMajor")
    @ResponseBody
    public Map<String,Object> changeMajor(@RequestParam("lec_id") String lecId, HttpSession session) {
        if (lecId == null || lecId.isBlank()) {
            return Map.of("ok", false, "reason", "lec_id is blank");
        }
        session.setAttribute("selectedLecId", lecId);
        return Map.of("ok", true, "lec_id", lecId);
    }

    // 목록
    @GetMapping("/list")
    public String lecNoticeList(
            @RequestParam(value = "lec_id", required = false) String lecId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "searchType", defaultValue = "") String searchType,
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            HttpSession session,
            Model model) {
        try {
            if (lecId == null || lecId.isBlank()) {
                lecId = (String) session.getAttribute("selectedLecId");
            }
            if (lecId == null || lecId.isBlank()) {
                // 강의 미선택 방지 (필요 시 리다이렉트나 메시지)
                lecId = "";
            }

            PageMakerMJ pageMaker = new PageMakerMJ();
            pageMaker.setPage(page);
            pageMaker.setPerPageNum(10);
            pageMaker.setSearchType(searchType);
            pageMaker.setKeyword(keyword);
            pageMaker.setLecId(lecId); // ★ 핵심

            int totalCount = lecNoticeService.getTotalCount(pageMaker);
            pageMaker.setTotalCount(totalCount);
            int totalPage = (int)Math.ceil((double)totalCount / pageMaker.getPerPageNum());

            model.addAttribute("lecNoticeList", lecNoticeService.getLecNoticeList(pageMaker));
            model.addAttribute("pageMaker", pageMaker);
            model.addAttribute("page", page);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("searchType", searchType);
            model.addAttribute("keyword", keyword);
            model.addAttribute("lec_id", lecId); // JSP에서 링크 유지용
            return "lecnotice/list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "공지사항 목록을 불러오는 중 오류가 발생했습니다.");
            return "lecnotice/list";
        }
    }

    // 작성 폼
    @GetMapping("/write")
    public String writeForm() { return "lecnotice/write"; }

    // 등록
    @PostMapping("/write")
    public String lecNoticeWrite(@ModelAttribute LecNoticeVO lecNotice,
                                 @RequestParam(value = "files", required = false) List<MultipartFile> files,
                                 @RequestParam(value = "lec_id", required = false) String lecId, // 파라미터 없을 수 있음
                                 HttpServletRequest request,
                                 HttpSession session,
                                 Model model) {
        try {
            // lec_id 보정: 파라미터 → 세션(selectedLecId) 순으로 채움
            if (lecId == null || lecId.isBlank()) {
                lecId = (String) session.getAttribute("selectedLecId");
            }
            if (lecId == null || lecId.isBlank()) {
                throw new IllegalStateException("lec_id가 비어있습니다. 전공을 먼저 선택하세요.");
            }

            // PK 생성
            int nextId = lecNoticeService.getMaxLecNoticeId() + 1;
            lecNotice.setLecNoticeId(String.valueOf(nextId));
            lecNotice.setLecId(lecId);

            // 로그인 사용자 확인
            MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
            if (loginUser == null) throw new IllegalStateException("로그인 정보가 없습니다.");

            String memId = loginUser.getMem_id();

            // mem_id → profes_id 조회
            String profesId = sqlSession.selectOne("LecNoticeMapper.selectProfesIdByMemId", memId);
            if (profesId == null) {
                throw new IllegalStateException("교수 ID를 찾을 수 없습니다. (mem_id=" + memId + ")");
            }
            lecNotice.setProfesId(profesId);

            // 담당 강의 여부 체크(있으면 1 이상)
            Integer owns = sqlSession.selectOne("LecNoticeMapper.countProLecByIds",
                    Map.of("profesId", profesId, "lecId", lecId));
            if (owns == null || owns == 0) {
                throw new IllegalStateException("담당 강의가 아닙니다. lec_id=" + lecId);
            }

            // 날짜
            lecNotice.setLecNoticeDate(new java.sql.Timestamp(System.currentTimeMillis()));

            // 파일 업로드
            String uploadPath = request.getServletContext().getRealPath("/resources/upload/lecnotice");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String fileName = "none.pdf";
            String fileDetail = "none.pdf";

            if (files != null) {
                int idx = 0;
                for (MultipartFile mf : files) {
                    if (mf != null && !mf.isEmpty()) {
                        String saved = UUID.randomUUID() + "_" + mf.getOriginalFilename();
                        mf.transferTo(new File(uploadDir, saved));
                        if (idx == 0) fileName = saved;
                        else if (idx == 1) fileDetail = saved;
                        if (++idx >= 2) break;
                    }
                }
            }
            lecNotice.setFileName(fileName);
            lecNotice.setFileDetail(fileDetail);

            // 저장
            lecNoticeService.registLecNotice(lecNotice);
            model.addAttribute("success", true);
            return "lecnotice/write";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "등록 실패: " + e.getMessage());
            return "lecnotice/write";
        }
    }

    // 상세
    @GetMapping("/detail")
    public String lecNoticeDetail(@RequestParam("lecNoticeId") String lecNoticeId,
                                  @RequestParam(value="mode", required=false) String mode,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  Model model) {
        try {
            String cookieName = "lecnotice_viewed_" + lecNoticeId;
            boolean hasViewed = false;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) for (Cookie ck : cookies) {
                if (cookieName.equals(ck.getName())) { hasViewed = true; break; }
            }
            System.out.println("[LecNotice] detail id="+lecNoticeId+" hasViewed="+hasViewed);

            if (!hasViewed) {
                lecNoticeService.updateViewCount(lecNoticeId);
                Cookie c = new Cookie(cookieName, "1");
                c.setMaxAge(60 * 60);
                c.setPath(request.getContextPath()); // "/campus" 로 제한
                response.addCookie(c);
            }

            LecNoticeVO lecNotice = lecNoticeService.getLecNoticeById(lecNoticeId);
            model.addAttribute("lecNotice", lecNotice);
            model.addAttribute("editMode", "edit".equalsIgnoreCase(mode));
            return "lecnotice/detail";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "공지사항을 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }

    // 수정
    @PostMapping("/update")
    public String lecNoticeUpdate(@ModelAttribute LecNoticeVO form,
                                  @RequestParam(value = "files", required = false) List<MultipartFile> files,
                                  @RequestParam(value = "removeFile1", required = false) String remove1,
                                  @RequestParam(value = "removeFile2", required = false) String remove2,
                                  HttpServletRequest request,
                                  Model model) {
        try {
            LecNoticeVO origin = lecNoticeService.getLecNoticeById(form.getLecNoticeId());

            String uploadPath = request.getServletContext().getRealPath("/resources/upload/lecnotice");
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            String fileName = origin.getFileName();
            String fileDetail = origin.getFileDetail();
            if ("on".equals(remove1)) fileName = "none.pdf";
            if ("on".equals(remove2)) fileDetail = "none.pdf";

            if (files != null) {
                int idx = 0;
                for (MultipartFile mf : files) {
                    if (mf != null && !mf.isEmpty()) {
                        String saved = UUID.randomUUID() + "_" + mf.getOriginalFilename();
                        mf.transferTo(new File(uploadPath, saved));
                        if (idx == 0) fileName = saved;
                        else if (idx == 1) fileDetail = saved;
                        if (++idx >= 2) break;
                    }
                }
            }

            origin.setLecNoticeName(form.getLecNoticeName());
            origin.setLecNoticeDesc(form.getLecNoticeDesc());
            origin.setFileName(fileName);
            origin.setFileDetail(fileDetail);

            lecNoticeService.modifyLecNotice(origin);
            return "redirect:/lecnotice/detail?lecNoticeId=" + origin.getLecNoticeId();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "수정 중 오류가 발생했습니다.");
            return "error";
        }
    }

    // 삭제
    @GetMapping("/delete")
    public String lecNoticeDelete(@RequestParam("lecNoticeId") String lecNoticeId) {
        try {
            lecNoticeService.removeLecNotice(lecNoticeId);
            return "redirect:/lecnotice/list";
        } catch (Exception e) {
            return "error";
        }
    }

    // 파일 다운로드
    @GetMapping("/file")
    public void download(@RequestParam("lecNoticeId") String id,
                         @RequestParam("which") int which,
                         HttpServletRequest req,
                         HttpServletResponse resp) throws Exception {
        LecNoticeVO n = lecNoticeService.getLecNoticeById(id);
        String fname = (which == 2) ? n.getFileDetail() : n.getFileName();
        if (fname == null || "none.pdf".equals(fname)) { resp.setStatus(404); return; }

        File f = new File(req.getServletContext()
                .getRealPath("/resources/upload/lecnotice/" + fname));
        if (!f.exists()) { resp.setStatus(404); return; }

        resp.setHeader("Content-Disposition", "attachment; filename=\"" + fname + "\"");
        resp.setContentLengthLong(f.length());
        java.nio.file.Files.copy(f.toPath(), resp.getOutputStream());
    }
    
   
}
