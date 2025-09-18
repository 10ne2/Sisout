package com.camp_us.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.camp_us.dto.LecVideoVO;

public interface LecVideoDAO {
	void insertVideo(LecVideoVO vo);
    void updateVideo(LecVideoVO vo);
    LecVideoVO selectVideo(String lecvidId);
    List<LecVideoVO> selectVideosByLecture(String lecId);
    List<LecVideoVO> selectVideosByWeek(@Param("lecId") String lecId, @Param("lecvidWeek") String lecvidWeek);
    String getLectureIdByVideoId(String lecvidId);
    LecVideoVO getVideoById(String lecvidId);
}