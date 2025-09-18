package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.camp_us.dto.NoticeVO;

public class NoticeDAOImpl implements NoticeDAO {
    private final SqlSession session;              // ★ 이름: session (c:session-ref 와 매칭)
    public NoticeDAOImpl(SqlSession session) {     // ★ 생성자 파라미터명도 session
        this.session = session;
    }
    @Override
    public List<NoticeVO> selectRecentNotices() throws SQLException {
        return session.selectList("Notice-Mapper.selectRecentNotices");
    }
}