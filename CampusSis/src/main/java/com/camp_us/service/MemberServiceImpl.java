package com.camp_us.service;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

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
        return memberDAO.selectMemberList();
    }

    @Override
    public void regist(MemberVO member) throws SQLException { }

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
        if (authorities.size() > 0) for (String authority : authorities) {
            memberDAO.insertAuthorities(id, Integer.parseInt(authority));
        }
    }

    @Override
    public void updatePicture(String memId, String picture) {
        memberDAO.updatePicture(memId, picture);
    }

    // ★ 평문 버전
    @Override
    public boolean changePassword(String memId, String currentPw, String newPw) throws Exception {
        String dbPw = memberDAO.selectPasswordHashById(memId); // 이름은 그대로 사용
        if (dbPw == null) return false;
        if (!currentPw.equals(dbPw)) return false; // 평문 비교
        memberDAO.updatePassword(memId, newPw);    // 평문 저장
        return true;
    }
}
