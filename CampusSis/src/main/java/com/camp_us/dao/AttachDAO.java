package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.AttachVO;

public interface AttachDAO {

	List<AttachVO> selectAttachByPno(String rm_id) throws SQLException;
	AttachVO selectAttachByAno(int ano)throws SQLException;
	
	void insertAttach(AttachVO attach)throws SQLException;
	void deletAttach(int ano)throws SQLException;
	void deletAllAttach(String rm_id)throws SQLException;
}
