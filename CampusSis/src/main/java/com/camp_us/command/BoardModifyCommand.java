package com.camp_us.command;

import com.camp_us.dto.BoardVO;

public class BoardModifyCommand extends BoardRegistCommand {

    private String boardId; // ⬅ String으로 변경

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public BoardVO toBoard() {
        BoardVO board = super.toBoard();
        board.setBoardId(boardId); 
        return board;
    }
}
