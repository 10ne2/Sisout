package com.camp_us.command;

import com.camp_us.dto.EvaluationVO;

public class EvaluationRegistCommand {
	private String eval_id;
	private String profes_id;
	private String eval_content;
	private int Eval_score;
	private String rm_id;
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
	public int getEval_score() {
		return Eval_score;
	}
	public void setEval_score(int eval_score) {
		Eval_score = eval_score;
	}
	public String getRm_id() {
		return rm_id;
	}
	public void setRm_id(String rm_id) {
		this.rm_id = rm_id;
	}
	public EvaluationVO toEvaluationVO(String eval_id,String rm_id) {
		EvaluationVO evaluation = new EvaluationVO();
		System.out.println(">>> rm_id from command: " + rm_id);
		evaluation.setEval_id(eval_id);
		evaluation.setEval_content(eval_content);
		evaluation.setEval_score(Eval_score);
		evaluation.setProfes_id(profes_id);
		evaluation.setRm_id(rm_id);
		return evaluation;
	}
	
}
