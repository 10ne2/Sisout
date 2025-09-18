package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;
import com.camp_us.dao.NoticeDAO;
import com.camp_us.dto.NoticeVO;

public class NoticeServiceImpl implements NoticeService {
    private final NoticeDAO noticeDAO;                     // ★ 이름: noticeDAO (c:noticeDAO-ref 와 매칭)
    public NoticeServiceImpl(NoticeDAO noticeDAO) {        // 생성자 1개
        this.noticeDAO = noticeDAO;
    }
    @Override
    public List<NoticeVO> getRecentNotices() throws SQLException {
        return noticeDAO.selectRecentNotices();
    }
}