package com.camp_us.command;

import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.camp_us.dto.HomeworkSubmitVO;

public class HomeworkSubmitCommand {
    private int hwNo;
    private String hwsubComment;
    private List<MultipartFile> uploadFile;

    public int getHwNo() { return hwNo; }
    public void setHwNo(int hwNo) { this.hwNo = hwNo; }

    public String getHwsubComment() { return hwsubComment; }
    public void setHwsubComment(String hwsubComment) { this.hwsubComment = hwsubComment; }

    public List<MultipartFile> getUploadFile() { return uploadFile; }
    public void setUploadFile(List<MultipartFile> uploadFile) { this.uploadFile = uploadFile; }

    public HomeworkSubmitVO toHomeworkSubmitVO() {
        HomeworkSubmitVO vo = new HomeworkSubmitVO();
        vo.setHwNo(this.hwNo);
        vo.setHwsubComment(this.hwsubComment);
        vo.setSubmittedAt(new Date());
        return vo;
    }
}