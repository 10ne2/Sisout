package com.camp_us.service;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.camp_us.command.PageMakerWH;
import com.camp_us.dao.MailFileDAO;
import com.camp_us.dao.MessageDAO;
import com.camp_us.dto.MailFileVO;
import com.camp_us.dto.MessageVO;

public class MessageServiceImpl implements MessageService{
	
	private MessageDAO messageDAO;
	private String summernotePath;
	private MailFileDAO mailFileDAO;
	
	public MessageServiceImpl(MessageDAO messageDAO, String summernotePath, MailFileDAO mailFileDAO) {
		this.messageDAO = messageDAO;
		this.summernotePath = summernotePath;
		this.mailFileDAO = mailFileDAO;
	}
	
	//카운트
	@Override
	public int unreadCount(String mem_id) throws SQLException{
		int count = messageDAO.selectReceiveUnreadMailCount(mem_id);
		return count;
	}

	
	
	//세부내용
	@Override
	public MessageVO detail(int mail_id) throws SQLException{
		messageDAO.selectMailByMailId(mail_id);
		MessageVO mail = messageDAO.selectMailByMailId(mail_id);
		
		List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail_id);
		mail.setMailFileList(mailFileList);
		
		return mail;
	}
	@Override
	public MessageVO getMail(int mail_id) throws SQLException {		
		MessageVO mail = messageDAO.selectMailByMailId(mail_id);
		
		List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail_id);
		mail.setMailFileList(mailFileList);
		
		return messageDAO.selectMailByMailId(mail_id);
	}
	

	// 대시보드
	@Override
	public List<MessageVO> receiveList(String mem_id) throws SQLException {
		List<MessageVO> mailList = messageDAO.selectReceiveMailByMemId(mem_id);
		
		if(mailList != null) for(MessageVO mail : mailList) {
			int mail_id = mail.getMail_id();
			
						
			List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail.getMail_id());
			mail.setMailFileList(mailFileList);
		}
		
		return mailList;
	}
	@Override
	public List<MessageVO> sendList(String mem_id) throws SQLException {
		List<MessageVO> mailList = messageDAO.selectSenderMailByMemId(mem_id);
		
		if(mailList != null) for(MessageVO mail : mailList) {
			int mail_id = mail.getMail_id();
			
						
			List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail.getMail_id());
			mail.setMailFileList(mailFileList);
		}
		
		return mailList;
	}
	@Override
	public List<MessageVO> wasteList(String mem_id) throws SQLException {
		List<MessageVO> mailList = messageDAO.selectAllWasteMail(mem_id);
		
		if(mailList != null) for(MessageVO mail : mailList) {
			int mail_id = mail.getMail_id();
			
						
			List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail.getMail_id());
			mail.setMailFileList(mailFileList);
		}
		
		return mailList;
	}
	
	
	
	// 받은메일함
	@Override
	public List<MessageVO> receiveMailList(PageMakerWH pageMaker, String mem_id) throws SQLException {
		List<MessageVO> mailList = messageDAO.selectSearchReceiveMailList(pageMaker, mem_id);
		
		if(mailList != null) for(MessageVO mail : mailList) {
			int mail_id = mail.getMail_id();
			
						
			List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail.getMail_id());
			mail.setMailFileList(mailFileList);
		}
		
		
		int totalCount = messageDAO.selectSearchReceiveMailListCount(pageMaker,mem_id);
		pageMaker.setTotalCount(totalCount);
		return mailList;
	}
	@Override
	public List<MessageVO> receiveImpList(PageMakerWH pageMaker, String mem_id) throws SQLException {
		List<MessageVO> mailList = messageDAO.selectSearchReceiveImpMailList(pageMaker, mem_id);
		
		if(mailList != null) for(MessageVO mail : mailList) {
			int mail_id = mail.getMail_id();
			
						
			List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail.getMail_id());
			mail.setMailFileList(mailFileList);
		}
		
		int totalCount = messageDAO.selectSearchReceiveImpMailListCount(pageMaker,mem_id);
		pageMaker.setTotalCount(totalCount);
		return mailList;
	}
	@Override
	public List<MessageVO> receiveReadList(PageMakerWH pageMaker, String mem_id) throws SQLException {
		List<MessageVO> mailList = messageDAO.selectSearchReceiveReadMailList(pageMaker, mem_id);
		
		if(mailList != null) for(MessageVO mail : mailList) {
			int mail_id = mail.getMail_id();
			
						
			List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail.getMail_id());
			mail.setMailFileList(mailFileList);
		}
		
		int totalCount = messageDAO.selectSearchReceiveReadMailListCount(pageMaker,mem_id);
		pageMaker.setTotalCount(totalCount);
		return mailList;
	}
	@Override
	public List<MessageVO> receiveLockList(PageMakerWH pageMaker, String mem_id) throws SQLException {
		List<MessageVO> mailList = messageDAO.selectSearchReceiveLockMailList(pageMaker, mem_id);
		
		if(mailList != null) for(MessageVO mail : mailList) {
			int mail_id = mail.getMail_id();
			
						
			List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail.getMail_id());
			mail.setMailFileList(mailFileList);
		}
		
		int totalCount = messageDAO.selectSearchReceiveLockMailListCount(pageMaker,mem_id);
		pageMaker.setTotalCount(totalCount);
		return mailList;
	}
	
	
	
	// 보낸메일함
	@Override
	public List<MessageVO> sendMailList(PageMakerWH pageMaker, String mem_id) throws SQLException {
		List<MessageVO> mailList = messageDAO.selectSearchSendMailList(pageMaker, mem_id);
		
		if(mailList != null) for(MessageVO mail : mailList) {
			int mail_id = mail.getMail_id();
			
						
			List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail.getMail_id());
			mail.setMailFileList(mailFileList);
		}
		
		int totalCount = messageDAO.selectSearchSendMailListCount(pageMaker,mem_id);
		pageMaker.setTotalCount(totalCount);
		return mailList;
	}
	@Override
	public List<MessageVO> sendImpList(PageMakerWH pageMaker, String mem_id) throws SQLException {
		List<MessageVO> mailList = messageDAO.selectSearchSendImpMailList(pageMaker, mem_id);
		
		if(mailList != null) for(MessageVO mail : mailList) {
			int mail_id = mail.getMail_id();
			
						
			List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail.getMail_id());
			mail.setMailFileList(mailFileList);
		}
		
		int totalCount = messageDAO.selectSearchSendImpMailListCount(pageMaker,mem_id);
		pageMaker.setTotalCount(totalCount);
		return mailList;
	}
	@Override
	public List<MessageVO> sendLockList(PageMakerWH pageMaker, String mem_id) throws SQLException {
		List<MessageVO> mailList = messageDAO.selectSearchSendLockMailList(pageMaker, mem_id);
		
		if(mailList != null) for(MessageVO mail : mailList) {
			int mail_id = mail.getMail_id();
			
						
			List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail.getMail_id());
			mail.setMailFileList(mailFileList);
		}
		
		int totalCount = messageDAO.selectSearchSendLockMailListCount(pageMaker,mem_id);
		pageMaker.setTotalCount(totalCount);
		return mailList;
	}
	
	
	
	//휴지통
	@Override
	public List<MessageVO> wasteList(PageMakerWH pageMaker, String mem_id) throws SQLException {
		List<MessageVO> mailList = messageDAO.selectWasteMailList(pageMaker, mem_id);
		
		if(mailList != null) for(MessageVO mail : mailList) {
			int mail_id = mail.getMail_id();
			
						
			List<MailFileVO> mailFileList = mailFileDAO.selectMailFileByMailId(mail.getMail_id());
			mail.setMailFileList(mailFileList);
		}
		
		int totalCount = messageDAO.selectWasteMailListCount(pageMaker,mem_id);
		pageMaker.setTotalCount(totalCount);
		return mailList;
	}
	
	
	//insert
	@Override
	public void registMail(MessageVO message) throws SQLException{
		
		/* mailID증가 */
		int mail_id = messageDAO.selectMailSeqNext();
		message.setMail_id(mail_id);
		messageDAO.insertMail(message);
		
		/* 첨부파일 */
		List<MailFileVO> mailFileList = message.getMailFileList();
		if(mailFileList != null) for(MailFileVO mailFile : mailFileList) {
			int mafile_no = mailFileDAO.selectMailFileSeqNext();
			mailFile.setMafile_no(mafile_no);
			mailFile.setMail_id(message.getMail_id());
			
			mailFileDAO.insertMailFile(mailFile);
		}
	}
		
		
	
	//update
	@Override
	public void updateRRead(int mail_id) throws SQLException{
		messageDAO.updateRRead(mail_id);
	}
	@Override
	public void updateRImp(int mail_id) throws SQLException{
		messageDAO.updateRImp(mail_id);
	}
	@Override
	public void updateSImp(int mail_id) throws SQLException{
		messageDAO.updateSImp(mail_id);
	}
	@Override
	public void updateRLock(int mail_id) throws SQLException{
		messageDAO.updateRLock(mail_id);
	}
	@Override
	public void updateSLock(int mail_id) throws SQLException{
		messageDAO.updateSLock(mail_id);
	}
	@Transactional
	public void updateWaste(int mail_id) throws SQLException{
		messageDAO.updateWaste(mail_id);
	}
	@Override
	public void updateWasteBack(int mail_id) throws SQLException{
		messageDAO.updateWasteBack(mail_id);
	}
	
	
	//delete
	@Override
	public void delete(int mail_id) throws SQLException{
		
		MessageVO mail = messageDAO.selectMailByMailId(mail_id);
		
		File dir = new File(summernotePath);
		File[] files = dir.listFiles();
		if(files!=null) for(File file : files) {
			if(mail.getMail_desc().contains(file.getName())) {
				file.delete();
			}
		}
		
		messageDAO.deleteMail(mail_id);
	}
	@Override
	public void deleteAll() throws SQLException{
		messageDAO.deleteAllWaste();
	}
}
