package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.command.PageMakerRM;
import com.camp_us.dto.EvaluationVO;


public interface EvaluationService {
	List<EvaluationVO> list(String rm_id, PageMakerRM pageMaker)throws SQLException;
	
	EvaluationVO selectEvaluationByEval_id(String eval_id)throws SQLException;
	//등록
	void regist(EvaluationVO evaluation) throws SQLException;
	
	//수정
	void modify(EvaluationVO evaluation)throws SQLException;
	
	//삭제
	void remove(String eval_id)throws SQLException;
}
