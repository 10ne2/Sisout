package com.camp_us.dto;

import java.util.Date;

public class AcademicVO {

	private String stuId;
    private Integer entranceYear;
    private Date entranceDate;
    private Integer entryGrade;
    private String college;
    private String major;
    private String minor;
    private Integer gradYear;
    private Integer semesters;
    private String gradMajor;
    private String gradDept;
    private Date gradDate;
    
    
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public Integer getEntranceYear() {
		return entranceYear;
	}
	public void setEntranceYear(Integer entranceYear) {
		this.entranceYear = entranceYear;
	}
	public Date getEntranceDate() {
		return entranceDate;
	}
	public void setEntranceDate(Date entranceDate) {
		this.entranceDate = entranceDate;
	}
	public Integer getEntryGrade() {
		return entryGrade;
	}
	public void setEntryGrade(Integer entryGrade) {
		this.entryGrade = entryGrade;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getMinor() {
		return minor;
	}
	public void setMinor(String minor) {
		this.minor = minor;
	}
	public Integer getGradYear() {
		return gradYear;
	}
	public void setGradYear(Integer gradYear) {
		this.gradYear = gradYear;
	}
	public Integer getSemesters() {
		return semesters;
	}
	public void setSemesters(Integer semesters) {
		this.semesters = semesters;
	}
	public String getGradMajor() {
		return gradMajor;
	}
	public void setGradMajor(String gradMajor) {
		this.gradMajor = gradMajor;
	}
	public String getGradDept() {
		return gradDept;
	}
	public void setGradDept(String gradDept) {
		this.gradDept = gradDept;
	}
	public Date getGradDate() {
		return gradDate;
	}
	public void setGradDate(Date gradDate) {
		this.gradDate = gradDate;
	}
    
}
