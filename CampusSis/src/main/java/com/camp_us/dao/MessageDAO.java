package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.command.PageMaker;
import com.camp_us.dto.MessageVO;

public interface MessageDAO {
	
	//카운트
	int selectReceiveUnreadMailCount(String mem_id) throws SQLException;
	//세부내용
	MessageVO selectMailByMailId(int mail_id) throws SQLException;
	
	//대시보드
	List<MessageVO> selectReceiveMailByMemId(String mem_id) throws SQLException;
	List<MessageVO> selectSenderMailByMemId(String mem_id) throws SQLException;
	List<MessageVO> selectAllWasteMail(String mem_id) throws SQLException;
	
	//받은메일함
	List<MessageVO> selectSearchReceiveMailList(PageMaker pageMaker, String mem_id) throws SQLException;
	List<MessageVO> selectSearchReceiveImpMailList(PageMaker pageMaker, String mem_id) throws SQLException;
	List<MessageVO> selectSearchReceiveReadMailList(PageMaker pageMaker, String mem_id) throws SQLException;
	List<MessageVO> selectSearchReceiveLockMailList(PageMaker pageMaker, String mem_id) throws SQLException;
	int selectSearchReceiveMailListCount(PageMaker pageMaker, String mem_id) throws SQLException;
	int selectSearchReceiveImpMailListCount(PageMaker pageMaker, String mem_id) throws SQLException;
	int selectSearchReceiveReadMailListCount(PageMaker pageMaker, String mem_id) throws SQLException;
	int selectSearchReceiveLockMailListCount(PageMaker pageMaker, String mem_id) throws SQLException;
	
	//보낸메일함
	List<MessageVO> selectSearchSendMailList(PageMaker pageMaker, String mem_id) throws SQLException;
	List<MessageVO> selectSearchSendImpMailList(PageMaker pageMaker, String mem_id) throws SQLException;
	List<MessageVO> selectSearchSendLockMailList(PageMaker pageMaker, String mem_id) throws SQLException;
	int selectSearchSendMailListCount(PageMaker pageMaker, String mem_id) throws SQLException;
	int selectSearchSendImpMailListCount(PageMaker pageMaker, String mem_id) throws SQLException;
	int selectSearchSendLockMailListCount(PageMaker pageMaker, String mem_id) throws SQLException;
	
	//휴지통
	List<MessageVO> selectWasteMailList(PageMaker pageMaker, String mem_id) throws SQLException;
	int selectWasteMailListCount(PageMaker pageMaker, String mem_id) throws SQLException;
	
	//오토인크리드
	int selectMailSeqNext()throws SQLException;
	
	//insert
	void insertMail(MessageVO message) throws SQLException;
	
	//update
	void updateRRead(int mail_id) throws SQLException;
	void updateRImp(int mail_id) throws SQLException;
	void updateSImp(int mail_id) throws SQLException;
	void updateRLock(int mail_id) throws SQLException;
	void updateSLock(int mail_id) throws SQLException;
	void updateWaste(int mail_id) throws SQLException;
	void updateWasteBack(int mail_id) throws SQLException;
	
	//delete
	void deleteMail(int mail_id) throws SQLException;
	void deleteAllWaste() throws SQLException;
	
}
