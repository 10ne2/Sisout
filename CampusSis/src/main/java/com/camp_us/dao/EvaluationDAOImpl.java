package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.camp_us.command.PageMakerRM;
import com.camp_us.dto.EvaluationVO;

public class EvaluationDAOImpl implements EvaluationDAO{
	
	private SqlSession session;
	public EvaluationDAOImpl(SqlSession session) {
		this.session = session;
	}
	
	@Override
	public List<EvaluationVO> selectEvaluationList(String rm_id, PageMakerRM pageMaker) throws SQLException {
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		
		RowBounds bounds = new RowBounds(offset,limit);
		
		return session.selectList("Evaluation-Mapper.selectEvaluationList",rm_id,bounds);
	}

	@Override
	public int countEvaluation(String rm_id) throws SQLException {
		return session.selectOne("Evaluation-Mapper.countEvaluation",rm_id);
	}

	@Override
	public String selectEvaluationSeqNextValue() throws SQLException {
		return session.selectOne("Evaluation-Mapper.selectEvaluationSeqNextValue");
	}

	@Override
	public void insertEvaluation(EvaluationVO evaluation) throws SQLException {
		session.insert("Evaluation-Mapper.insertEvaluation",evaluation);	
	}

	@Override
	public void updateEvaluation(EvaluationVO evaluation) throws SQLException {
		session.update("Evaluation-Mapper.updateEvaluation",evaluation);
	}

	@Override
	public void deleteEvaluation(String eval_id) throws SQLException {
		session.delete("Evaluation-Mapper.deleteEvaluation",eval_id);
	}
	@Override
	public EvaluationVO selectEvaluationByEval_id(String eval_id) {
		return session.selectOne("Evaluation-Mapper.selectEvaluationByEval_id",eval_id);
	}
}
