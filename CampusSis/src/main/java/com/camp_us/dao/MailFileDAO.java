package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.MailFileVO;

public interface MailFileDAO {
	
	MailFileVO selectMailFileByMafileNo(int mafile_no) throws SQLException;
	List<MailFileVO> selectMailFileByMailId(int mail_id) throws SQLException;
	int selectMailFileSeqNext() throws SQLException;
	
	void insertMailFile(MailFileVO mailFile) throws SQLException;
	void deleteMailFile(int mafile_no) throws SQLException;
	void deleteAllMailFile(int mail_id) throws SQLException;
}
