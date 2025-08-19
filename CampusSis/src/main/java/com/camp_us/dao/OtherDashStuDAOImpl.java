package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.camp_us.dto.OtherDashStuVO;

public class OtherDashStuDAOImpl implements OtherDashStuDAO{
	
	private SqlSession session;	
	public OtherDashStuDAOImpl (SqlSession session) {
		this.session = session;
	}

	@Override
	public List<OtherDashStuVO> selectNoticeList(String stu_id) throws SQLException {
		return session.selectList("OtherDashStu-Mapper.selectNoticeList", stu_id);
	}

	@Override
	public List<OtherDashStuVO> selectFileList(String stu_id) throws SQLException {
		return session.selectList("OtherDashStu-Mapper.selectFileList", stu_id);
	}

	@Override
	public List<OtherDashStuVO> selectAttendenceList(String stu_id) throws SQLException {
		return session.selectList("OtherDashStu-Mapper.selectAttendenceList", stu_id);
	}

	@Override
	public List<OtherDashStuVO> selectAttendencePercent(String stu_id) throws SQLException {
		return session.selectList("OtherDashStu-Mapper.selectAttendencePercent", stu_id);
	}

}
