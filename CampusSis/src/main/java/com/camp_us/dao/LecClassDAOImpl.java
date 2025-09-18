package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.camp_us.dto.LecClassVO;
import com.camp_us.dto.ProLecVO;

public class LecClassDAOImpl implements LecClassDAO{
	private SqlSession session;
	public LecClassDAOImpl(SqlSession session) {
		this.session = session;
	}
	
	@Override
	public List<LecClassVO> selectLecClassList() throws SQLException {
		return session.selectList("LecClass-Mapper.selectLecClassList");
	}

	@Override
	public LecClassVO selectLecClassById(String lec_id) throws SQLException {
			return session.selectOne("LecClass-Mapper.selectLecClassByID",lec_id);
	}

	@Override
	public List<ProLecVO> selectLecClassByProfessorId(String mem_id) throws SQLException {
		return session.selectList("LecClass-Mapper.selectLecClassByProfessorId",mem_id);
	}

}