package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.LecClassVO;
import com.camp_us.dto.ProLecVO;

public interface LecClassService {
    List<LecClassVO> getLecClassList() throws SQLException;

    LecClassVO getLecClassById(String lec_id) throws SQLException;
    
    List<ProLecVO> selectLecClassByProfessorId(String mem_id) throws SQLException;
    
}
