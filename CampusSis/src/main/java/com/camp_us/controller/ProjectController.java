package com.camp_us.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.camp_us.command.PageMakerStu;
import com.camp_us.command.PageMakerPro;
import com.camp_us.command.ProjectModifyCommand;
import com.camp_us.command.ProjectRegistCommand;
import com.camp_us.dto.EditBfProjectVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.ProjectListVO;
import com.camp_us.dto.ProjectVO;
import com.camp_us.dto.TeamMemberVO;
import com.camp_us.dto.TeamVO;
import com.camp_us.service.ProjectService;
import com.josephoconnell.html.HTMLInputFilter;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @GetMapping("/list/stu")
    public String listStudent(HttpSession session, Model model,@RequestParam(value = "samester", required = false) String samester,@RequestParam(value = "project_name", required = false) String project_name,
    		@ModelAttribute PageMakerStu pageMaker) throws Exception {
    	String url="/project/stulist";
        MemberVO member = (MemberVO) session.getAttribute("loginUser");
        if (member == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }
        model.addAttribute("member",member);
        String mem_id = member.getMem_id();
        pageMaker.setKeyword(samester);
        pageMaker.setProject_name(project_name);
        List<ProjectListVO> projectList = projectService.searchProjectList(pageMaker, mem_id);

        
        Map<String, List<String>> projectTeamMembersMap = new HashMap<>();
        for (ProjectListVO project : projectList) {
            String project_id = project.getProject_id();
            List<String> members = projectService.selectTeamMembers(project_id);
            projectTeamMembersMap.put(project_id, members);
        }
        Map<String, List<String>> projectEditStatusMap = new HashMap<>();

        for (ProjectListVO project : projectList) {
            String project_id = project.getProject_id();
            if (project_id != null) {
                List<String> editStatusList = projectService.selectEditStatusByProjectid(project_id);
                // 예: 여러 개가 있으면 첫 번째만 쓰거나, 없으면 "수정 가능" 기본값 설정
                if (editStatusList != null && !editStatusList.isEmpty()) {
                    projectEditStatusMap.put(project_id, editStatusList);
                } else {
                    projectEditStatusMap.put(project_id, List.of("수정 가능"));
                }
            } else {
                projectEditStatusMap.put("unknown", List.of("수정 가능"));
            }
        }
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("projectEditStatusMap", projectEditStatusMap);
        model.addAttribute("selectedSamester", samester); 
        model.addAttribute("projectList", projectList);
        model.addAttribute("projectTeamMembersMap", projectTeamMembersMap);
        System.out.println("project_stdate: " + pageMaker.getProject_stdate());
        System.out.println("project_endate: " + pageMaker.getProject_endate());
        System.out.println("pageMaker.project_name = " + pageMaker.getProject_name());
        model.addAttribute("project_stdate",pageMaker.getProject_stdate());
        model.addAttribute("project_endate",pageMaker.getProject_endate());
        model.addAttribute("project_name",pageMaker.getProject_name());
        return url;
    }
    
    
    @GetMapping("/list/pro")
    public String listPro(HttpSession session, Model model,@RequestParam(value = "samester", required = false) String samester,@RequestParam(value = "project_name", required = false) String project_name,
    		@RequestParam(value = "modifyRequest", required = false, defaultValue = "false") boolean modifyRequest,@ModelAttribute PageMakerPro pageMaker) throws Exception {
    	String url = "/project/prolist";
    	
        MemberVO member = (MemberVO) session.getAttribute("loginUser");
        if (member == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }
        model.addAttribute("member",member);
        pageMaker.setKeyword(samester);
        pageMaker.setProject_name(project_name);
        String mem_id = member.getMem_id();
        List<ProjectListVO> projectListpro;
        if (modifyRequest) {
            projectListpro = projectService.selectModifyRequestProjectList(pageMaker, mem_id);
        } else {
            projectListpro = projectService.searchProjectListpro(pageMaker, mem_id);
            // searchProjectListpro도 서비스에서 totalCount 세팅하는 구조여야 함
        }

        
        Map<String, List<String>> projectTeamMembersMap = new HashMap<>();
        Map<String, List<String>> projectProfessorMap = new HashMap<>();

        for (ProjectListVO project : projectListpro) {
            String project_id = project.getProject_id();
            List<String> professor = projectService.selectTeamProfessor(project_id);
            projectProfessorMap.put(project_id, professor);
        }
        Map<String, List<String>> projectEditStatusMap = new HashMap<>();

        for (ProjectListVO project : projectListpro) {
            String project_id = project.getProject_id();
            if (project_id != null) {
                List<String> editStatusList = projectService.selectEditStatusByProjectid(project_id);
                // 예: 여러 개가 있으면 첫 번째만 쓰거나, 없으면 "수정 가능" 기본값 설정
                if (editStatusList != null && !editStatusList.isEmpty()) {
                    projectEditStatusMap.put(project_id, editStatusList);
                } else {
                    projectEditStatusMap.put(project_id, List.of("수정 가능"));
                }
            } else {
                projectEditStatusMap.put("unknown", List.of("수정 가능"));
            }
        }
        model.addAttribute("projectEditStatusMap", projectEditStatusMap);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("projectListpro", projectListpro);
        model.addAttribute("projectTeamMembersMap", projectTeamMembersMap);
        model.addAttribute("selectedSamester", samester); 
        model.addAttribute("projectProfessorMap",projectProfessorMap);
        System.out.println("project_stdate: " + pageMaker.getProject_stdate());
        System.out.println("project_endate: " + pageMaker.getProject_endate());
        System.out.println("pageMaker.project_name = " + pageMaker.getProject_name());
        model.addAttribute("project_stdate",pageMaker.getProject_stdate());
        model.addAttribute("project_endate",pageMaker.getProject_endate());
        model.addAttribute("project_name",pageMaker.getProject_name());
        return url;
    }
    @GetMapping("/modify/stu")
    public String modifyForm(@RequestParam("project_id") String project_id, Model model) throws Exception {
        String url = "/project/modify";

        // 1. 프로젝트 정보 조회
        List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
        List<MemberVO> professorList = projectService.selectProfessorList();
        List<MemberVO> studentList = projectService.selectTeamMemberList();

        if (!projectList.isEmpty()) {
            // 2. 해당 프로젝트 팀원명 리스트 조회
            List<String> teamMemberNames = projectService.selectTeamMembers(project_id);

            // 3. 첫번째 객체에 팀원 리스트 세팅
            projectList.get(0).setMem_name(teamMemberNames);
        }
        model.addAttribute("professorList", professorList);	
        model.addAttribute("studentList", studentList);
        model.addAttribute("projectList", projectList);

        return url;
    }
    @PostMapping("/modify/stu")
    public String modifyPost(ProjectModifyCommand command, Model model) throws Exception {
    	
    	String url="/project/modify_success";
        System.out.println("받은 프로젝트명: " + command.getProject_name());
        System.out.println("받은 프로젝트 ID: " + command.getProject_id());
        System.out.println("받은 수정 사유: " + command.getEdit_content());
    	String before_id = projectService.selectEditBeforeSeqNext();
        command.setBefore_id(before_id);
        EditBfProjectVO editVO = command.toEditBfProjectVO();
        projectService.insertEditBeforeProject(editVO);
        
        return url;
    }
    @GetMapping("/detail")
    public String detail(@RequestParam("project_id") String project_id, Model model) throws Exception {
        String url = "/project/detail";
        List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
        List<MemberVO> professorList = projectService.selectProfessorList();
        List<MemberVO> studentList = projectService.selectTeamMemberList();

        if (!projectList.isEmpty()) {
            // 2. 해당 프로젝트 팀원명 리스트 조회
            List<String> teamMemberNames = projectService.selectTeamMembers(project_id);

            // 3. 첫번째 객체에 팀원 리스트 세팅
            projectList.get(0).setMem_name(teamMemberNames);
        }
        
        
        model.addAttribute("professorList", professorList);	
        model.addAttribute("studentList", studentList);
        model.addAttribute("projectList", projectList);
        model.addAttribute("project_id", project_id);
        return url;
    }

    // ✅ 프로젝트 등록 폼 (교수/학생 리스트 조회)
    @GetMapping("/regist")
    public void registForm(Model model) throws Exception {
    	System.out.println("[ProjectController] registForm() 호출됨");
    	List<MemberVO> professorList = projectService.selectProfessorList();
        List<MemberVO> studentList = projectService.selectTeamMemberList();
        
        model.addAttribute("professorList", professorList);
        model.addAttribute("studentList", studentList);
    }
    @GetMapping("/modify/pro")
    public String modifyCheckForm(@RequestParam("project_id") String project_id, Model model)throws Exception{
    	String url = "/project/modifyCheck";

        // 1. 프로젝트 정보 조회
        List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
        List<MemberVO> professorList = projectService.selectProfessorList();
        List<MemberVO> studentList = projectService.selectTeamMemberList();
        List<EditBfProjectVO> editList = projectService.selectEditProjectByProjectId(project_id);
        if (!projectList.isEmpty()) {
            // 2. 해당 프로젝트 팀원명 리스트 조회
            List<String> teamMemberNames = projectService.selectTeamMembers(project_id);

            // 3. 첫번째 객체에 팀원 리스트 세팅
            projectList.get(0).setMem_name(teamMemberNames);
        }
        model.addAttribute("editList", editList);
        model.addAttribute("professorList", professorList);	
        model.addAttribute("studentList", studentList);
        model.addAttribute("projectList", projectList);

        return url;
    }
    @PostMapping("/modify/pro")
    public String modifyCheckPost(ProjectListVO project,
                                  TeamVO team,
                                  @RequestParam List<String> team_member_ids,
                                  @RequestParam("before_id") String beforeId
    																) throws SQLException {

        // TeamMemberVO 리스트로 변환
        List<TeamMemberVO> teamMember = team_member_ids.stream()
            .map(team_member -> {
                TeamMemberVO tm = new TeamMemberVO();
                tm.setTeam_member(team_member);
                // team_id는 나중에 서비스 메서드에서 세팅
                return tm;
            })
            .collect(Collectors.toList());

        // 서비스 호출
        projectService.updateProjectTeamAndMembers(project, team, teamMember);
        projectService.deleteEditBefore(beforeId);
        return "/project/modifyCheck_success";
    }
    // ✅ 프로젝트 등록 처리
    @PostMapping("/regist")
    public String registPost(ProjectRegistCommand command) throws Exception {
    	String url = "/project/regist_success";
        // 1) 시퀀스 조회
        String project_id = projectService.selectProjectSeqNext();
        String team_id = projectService.selectTeamSeqNext();

        // 2) 커맨드를 DTO로 변환
        ProjectVO project = command.toProjectVO(project_id, team_id);
        project.setProject_name(HTMLInputFilter.htmlSpecialChars(project.getProject_name()));
        TeamVO team = command.toTeamVO(team_id);
        List<TeamMemberVO> memberList = command.toTeamMemberVOList(team_id);
        // 3) DB insert (순차적으로 처리)
        projectService.insertTeamLeader(team);
        projectService.insertProject(project);
        
        for (TeamMemberVO tm : memberList) {
            projectService.insertTeamMemberList(tm);
        }

        return url;
    }
    @GetMapping("/modify/reject")
    public String reject(@RequestParam("before_id") String before_id)throws Exception{
    	String url="/project/reject_success";
    	
    	projectService.deleteEditBefore(before_id);
    	return url;
    }
    
    @GetMapping("/remove")
	public String remove(@RequestParam("team_id") String team_id)throws Exception{
		String url="/project/remove_success";		
		
		projectService.deleteTeamByTeamId(team_id);
	
		return url;
	}
    @GetMapping("/main/stu")
    public String main() {
    	String url="/project/main";
    	return url;
    }
    @GetMapping("/main/pro")
    public String mainPro() {
    	String url ="/project/mainpro";
    	return url;
    }
}