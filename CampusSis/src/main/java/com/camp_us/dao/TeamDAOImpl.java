package com.camp_us.dao;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;

import com.camp_us.dto.TeamVO;

public class TeamDAOImpl implements TeamDAO {

    private SqlSession session;

    public TeamDAOImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public void insertTeam(TeamVO team) throws SQLException {
        session.insert("Team-Mapper.insertTeam", team);
    }

    @Override
    public String selectTeamSeqNext() throws SQLException {
        return session.selectOne("Team-Mapper.selectTeamSeqNext");
    }
}