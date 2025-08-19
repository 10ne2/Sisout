package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.LecClassVO;

public interface LecClassService {
    List<LecClassVO> getLecClassList() throws SQLException;

    LecClassVO getLecClassById(String lec_id) throws SQLException;
}
