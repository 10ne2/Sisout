package com.camp_us.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.BoardVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.service.BoardService;
import com.camp_us.service.ReplyService;

@RestController
@RequestMapping("/api/board")
public class BoardController2 {

    private final BoardService boardService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    public BoardController2(BoardService boardService) {
        this.boardService = boardService;
    }

    // 전공(강의) 선택 세션 저장
    @PostMapping("/changeMajor")
    public Map<String, Object> changeMajor(@RequestParam("lec_id") String lecId, HttpSession session) {
        if (lecId == null || lecId.isBlank()) return Map.of("ok", false, "reason", "lec_id is blank");
        session.setAttribute("selectedLecId", lecId);
        return Map.of("ok", true, "lec_id", lecId);
    }

    // 목록
    @GetMapping("")
    public Map<String, Object> list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "perPage", defaultValue = "10") int perPage,
            @RequestParam(value = "searchType", defaultValue = "") String searchType,
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "category", required = false, defaultValue = "") String category,
            @RequestParam(value = "lecId", required = false, defaultValue = "") String lecId
    ) throws Exception {

        PageMakerMJ pm = new PageMakerMJ();
        pm.setPage(page);
        pm.setPerPageNum(perPage);
        pm.setSearchType(searchType);
        pm.setKeyword(keyword);

        try { pm.getClass().getMethod("setCategory", String.class).invoke(pm, category); } catch (Exception ignore) {}
        try { pm.getClass().getMethod("setLecId", String.class).invoke(pm, lecId); } catch (Exception ignore) {}

        int totalCount = boardService.getTotalCount(pm);
        List<BoardVO> items = boardService.selectBoardList(pm);
        int totalPage = (int) Math.ceil((double) totalCount / perPage);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("page", page);
        pagination.put("perPage", perPage);
        pagination.put("totalCount", totalCount);
        pagination.put("totalPage", totalPage);

        Map<String, Object> res = new HashMap<>();
        res.put("ok", true);
        res.put("items", items);
        res.put("page", pagination);
        return res;
    }

    // 상세
    @GetMapping("/{id}")
    public Map<String, Object> detail(@PathVariable("id") String boardId) throws Exception {
        BoardVO item = boardService.selectBoardByNo(boardId);
        return Map.of("ok", true, "item", item);
    }

    // 등록 (JSON)
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> createJson(@RequestBody BoardVO body, HttpSession session) throws Exception {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) return fail("로그인 필요");

        body.setMemId(loginUser.getMem_id());
        if (body.getBoardDesc() == null || body.getBoardDesc().trim().isEmpty()) {
            body.setBoardDesc(body.getBoardContent());
        }
        if (body.getBoardDate() == null) {
            body.setBoardDate(new Timestamp(System.currentTimeMillis()));
        }
        if (body.getPfileName() == null || body.getPfileName().trim().isEmpty()) body.setPfileName("none.pdf");
        if (body.getPfileDetail() == null) body.setPfileDetail("");

        boardService.insertBoard(body);
        return Map.of("ok", true, "boardId", body.getBoardId());
    }

    // 등록 (Multipart)
    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> createMultipart(
            @RequestParam("boardName") String boardName,
            @RequestParam(value = "boardContent", required = false, defaultValue = "") String boardContent,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            HttpSession session
    ) throws Exception {

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) return fail("로그인 필요");

        String uploadDir = "C:/campus_upload";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String pfileName = "none.pdf";
        String pfileDetail = "";

        if (files != null && !files.isEmpty()) {
            MultipartFile mf = files.get(0);
            if (mf != null && !mf.isEmpty()) {
                String originalName = mf.getOriginalFilename();
                String saveName = UUID.randomUUID() + "_" + originalName;
                mf.transferTo(new File(dir, saveName));
                pfileName = saveName;
                pfileDetail = originalName;
            }
        }

        BoardVO vo = new BoardVO();
        vo.setBoardName(boardName);
        vo.setBoardContent(boardContent);
        vo.setBoardDesc(boardContent);
        vo.setMemId(loginUser.getMem_id());
        vo.setBoardDate(new Timestamp(System.currentTimeMillis()));
        vo.setPfileName(pfileName);
        vo.setPfileDetail(pfileDetail);

        boardService.insertBoard(vo);
        return Map.of("ok", true, "boardId", vo.getBoardId(), "fileName", pfileName, "fileDetail", pfileDetail);
    }

    // 수정 (JSON)
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateJson(
            @PathVariable("id") String id,
            @RequestBody BoardVO body,
            HttpSession session) throws Exception {

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) return fail("로그인 필요");

        BoardVO origin = boardService.selectBoardByNo(id);
        if (origin == null) return fail("not found");
        if (!canModify(loginUser, origin)) return fail("권한 없음");

        if (body.getBoardName() != null) origin.setBoardName(body.getBoardName());
        if (body.getBoardDesc() != null) origin.setBoardDesc(body.getBoardDesc());
        if (body.getBoardContent() != null) origin.setBoardContent(body.getBoardContent());
        if (body.getPfileName() != null) origin.setPfileName(body.getPfileName());
        if (body.getPfileDetail() != null) origin.setPfileDetail(body.getPfileDetail());

        boardService.updateBoard(origin);
        return ok();
    }

    // 수정 (Multipart)
    @PostMapping(
    	    path = "/{id}/update",
    	    produces = "application/json; charset=UTF-8"
    	)
    	public Map<String, Object> updateMultipart(
    	        @PathVariable("id") String id,
    	        @RequestParam("boardName") String boardName,
    	        @RequestParam(value = "boardContent", required = false, defaultValue = "") String boardContent,
    	        @RequestParam(value = "removeFile", required = false) String removeFile,
    	        @RequestParam(value = "files", required = false) List<MultipartFile> files,
    	        HttpSession session) throws Exception {

    	    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
    	    if (loginUser == null) return fail("로그인 필요");

    	    BoardVO origin = boardService.selectBoardByNo(id);
    	    if (origin == null) return fail("not found");
    	    if (!canModify(loginUser, origin)) return fail("권한 없음");

    	    String uploadDir = "C:/campus_upload";
    	    File dir = new File(uploadDir);
    	    if (!dir.exists()) dir.mkdirs();

    	    // 기존 파일 유지
    	    String pfileName = origin.getPfileName();
    	    String pfileDetail = origin.getPfileDetail();

    	    // 파일 삭제 체크
    	    if ("on".equalsIgnoreCase(removeFile)) {
    	        pfileName = "none.pdf";
    	        pfileDetail = "";
    	    }

    	    // 새 파일 업로드
    	    if (files != null) {
    	        for (MultipartFile mf : files) {
    	            if (mf != null && !mf.isEmpty()) {
    	                String originalName = mf.getOriginalFilename();
    	                String saveName = UUID.randomUUID() + "_" + originalName;
    	                mf.transferTo(new File(dir, saveName));
    	                pfileName = saveName;
    	                pfileDetail = originalName;
    	                break; // 하나만 업로드
    	            }
    	        }
    	    }

    	    // 수정 적용
    	    origin.setBoardName(boardName);
    	    origin.setBoardContent(boardContent);
    	    origin.setBoardDesc(boardContent);
    	    origin.setPfileName(pfileName);
    	    origin.setPfileDetail(pfileDetail);

    	    boardService.updateBoard(origin);

    	    return ok();
    	}

    // 삭제
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") String id, HttpSession session) throws Exception {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) return fail("로그인 필요");

        BoardVO origin = boardService.selectBoardByNo(id);
        if (origin == null) return fail("not found");
        if (!canModify(loginUser, origin)) return fail("권한 없음");

        try { replyService.deleteAllReplies(id); } catch (Exception ignore) {}
        boardService.deleteBoard(id);
        return ok();
    }

    // ===== util =====
    private boolean canModify(MemberVO user, BoardVO post) {
        if (user == null || post == null) return false;
        String uid = user.getMem_id();
        String auth = user.getMem_auth() == null ? "" : user.getMem_auth();
        boolean owner = uid != null && uid.equals(post.getMemId());
        boolean staffOrAdmin =
                auth.contains("ROLE03") || auth.contains("ROLE04") ||
                auth.toUpperCase().contains("STAFF") || auth.toUpperCase().contains("ADMIN");
        return owner || staffOrAdmin;
    }

    private Map<String, Object> ok() {
        return Map.of("ok", true);
    }

    private Map<String, Object> fail(String r) {
        return Map.of("ok", false, "reason", r);
    }
    
}