package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.LecNoticeVO;

@Repository
public class LecNoticeDAOImpl implements LecNoticeDAO {

    private final SqlSession session;

    public LecNoticeDAOImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public int getTotalCount(PageMakerMJ pageMaker) throws SQLException {
        // 검색 파라미터(PageMaker) 전달
        return session.selectOne("LecNoticeMapper.getTotalCount", pageMaker);
    }

    @Override
    public List<LecNoticeVO> selectLecNoticeList(PageMakerMJ pageMaker) throws SQLException {
        // ★ RowBounds 제거! ROWNUM 페이징을 사용하는 getLecNoticeList 호출
        return session.selectList("LecNoticeMapper.getLecNoticeList", pageMaker);
    }

    @Override
    public LecNoticeVO selectLecNoticeById(String lecNoticeId) throws SQLException {
        return session.selectOne("LecNoticeMapper.selectLecNoticeById", lecNoticeId);
    }

    @Override
    public int insertLecNotice(LecNoticeVO lecNotice) throws SQLException {
        return session.insert("LecNoticeMapper.insertLecNotice", lecNotice);
    }

    @Override
    public int updateLecNotice(LecNoticeVO lecNotice) throws SQLException {
        return session.update("LecNoticeMapper.updateLecNotice", lecNotice);
    }

    @Override
    public int deleteLecNotice(String lecNoticeId) throws SQLException {
        return session.delete("LecNoticeMapper.deleteLecNotice", lecNoticeId);
    }

    @Override
    public int getMaxLecNoticeId() throws Exception {
        return session.selectOne("LecNoticeMapper.getMaxLecNoticeId");
    }

    @Override
    public void updateViewCount(String lecNoticeId) {
        int updated = session.update("LecNoticeMapper.updateViewCount", lecNoticeId);
        System.out.println("[LecNotice] view++ id=" + lecNoticeId + " updated=" + updated);
    }
}
