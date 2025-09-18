package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dto.LecNoticeVO;

public interface LecNoticeService {
    List<LecNoticeVO> getLecNoticeList(PageMakerMJ pageMaker) throws SQLException;

    LecNoticeVO getLecNoticeById(String lecNoticeId) throws SQLException;

    void registLecNotice(LecNoticeVO lecNotice) throws SQLException;

    void modifyLecNotice(LecNoticeVO lecNotice) throws SQLException;

    void removeLecNotice(String lecNoticeId) throws SQLException;

    int getTotalCount(PageMakerMJ pageMaker) throws SQLException;
    int getMaxLecNoticeId() throws Exception;
    void updateViewCount(String lecNoticeId) throws Exception;
}
