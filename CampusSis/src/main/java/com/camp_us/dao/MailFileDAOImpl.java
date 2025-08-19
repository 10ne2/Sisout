package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.camp_us.dto.MailFileVO;

public class MailFileDAOImpl implements MailFileDAO{
	
	private SqlSession session;
	public MailFileDAOImpl(SqlSession session) {
		this.session = session;
	}

	@Override
	public MailFileVO selectMailFileByMafileNo(int mafile_no) throws SQLException {
		return session.selectOne("MailFile-Mapper.selectMailFileByMafileNo",mafile_no);
	}

	@Override
	public List<MailFileVO> selectMailFileByMailId(int mail_id) throws SQLException {
		return session.selectList("MailFile-Mapper.selectMailFileByMailId",mail_id);
	}

	@Override
	public void insertMailFile(MailFileVO mailFile) throws SQLException {
		session.insert("MailFile-Mapper.insertMailFile",mailFile);
	}

	@Override
	public void deleteMailFile(int mafile_no) throws SQLException {
		session.delete("MailFile-Mapper.deleteMailFile",mafile_no);
	}

	@Override
	public void deleteAllMailFile(int mail_id) throws SQLException {
		session.delete("MailFile-Mapper.deleteAllMailFile",mail_id); 
	}

	@Override
	public int selectMailFileSeqNext() throws SQLException {
		return  session.selectOne("MailFile-Mapper.selectMailFileSeqNext");
	}

}
