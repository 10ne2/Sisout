package com.camp_us.dao;

import java.sql.SQLException;

import com.camp_us.dto.HomeworkSubmitVO;
import com.camp_us.dto.HomeworkVO;

public interface HomeworkSubmitDAO {
    void insertHomeworkSubmit(HomeworkSubmitVO submitVO) throws SQLException;
    void updateHomeworkSubmit(HomeworkSubmitVO submitVO) throws SQLException;
    HomeworkSubmitVO selectSubmitByStuIdAndHwNo(int hwNo, String stuId) throws SQLException;
    HomeworkVO selectHomeworkByNo(int hwNo) throws SQLException;
    void deleteHomeworkSubmit(String hwNo) throws SQLException;
    HomeworkSubmitVO selectHomeworkSubmitByPk(String hwsubHsno) throws SQLException;
    HomeworkSubmitVO selectSubmitById(String submitId);
    void updateFeedback(HomeworkSubmitVO submit); 
    
}