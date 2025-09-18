package com.camp_us.service;

import java.util.List;

import com.camp_us.dto.AttendanceVO;

public interface VidAttendanceService {
    void createInitialAttendanceByLecVideo(String lecId, String lecvidId);
    void saveInitialAttendance(AttendanceVO vo);
    void updateProgressAndDetail(String aNo, int newProgress);
    void updateAttendance(AttendanceVO vo);
    AttendanceVO getAttendanceInfo(String aNo);
    
}