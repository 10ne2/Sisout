package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.camp_us.dto.ComingLecVO;

public class ComingLecDAOImpl implements ComingLecDAO{
	
	private SqlSession session;	
	public ComingLecDAOImpl (SqlSession session) {
		this.session = session;
	}

	@Override
	public List<ComingLecVO> selectComingLec(String stu_id) throws SQLException {
		return session.selectList("ComingLec-Mapper.selectComingLec", stu_id);
	}

}
