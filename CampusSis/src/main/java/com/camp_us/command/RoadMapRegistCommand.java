package com.camp_us.command;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


import com.camp_us.dto.RoadMapVO;

public class RoadMapRegistCommand {
	private String rm_category;
	private String rm_name;
	private String rm_content;
	private String writer;
	private List<MultipartFile> uploadFile;
	private String project_id;
	private String team_id;
	private String eval_status;
	
	
	public String getEval_status() {
		return eval_status;
	}
	public void setEval_status(String eval_status) {
		this.eval_status = eval_status;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getRm_category() {
		return rm_category;
	}
	public void setRm_category(String rm_category) {
		this.rm_category = rm_category;
	}
	public String getRm_name() {
		return rm_name;
	}
	public void setRm_name(String rm_name) {
		this.rm_name = rm_name;
	}
	public String getRm_content() {
		return rm_content;
	}
	public void setRm_content(String rm_content) {
		this.rm_content = rm_content;
	}
	public List<MultipartFile> getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(List<MultipartFile> uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	public RoadMapVO toRoadMapVO(){
		RoadMapVO roadMap= new RoadMapVO();
		
		roadMap.setProject_id(project_id);
		roadMap.setTeam_id(team_id);
		roadMap.setRm_category(rm_category);
		roadMap.setRm_content(this.rm_content);
		roadMap.setRm_name(this.rm_name);
		roadMap.setWriter(this.writer);
		roadMap.setEval_status(eval_status);
		return roadMap;
	}
	
	
}
