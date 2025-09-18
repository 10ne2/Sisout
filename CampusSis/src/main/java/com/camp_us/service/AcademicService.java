package com.camp_us.service;

import java.sql.SQLException;

import com.camp_us.dto.AcademicVO;

public interface AcademicService {

	AcademicVO getByMemId(String memId)throws SQLException;

	int save(AcademicVO vo)throws SQLException;
	
	int countSaByMemId(String memId);
	
	String whoAmI();
	
	String findStuIdByMemId(String memId);
	int countSaByStuId(String stuId);

}