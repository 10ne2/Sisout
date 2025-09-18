package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.LecNoticeVO;

public interface LecNoticeDAO {
    int getTotalCount(PageMakerMJ pageMaker) throws SQLException;

    List<LecNoticeVO> selectLecNoticeList(PageMakerMJ pageMaker) throws SQLException;

    LecNoticeVO selectLecNoticeById(String lecNoticeId) throws SQLException;

    int insertLecNotice(LecNoticeVO lecNotice) throws SQLException;

    int updateLecNotice(LecNoticeVO lecNotice) throws SQLException;

    int deleteLecNotice(String lecNoticeId) throws SQLException;
    int getMaxLecNoticeId() throws Exception;
    void updateViewCount(String lecNoticeId) throws Exception;
}
