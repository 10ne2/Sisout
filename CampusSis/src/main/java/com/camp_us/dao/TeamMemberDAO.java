package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.TeamMemberVO;

public interface TeamMemberDAO {

    // 팀원 등록
    void insertTeamMember(TeamMemberVO member) throws SQLException;

    // 특정 팀에 속한 팀원 목록 조회
    List<TeamMemberVO> selectTeamMembersByTeamId(String team_id) throws SQLException;
}