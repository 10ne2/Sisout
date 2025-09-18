package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.BoardVO;

//Service Interface
public interface BoardService {
 List<BoardVO> selectBoardList(PageMakerMJ pageMaker)throws SQLException;
 BoardVO selectBoardByNo(String boardNo);
 int insertBoard(BoardVO board);
 int updateBoard(BoardVO board);
 int deleteBoard(String boardId);
 int getTotalCount(PageMakerMJ pageMaker);
 void deleteBoardWithReplies(String boardId);


 
}

