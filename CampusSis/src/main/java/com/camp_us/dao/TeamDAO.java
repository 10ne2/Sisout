package com.camp_us.dao;

import java.sql.SQLException;

import com.camp_us.dto.TeamVO;

public interface TeamDAO {

    // 팀 등록
    void insertTeam(TeamVO team) throws SQLException;

    // 팀 ID 생성
    String selectTeamSeqNext() throws SQLException;
}