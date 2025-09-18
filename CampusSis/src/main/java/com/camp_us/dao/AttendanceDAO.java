package com.camp_us.dao;

import com.camp_us.dto.AttendanceVO;

public interface AttendanceDAO {
    void insertAttendance(AttendanceVO vo);
    void updateProgressAndDetail(String aNo, int newProgress);
    void updateAttendance(AttendanceVO vo);
    AttendanceVO selectAttendance(String aNo);
}