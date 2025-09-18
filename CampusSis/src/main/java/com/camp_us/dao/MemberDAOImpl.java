package com.camp_us.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.camp_us.dto.MemberVO;

public class MemberDAOImpl implements MemberDAO{
	
	private SqlSession session;	
	public MemberDAOImpl(SqlSession session) {
		this.session = session;
	}


	@Override
	public MemberVO getMemberById(String mem_id) throws SQLException {
		return session.selectOne("Member-Mapper.selectMemberByID",mem_id);
	}


	@Override
	public String selectAuthoritiesById(String mem_id) {
		return session.selectOne("Member-Mapper.selectAuthoritiesById",mem_id);
	}


	@Override
	public void insertLastLogin(MemberVO vo) throws Exception {
		session.insert("Member-Mapper.updateLastLogin",vo);	
	}
	
	@Override
	public MemberVO selectMemberById(String mem_id) throws SQLException {
		return session.selectOne("Member-Mapper.selectMemberByID",mem_id);
	}

	@Override
	public void insertMember(MemberVO account) throws SQLException {
		session.insert("Member-Mapper.insertMember",account);	
	}

	@Override
	public void updateMember(MemberVO account) throws SQLException {
		session.update("Member-Mapper.updateMember",account);
		
	}

	@Override
	public void deleteMember(String mem_id) throws SQLException {
		session.delete("Member-Mapper.deleteMember",mem_id);
		
	}

	@Override
	public void insertAuthorities(String mem_id, int mem_auth) throws SQLException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("mem_id",mem_id);
		paramMap.put("mem_auth",mem_auth);
		session.insert("Member-Mapper.insertAuthorities",paramMap);	
	}

	@Override
	public void deleteAllAuthorityById(String id) throws SQLException {
		session.delete("Member-Mapper.deleteAllAuthorityById",id);	
	}


	@Override
	public List<MemberVO> selectMemberList() throws SQLException {
		
		return session.selectList("Member-Mapper.selectMemberList");
	}

	@Override
    public void updatePicture(String memId, String picture) {
        session.update("Member-Mapper.updatePicture", Map.of("mem_id", memId, "picture", picture));
    }
	
	@Override
	public String selectPasswordHashById(String mem_id) {
	    return session.selectOne("Member-Mapper.selectPasswordHashById", mem_id);
	}

	@Override
	public void updatePassword(String mem_id, String encodedPw) {
	    Map<String,Object> p = new HashMap<>();
	    p.put("mem_id", mem_id);
	    p.put("mem_pass", encodedPw);
	    session.update("Member-Mapper.updatePassword", p);
	}
	
	
}