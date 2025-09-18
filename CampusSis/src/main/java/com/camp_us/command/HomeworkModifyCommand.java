package com.camp_us.command;

import org.springframework.web.multipart.MultipartFile;

import com.camp_us.dto.HomeworkSubmitVO;

public class HomeworkModifyCommand {

	private int hwNo;
	private String lecsId;
	private String comment;
	private MultipartFile file;
	public int getHwNo() {
		return hwNo;
	}
	public void setHwNo(int hwNo) {
		this.hwNo = hwNo;
	}
	public String getLecsId() {
		return lecsId;
	}
	public void setLecsId(String lecsId) {
		this.lecsId = lecsId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public HomeworkSubmitVO toHomeworkSubmitVO() {
		HomeworkSubmitVO vo = new HomeworkSubmitVO();
		
		vo.setHwNo(this.hwNo);
		vo.setLecsId(this.lecsId);
		vo.setHwsubComment(this.comment);
		
		return vo;
	}
}
