package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.BoardVO;

public interface BoardDAO {

    // 게시글 목록 조회
    List<BoardVO> selectBoardList(PageMakerMJ pageMaker) throws SQLException;
    int getTotalCount(PageMakerMJ pageMaker);


    // 게시글 상세 조회
    BoardVO selectBoardByNo(String boardNo);

    // 게시글 등록
    int insertBoard(BoardVO board);

    // 게시글 수정
    int updateBoard(BoardVO board);

    // 게시글 삭제
   
    int deleteBoard(String boardId);
    int deleteRepliesByBoardId(String boardId);
  

}
