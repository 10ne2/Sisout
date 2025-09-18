package com.camp_us.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.camp_us.dto.LectureVO;

@Repository("lectureDAO")
public class LectureDAOImpl implements LectureDAO {

	private SqlSession session;	
	public LectureDAOImpl(SqlSession session) {
		this.session = session;
	}
	@Override
	public void insertFinalPlan(LectureVO vo) throws Exception {
		session.insert("com.camp_us.mybatis.mappers.LectureMapper.insertFinalPlan",vo);	
	}
	
	@Override
	public LectureVO selectFirstOne() {
        return session.selectOne("com.camp_us.mybatis.mappers.LectureMapper.selectFirstOne");
    }
	
    @Override
    public String selectFilePathById(String lec_id) {
        return session.selectOne("com.camp_us.mybatis.mappers.LectureMapper.selectFilePathById", lec_id);
    }
	
}
