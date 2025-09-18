package com.camp_us.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.camp_us.dto.LecVideoVO;


public class LecVideoDAOImpl implements LecVideoDAO {
	
	private SqlSession sqlSession;
	public LecVideoDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	private static final String NAMESPACE = "com.camp_us.mapper.LecVideoMapper";

    public void insertVideo(LecVideoVO vo) {
        sqlSession.insert(NAMESPACE + ".insertVideo", vo);
    }
    
    @Override
    public void updateVideo(LecVideoVO vo) {
        sqlSession.update(NAMESPACE + ".updateVideo", vo);
    }
    public LecVideoVO selectVideo(String lecvidId) {
        return sqlSession.selectOne(NAMESPACE + ".selectVideo", lecvidId);
    }
    public List<LecVideoVO> selectVideosByLecture(String lecId) {
        return sqlSession.selectList(NAMESPACE + ".selectVideosByLecture", lecId);
    }
    public List<LecVideoVO> selectVideosByWeek(String lecId, String lecvidWeek) {
        Map<String, String> params = new HashMap<>();
        params.put("lecId", lecId);
        params.put("lecvidWeek", lecvidWeek);
        return sqlSession.selectList(NAMESPACE + ".selectVideosByWeek", params);
    }
    
    @Override
    public String getLectureIdByVideoId(String lecvidId) {
        return sqlSession.selectOne(NAMESPACE + ".getLectureIdByVideoId", lecvidId);
    }
    
    @Override
    public LecVideoVO getVideoById(String lecvidId) {
        return sqlSession.selectOne(NAMESPACE + ".getVideoById", lecvidId);
    }

}
