package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.StuLecVO;

public interface StuLecDAO {

	List<StuLecVO> selectLectureListByStudentId(String stu_id) throws SQLException;
}