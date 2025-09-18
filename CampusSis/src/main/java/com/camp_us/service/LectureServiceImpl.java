package com.camp_us.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.camp_us.dao.LectureDAO;
import com.camp_us.dto.LectureVO;

@Service("lectureService")
public class LectureServiceImpl implements LectureService {

    private LectureDAO lectureDAO;

    @Autowired
    public LectureServiceImpl(@Qualifier("lectureDAO") LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
    
    private String uploadPath;


    @Override
    public void saveFinalPlan(String courseId, MultipartFile file) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("PDF 파일만 업로드 가능합니다.");
        }

        // 저장 디렉토리 보장
        File dir = new File("c:/uploads/final");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String savedName = UUID.randomUUID().toString() + "_" + originalName;
        File dest = new File(dir, savedName);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }

        LectureVO vo = new LectureVO();
        vo.setLec_id("JAVA101");
        vo.setLec_filename(originalName);
        vo.setLec_filepath(dest.getAbsolutePath());
        vo.setRegDate(new Date());

        lectureDAO.insertFinalPlan(vo);
    }
    
    public LectureVO getFirstLecturePlanmaker() {
        return lectureDAO.selectFirstOne();
    }
    
    public String getPlanFilePathByLecId(String lec_id) {
        return lectureDAO.selectFilePathById(lec_id);
    }

}

