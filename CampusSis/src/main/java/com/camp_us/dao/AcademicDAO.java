package com.camp_us.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;

import com.camp_us.dto.AcademicVO;

public interface AcademicDAO {
	AcademicVO selectByMemId(String memId) throws SQLException;
    int upsert(AcademicVO vo)throws SQLException;
    int countSaByMemId(@Param("memId") String memId);
    String whoAmI();
    String findStuIdByMemId(@Param("memId") String memId);
    int countSaByStuId(@Param("stuId") String stuId);

}
