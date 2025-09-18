package com.camp_us.command;

import com.camp_us.dto.BoardVO;

public class BoardRegistCommand {

    private String boardCat;
    private String boardName;
    private String boardDesc;
    private String memId;

    // 첨부파일 이름, 경로
    private String pfileName;
    private String pfileDetail;

    // Getters/Setters 생략 (자동 생성)

    public BoardVO toBoard() {
        BoardVO board = new BoardVO();
        board.setBoardCat(boardCat);
        board.setBoardName(boardName);
        board.setBoardDesc(boardDesc);
        board.setMemId(memId);
        board.setPfileName(pfileName);
        board.setPfileDetail(pfileDetail);
        return board;
    }
}
