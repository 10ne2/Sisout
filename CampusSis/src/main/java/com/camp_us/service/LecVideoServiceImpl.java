package com.camp_us.service;

import java.util.List;

import com.camp_us.dao.LecVideoDAO;
import com.camp_us.dto.LecVideoVO;

public class LecVideoServiceImpl implements LecVideoService {

    private LecVideoDAO videoDAO;
    public LecVideoServiceImpl(LecVideoDAO videoDAO) {
        this.videoDAO = videoDAO;
    }

    @Override
    public void addVideo(LecVideoVO vo) {
        videoDAO.insertVideo(vo);
    }

    @Override
    public void modifyVideo(LecVideoVO vo) {
        videoDAO.updateVideo(vo);
    }

    @Override
    public LecVideoVO getVideo(String lecvidId) {
        return videoDAO.selectVideo(lecvidId);
    }

    @Override
    public List<LecVideoVO> getVideosByLecture(String lecId) {
        return videoDAO.selectVideosByLecture(lecId);
    }

    @Override
    public List<LecVideoVO> getVideosByWeek(String lecId, String week) {
        return videoDAO.selectVideosByWeek(lecId, week);
    }
    
    @Override
    public String getLectureIdByVideoId(String lecvidId) {
        return videoDAO.getLectureIdByVideoId(lecvidId);
    }
    
    @Override
    public LecVideoVO getVideoById(String lecvidId) {
        return videoDAO.getVideoById(lecvidId);
    }
}
