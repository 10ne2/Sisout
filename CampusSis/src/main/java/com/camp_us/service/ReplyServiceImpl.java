package com.camp_us.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dao.ReplyDAO;
import com.camp_us.dto.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyDAO replyDAO;

    public ReplyServiceImpl(ReplyDAO replyDAO) {
        this.replyDAO = replyDAO;
    }
    @Override
    public void regist(ReplyVO reply) throws Exception {
        replyDAO.insertReply(reply);
    }


    @Override
    public List<ReplyVO> getReplyList(int boardId, PageMakerMJ pageMaker) throws Exception {
        int offset = pageMaker.getStartRow() - 1;
        int limit = pageMaker.getPerPageNum();
        return replyDAO.selectReplyListPage(boardId, offset, limit);
    }

    @Override
    public int countReply(int boardId) throws Exception {
        return replyDAO.countReply(boardId);
    }

    @Override
    public void registReply(ReplyVO reply) throws Exception {
        replyDAO.insertReply(reply);
    }

    @Override
    public void modifyReply(ReplyVO reply) throws Exception {
        replyDAO.updateReply(reply);
    }

    @Override
    public void removeReply(int rno) throws Exception {
        replyDAO.deleteReply(rno);
    }

    @Override
    public int getLastPage(int boardId, int perPageNum) throws Exception {
        int totalCount = replyDAO.countReply(boardId);
        return (int) Math.ceil((double) totalCount / perPageNum);
    }
    @Override
    public void deleteAllReplies(String boardId) {
        try {
            replyDAO.deleteAllReplies(boardId); // DAO 호출
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로깅
        }
    }
}



