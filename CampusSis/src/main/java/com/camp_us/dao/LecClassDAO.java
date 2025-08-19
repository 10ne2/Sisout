package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.LecClassVO;

public interface LecClassDAO {
	List<LecClassVO> selectLecClassList()throws SQLException;
	
	
	LecClassVO selectLecClassById(String lec_id)throws SQLException;
}
