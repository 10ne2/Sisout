package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

    private SqlSession session;

    public BoardDAOImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public int getTotalCount(PageMakerMJ pageMaker) {
        return session.selectOne("Board-Mapper.getTotalCount", pageMaker);
    }

    @Override
    public List<BoardVO> selectBoardList(PageMakerMJ pageMaker) throws SQLException {
        int offset = pageMaker.getStartRow() - 1;
        int limit = pageMaker.getPerPageNum();
        RowBounds bounds = new RowBounds(offset, limit);
        return session.selectList("Board-Mapper.selectBoardList", pageMaker, bounds);
    }

    @Override
    public BoardVO selectBoardByNo(String boardNo) {
        return session.selectOne("Board-Mapper.selectBoardByNo", boardNo);
    }

    @Override
    public int insertBoard(BoardVO board) {
        return session.insert("Board-Mapper.insertBoard", board);
    }

    @Override
    public int updateBoard(BoardVO board) {
        return session.update("Board-Mapper.updateBoard", board);
    }

  
    @Override
    public int deleteBoard(String boardId) {
        return session.delete("Board-Mapper.deleteBoard", boardId);
    }
    @Override
    public int deleteRepliesByBoardId(String boardId) {
        return session.delete("Board-Mapper.deleteRepliesByBoardId", boardId);
    }


}
