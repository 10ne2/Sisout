package com.camp_us.service;

import java.util.List;
import com.camp_us.dao.AttendanceDAO;
import com.camp_us.dao.LectureListDAO;
import com.camp_us.dto.AttendanceVO;

public class VidAttendanceServiceImpl implements VidAttendanceService {

    private AttendanceDAO attendanceDAO;
    private LectureListDAO lectureListDAO;

    public VidAttendanceServiceImpl(AttendanceDAO attendanceDAO, LectureListDAO lectureListDAO) {
        this.attendanceDAO = attendanceDAO;
        this.lectureListDAO = lectureListDAO;
    }

    @Override
    public void createInitialAttendanceByLecVideo(String lecId, String lecvidId) {
        List<String> memIdList = lectureListDAO.getStudentMemIdsByLecId(lecId);

        for (String memId : memIdList) {
            String lecsId = lectureListDAO.getLecsIdByLecIdAndMemId(lecId, memId);

            AttendanceVO vo = new AttendanceVO();
            vo.setaNo(lecId + memId + lecvidId);
            vo.setLecsId(lecsId);
            vo.setaDetail("결석");
            vo.setaCat("동영상");
            vo.setModPending("NOTSEND");

            attendanceDAO.insertAttendance(vo);
        }
    }
    
    @Override
    public void saveInitialAttendance(AttendanceVO vo) {
        attendanceDAO.insertAttendance(vo);
    }
    
    @Override
    public void updateProgressAndDetail(String aNo, int newProgress) {
        attendanceDAO.updateProgressAndDetail(aNo, newProgress);
    }
    
    @Override
    public void updateAttendance(AttendanceVO vo) {
        attendanceDAO.updateAttendance(vo);
    }

	@Override
	public AttendanceVO getAttendanceInfo(String aNo) {
		return attendanceDAO.selectAttendance(aNo);
	}
}
