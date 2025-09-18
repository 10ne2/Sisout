package com.camp_us.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.BoardVO;
import com.camp_us.service.BoardService;
import com.camp_us.service.ReplyService;

@Controller
@RequestMapping("")
public class BoardController {

    private final BoardService boardService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
       
    }

    // 게시글 목록 조회
    @GetMapping("/boardlist")
    public String list(@ModelAttribute PageMakerMJ pageMaker, Model model) throws Exception {
        int totalCount = boardService.getTotalCount(pageMaker);
        pageMaker.setTotalCount(totalCount);

        List<BoardVO> boardList = boardService.selectBoardList(pageMaker);
        int totalPage = (int) Math.ceil((double) totalCount / pageMaker.getPerPageNum());

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("page", pageMaker.getPage());
        model.addAttribute("totalPage", totalPage);

        // ✨ 추가된 부분 (검색 조건 값 유지)
        model.addAttribute("category", pageMaker.getCategory());
        model.addAttribute("searchType", pageMaker.getSearchType());
        model.addAttribute("keyword", pageMaker.getKeyword());

        return "board/list";
    }


    @GetMapping("/board/write")
    public String writeForm(Model model) {
        model.addAttribute("board", new BoardVO());
        return "board/write";
    }

    @PostMapping("/board/write")
    public String writeBoard(@ModelAttribute BoardVO board,
                             @RequestParam(value = "file1", required = false) MultipartFile file1,
                             @RequestParam(value = "file2", required = false) MultipartFile file2,
                             Model model,
                             Principal principal) {

        String loginId = principal.getName();
        board.setMemId(loginId);
        board.setBoardDesc(board.getBoardContent());

        String uploadDir = "C:/campus_upload";

        if (file1 != null && !file1.isEmpty()) {
            try {
                String fileName = file1.getOriginalFilename();
                file1.transferTo(new File(uploadDir, fileName));
                board.setPfileName(fileName);
                board.setPfileDetail("/upload/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boardService.insertBoard(board);

        // ✨ redirect 하지 말고 write.jsp로 이동
        model.addAttribute("success", true);
        return "board/write";
    }


    // 게시글 상세 조회
    @GetMapping("/board/detail")
    public String boardDetail(@RequestParam("bno") String bno, Model model, Principal principal) {
        BoardVO board = boardService.selectBoardByNo(bno);
        String MemId = principal.getName();
        model.addAttribute("board", board);
        model.addAttribute("loginUser", MemId);
        return "board/detail";
    }
    @PostMapping("/board/update")
    public String updateBoard(@ModelAttribute BoardVO board,
                              @RequestParam(value = "file1", required = false) MultipartFile file,
                              @RequestParam(value = "keepFile", required = false) String keepFile) {

        System.out.println("keepFile 파라미터: " + keepFile);

        if ("false".equals(keepFile)) {
            // 실제 파일 삭제
            if (board.getPfileName() != null) {
                File fileToDelete = new File("C:/campus_upload", board.getPfileName());
                if (fileToDelete.exists()) {
                    fileToDelete.delete();
                }
            }

            board.setPfileName(null);
            board.setPfileDetail(null);
        }

        // 새 파일 업로드
        if (file != null && !file.isEmpty()) {
            String originalName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String saveName = uuid + "_" + originalName;

            File saveFile = new File("C:/campus_upload", saveName);
            try {
                file.transferTo(saveFile);
                board.setPfileName(saveName);
                board.setPfileDetail(originalName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boardService.updateBoard(board);
        return "redirect:/board/detail?bno=" + board.getBoardId();
    }
    @PostMapping("/board/delete")
    public String deleteBoard(@RequestParam("boardId") String boardId, RedirectAttributes rattr) {
        try {
            // 댓글 전체 삭제
        	replyService.deleteAllReplies(boardId);
            // 게시글 삭제
            boardService.deleteBoard(boardId);

            rattr.addFlashAttribute("message", "게시글이 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("message", "삭제 중 오류 발생");
        }

        return "redirect:/boardlist";
    }


}
