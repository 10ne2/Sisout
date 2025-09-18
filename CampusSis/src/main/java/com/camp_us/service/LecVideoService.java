package com.camp_us.service;

import java.util.List;

import com.camp_us.dto.LecVideoVO;

public interface LecVideoService {
	void addVideo(LecVideoVO vo);
    void modifyVideo(LecVideoVO vo);
    LecVideoVO getVideo(String lecvidId);
    List<LecVideoVO> getVideosByLecture(String lecId);
    List<LecVideoVO> getVideosByWeek(String lecId, String week);
    String getLectureIdByVideoId(String lecvidId);
    LecVideoVO getVideoById(String lecvidId);


}
