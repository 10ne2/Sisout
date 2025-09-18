package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;
import com.camp_us.dto.NoticeVO;

public interface NoticeService {
    List<NoticeVO> getRecentNotices() throws SQLException;
}