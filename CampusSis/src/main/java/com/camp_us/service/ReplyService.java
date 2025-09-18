package com.camp_us.service;

import java.util.List;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.ReplyVO;

public interface ReplyService {
    List<ReplyVO> getReplyList(int boardId, PageMakerMJ pageMaker) throws Exception;
    int countReply(int boardId) throws Exception;
    void registReply(ReplyVO reply) throws Exception;
    void modifyReply(ReplyVO reply) throws Exception;
    void removeReply(int rno) throws Exception;
    int getLastPage(int boardId, int perPageNum) throws Exception;
    void regist(ReplyVO reply) throws Exception;
    void deleteAllReplies(String boardId) throws Exception;

}
