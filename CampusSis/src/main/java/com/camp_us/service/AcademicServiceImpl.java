package com.camp_us.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camp_us.dao.AcademicDAO;
import com.camp_us.dto.AcademicVO;

@Service
public class AcademicServiceImpl implements AcademicService {

    private AcademicDAO academicDAO;
    
    public AcademicServiceImpl(AcademicDAO academicDAO) {
	    this.academicDAO = academicDAO;
	}


    @Override
    public AcademicVO getByMemId(String memId)throws SQLException {
        return academicDAO.selectByMemId(memId);
    }

    @Override
    @Transactional
    public int save(AcademicVO vo)throws SQLException {
        return academicDAO.upsert(vo);
    }

    @Override
    public int countSaByMemId(String memId) {
        return academicDAO.countSaByMemId(memId);   // ← 실제 DAO 위임
    }
    
    @Override
    public String whoAmI() {
        return academicDAO.whoAmI();                // ← 위임
    }
    
    @Override
    public String findStuIdByMemId(String memId) {
        return academicDAO.findStuIdByMemId(memId);
    }
    @Override
    public int countSaByStuId(String stuId) {
        return academicDAO.countSaByStuId(stuId);
    }

    
}
