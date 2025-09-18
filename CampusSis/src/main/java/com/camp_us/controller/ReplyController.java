package com.camp_us.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dao.ReplyDAO;
import com.camp_us.dto.ReplyVO;
import com.camp_us.service.ReplyService;
import com.josephoconnell.html.HTMLInputFilter;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyDAO replyDAO;

    @Autowired
    private ReplyService replyService;

    @GetMapping("/list")
    public Map<String, Object> getReplyList(@RequestParam("bno") int boardId,
                                            @RequestParam(defaultValue = "1") int page) throws Exception {
        PageMakerMJ pageMaker = new PageMakerMJ();
        pageMaker.setPage(page);
        pageMaker.setPerPageNum(5);

        int totalCount = replyService.countReply(boardId);
        pageMaker.setTotalCount(totalCount);

        List<ReplyVO> replyList = totalCount > 0
                ? replyService.getReplyList(boardId, pageMaker)
                : new java.util.ArrayList<>();

        // 날짜 포맷
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ReplyVO reply : replyList) {
            if (reply.getRegdate() != null) {
                reply.setFormattedRegdate(sdf.format(reply.getRegdate()));
            }
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("replyList", replyList);
        dataMap.put("pageMaker", pageMaker);

        return dataMap;
    }

    // ✅ 댓글 등록
    @PostMapping("/regist")
    public ResponseEntity<String> regist(@RequestBody ReplyVO reply) throws Exception {
        if (reply.getReplyer() == null || reply.getReplyer().isBlank()) {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

        reply.setReplytext(HTMLInputFilter.htmlSpecialChars(reply.getReplytext()));

        try {
            replyService.regist(reply);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("댓글 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        int totalCount = replyDAO.countReply(reply.getBoardId());
        int perPageNum = new PageMakerMJ().getPerPageNum();
        String pageNum = "" + (int) Math.ceil(totalCount / (double) perPageNum);

        return new ResponseEntity<>(pageNum, HttpStatus.OK);
    }

    // ✅ 댓글 수정
    @PostMapping(value = "/modify", headers = "X-HTTP-Method-Override=PUT")
    public ResponseEntity<String> modifyReply(@RequestBody Map<String, Object> payload) throws Exception {
        int rno = Integer.parseInt(payload.get("rno").toString());
        String replytext = payload.get("replytext").toString();

        ReplyVO reply = new ReplyVO();
        reply.setRno(rno);
        reply.setReplytext(replytext);

        replyService.modifyReply(reply);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping(value = "/remove", headers = "X-HTTP-Method-Override=DELETE")
    @ResponseBody
    public ResponseEntity<String> removeReply(@RequestBody Map<String, Object> param) {
        try {
            System.out.println("삭제 요청 받은 데이터: " + param);

            if (!param.containsKey("rno")) {
                return new ResponseEntity<>("rno 없음", HttpStatus.BAD_REQUEST);
            }

            int rno = Integer.parseInt(param.get("rno").toString());
            replyService.removeReply(rno); // ✅ void 메서드 호출 (리턴값 없음)

            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    }


