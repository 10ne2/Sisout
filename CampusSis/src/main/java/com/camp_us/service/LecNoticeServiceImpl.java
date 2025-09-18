package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.camp_us.command.PageMakerMJ;
import com.camp_us.dao.LecNoticeDAO;
import com.camp_us.dto.LecNoticeVO;

@Service
public class LecNoticeServiceImpl implements LecNoticeService {

    private final LecNoticeDAO lecNoticeDAO;

    public LecNoticeServiceImpl(LecNoticeDAO lecNoticeDAO) {
        this.lecNoticeDAO = lecNoticeDAO;
    }

    @Override
    public List<LecNoticeVO> getLecNoticeList(PageMakerMJ pageMaker) throws SQLException {
        return lecNoticeDAO.selectLecNoticeList(pageMaker);
    }

    @Override
    public LecNoticeVO getLecNoticeById(String lecNoticeId) throws SQLException {
        return lecNoticeDAO.selectLecNoticeById(lecNoticeId);
    }

    @Override
    public void registLecNotice(LecNoticeVO lecNotice) throws SQLException {
        lecNoticeDAO.insertLecNotice(lecNotice);
    }

    @Override
    public void modifyLecNotice(LecNoticeVO lecNotice) throws SQLException {
        lecNoticeDAO.updateLecNotice(lecNotice);
    }

    @Override
    public void removeLecNotice(String lecNoticeId) throws SQLException {
        lecNoticeDAO.deleteLecNotice(lecNoticeId);
    }

    @Override
    public int getTotalCount(PageMakerMJ pageMaker) throws SQLException {
        return lecNoticeDAO.getTotalCount(pageMaker);
    }
    @Override
    public int getMaxLecNoticeId() throws Exception {
        return lecNoticeDAO.getMaxLecNoticeId();
    }
    @Override
    public void updateViewCount(String lecNoticeId)throws Exception {
        lecNoticeDAO.updateViewCount(lecNoticeId);
    }
    
}
