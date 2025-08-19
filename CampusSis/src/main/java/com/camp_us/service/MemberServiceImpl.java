package com.camp_us.service;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.camp_us.dao.MemberDAO;
import com.camp_us.dto.MemberVO;



public class MemberServiceImpl implements MemberService {

	private MemberDAO memberDAO;

	public MemberServiceImpl(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	@Override
    public MemberVO getMemberById(String mem_id) throws SQLException{
        return memberDAO.getMemberById(mem_id);
    }
	
	@Override
	public MemberVO getMember(String id) throws SQLException {
		MemberVO member = memberDAO.getMemberById(id);
		if (member != null)
			member.setMem_auth(memberDAO.selectAuthoritiesById(id));
		return member;
	}

	@Override
	public void updateMemLastLogin(String mem_id) throws Exception{
		MemberVO member = memberDAO.getMemberById(mem_id);
		if (member != null) {
			Date date = new Date();
			Time time = new Time(new Date().getTime());
			member.setMem_lastlogin_date(date);
			member.setMem_lastlogin_time(time);
			
			memberDAO.insertLastLogin(member);
		}
		
	}
	
	@Override
	public List<MemberVO> list() throws SQLException {
		List<MemberVO> memberList = memberDAO.selectMemberList();
		
		return memberList;
	}

	@Override
	public void regist(MemberVO member) throws SQLException {

	}

	@Override
	public void modify(MemberVO member) throws SQLException {
		memberDAO.updateMember(member);
	}

	@Override
	public void remove(String id) throws SQLException {
		memberDAO.deleteMember(id);
	}

	@Override
	public void modifyAuthority(String id, List<String> authorities) throws SQLException {
		memberDAO.deleteAllAuthorityById(id);
		if(authorities.size()>0)for(String authority:authorities) {
			memberDAO.insertAuthorities(id,Integer.parseInt(authority));
		}
	}

	@Override
	public String findMemIdByEmail(String mem_email) throws SQLException {
		return memberDAO.findMemIdByEmail(mem_email);
	}


}