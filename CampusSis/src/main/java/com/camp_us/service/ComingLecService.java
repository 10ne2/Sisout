package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.ComingLecVO;

public interface ComingLecService {
	List<ComingLecVO> getComingLecList(String stu_id) throws SQLException;
}
