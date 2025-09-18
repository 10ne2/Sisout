package com.camp_us.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.camp_us.dto.HomeworkSubmitVO;
import com.camp_us.dto.HomeworkVO;

public class HomeworkSubmitDAOImpl implements HomeworkSubmitDAO {

    private SqlSession session;
    private final String namespace = "HomeworkSubmit-Mapper.";

    public HomeworkSubmitDAOImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public void insertHomeworkSubmit(HomeworkSubmitVO submitVO) throws SQLException {
        session.insert(namespace + "insertHomeworkSubmit", submitVO);
    }

    @Override
    public void updateHomeworkSubmit(HomeworkSubmitVO submitVO) throws SQLException {
    	session.update("HomeworkSubmit-Mapper.updateSubmit", submitVO);
    }

    @Override
    public void deleteHomeworkSubmit(String hwNo) throws SQLException {
        session.delete(namespace + "deleteHomeworkSubmit", hwNo);
    }

    @Override
    public HomeworkSubmitVO selectHomeworkSubmitByPk(String hwsubHsno) throws SQLException {
        return session.selectOne(namespace + "selectHomeworkSubmitByPk", hwsubHsno);
    }

    @Override
    public HomeworkSubmitVO selectSubmitByStuIdAndHwNo(int hwNo, String stuId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hwNo", hwNo);
        paramMap.put("stuId", stuId);
        return session.selectOne("HomeworkSubmit-Mapper.selectSubmitByStuIdAndHwNo", paramMap);
    }

    @Override
    public HomeworkVO selectHomeworkByNo(int hwNo) {
    	return session.selectOne("HomeworkSubmit-Mapper.selectHomeworkByNo", hwNo);
    }
    @Override
    public HomeworkSubmitVO selectSubmitById(String submitId) {
        return session.selectOne("HomeworkSubmit-Mapper.selectSubmitById", submitId);
    }

    @Override
    public void updateFeedback(HomeworkSubmitVO submit) {
        session.update("HomeworkSubmit-Mapper.updateFeedback", submit);
    }
    
}
