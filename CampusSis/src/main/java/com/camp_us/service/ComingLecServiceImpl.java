package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dao.ComingLecDAO;
import com.camp_us.dto.ComingLecVO;

public class ComingLecServiceImpl implements ComingLecService{
	
	private ComingLecDAO comingLecDAO;
	
	public ComingLecServiceImpl(ComingLecDAO comingLecDAO) {
	    this.comingLecDAO = comingLecDAO;
	}

	@Override
	public List<ComingLecVO> getComingLecList(String stu_id) throws SQLException {
		return comingLecDAO.selectComingLec(stu_id);
	}

}
