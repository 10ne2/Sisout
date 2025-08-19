package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dao.StuLecDAO;
import com.camp_us.dto.StuLecVO;

public class StuLecServiceImpl implements StuLecService{
    private StuLecDAO stuLecDAO;

    public StuLecServiceImpl(StuLecDAO stuLecDAO) {
        this.stuLecDAO = stuLecDAO;
    }

    @Override
    public List<StuLecVO> selectLectureListByStudentId(String stu_id) throws SQLException {
        return stuLecDAO.selectLectureListByStudentId(stu_id);
    }
}