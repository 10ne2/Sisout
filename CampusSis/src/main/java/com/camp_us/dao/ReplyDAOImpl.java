package com.camp_us.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.camp_us.dto.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

    private final SqlSession session;

    public ReplyDAOImpl(SqlSession session) {
        this.session = session;
    }

    private static final String NAMESPACE = "Reply-Mapper.";

    @Override
    public List<ReplyVO> selectReplyListPage(int boardId, int offset, int limit) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("boardId", boardId);
        paramMap.put("startRow", offset + 1); // Oracle은 1부터 시작
        paramMap.put("endRow", offset + limit);
        
        return session.selectList("Reply-Mapper.selectReplyListPage", paramMap);
    }


    @Override
    public int countReply(int boardId) {
        return session.selectOne(NAMESPACE + "countReply", boardId);
    }

    @Override
    public void insertReply(ReplyVO reply) {
        session.insert(NAMESPACE + "insertReply", reply);
    }

    @Override
    public void updateReply(ReplyVO reply) {
        int updated = session.update(NAMESPACE + "updateReply", reply);
        System.out.println("댓글 수정 결과: " + updated);
    }


    @Override
    public void deleteReply(int rno) {
        session.delete(NAMESPACE + "deleteReply", rno);
    }
    @Override
    public void deleteAllReplies(String boardId) {
        int deleted = session.delete(NAMESPACE + "deleteAllReplies", boardId);
        System.out.println("📌 댓글 삭제된 개수: " + deleted);
    }

}
