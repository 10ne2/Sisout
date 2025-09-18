package com.camp_us.dto;

import java.util.Date;

public class EvaluationVO {
	private String eval_id;  // 고유번호
    private String profes_id; // 작성자
    private String eval_content; // 평가내용
    private int eval_score; //평가점수
    private Date eval_regdate; // 등록일
    private Date eval_update; //수정일
    private String rm_id;
    
    
	public String getRm_id() {
		return rm_id;
	}
	public void setRm_id(String rm_id) {
		this.rm_id = rm_id;
	}
	public void setEval_score(int eval_score) {
		this.eval_score = eval_score;
	}
	public String getEval_id() {
		return eval_id;
	}
	public void setEval_id(String eval_id) {
		this.eval_id = eval_id;
	}
	public String getProfes_id() {
		return profes_id;
	}
	public void setProfes_id(String profes_id) {
		this.profes_id = profes_id;
	}
	public String getEval_content() {
		return eval_content;
	}
	public void setEval_content(String eval_content) {
		this.eval_content = eval_content;
	}
	public Date getEval_regdate() {
		return eval_regdate;
	}
	public void setEval_regdate(Date eval_regdate) {
		this.eval_regdate = eval_regdate;
	}
	public Date getEval_update() {
		return eval_update;
	}
	public void setEval_update(Date eval_update) {
		this.eval_update = eval_update;
	}
	public int getEval_score() {
		return eval_score;
	}
    
    
}
