package com.camp_us.dao;

import java.util.List;

import com.camp_us.dto.ReplyVO;

public interface ReplyDAO {
	public List<ReplyVO> selectReplyListPage(int boardId, int offset, int limit);

    int countReply(int boardId);
    void insertReply(ReplyVO reply);
    void updateReply(ReplyVO reply);
    void deleteReply(int rno);
    void deleteAllReplies(String boardId) throws Exception;

}
