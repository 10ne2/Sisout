package com.camp_us.dto;

import java.util.Date;

public class BoardVO {
    private String boardId;       // 게시물 ID
    private String boardName;     // 게시물 제목
    private String boardCat;      // 카테고리
    private Date boardDate;     // 최초 작성일
    private String pfileName;     // 첨부파일명
    private String pfileDetail;   // 첨부파일 상세
    private String boardDesc;     // 게시물 본문
    private Date boardMod;      // 수정일
    private String memId; 
    private String boardContent;
    private String memName;
    
    public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	// 작성자 (회원 ID)
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getBoardCat() {
		return boardCat;
	}
	public void setBoardCat(String boardCat) {
		this.boardCat = boardCat;
	}
	
	public Date getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(Date boardDate) {
		this.boardDate = boardDate;
	}
	public String getPfileName() {
		return pfileName;
	}
	public void setPfileName(String pfileName) {
		this.pfileName = pfileName;
	}
	public String getPfileDetail() {
		return pfileDetail;
	}
	public void setPfileDetail(String pfileDetail) {
		this.pfileDetail = pfileDetail;
	}
	public String getBoardDesc() {
		return boardDesc;
	}
	public void setBoardDesc(String boardDesc) {
		this.boardDesc = boardDesc;
	}
	public Date getBoardMod() {
		return boardMod;
	}
	public void setBoardMod(Date boardMod) {
		this.boardMod = boardMod;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	
	

    
 
}
