package com.camp_us.controller;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import com.camp_us.command.EvaluationRegistCommand;
import com.camp_us.command.PageMakerPro;
import com.camp_us.command.PageMakerRM;
import com.camp_us.command.RoadMapRegistCommand;
import com.camp_us.dao.AttachDAO;
import com.camp_us.dto.AttachVO;
import com.camp_us.dto.EvaluationVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.ProjectListVO;
import com.camp_us.dto.RoadMapVO;
import com.camp_us.service.EvaluationService;
import com.camp_us.service.MemberService;
import com.camp_us.service.ProjectService;
import com.camp_us.service.RoadMapService;
import com.josephoconnell.html.HTMLInputFilter;

@RestController
@RequestMapping("/api/roadmap")
public class RodeMapController2 {
	
	@Autowired
	private RoadMapService roadMapService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private AttachDAO attachDAO;
	
	@Autowired
	private EvaluationService evaluationService;
	
	@Autowired
	private MemberService memberService;
//	@Autowired
//    public RodeMapController(ProjectService projectService, RoadMapService roadMapService) {
//        this.projectService = projectService;
//        this.roadMapService = roadMapService;
//    }
//	
	@GetMapping("/projectlist/stu")
    public ResponseEntity<Map<String, Object>> getProjectList(
            @RequestParam String memId,
            @RequestParam(value = "samester", required = false) String samester,
            @RequestParam(value = "project_name", required = false) String project_name,
            @RequestParam(value = "eval_status", required = false) String eval_status,
            @RequestParam(required = false, defaultValue = "7") int perPageNum,
            @ModelAttribute PageMakerPro pageMaker,
            PageMakerRM pageMakerRm) throws Exception {

        Map<String, Object> result = new HashMap<>();

        // 멤버 정보
        MemberVO member = memberService.getMemberById(memId);
        result.put("member", member);

        // 페이징 및 필터
        pageMaker.setKeyword(samester);
        pageMaker.setProject_name(project_name);

        // 프로젝트 리스트
        List<ProjectListVO> projectList = roadMapService.projectlist(pageMaker, memId);
        result.put("projectList", projectList);

        // 프로젝트별 팀원
        Map<String, List<String>> projectTeamMembersMap = new HashMap<>();
        for (ProjectListVO project : projectList) {
            String project_id = project.getProject_id();
            List<String> members = projectService.selectTeamMembers(project_id);
            projectTeamMembersMap.put(project_id, members);
        }
        result.put("projectTeamMembersMap", projectTeamMembersMap);

        // 프로젝트별 수정 상태
        Map<String, List<String>> projectEditStatusMap = new HashMap<>();
        for (ProjectListVO project : projectList) {
            String project_id = project.getProject_id();
            List<String> editStatusList = projectService.selectEditStatusByProjectid(project_id);
            if (editStatusList != null && !editStatusList.isEmpty()) {
                projectEditStatusMap.put(project_id, editStatusList);
            } else {
                projectEditStatusMap.put(project_id, List.of("수정 가능"));
            }
        }
        result.put("projectEditStatusMap", projectEditStatusMap);

        // 프로젝트별 평가 상태
        Map<String, List<String>> projectEvalMap = new HashMap<>();
        for(ProjectListVO project : projectList) {
            String project_id = project.getProject_id();
            List<RoadMapVO> roadMaps = roadMapService.roadmaplist(pageMakerRm, project_id);

            List<String> evalStatusList = roadMaps.stream()
                    .map(RoadMapVO::getEval_status)
                    .collect(Collectors.toList());

            projectEvalMap.put(project_id, evalStatusList);
        }
        result.put("projectEvalMap", projectEvalMap);

        // 요청 파라미터도 반환 (선택)
        result.put("eval_status", eval_status);
        result.put("selectedSamester", samester);
        result.put("project_stdate", pageMaker.getProject_stdate());
        result.put("project_endate", pageMaker.getProject_endate());
        result.put("project_name", pageMaker.getProject_name());
        result.put("pageMaker", pageMaker);
        return ResponseEntity.ok(result);
    }
	@GetMapping("/projectlist/pro")
    public ResponseEntity<Map<String, Object>> listPro(
            @RequestParam String memId,
            @RequestParam(value = "samester", required = false) String samester,
            @RequestParam(value = "project_name", required = false) String project_name,
            @RequestParam(value = "eval_status", required = false) String eval_status,
            @RequestParam(value = "modifyRequest", required = false, defaultValue = "false") boolean modifyRequest,
            @ModelAttribute PageMakerPro pageMaker,
            PageMakerRM pageMakerRm) throws Exception {

		 MemberVO member = memberService.getMemberById(memId);

        pageMaker.setKeyword(samester);
        pageMaker.setProject_name(project_name);

        List<ProjectListVO> projectListpro;
        if (modifyRequest) {
            projectListpro = projectService.selectModifyRequestProjectList(pageMaker, memId);
        } else {
            projectListpro = projectService.searchProjectListpro(pageMaker, memId);
        }

        Map<String, List<String>> projectTeamMembersMap = new HashMap<>();
        Map<String, List<String>> projectProfessorMap = new HashMap<>();
        Map<String, List<String>> projectEvalMap = new HashMap<>();

        for (ProjectListVO project : projectListpro) {
            String project_id = project.getProject_id();

            // 교수 정보
            List<String> professor = projectService.selectTeamProfessor(project_id);
            projectProfessorMap.put(project_id, professor);

            // 평가 상태
            List<RoadMapVO> roadMaps = roadMapService.roadmaplist(pageMakerRm, project_id);
            List<String> evalStatusList = roadMaps.stream()
                    .map(RoadMapVO::getEval_status)
                    .collect(Collectors.toList());
            projectEvalMap.put(project_id, evalStatusList);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("member", member);
        response.put("projectList", projectListpro);
        response.put("projectTeamMembersMap", projectTeamMembersMap);
        response.put("projectProfessorMap", projectProfessorMap);
        response.put("projectEvalMap", projectEvalMap);
        response.put("selectedSamester", samester);
        response.put("eval_status", eval_status);
        response.put("project_stdate", pageMaker.getProject_stdate());
        response.put("project_endate", pageMaker.getProject_endate());
        response.put("project_name", pageMaker.getProject_name());
        response.put("pageMaker", pageMaker);

        return ResponseEntity.ok(response);
    }

	 @GetMapping("/list/stu")
	    public Map<String, Object> getRoadMapList(
	    		@RequestParam(value = "project_id") String projectId,
	            @RequestParam(value = "memId") String memId,
	            @RequestParam(value = "rm_category", required = false) String rmCategory,
	            @RequestParam(value = "rm_name", required = false) String rmName,
	            @RequestParam(value = "rm_stdate", required = false) String rmStdate,
	            @RequestParam(value = "rm_endate", required = false) String rmEndate,
	            PageMakerRM pageMaker) throws Exception {

	        List<RoadMapVO> roadMapList = roadMapService.roadmaplist(pageMaker, projectId);
	        List<ProjectListVO> projectList = projectService.selectProjectByProjectId(projectId);

	        Map<String, Object> result = new HashMap<>();
	        result.put("rm_category", rmCategory);
	        result.put("rm_stdate", pageMaker.getRm_stdate());
	        result.put("rm_endate", pageMaker.getRm_endate());
	        result.put("rm_name", pageMaker.getRm_name());
	        result.put("pageMaker", pageMaker);
	        result.put("roadMapList", roadMapList);
	        result.put("project", projectList);

	        return result;
	    }
	
	@GetMapping("/regist")
	public String registForm(HttpSession session, @RequestParam("project_id") String project_id,Model model)throws SQLException {
		String url="/roadmap/regist";
		MemberVO member = (MemberVO) session.getAttribute("loginUser");
        if (member == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }
        model.addAttribute("member",member);
        String mem_id = member.getMem_id();
        
		List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
        List<MemberVO> professorList = projectService.selectProfessorList();
        List<MemberVO> studentList = projectService.selectTeamMemberList();
        List<String>teammembers = projectService.selectTeamMembers(project_id);
        String teammembersStr = String.join(", ", teammembers);
        
   
        model.addAttribute("teammembers", teammembersStr);
        model.addAttribute("professorList", professorList);	
        model.addAttribute("studentList", studentList);
        model.addAttribute("projectList", projectList);
        
		return url;
	}
	@PostMapping(value = "/regist", produces = "text/plain;charset=utf-8")
	public ModelAndView regist(HttpSession session, RoadMapRegistCommand regCommand, ModelAndView mnv)throws Exception {
		String url = "/roadmap/regist_success";
		List<MultipartFile> uploadFiles  = regCommand.getUploadFile();
		String uploadPath = fileUploadPath;
		//DB 
		List<AttachVO> attaches = saveFileToAttaches(uploadFiles, uploadPath);
		System.out.println("uploadFiles = " + regCommand.getUploadFile());
		System.out.println("fileUploadPath = " + fileUploadPath);
				RoadMapVO roadMap = regCommand.toRoadMapVO();
				roadMap.setRm_name(HTMLInputFilter.htmlSpecialChars(roadMap.getRm_name()));
				roadMap.setAttachList(attaches);
				String project_id = roadMap.getProject_id();
				System.out.println("Upload path: " + fileUploadPath);
				roadMapService.regist(roadMap);
				roadMapService.updateRoadMapStatus(project_id);
				mnv.addObject("project_id", project_id);
				mnv.setViewName(url);
				return mnv;
	}
	@GetMapping("/detail")
    public ResponseEntity<?> getRoadMapDetail(
            @RequestParam String rm_id,
            @RequestParam String memId,
            PageMakerRM pageMakers,
            PageMakerPro pageMaker,
            HttpSession session) throws Exception {

        MemberVO member = memberService.getMemberById(memId);

        RoadMapVO roadMap = roadMapService.detail(rm_id);
        if (roadMap == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "로드맵을 찾을 수 없습니다."));
        }

