package com.camp_us.dto;


import java.sql.Timestamp;

public class LecNoticeVO {

    private String lecNoticeId;     // 강의 공지사항 ID
    private String lecId;           // 강의 ID
    private String profesId;        // 교수 ID

    private String lecNoticeName;   // 강의 공지사항 제목
    private Timestamp lecNoticeDate;     // 작성일자

    private String lecNoticeDesc;   // 공지 내용
    private String fileName;        // 첨부파일명
    private String fileDetail;      // 첨부파일 상세

    private int viewCnt;            // 조회수

    private int rnum;               // 행 번호 (번호 출력을 위한 필드)

    // Getter / Setter
    public String getLecNoticeId() {
        return lecNoticeId;
    }

    public void setLecNoticeId(String lecNoticeId) {
        this.lecNoticeId = lecNoticeId;
    }

    public String getLecId() {
        return lecId;
    }

    public void setLecId(String lecId) {
        this.lecId = lecId;
    }

    public String getProfesId() {
        return profesId;
    }

    public void setProfesId(String profesId) {
        this.profesId = profesId;
    }

    public String getLecNoticeName() {
        return lecNoticeName;
    }

    public void setLecNoticeName(String lecNoticeName) {
        this.lecNoticeName = lecNoticeName;
    }

    public Timestamp getLecNoticeDate() {
		return lecNoticeDate;
	}

	public void setLecNoticeDate(Timestamp lecNoticeDate) {
		this.lecNoticeDate = lecNoticeDate;
	}

	public String getLecNoticeDesc() {
        return lecNoticeDesc;
    }

    public void setLecNoticeDesc(String lecNoticeDesc) {
        this.lecNoticeDesc = lecNoticeDesc;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDetail() {
        return fileDetail;
    }

    public void setFileDetail(String fileDetail) {
        this.fileDetail = fileDetail;
    }

    public int getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }

    public int getRnum() {
        return rnum;
    }

    public void setRnum(int rnum) {
        this.rnum = rnum;
    }
}
