package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.camp_us.dto.TeamMemberVO;

public class TeamMemberDAOImpl implements TeamMemberDAO {

    private SqlSession session;

    public TeamMemberDAOImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public void insertTeamMember(TeamMemberVO member) throws SQLException {
        session.insert("TeamMember-Mapper.insertTeamMember", member);
    }

    @Override
    public List<TeamMemberVO> selectTeamMembersByTeamId(String team_id) throws SQLException {
        return session.selectList("TeamMember-Mapper.selectTeamMembersByTeamId", team_id);
    }
}