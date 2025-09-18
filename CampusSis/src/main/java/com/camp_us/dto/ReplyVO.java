package com.camp_us.dto;

import java.util.Date;

public class ReplyVO {

    private int rno;
    private int boardId; // 게시글 번호
    private String replyer;
    private String replytext;
    private Date regdate;
    private Date updatedate;
    private String formattedRegdate;

    public String getFormattedRegdate() {
        return formattedRegdate;
    }
    public void setFormattedRegdate(String formattedRegdate) {
        this.formattedRegdate = formattedRegdate;
    }

    // Getter/Setter
    public int getRno() {
        return rno;
    }

    public void setRno(int rno) {
        this.rno = rno;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getReplyer() {
        return replyer;
    }

    public void setReplyer(String replyer) {
        this.replyer = replyer;
    }

    public String getReplytext() {
        return replytext;
    }

    public void setReplytext(String replytext) {
        this.replytext = replytext;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }
}