        String project_id = roadMap.getProject_id();
        MemberVO writer = memberService.getMember(roadMap.getWriter());
        String mem_name = writer != null ? writer.getMem_name() : "";

        List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
        List<ProjectListVO> projectLists = roadMapService.projectlist(pageMaker, memId);

        List<EvaluationVO> evalList = evaluationService.list(rm_id, pageMakers);

        // 교수 이름 매핑
        Map<String, String> evalProfessorNames = new HashMap<>();
        for(EvaluationVO e : evalList) {
            if (!evalProfessorNames.containsKey(e.getProfes_id())) {
                MemberVO prof = memberService.getMember(e.getProfes_id());
                evalProfessorNames.put(e.getProfes_id(), prof != null ? prof.getMem_name() : "Unknown");
            }
        }

        // JSON으로 반환
        Map<String, Object> response = new HashMap<>();
        response.put("roadMap", roadMap);
        response.put("mem_name", mem_name);
        response.put("projectList", projectList);
        response.put("projectLists", projectLists);
        response.put("eval", evalList);
        response.put("evalProfessorNames", evalProfessorNames);

        return ResponseEntity.ok(response);
    }
	@GetMapping("/remove")
	public ModelAndView remove(String rm_id, ModelAndView mnv,@RequestParam("project_id") String project_id) throws Exception {
		String url = "/roadmap/remove_success";
	
		// 첨부파일 삭제
		List<AttachVO> attachList = roadMapService.detail(rm_id).getAttachList();
		if (attachList != null) {
			for (AttachVO attach : attachList) {
				File target = new File(attach.getUploadPath(), attach.getFileName());
				if (target.exists()) {
					target.delete();
				}
			}
		}
		
		
		//DB 삭제
		roadMapService.remove(rm_id);
		mnv.addObject("project_id", project_id); 
		mnv.setViewName(url);
		return mnv;
	}
	@GetMapping(value = "/evaluation/form", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getEvaluationForm(@RequestParam String rm_id) throws Exception {
	    RoadMapVO roadMap = roadMapService.detail(rm_id);
	    String project_id = roadMap.getProject_id();
	    List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
	    List<MemberVO> studentList = projectService.selectTeamMemberList();
	    List<String> teammembers = projectService.selectTeamMembers(project_id);

	    Map<String, Object> result = new HashMap<>();
	    result.put("rm_id", rm_id);
	    result.put("teammembers", String.join(", ", teammembers));
	    result.put("studentList", studentList);
	    result.put("projectList", projectList);

	    return ResponseEntity.ok(result);
	}
	@PostMapping(value = "/evaluation/regist", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> registerEvaluation(
	        @RequestBody EvaluationRegistCommand regCommand,
	        @RequestParam String memId,
	        HttpSession session) throws Exception {

	    EvaluationVO evaluation = regCommand.toEvaluationVO(null, regCommand.getRm_id());
	    evaluation.setEval_content(HTMLInputFilter.htmlSpecialChars(evaluation.getEval_content()));

	    MemberVO loginUser = memberService.getMemberById(memId);
	    if (loginUser != null) {
	        evaluation.setProfes_id(memId);
	    }

	    evaluationService.regist(evaluation);
	    roadMapService.updateEvalStatus(regCommand.getRm_id());

	    Map<String, Object> result = new HashMap<>();
	    result.put("success", true);
	    result.put("message", "평가가 등록되었습니다.");

	    return ResponseEntity.ok(result);
	}

	@PostMapping("/evaluation/remove")
	public String removeEvaluation(@RequestParam String eval_id, @RequestParam String rm_id) throws Exception {
	    evaluationService.remove(eval_id); // DB 삭제
	    return "redirect:/roadmap/detail?rm_id=" + rm_id; // 삭제 후 상세페이지로 리다이렉트
	}
	@javax.annotation.Resource(name="roadMapSavedFilePath")
	private String fileUploadPath;

	private List<AttachVO> saveFileToAttaches(List<MultipartFile> multiFiles,
												String savePath )throws Exception{
		if (multiFiles == null) return null;
		
		//저장 -> attachVO -> attachList.add
		List<AttachVO> attachList = new ArrayList<AttachVO>();
		for (MultipartFile multi : multiFiles) {
			//파일명
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String fileName = uuid+"$$"+multi.getOriginalFilename();
			
			//파일저장
			File target = new File(savePath, fileName);
			target.mkdirs();
			multi.transferTo(target);
			
			AttachVO attach = new AttachVO();
			attach.setUploadPath(savePath);
			attach.setFileName(fileName);
			attach.setFileType(fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase());

			//attchList 추가
			attachList.add(attach);
			
		}
		return attachList;
	}
	 @GetMapping("/modify")
	    public ResponseEntity<Map<String, Object>> getEvaluationForModify(
	            @RequestParam String eval_id,
	            @RequestParam String rm_id) throws Exception {

	        EvaluationVO evaluation = evaluationService.selectEvaluationByEval_id(eval_id);
	        if (evaluation == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(Map.of("message", "해당 평가를 찾을 수 없습니다."));
	        }

	        RoadMapVO roadMap = roadMapService.detail(rm_id);
	        String project_id = roadMap.getProject_id();
	        List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
	        List<MemberVO> studentList = projectService.selectTeamMemberList();
	        List<String> teammembers = projectService.selectTeamMembers(project_id);
	        String teammembersStr = String.join(", ", teammembers);

	        Map<String, Object> result = new HashMap<>();
	        result.put("eval_id", eval_id);
	        result.put("evaluation", evaluation);
	        result.put("rm_id", rm_id);
	        result.put("teammembers", teammembersStr);
	        result.put("studentList", studentList);
	        result.put("projectList", projectList);

	        return ResponseEntity.ok(result);
	    }

	    // 평가 수정 처리
	    @PostMapping("/modify")
	    public ResponseEntity<Map<String, Object>> modifyEvaluation(
	            @RequestBody EvaluationRegistCommand regCommand,
	            @RequestParam String eval_id,
	            @RequestParam String rm_id,
	            @RequestParam String memId,
	            HttpSession session) throws Exception {

	        EvaluationVO evaluation = regCommand.toEvaluationVO(eval_id, rm_id);
	        evaluation.setEval_content(HTMLInputFilter.htmlSpecialChars(evaluation.getEval_content()));

	        MemberVO loginUser = memberService.getMemberById(memId);
	        if (loginUser != null) {
	            evaluation.setProfes_id(memId);
	        }

	        evaluationService.modify(evaluation);
	        roadMapService.updateEvalStatus(rm_id);

	        return ResponseEntity.ok(Map.of(
	                "success", true,
	                "message", "평가가 수정되었습니다."
	        ));
	    }
@GetMapping("/getFile")
	@ResponseBody
	public ResponseEntity<Resource> getFile(int ano) throws Exception {
						
		AttachVO attach  = attachDAO.selectAttachByAno(ano);
	    String filePath = attach.getUploadPath() + File.separator + attach.getFileName();
		
		
	    Resource resource = new UrlResource(Paths.get(filePath).toUri());
	    
	    return ResponseEntity.ok()
	    		.contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + 
				UriUtils.encode(attach.getFileName().split("\\$\\$")[1], "UTF-8") + "\"")
                .body(resource);		
	}
	
}