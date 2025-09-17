package com.camp_us.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.camp_us.command.ModifyProjectRequest;
import com.camp_us.command.PageMakerPro;
import com.camp_us.command.PageMakerStu;
import com.camp_us.command.ProjectModifyCommand;
import com.camp_us.command.ProjectRegistCommand;
import com.camp_us.dto.EditBfProjectVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.ProjectListVO;
import com.camp_us.dto.ProjectVO;
import com.camp_us.dto.TeamMemberVO;
import com.camp_us.dto.TeamVO;
import com.camp_us.service.MemberService;
import com.camp_us.service.ProjectService;
import com.josephoconnell.html.HTMLInputFilter;

@RestController
@RequestMapping("/api/project")
public class ProjectController2 {

    private ProjectService projectService;
    @Autowired
	private MemberService memberService;
    @Autowired
    public ProjectController2(ProjectService projectService) {
        this.projectService = projectService;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

        @GetMapping(value = "/list/stu", produces = "application/json; charset=UTF-8")
        public ResponseEntity<?> listStudent (@RequestParam(value = "samester", required = false) String samester,
                @RequestParam(value = "project_name", required = false) String project_name,
                @RequestParam String memId,
                @ModelAttribute PageMakerStu pageMaker)throws Exception {
       
            pageMaker.setKeyword(samester);
            pageMaker.setProject_name(project_name);

            // 프로젝트 리스트 조회
            List<ProjectListVO> projectList = projectService.searchProjectList(pageMaker, memId);
            MemberVO member = memberService.getMemberById(memId);
            // 프로젝트별 팀 멤버 조회
            Map<String, List<String>> projectTeamMembersMap = new HashMap<>();
            for (ProjectListVO project : projectList) {
                String project_id = project.getProject_id();
                List<String> members = projectService.selectTeamMembers(project_id);
                projectTeamMembersMap.put(project_id, members);
            }

            // 프로젝트별 수정 상태 조회
            Map<String, List<String>> projectEditStatusMap = new HashMap<>();
            for (ProjectListVO project : projectList) {
                String project_id = project.getProject_id();
                if (project_id != null) {
                    List<String> editStatusList = projectService.selectEditStatusByProjectid(project_id);
                    if (editStatusList != null && !editStatusList.isEmpty()) {
                        projectEditStatusMap.put(project_id, editStatusList);
                    } else {
                        projectEditStatusMap.put(project_id, List.of("수정 가능"));
                    }
                } else {
                    projectEditStatusMap.put("unknown", List.of("수정 가능"));
                }
            }

            // 반환 데이터 구성
            Map<String, Object> response = new HashMap<>();
            response.put("member", member);
            response.put("projectList", projectList);
            response.put("projectTeamMembersMap", projectTeamMembersMap);
            response.put("projectEditStatusMap", projectEditStatusMap);
            response.put("selectedSamester", samester);
            response.put("project_stdate", pageMaker.getProject_stdate());
            response.put("project_endate", pageMaker.getProject_endate());
            response.put("project_name", pageMaker.getProject_name());
            response.put("pageMaker", pageMaker);

            return ResponseEntity.ok(response);
        }
    
    
    @GetMapping(value = "/list/pro", produces = "application/json; charset=UTF-8")
    public  ResponseEntity<?> listPro( @RequestParam(value = "samester", required = false) String samester,
            @RequestParam(value = "project_name", required = false) String project_name,
            @RequestParam String memId,
            @RequestParam(value = "modifyRequest", required = false, defaultValue = "false") boolean modifyRequest,
            @RequestParam(required = false, defaultValue = "7") int perPageNum,
            @ModelAttribute PageMakerPro pageMaker) throws Exception {
    	String url = "/project/prolist";
         MemberVO member = memberService.getMemberById(memId);
         
        pageMaker.setKeyword(samester);
        pageMaker.setProject_name(project_name);
        pageMaker.setPerPageNum(perPageNum); 
        List<ProjectListVO> projectListpro;
        if (modifyRequest) {
            projectListpro = projectService.selectModifyRequestProjectList(pageMaker, memId);
        } else {
            projectListpro = projectService.searchProjectListpro(pageMaker, memId);
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
        Map<String, Object> response = new HashMap<>();
        response.put("projectEditStatusMap", projectEditStatusMap);
        response.put("pageMaker", pageMaker);
        response.put("projectListpro", projectListpro);
        response.put("projectTeamMembersMap", projectTeamMembersMap);
        response.put("selectedSamester", samester); 
        response.put("projectProfessorMap",projectProfessorMap);
        response.put("project_stdate",pageMaker.getProject_stdate());
        response.put("project_endate",pageMaker.getProject_endate());
        response.put("project_name",pageMaker.getProject_name());
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "/modify/stu", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> modifyFormJson(@RequestParam("project_id") String project_id) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
        List<MemberVO> professorList = projectService.selectProfessorList();
        List<MemberVO> studentList = projectService.selectTeamMemberList();

        if (!projectList.isEmpty()) {
            List<String> teamMemberNames = projectService.selectTeamMembers(project_id);
            projectList.get(0).setMem_name(teamMemberNames);
        }

        result.put("projectList", projectList);
        result.put("professorList", professorList);
        result.put("studentList", studentList);
        return result;
    }
    @PostMapping(value = "/modify/stu", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> modifyPostJson(@RequestBody ProjectModifyCommand command) throws Exception {
        Map<String, Object> result = new HashMap<>();
        String before_id = projectService.selectEditBeforeSeqNext();
        command.setBefore_id(before_id);
        EditBfProjectVO editVO = command.toEditBfProjectVO();
        projectService.insertEditBeforeProject(editVO);
        result.put("success", true);
        return result;
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
    public ResponseEntity<?> registForm() throws Exception {
        System.out.println("[ProjectController] registForm() 호출됨");
        List<MemberVO> professorList = projectService.selectProfessorList();
        List<MemberVO> studentList = projectService.selectTeamMemberList();

        return ResponseEntity.ok(Map.of(
            "professorList", professorList,
            "studentList", studentList
        ));
    }
    @GetMapping("/modify/pro")
    public ResponseEntity<?> modifyCheckForm(@RequestParam("project_id") String project_id) {
        try {
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

            // 4. 결과를 Map으로 묶어서 JSON으로 반환
            Map<String, Object> response = new HashMap<>();
            response.put("projectList", projectList);
            response.put("editList", editList);
            response.put("professorList", professorList);
            response.put("studentList", studentList);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "프로젝트 정보를 가져오는 중 오류 발생"));
        }
    }
    @PostMapping(value = "/modify/pro", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> modifyCheckPost(
            @RequestBody ModifyProjectRequest request
    ) throws SQLException {

        String beforeId = request.getBefore_id();

        // 승인/거부 분기
        if (request.getProject() != null && request.getTeam() != null) {
            // 승인 로직
            ProjectListVO project = request.getProject();
            TeamVO team = request.getTeam();
            List<String> team_member_ids = request.getTeam_member_ids();

            List<TeamMemberVO> teamMember = team_member_ids.stream()
                .map(tmId -> {
                    TeamMemberVO tm = new TeamMemberVO();
                    tm.setTeam_member(tmId);
                    return tm;
                })
                .collect(Collectors.toList());

            projectService.updateProjectTeamAndMembers(project, team, teamMember);
        }

        // 승인/거부 둘 다 요청 삭제
        projectService.deleteEditBefore(beforeId);

        // JSON 응답
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", (request.getProject() != null ? "프로젝트 수정 승인 완료" : "프로젝트 수정 거부 완료"));

        return ResponseEntity.ok(response);
    }
    // ✅ 프로젝트 등록 처리
    @PostMapping("/regist")
    public ResponseEntity<?> registPost(@RequestBody ProjectRegistCommand command) throws Exception {
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

        return ResponseEntity.ok(Map.of("message", "등록 완료"));
    }
    @GetMapping("/modify/reject")
    public String reject(@RequestParam("before_id") String before_id)throws Exception{
    	String url="/project/reject_success";
    	
    	projectService.deleteEditBefore(before_id);
    	return url;
    }
    
    @GetMapping("/remove")
    @ResponseBody
    public Map<String, Object> remove(@RequestParam("team_id") String team_id) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            projectService.deleteTeamByTeamId(team_id);
            result.put("status", "success");
            result.put("message", "팀 삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "fail");
            result.put("message", "팀 삭제 실패");
        }
        return result;
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