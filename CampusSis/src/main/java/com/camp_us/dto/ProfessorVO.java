package com.camp_us.dto;

public class ProfessorVO {
	private String project_id;
	private String profes_id;
	private String mem_name;
	private String picture;
	private String professorMemId;
	
	public String getProfess_id() {
		return profes_id;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getProfes_id() {
		return profes_id;
	}
	public void setProfes_id(String profes_id) {
		this.profes_id = profes_id;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public void setProfess_id(String profes_id) {
		this.profes_id = profes_id;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getProfessorMemId() {
		return professorMemId;
	}
	public void setProfessorMemId(String professorMemId) {
		this.professorMemId = professorMemId;
	}
	
	
}
