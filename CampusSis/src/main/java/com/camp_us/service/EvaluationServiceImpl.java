package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.command.PageMakerRM;
import com.camp_us.dao.EvaluationDAO;
import com.camp_us.dto.EvaluationVO;

public class EvaluationServiceImpl implements EvaluationService{

	private EvaluationDAO evaluationDAO;
	public EvaluationServiceImpl(EvaluationDAO evaluationDAO) {
		this.evaluationDAO = evaluationDAO;
	}
	@Override
	public List<EvaluationVO> list(String rm_id, PageMakerRM pageMaker) throws SQLException {
		List<EvaluationVO> evaluationList = evaluationDAO.selectEvaluationList(rm_id, pageMaker);
		
		int count = evaluationDAO.countEvaluation(rm_id);
		pageMaker.setTotalCount(count);
		
		return evaluationList;
	}

	@Override
	public void regist(EvaluationVO evaluation) throws SQLException {
		String eval_id = evaluationDAO.selectEvaluationSeqNextValue();
		evaluation.setEval_id(eval_id);
		evaluationDAO.insertEvaluation(evaluation);	
		
	}

	@Override
	public void modify(EvaluationVO evaluation) throws SQLException {
		evaluationDAO.updateEvaluation(evaluation);
		
	}

	@Override
	public void remove(String eval_id) throws SQLException {
		evaluationDAO.deleteEvaluation(eval_id);
	}
	@Override
	public EvaluationVO selectEvaluationByEval_id(String eval_id) throws SQLException {
		return evaluationDAO.selectEvaluationByEval_id(eval_id);
	}
}
