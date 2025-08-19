package com.camp_us.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

import com.camp_us.dao.UnsubmitHomeworkDAO;
import com.camp_us.dto.UnsubmitHomeworkVO;

public class UnsubmitHomeworkServiceImpl implements UnsubmitHomeworkService{
	
	private UnsubmitHomeworkDAO unsubmithomeworkDAO;

	public UnsubmitHomeworkServiceImpl(UnsubmitHomeworkDAO unsubmithomeworkDAO) {
		this.unsubmithomeworkDAO = unsubmithomeworkDAO;
	}

	@Override
	public List<UnsubmitHomeworkVO> getUnsubmitHomeworkList(String stu_id) throws SQLException {
		List<UnsubmitHomeworkVO> list = unsubmithomeworkDAO.selectUnsubmitHomework(stu_id);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = new Date();
		
		Iterator<UnsubmitHomeworkVO> it = list.iterator();
		while (it.hasNext()) {
			UnsubmitHomeworkVO hw = it.next();
			Date endDate = hw.getHw_enddate();
			
			if(endDate == null || endDate.before(today)) {
				it.remove();
				continue;
			}
			hw.setHw_enddateStr(sdf.format(endDate));
		}
		
		return list;
	}

	@Override
	public UnsubmitHomeworkVO getStuIdbyMemId(String mem_id) throws SQLException {
		return unsubmithomeworkDAO.selectStuIdbyMemId(mem_id);
	}
	

}
