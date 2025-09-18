package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camp_us.command.PageMaker;
import com.camp_us.dao.HomeworkDAO;
import com.camp_us.dao.HomeworkSubmitDAO;
import com.camp_us.dto.HomeworkVO;

@Service
public class HomeworkServiceImpl implements HomeworkService {

	private HomeworkDAO homeworkDAO;
	private String summernotePath;
	private HomeworkSubmitDAO homeworkSubmitDAO;

	public HomeworkServiceImpl(HomeworkDAO homeworkDAO, String summernotePath) {
	    this.summernotePath = summernotePath;
	    this.homeworkDAO = homeworkDAO;
	}

	@Override
	public List<HomeworkVO> getHomeworkList(PageMaker pageMaker) throws SQLException {
	    List<HomeworkVO> homeworkList = homeworkDAO.getHomeworkList(pageMaker);

	    return homeworkList;
	}
	
	
	@Override
	public HomeworkVO getHomeworkByNo(int hwNo) throws SQLException {
	    return homeworkDAO.getHomeworkByNo(hwNo);
	}
	
	@Override
	public int getHomeworkTotalCount(PageMaker pageMaker) {
	    return homeworkDAO.getHomeworkTotalCount(pageMaker);
	}

	@Transactional
	@Override
	public void insertHomework(HomeworkVO homework) throws SQLException {
	    System.out.println("insertHomework 실행: " + homework);
	    homeworkDAO.insertHomework(homework);
	}
	@Override
	public int getNextHwNo() throws SQLException {
	    return homeworkDAO.getNextHwNo();
	}

	@Override
	public void updateHomework(HomeworkVO homework) {
	    homeworkDAO.updateHomework(homework);
	}

	@Override
	public void deleteHomework(int hwNo) throws SQLException {
	    HomeworkVO homework = homeworkDAO.getHomeworkByNo(hwNo);
	    if (homework == null) {
	        throw new SQLException("해당 과제를 찾을 수 없습니다: " + hwNo);
	    }

	    homeworkDAO.deleteHomework(hwNo);
	}

	@Override
	public List<HomeworkVO> getStudentHomeworkList(String studentId) {
	    return homeworkDAO.getStudentHomeworkList(studentId);
	}

	@Override
    public HomeworkVO getHomeworkDetail(int hwNo) throws SQLException {
        return homeworkDAO.selectHomeworkByNo(hwNo);
    }


	
}