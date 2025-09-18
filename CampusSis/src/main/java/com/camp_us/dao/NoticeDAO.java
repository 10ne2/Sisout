package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;
import com.camp_us.dto.NoticeVO;

public interface NoticeDAO {
    List<NoticeVO> selectRecentNotices() throws SQLException;
}