package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.command.PageMaker;
import com.camp_us.dto.MessageVO;

public interface MessageService {
	
	//카운트
	int unreadCount(String mem_id) throws SQLException;
	//세부내용
	MessageVO detail(int mail_id) throws SQLException;
	MessageVO getMail(int mail_id)throws SQLException;
	
	
	//대시보드
	List<MessageVO> receiveList(String mem_id) throws SQLException;
	List<MessageVO> sendList(String mem_id) throws SQLException;
	List<MessageVO> wasteList(String mem_id) throws SQLException;
	
	//받은메일
	List<MessageVO> receiveMailList(PageMaker pageMaker, String mem_id) throws SQLException;
	List<MessageVO> receiveImpList(PageMaker pageMaker, String mem_id) throws SQLException;
	List<MessageVO> receiveReadList(PageMaker pageMaker, String mem_id) throws SQLException;
	List<MessageVO> receiveLockList(PageMaker pageMaker, String mem_id) throws SQLException;
	
	//보낸메일
	List<MessageVO> sendMailList(PageMaker pageMaker, String mem_id) throws SQLException;
	List<MessageVO> sendImpList(PageMaker pageMaker, String mem_id) throws SQLException;
	List<MessageVO> sendLockList(PageMaker pageMaker, String mem_id) throws SQLException;
	
	//휴지통
	List<MessageVO> wasteList(PageMaker pageMaker, String mem_id) throws SQLException;
	
	//insert
	void registMail(MessageVO message) throws SQLException;
	
	//updqte
	void updateRRead(int mail_id) throws SQLException;
	void updateRImp(int mail_id) throws SQLException;
	void updateSImp(int mail_id) throws SQLException;
	void updateRLock(int mail_id) throws SQLException;
	void updateSLock(int mail_id) throws SQLException;
	void updateWaste(int mail_id) throws SQLException;
	void updateWasteBack(int mail_id) throws SQLException;
	
	//delete
	void delete(int mail_id) throws SQLException;
	void deleteAll() throws SQLException;
}