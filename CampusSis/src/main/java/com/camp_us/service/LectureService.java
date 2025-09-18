package com.camp_us.service;

import org.springframework.web.multipart.MultipartFile;

import com.camp_us.dto.LectureVO;

public interface LectureService {
    void saveFinalPlan(String courseId, MultipartFile file) throws Exception;
    LectureVO getFirstLecturePlanmaker();
    
    String getPlanFilePathByLecId(String lec_id);
}