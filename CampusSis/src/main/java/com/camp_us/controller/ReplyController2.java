package com.camp_us.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.ReplyVO;
import com.camp_us.service.ReplyService;

@RestController
@RequestMapping("/api/reply")
public class ReplyController2 {

    @Autowired
    private ReplyService replyService;

    // 댓글 목록 조회
    @GetMapping("/list/{boardId}")
    public Map<String, Object> getReplyList(@PathVariable("boardId") int boardId,
                                            @RequestParam(defaultValue = "1") int page) throws Exception {
        PageMakerMJ pageMaker = new PageMakerMJ();
        pageMaker.setPage(page);
        pageMaker.setPerPageNum(10);

        List<ReplyVO> replyList = replyService.getReplyList(boardId, pageMaker);

        Map<String, Object> res = new HashMap<>();
        res.put("ok", true);
        res.put("items", replyList);
        res.put("page", pageMaker);
        return res;
    }

    @PostMapping(path = "/regist", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> regist(
            @RequestParam("boardId") int boardId,
            @RequestParam("replytext") String replytext,
            @RequestParam("memId") String memId,
            @RequestParam("memName") String memName
    ) throws Exception {
        System.out.println("📌 댓글 등록 요청: boardId=" + boardId + ", replytext=" + replytext + ", memId=" + memId);

        if (memId == null || memId.isEmpty()) return fail("memId 없음");

        ReplyVO reply = new ReplyVO();
        reply.setBoardId(boardId);
        reply.setReplytext(replytext);
        reply.setReplyer(memName != null && !memName.isEmpty() ? memName : memId);
        reply.setRegdate(new Timestamp(System.currentTimeMillis()));
        reply.setUpdatedate(new Timestamp(System.currentTimeMillis()));

        replyService.regist(reply);

        return ok();
    }

    // 댓글 수정
    @PostMapping(path = "/{rno}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> modify(
            @PathVariable("rno") int rno,
            @RequestParam("replytext") String replytext,
            @RequestParam("memId") String memId
    ) throws Exception {
        System.out.println("📌 댓글 수정 요청: rno=" + rno + ", replytext=" + replytext + ", memId=" + memId);

        if (memId == null || memId.isEmpty()) return fail("memId 없음");
        if (replytext == null || replytext.trim().isEmpty()) return fail("내용 없음");

        ReplyVO vo = new ReplyVO();
        vo.setRno(rno);
        vo.setReplytext(replytext);
        vo.setUpdatedate(new Timestamp(System.currentTimeMillis()));

        replyService.modifyReply(vo);

        return ok();
    }

 // 댓글 삭제
    @DeleteMapping("/{rno}")
    public Map<String, Object> delete(
            @PathVariable("rno") int rno,
            @RequestParam("memId") String memId
    ) throws Exception {
        System.out.println("📌 댓글 삭제 요청: rno=" + rno + ", memId=" + memId);

        if (memId == null || memId.isEmpty()) return fail("memId 없음");

        replyService.removeReply(rno);

        return ok();
    }


    // ===== util =====
    private Map<String, Object> ok() {
        return Map.of("ok", true);
    }

    private Map<String, Object> fail(String reason) {
        Map<String, Object> res = new HashMap<>();
        res.put("ok", false);
        res.put("reason", reason);
        return res;
    }
}