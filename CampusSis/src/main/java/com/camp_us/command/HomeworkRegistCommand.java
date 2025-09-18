package com.camp_us.command;

import java.text.SimpleDateFormat;
import org.springframework.web.multipart.MultipartFile;

import com.camp_us.dto.HomeworkVO;

public class HomeworkRegistCommand {

    private String hwName;
    private String hwDesc;
    private int hwNo;          // 주차
    private String startDate;     // yyyy-MM-dd
    private String startTime;     // HH:mm
    private String endDate;       // yyyy-MM-dd
    private String endTime;       // HH:mm
    private String lecsId;        // 선택형 또는 hidden
    private MultipartFile uploadFile; // 첨부파일 (선택)


    public String getHwName() {
		return hwName;
	}


	public void setHwName(String hwName) {
		this.hwName = hwName;
	}


	public String getHwDesc() {
		return hwDesc;
	}


	public void setHwDesc(String hwDesc) {
		this.hwDesc = hwDesc;
	}


	public int getHwNo() {
		return hwNo;
	}


	public void setHwNo(int hwNo) {
		this.hwNo = hwNo;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getLecsId() {
		return lecsId;
	}


	public void setLecsId(String lecsId) {
		this.lecsId = lecsId;
	}


	public MultipartFile getUploadFile() {
		return uploadFile;
	}


	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}


	public HomeworkVO toHomeworkVO() {
        HomeworkVO vo = new HomeworkVO();

        vo.setHwName(this.hwName);
        vo.setHwDesc(this.hwDesc);
        vo.setHwNo(this.hwNo);
        vo.setLecsId(this.lecsId);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            vo.setHwStartDate(sdf.parse(startDate + " " + startTime));
            vo.setHwEndDate(sdf.parse(endDate + " " + endTime));
        } catch (Exception e) {
            e.printStackTrace(); // 로그로 남기세요
        }

        return vo;
    }
}
