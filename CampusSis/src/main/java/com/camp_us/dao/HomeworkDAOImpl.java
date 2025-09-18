package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.camp_us.command.PageMaker;
import com.camp_us.dto.HomeworkVO;

@Repository
public class HomeworkDAOImpl implements HomeworkDAO {
	
private SqlSession session;
	
	public HomeworkDAOImpl(SqlSession session) {
		this.session = session;
	}

	@Override
	public List<HomeworkVO> getHomeworkList(PageMaker pageMaker) throws SQLException {
	    return session.selectList("Homework-Mapper.getHomeworkList", pageMaker); // ✅ RowBounds 제거 완료
	}

	@Override
	public HomeworkVO getHomeworkByNo(int hwNo) throws SQLException{
		return session.selectOne("Homework-Mapper.selectHomeworkByHwNo", hwNo);
	}
	@Override
	public int getHomeworkTotalCount(PageMaker pageMaker) {
	    return session.selectOne("Homework-Mapper.getHomeworkTotalCount", pageMaker);
	}

	@Override
	public void insertHomework(HomeworkVO homework) throws SQLException{
	    session.insert("Homework-Mapper.insertHomework", homework);
	}
	@Override
	public int getNextHwNo() {
	    Integer lastNo = session.selectOne("Homework-Mapper.getNextHwNo");
	    return (lastNo != null) ? lastNo + 1 : 1;
	}

	@Override
	public void updateHomework(HomeworkVO homework) {
	    session.update("Homework-Mapper.updateHomework", homework);
	}

	@Override
	public void deleteHomework(int hwNo)throws SQLException {
		session.delete("Homework-Mapper.deleteHomework", hwNo);
		
	}

	@Override
	public List<HomeworkVO> getStudentHomeworkList(String studentId) {
	    return session.selectList("Homework-Mapper.getStudentHomeworkList", studentId);
	}
	
	@Override
	public HomeworkVO selectHomeworkByNo(int hwNo) throws SQLException {
	    return session.selectOne("Homework-Mapper.selectHomeworkByNo", hwNo);
	}
	

}