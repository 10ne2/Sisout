package com.camp_us.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.camp_us.command.PageMaker;
import com.camp_us.dto.MessageVO;

public class MessageDAOImpl implements MessageDAO{
	
private SqlSession session;
	
	public MessageDAOImpl(SqlSession session) {
		this.session = session;
	}
	
	//카운트
	
	@Override
	public int selectReceiveUnreadMailCount(String mem_id) throws SQLException{
		int count = session.selectOne("Message-Mapper.selectReceiveUnreadMailCount", mem_id);
		
		return count;
	}
	// 세부내용
	@Override
	public MessageVO selectMailByMailId(int mail_id) throws SQLException{
		MessageVO mailList = session.selectOne("Message-Mapper.selectMailByMailId", mail_id);
		
		return mailList;
	}

	
	
	
	// 대시보드
	@Override
	public List<MessageVO> selectReceiveMailByMemId(String mem_id) throws SQLException {
		List<MessageVO> mailList = session.selectList("Message-Mapper.selectReceiveMailByMemId", mem_id);
		
		return mailList;
	}
	@Override
	public List<MessageVO> selectSenderMailByMemId(String mem_id) throws SQLException {
		List<MessageVO> mailList = session.selectList("Message-Mapper.selectSenderMailByMemId", mem_id);
		
		return mailList;
	}
	@Override
	public List<MessageVO> selectAllWasteMail(String mem_id) throws SQLException {
		List<MessageVO> mailList = session.selectList("Message-Mapper.selectAllWasteMail", mem_id);
		
		return mailList;
	}

	
	
	// 받은메일함
	@Override
	public List<MessageVO> selectSearchReceiveMailList(PageMaker pageMaker, String mem_id) throws SQLException {
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		RowBounds bounds = new RowBounds(offset,limit);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		List<MessageVO> mailList = session.selectList("Message-Mapper.selectSearchReceiveMailList", dataMap, bounds);
		
		return mailList;
	}
	@Override
	public List<MessageVO> selectSearchReceiveImpMailList(PageMaker pageMaker, String mem_id) throws SQLException {
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		RowBounds bounds = new RowBounds(offset,limit);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		List<MessageVO> mailList = session.selectList("Message-Mapper.selectSearchReceiveImpMailList", dataMap, bounds);
		
		return mailList;
	}
	@Override
	public List<MessageVO> selectSearchReceiveReadMailList(PageMaker pageMaker, String mem_id) throws SQLException {
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		RowBounds bounds = new RowBounds(offset,limit);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		List<MessageVO> mailList = session.selectList("Message-Mapper.selectSearchReceiveReadMailList", dataMap, bounds);
		
		return mailList;
	}
	@Override
	public List<MessageVO> selectSearchReceiveLockMailList(PageMaker pageMaker, String mem_id) throws SQLException {
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		RowBounds bounds = new RowBounds(offset,limit);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		List<MessageVO> mailList = session.selectList("Message-Mapper.selectSearchReceiveLockMailList", dataMap, bounds);
		
		return mailList;
	}
	@Override
	public int selectSearchReceiveMailListCount(PageMaker pageMaker, String mem_id) throws SQLException {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		int mailList = session.selectOne("Message-Mapper.selectSearchReceiveMailListCount", dataMap);
		
		return mailList;
	}
	@Override
	public int selectSearchReceiveImpMailListCount(PageMaker pageMaker, String mem_id) throws SQLException {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		int mailList = session.selectOne("Message-Mapper.selectSearchReceiveImpMailListCount", dataMap);
		
		return mailList;
	}
	
	@Override
	public int selectSearchReceiveReadMailListCount(PageMaker pageMaker, String mem_id) throws SQLException {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		int mailList = session.selectOne("Message-Mapper.selectSearchReceiveReadMailListCount", dataMap);
		
		return mailList;
	}
	@Override
	public int selectSearchReceiveLockMailListCount(PageMaker pageMaker, String mem_id) throws SQLException {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		int mailList = session.selectOne("Message-Mapper.selectSearchReceiveLockMailListCount", dataMap);
		
		return mailList;
	}
	
	
	
