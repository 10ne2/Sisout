package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.camp_us.dto.StuLecVO;

public class StuLecDAOImpl implements StuLecDAO{
    private SqlSession session;

    public StuLecDAOImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public List<StuLecVO> selectLectureListByStudentId(String stu_id) throws SQLException {
        return session.selectList("StuLec-Mapper.selectLectureListByStudentId", stu_id);
    }
}