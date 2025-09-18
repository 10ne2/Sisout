package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.HomeworkSubmitVO;
import com.camp_us.dto.HomeworkVO;

public interface HomeworkSubmitService {
    void regist(HomeworkSubmitVO submitVO) throws SQLException;
    HomeworkSubmitVO getSubmitByHwNoAndStdId(int hwNo, String stuId) throws SQLException;
    HomeworkVO getHomeworkDetail(int hwNo) throws SQLException;
    void remove(String hwNo) throws SQLException;
    HomeworkSubmitVO getSubmitByHsno(String hwsubHsno) throws SQLException;
    void updateSubmit(HomeworkSubmitVO submitVO) throws SQLException;
    void modify(HomeworkSubmitVO submitVO) throws SQLException;
    void updateFeedback(HomeworkSubmitVO submit)throws SQLException;
    HomeworkSubmitVO getSubmitById(String submitId) throws SQLException;
    List<HomeworkSubmitVO> getSubmitListByHwNo(int hwNo) throws SQLException;
}