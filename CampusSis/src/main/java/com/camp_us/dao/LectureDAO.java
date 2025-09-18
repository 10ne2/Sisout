package com.camp_us.dao;

import com.camp_us.dto.LectureVO;

public interface LectureDAO {
    void insertFinalPlan(LectureVO vo) throws Exception;
    
    LectureVO selectFirstOne();
    String selectFilePathById(String lec_id);
}
