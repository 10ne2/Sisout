package com.camp_us.dao;

import java.sql.SQLException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.camp_us.dto.AcademicVO;

@Repository
public class AcademicDAOImpl implements AcademicDAO {

    @Autowired
    private SqlSession session;

    @Override
    public AcademicVO selectByMemId(String memId) throws SQLException {
        // 매퍼 네임스페이스: com.camp_us.dao.AcademicDAO
        return session.selectOne("com.camp_us.dao.AcademicDAO.selectByMemId", memId);
        // 또는 mapper proxy로:
        // return session.getMapper(AcademicDAO.class).selectByMemId(memId);
    }

    @Override
    public int upsert(AcademicVO vo) throws SQLException {
        // <update id="upsert"> 이므로 update로 호출
        return session.update("com.camp_us.dao.AcademicDAO.upsert", vo);
        // 또는:
        // return session.getMapper(AcademicDAO.class).upsert(vo);
    }

    @Override
    public int countSaByMemId(String memId) {
        return session.getMapper(AcademicDAO.class).countSaByMemId(memId);
    }

    @Override
    public String whoAmI() {
        return session.getMapper(AcademicDAO.class).whoAmI();
    }

    @Override
    public String findStuIdByMemId(String memId) {
        return session.getMapper(AcademicDAO.class).findStuIdByMemId(memId);
    }

    @Override
    public int countSaByStuId(String stuId) {
        return session.getMapper(AcademicDAO.class).countSaByStuId(stuId);
    }
}
