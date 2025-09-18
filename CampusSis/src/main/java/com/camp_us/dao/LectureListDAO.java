package com.camp_us.dao;

import java.util.List;

import com.camp_us.dto.LectureListVO;

public interface LectureListDAO {
    List<String> getStudentMemIdsByLecId(String lecId);
    String getLecsIdByLecIdAndMemId(String lecId, String memId);
    List<LectureListVO> getStudentsByLecture(String lecId);
}