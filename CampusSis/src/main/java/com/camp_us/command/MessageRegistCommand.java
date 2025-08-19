package com.camp_us.command;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.camp_us.dto.MessageVO;

public class MessageRegistCommand {
	
	private String mail_receiver;
	private String mail_name;
	private String mail_desc;
	private String mail_sender;
	private List<MultipartFile> uploadFile;
	
	public String getMail_name() {
		return mail_name;
	}
	public void setMail_name(String mail_name) {
		this.mail_name = mail_name;
	}
	public String getMail_desc() {
		return mail_desc;
	}
	public void setMail_desc(String mail_desc) {
		this.mail_desc = mail_desc;
	}
	
	public MessageVO toMessage() {
		MessageVO message = new MessageVO();
		
		message.setMail_desc(mail_desc);
		message.setMail_name(mail_name);
		message.setMail_receiver(mail_receiver);
		message.setMail_sender(mail_sender);
		
		return message;
	}
	public String getMail_sender() {
		return mail_sender;
	}
	public void setMail_sender(String mail_sender) {
		this.mail_sender = mail_sender;
	}
	public String getMail_receiver() {
		return mail_receiver;
	}
	public void setMail_receiver(String mail_receiver) {
		this.mail_receiver = mail_receiver;
	}
	public List<MultipartFile> getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(List<MultipartFile> uploadFile) {
		this.uploadFile = uploadFile;
	}
	
}
