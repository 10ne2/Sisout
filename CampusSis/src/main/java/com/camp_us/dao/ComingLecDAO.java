package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.ComingLecVO;

public interface ComingLecDAO {
	
	List<ComingLecVO> selectComingLec(String stu_id)throws SQLException;
}
