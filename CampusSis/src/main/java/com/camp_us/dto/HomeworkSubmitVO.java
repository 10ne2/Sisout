package com.camp_us.dto;

import java.util.Date;

public class HomeworkSubmitVO {
    
    private String hwsubHsno;         // 과제 제출 번호 (PK)
    private int hwNo;                 // 과제 번호 (FK)
    private String hwsubFilename;     // 제출 파일명
    private Date submittedAt;         // 제출일
    private String hwsubComment;      // 과제 내용(학생 입력)
    private String hwsubFeedback;     // 교수 피드백
    private String hwsubStatus;       // 제출 상태 (예: 제출완료)
    private String lecsId;            // 강의실 ID
    private String stuId;             // 학생 ID
    private String writer;
    
    private String professorId;
    private String professorName;
    private String professorPicPath;
    private String professorMemId;

    // 기본 생성자
    public HomeworkSubmitVO() {}

    // --- Getter / Setter ---
    public String getHwsubHsno() {
        return hwsubHsno;
    }

    public void setHwsubHsno(String hwsubHsno) {
        this.hwsubHsno = hwsubHsno;
    }

    public int getHwNo() {
        return hwNo;
    }

    public void setHwNo(int hwNo) {
        this.hwNo = hwNo;
    }

    public String getHwsubFilename() {
        return hwsubFilename;
    }

    public void setHwsubFilename(String hwsubFilename) {
        this.hwsubFilename = hwsubFilename;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getHwsubComment() {
        return hwsubComment;
    }

    public void setHwsubComment(String hwsubComment) {
        this.hwsubComment = hwsubComment;
    }

    public String getHwsubFeedback() {
        return hwsubFeedback;
    }

    public void setHwsubFeedback(String hwsubFeedback) {
        this.hwsubFeedback = hwsubFeedback;
    }

    public String getHwsubStatus() {
        return hwsubStatus;
    }

    public void setHwsubStatus(String hwsubStatus) {
        this.hwsubStatus = hwsubStatus;
    }

    public String getLecsId() {
        return lecsId;
    }

    public void setLecsId(String lecsId) {
        this.lecsId = lecsId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }


	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getProfessorId() {
		return professorId;
	}

	public void setProfessorId(String professorId) {
		this.professorId = professorId;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public String getProfessorPicPath() {
		return professorPicPath;
	}

	public void setProfessorPicPath(String professorPicPath) {
		this.professorPicPath = professorPicPath;
	}
	

	public String getProfessorMemId() {
		return professorMemId;
	}

	public void setProfessorMemId(String professorMemId) {
		this.professorMemId = professorMemId;
	}

	@Override
    public String toString() {
        return "HomeworkSubmitVO [hwsubHsno=" + hwsubHsno + ", hwNo=" + hwNo + ", hwsubFilename=" + hwsubFilename
                + ", submittedAt=" + submittedAt + ", hwsubComment=" + hwsubComment + ", hwsubFeedback="
                + hwsubFeedback + ", hwsubStatus=" + hwsubStatus + ", lecsId=" + lecsId + ", stuId=" + stuId + ", professorMemId=" + professorMemId + "}";
    }
}
