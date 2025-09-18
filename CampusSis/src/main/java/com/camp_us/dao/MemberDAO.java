package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.camp_us.dto.MemberVO;

public interface MemberDAO {
	
	List<MemberVO> selectMemberList()throws SQLException;
	
	
	MemberVO selectMemberById(String mem_id)throws SQLException;
	void insertMember(MemberVO member)throws SQLException;
	void updateMember(MemberVO member)throws SQLException;
	void deleteMember(String mem_id)throws SQLException;	
	
	void insertAuthorities(String mem_id, int ano)throws SQLException;
	void deleteAllAuthorityById(String mem_id)throws SQLException;
	MemberVO getMemberById(String mem_id) throws SQLException;

	String selectAuthoritiesById(String id);
	
	void insertLastLogin(MemberVO vo) throws Exception;
	
	void updatePicture(@Param("mem_id") String memId, @Param("picture") String picture);
	
	String selectPasswordHashById(String mem_id);
	void updatePassword(String mem_id, String encodedPw);
}