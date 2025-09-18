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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

@Controller
@RequestMapping("/roadmap")
public class RodeMapController {
	
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
	public String list(HttpSession session, Model model,@RequestParam(value = "samester", required = false) String samester,@RequestParam(value = "project_name", required = false) String project_name,@RequestParam(value = "eval_status", required = false) String eval_status,
    		@ModelAttribute PageMakerPro pageMaker, PageMakerRM pageMakerRm) throws Exception {
    	String url="/roadmap/projectlist";
        MemberVO member = (MemberVO) session.getAttribute("loginUser");
        if (member == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }
        model.addAttribute("member",member);
        String mem_id = member.getMem_id();
        pageMaker.setKeyword(samester);
        pageMaker.setProject_name(project_name);
        List<ProjectListVO> projectList = roadMapService.projectlist(pageMaker, mem_id);
        
        
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
                if (editStatusList != null && !editStatusList.isEmpty()) {
                    projectEditStatusMap.put(project_id, editStatusList);
                } else {
                    projectEditStatusMap.put(project_id, List.of("수정 가능"));
                }
            } else {
                projectEditStatusMap.put("unknown", List.of("수정 가능"));
            }
        }
        Map<String, List<String>> projectEvalMap = new HashMap<>();
        for(ProjectListVO project : projectList) {
            String project_id = project.getProject_id();
            List<RoadMapVO> roadMaps = roadMapService.roadmaplist(pageMakerRm, project_id);

            // eval_status만 뽑아서 리스트로 넣기
            List<String> evalStatusList = roadMaps.stream()
                .map(rm -> rm.getEval_status()) // eval_status 필드
                .collect(Collectors.toList());

            projectEvalMap.put(project_id, evalStatusList);
        }
        model.addAttribute("eval_status",eval_status);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("projectEvalMap", projectEvalMap);
        model.addAttribute("projectEditStatusMap", projectEditStatusMap);
        model.addAttribute("selectedSamester", samester); 
        model.addAttribute("projectList", projectList);
        model.addAttribute("projectTeamMembersMap", projectTeamMembersMap);
        model.addAttribute("project_stdate",pageMaker.getProject_stdate());
        model.addAttribute("project_endate",pageMaker.getProject_endate());
        model.addAttribute("project_name",pageMaker.getProject_name());
        return url;
    }
	 @GetMapping("/projectlist/pro")
	    public String listPro(HttpSession session, Model model,@RequestParam(value = "samester", required = false) String samester,@RequestParam(value = "project_name", required = false) String project_name,@RequestParam(value = "eval_status", required = false) String eval_status,
	    		@RequestParam(value = "modifyRequest", required = false, defaultValue = "false") boolean modifyRequest,@ModelAttribute PageMakerPro pageMaker, PageMakerRM pageMakerRm) throws Exception {
	    	String url = "/roadmap/projectlist";
	    	
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
	        Map<String, List<String>> projectEvalMap = new HashMap<>();
	        for(ProjectListVO project : projectListpro) {
	            String project_id = project.getProject_id();
	            List<RoadMapVO> roadMaps = roadMapService.roadmaplist(pageMakerRm, project_id);

	            // eval_status만 뽑아서 리스트로 넣기
	            List<String> evalStatusList = roadMaps.stream()
	                .map(rm -> rm.getEval_status()) // eval_status 필드
	                .collect(Collectors.toList());

	            projectEvalMap.put(project_id, evalStatusList);
	        }
	        model.addAttribute("eval_status",eval_status);
	        model.addAttribute("pageMaker", pageMaker);
	        model.addAttribute("projectList", projectListpro);
	        model.addAttribute("projectTeamMembersMap", projectTeamMembersMap);
	        model.addAttribute("selectedSamester", samester); 
	        model.addAttribute("projectEvalMap", projectEvalMap);
	        model.addAttribute("projectProfessorMap",projectProfessorMap);
	        System.out.println("project_stdate: " + pageMaker.getProject_stdate());
	        System.out.println("project_endate: " + pageMaker.getProject_endate());
	        System.out.println("pageMaker.project_name = " + pageMaker.getProject_name());
	        model.addAttribute("project_stdate",pageMaker.getProject_stdate());
	        model.addAttribute("project_endate",pageMaker.getProject_endate());
	        model.addAttribute("project_name",pageMaker.getProject_name());
	        return url;
	    }
	@GetMapping("/list/stu")
	public String roadMapList(HttpSession session,@RequestParam(value = "rm_category", required = false) String rm_category,
	                          @ModelAttribute PageMakerRM pageMaker,
	                          Model model,
	                          String project_id) throws Exception {
	    String url = "/roadmap/stulist";
	    
	    List<RoadMapVO> roadMapList = roadMapService.roadmaplist(pageMaker, project_id);
	    List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
	    
        model.addAttribute("rm_category", rm_category);
        model.addAttribute("rm_stdate",pageMaker.getRm_stdate());
        model.addAttribute("rm_endate",pageMaker.getRm_endate());
        model.addAttribute("rm_name",pageMaker.getRm_name());
	    model.addAttribute("pageMaker", pageMaker);
	    model.addAttribute("roadMapList", roadMapList);
	    model.addAttribute("project", projectList);

	    return url;
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
	public ModelAndView detail(
	        String rm_id,PageMakerRM pageMakers,PageMakerPro pageMaker,
	        HttpSession session,
	        ModelAndView mnv) throws Exception {

	    String url = "/roadmap/detail";
	    ServletContext ctx = session.getServletContext();
	    MemberVO member = (MemberVO) session.getAttribute("loginUser");
	    if (member == null) {
	        throw new IllegalStateException("로그인 정보가 없습니다.");
	    }
	    String mem_id = member.getMem_id();

	    String key = "roadMap:" + member.getMem_id() + rm_id;
	    
	    RoadMapVO roadMap = roadMapService.detail(rm_id);
	    String project_id = roadMap.getProject_id();
	    MemberVO members = memberService.getMember(roadMap.getWriter());
	    String mem_name = members.getMem_name();
	    List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
	    List<ProjectListVO> projectLists = roadMapService.projectlist(pageMaker, mem_id);
	    RoadMapVO rdm = roadMapService.detail(rm_id);
	    List<EvaluationVO> eval = evaluationService.list(rm_id, pageMakers);

	    // 각 평가의 profes_id → 교수 이름 매핑
	    Map<String, String> evalProfessorNames = new HashMap<>();
	    for(EvaluationVO e : eval) {
	        if (!evalProfessorNames.containsKey(e.getProfes_id())) {
	            MemberVO prof = memberService.getMember(e.getProfes_id());
	            evalProfessorNames.put(e.getProfes_id(), prof.getMem_name());
	        }
	    }

	    ctx.setAttribute(key, key); // 캐싱 목적
	    mnv.addObject("eval",eval);
	    mnv.addObject("evalProfessorNames", evalProfessorNames);
	    mnv.addObject("rdm", rdm);
	    mnv.addObject("projectList", projectList);
	    mnv.addObject("roadMap", roadMap);
	    mnv.addObject("mem_name", mem_name);

	    mnv.setViewName(url);
	    return mnv;
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
	@GetMapping("/evaluation/regist")
	public ModelAndView evalutionForm(String rm_id,ModelAndView mnv)throws Exception {
		
		String url="/roadmap/evalution";
		RoadMapVO roadMap = roadMapService.detail(rm_id);
		String project_id = roadMap.getProject_id();
		List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
        List<MemberVO> studentList = projectService.selectTeamMemberList();
        List<String>teammembers = projectService.selectTeamMembers(project_id);
        String teammembersStr = String.join(", ", teammembers);
        
        mnv.addObject("rm_id", rm_id);
        mnv.addObject("teammembers", teammembersStr);
        mnv.addObject("studentList", studentList);
        mnv.addObject("projectList", projectList);
		mnv.setViewName(url);
		return mnv;
	}
	@PostMapping(value = "/evaluation/regist", produces = "text/plain;charset=utf-8")
	public ModelAndView evalution(EvaluationRegistCommand regCommand,String eval_id,String rm_id, HttpSession session,ModelAndView mnv)throws Exception {
		String url="/roadmap/evaluation_success";
		EvaluationVO evaluation = regCommand.toEvaluationVO(eval_id,rm_id);
		evaluation.setEval_content(HTMLInputFilter.htmlSpecialChars(evaluation.getEval_content()));
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
	    if(loginUser != null){
	        evaluation.setProfes_id(loginUser.getMem_id());
	    }

	    evaluationService.regist(evaluation);
		roadMapService.updateEvalStatus(rm_id);
		mnv.setViewName(url);
		return mnv;
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
	@GetMapping("/evaluation/modify")
	public ModelAndView modifyEvaluation(@RequestParam String eval_id, String rm_id) throws Exception {
	    ModelAndView mnv = new ModelAndView("/roadmap/evalmodify");

	    EvaluationVO evaluation = evaluationService.selectEvaluationByEval_id(eval_id);
	    if (evaluation == null) {
	        throw new IllegalArgumentException("해당 평가를 찾을 수 없습니다. eval_id=" + eval_id);
	    }

	    RoadMapVO roadMap = roadMapService.detail(rm_id);
	    String project_id = roadMap.getProject_id();
	    List<ProjectListVO> projectList = projectService.selectProjectByProjectId(project_id);
	    List<MemberVO> studentList = projectService.selectTeamMemberList();
	    List<String> teammembers = projectService.selectTeamMembers(project_id);
	    String teammembersStr = String.join(", ", teammembers);

	    mnv.addObject("eval_id", eval_id); // ← 반드시 요청 파라미터 eval_id 사용
	    mnv.addObject("evaluation", evaluation);
	    mnv.addObject("rm_id", rm_id);
	    mnv.addObject("teammembers", teammembersStr);
	    mnv.addObject("studentList", studentList);
	    mnv.addObject("projectList", projectList);

	    return mnv;
	}
	@PostMapping(value = "/evaluation/modify", produces = "text/plain;charset=utf-8")
	public ModelAndView modifyEvaluation(EvaluationRegistCommand regCommand, 
	                                     @RequestParam String eval_id,
	                                     @RequestParam String rm_id, 
	                                     HttpSession session, 
	                                     ModelAndView mnv) throws Exception {
	    String url="/roadmap/evaluationmodify_success";
	    EvaluationVO evaluation = regCommand.toEvaluationVO(eval_id, rm_id);
	    evaluation.setEval_content(HTMLInputFilter.htmlSpecialChars(evaluation.getEval_content()));

	    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
	    if(loginUser != null){
	        evaluation.setProfes_id(loginUser.getMem_id());
	    }

	    evaluationService.modify(evaluation);
	    roadMapService.updateEvalStatus(rm_id);
	    mnv.setViewName(url);
	    return mnv;
	}
	@GetMapping("/getFile")
	@ResponseBody
	public ResponseEntity<Resource> getFile(int ano) throws Exception {
						
		AttachVO attach  = attachDAO.selectAttachByAno(ano);
	    String filePath = attach.getUploadPath() + File.separator + attach.getFileName();
		
		
	    Resource resource = new UrlResource(Paths.get(filePath).toUri());
	    
	    return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + 
				UriUtils.encode(attach.getFileName().split("\\$\\$")[1], "UTF-8") + "\"")
                .body(resource);		
	}
	
}