	//보낸 메일
	@Override
	public List<MessageVO> selectSearchSendMailList(PageMaker pageMaker, String mem_id) throws SQLException {
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		RowBounds bounds = new RowBounds(offset,limit);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		List<MessageVO> mailList = session.selectList("Message-Mapper.selectSearchSendMailList", dataMap, bounds);
		
		return mailList;
	}
	@Override
	public List<MessageVO> selectSearchSendImpMailList(PageMaker pageMaker, String mem_id) throws SQLException {
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		RowBounds bounds = new RowBounds(offset,limit);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		List<MessageVO> mailList = session.selectList("Message-Mapper.selectSearchSendImpMailList", dataMap, bounds);
		
		return mailList;
	}
	@Override
	public List<MessageVO> selectSearchSendLockMailList(PageMaker pageMaker, String mem_id) throws SQLException {
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		RowBounds bounds = new RowBounds(offset,limit);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		List<MessageVO> mailList = session.selectList("Message-Mapper.selectSearchSendLockMailList", dataMap, bounds);
		
		return mailList;
	}
	@Override
	public int selectSearchSendMailListCount(PageMaker pageMaker, String mem_id) throws SQLException {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		int mailList = session.selectOne("Message-Mapper.selectSearchSendMailListCount", dataMap);
		
		return mailList;
	}
	@Override
	public int selectSearchSendImpMailListCount(PageMaker pageMaker, String mem_id) throws SQLException {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		int mailList = session.selectOne("Message-Mapper.selectSearchSendImpMailListCount", dataMap);
		
		return mailList;
	}
	@Override
	public int selectSearchSendLockMailListCount(PageMaker pageMaker, String mem_id) throws SQLException {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("mem_id", mem_id);
		
		int mailList = session.selectOne("Message-Mapper.selectSearchSendLockMailListCount", dataMap);
		
		return mailList;
	}
	
	
	
	// 휴지통
	@Override
	public List<MessageVO> selectWasteMailList(PageMaker pageMaker, String mem_id) throws SQLException{
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		RowBounds bounds = new RowBounds(offset,limit);
		
		List<MessageVO> mailList = session.selectList("Message-Mapper.selectWasteMailList", mem_id, bounds);
		
		return mailList;
	}
	@Override
	public int selectWasteMailListCount(PageMaker pageMaker, String mem_id) throws SQLException{
		int mailList = session.selectOne("Message-Mapper.selectWasteMailListCount", mem_id);
		
		return mailList;
	}
	
	
	
	//오토 인크리드
	@Override
	public int selectMailSeqNext() throws SQLException {
		return  session.selectOne("Message-Mapper.selectMailSeqNext");
	}
	
	
	
	// insert
	@Override
	public void insertMail(MessageVO message) throws SQLException{
		session.insert("Message-Mapper.insertMail", message);
	}
	
	
	
	//update
	@Override
	public void updateRRead(int mail_id) throws SQLException {
		session.update("Message-Mapper.updateRRead",mail_id);		
	}
	@Override
	public void updateRImp(int mail_id) throws SQLException {
	    session.update("Message-Mapper.updateRImp", mail_id);		
	}
	@Override
	public void updateSImp(int mail_id) throws SQLException {
	    session.update("Message-Mapper.updateSImp", mail_id);		
	}
	@Override
	public void updateRLock(int mail_id) throws SQLException {
	    session.update("Message-Mapper.updateRLock", mail_id);		
	}
	@Override
	public void updateSLock(int mail_id) throws SQLException {
	    session.update("Message-Mapper.updateSLock", mail_id);		
	}
	@Override
	public void updateWaste(int mail_id) throws SQLException {
	    session.update("Message-Mapper.updateWaste", mail_id);		
	}
	@Override
	public void updateWasteBack(int mail_id) throws SQLException {
	    session.update("Message-Mapper.updateWasteBack", mail_id);		
	}
	
	
	
	//delete
	@Override
	public void deleteMail(int mail_id) throws SQLException {
	    session.delete("Message-Mapper.deleteMail", mail_id);		
	}
	@Override
	public void deleteAllWaste() throws SQLException {
	    session.delete("Message-Mapper.deleteAllWaste");		
	}
	

}
