package com.camp_us.controller;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.camp_us.command.MessageRegistCommand;
import com.camp_us.command.PageMaker;
import com.camp_us.dao.MailFileDAO;
import com.camp_us.dto.MailFileVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.MessageVO;
import com.camp_us.service.MessageService;
import com.josephoconnell.html.HTMLInputFilter;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MailFileDAO mailFileDAO;
	
	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	
	@GetMapping("/main")
	public String dashList(HttpSession session, Model model) throws Exception{
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String mem_id = loginUser.getMem_id();
        
        List<MessageVO> receiveList = messageService.receiveList(mem_id);
        model.addAttribute("receiveList", receiveList);
        
        List<MessageVO> sendList = messageService.sendList(mem_id);
        model.addAttribute("sendList", sendList);
         
        List<MessageVO> wasteList = messageService.wasteList(mem_id);
        model.addAttribute("wasteList", wasteList);
        
        int unreadCount = messageService.unreadCount(mem_id);
        String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);
        model.addAttribute("unreadCount",displayCount);
        
        return "/message/main";
	}
	
	//받은메일
	@GetMapping("/receive")
	public String receiveList(@ModelAttribute PageMaker pageMaker, HttpSession session, Model model) throws Exception{
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String mem_id = loginUser.getMem_id();
        
        List<MessageVO> receiveMailList = messageService.receiveMailList(pageMaker, mem_id);
        model.addAttribute("receiveMailList", receiveMailList);
        
        int unreadCount = messageService.unreadCount(mem_id);
        String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);
        model.addAttribute("unreadCount",displayCount);
        
        
		return "/message/receive";
	}
	
	@GetMapping("/receiveImp")
	public String receiveImpList(@ModelAttribute PageMaker pageMaker, HttpSession session, Model model) throws Exception{
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String mem_id = loginUser.getMem_id();
        
        List<MessageVO> receiveImpList = messageService.receiveImpList(pageMaker, mem_id);
        model.addAttribute("receiveMailList", receiveImpList);
        model.addAttribute("selectedFilter", "imp");
        
        int unreadCount = messageService.unreadCount(mem_id);
        String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);
        model.addAttribute("unreadCount",displayCount);
        
		return "/message/receive";
	}
	
	@PostMapping("/toggleRImp")
	@ResponseBody
	public Map<String,Object> toggleRImp(@RequestParam("mail_id") int mail_id) throws SQLException {
	    messageService.updateRImp(mail_id); // DB에서 바로 토글

	    // DB에서 새 상태 확인 (또는 클라이언트에서 아이콘 교체 시 단순 토글)
	    MessageVO mail = messageService.getMail(mail_id);
	    int newStatus = mail.getMail_rimp();

	    Map<String,Object> result = new HashMap<>();
	    result.put("success", true);
	    result.put("newStatus", newStatus);
	    return result;
	}
	
	@GetMapping("/receiveRead")
	public String receiveReadList(@ModelAttribute PageMaker pageMaker, HttpSession session, Model model) throws Exception{
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String mem_id = loginUser.getMem_id();
        
        List<MessageVO> receiveReadList = messageService.receiveReadList(pageMaker, mem_id);
        model.addAttribute("receiveMailList", receiveReadList);
        model.addAttribute("selectedFilter", "read");
        
        int unreadCount = messageService.unreadCount(mem_id);
        String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);
        model.addAttribute("unreadCount",displayCount);
        
		return "/message/receive";
	}
	
	@GetMapping("/receiveLock")
	public String receiveLockList(@ModelAttribute PageMaker pageMaker, HttpSession session, Model model) throws Exception{
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String mem_id = loginUser.getMem_id();
        
        List<MessageVO> receiveLockList = messageService.receiveLockList(pageMaker, mem_id);
        model.addAttribute("receiveMailList", receiveLockList);
        model.addAttribute("selectedFilter", "lock");
        
        int unreadCount = messageService.unreadCount(mem_id);
        String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);
        model.addAttribute("unreadCount",displayCount);
        
		return "/message/receive";
	}
	
	@PostMapping("/toggleRLock")
	@ResponseBody
	public Map<String,Object> toggleRLock(@RequestParam("mail_id") int mail_id) throws SQLException {
	    messageService.updateRLock(mail_id); // DB에서 바로 토글

	    // DB에서 새 상태 확인 (또는 클라이언트에서 아이콘 교체 시 단순 토글)
	    MessageVO mail = messageService.getMail(mail_id);
	    int newStatus = mail.getMail_rlock();

	    Map<String,Object> result = new HashMap<>();
	    result.put("success", true);
	    result.put("newStatus", newStatus);
	    return result;
	}
	
	//보낸메일
	@GetMapping("/send")
	public String sendList(@ModelAttribute PageMaker pageMaker, HttpSession session, Model model) throws Exception{
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String mem_id = loginUser.getMem_id();
        
        List<MessageVO> sendMailList = messageService.sendMailList(pageMaker, mem_id);
        model.addAttribute("sendMailList", sendMailList);
        
        int unreadCount = messageService.unreadCount(mem_id);
        String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);
        model.addAttribute("unreadCount",displayCount);
        
        
		return "/message/send";
	}
	
	@GetMapping("/sendImp")
	public String sendImpList(@ModelAttribute PageMaker pageMaker, HttpSession session, Model model) throws Exception{
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String mem_id = loginUser.getMem_id();
        
        List<MessageVO> sendImpList = messageService.sendImpList(pageMaker, mem_id);
        model.addAttribute("sendMailList", sendImpList);
        model.addAttribute("selectedFilter", "imp");
        
        int unreadCount = messageService.unreadCount(mem_id);
        String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);
        model.addAttribute("unreadCount",displayCount);
        
		return "/message/send";
	}
	
	@PostMapping("/toggleSImp")
	@ResponseBody
	public Map<String,Object> toggleSImp(@RequestParam("mail_id") int mail_id) throws SQLException {
	    messageService.updateSImp(mail_id); // DB에서 바로 토글

	    // DB에서 새 상태 확인 (또는 클라이언트에서 아이콘 교체 시 단순 토글)
	    MessageVO mail = messageService.getMail(mail_id);
	    int newStatus = mail.getMail_simp();

	    Map<String,Object> result = new HashMap<>();
	    result.put("success", true);
	    result.put("newStatus", newStatus);
	    return result;
	}
	
	@GetMapping("/sendLock")
	public String sendLockList(@ModelAttribute PageMaker pageMaker, HttpSession session, Model model) throws Exception{
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String mem_id = loginUser.getMem_id();
        
        List<MessageVO> sendLockList = messageService.sendLockList(pageMaker, mem_id);
        model.addAttribute("sendMailList", sendLockList);
        model.addAttribute("selectedFilter", "lock");
        
        int unreadCount = messageService.unreadCount(mem_id);
        String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);
        model.addAttribute("unreadCount",displayCount);
        
		return "/message/send";
	}
	
	@PostMapping("/toggleSLock")
	@ResponseBody
	public Map<String,Object> toggleSLock(@RequestParam("mail_id") int mail_id) throws SQLException {
	    messageService.updateSLock(mail_id); // DB에서 바로 토글

	    // DB에서 새 상태 확인 (또는 클라이언트에서 아이콘 교체 시 단순 토글)
	    MessageVO mail = messageService.getMail(mail_id);
	    int newStatus = mail.getMail_slock();

	    Map<String,Object> result = new HashMap<>();
	    result.put("success", true);
	    result.put("newStatus", newStatus);
	    return result;
	}
	
	
	//휴지통
	@GetMapping("/waste")
	public String wasteList(@ModelAttribute PageMaker pageMaker, HttpSession session, Model model) throws Exception {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String mem_id = loginUser.getMem_id();
        
        List<MessageVO> wasteList = messageService.wasteList(pageMaker, mem_id);
        model.addAttribute("wasteList", wasteList);
        
        int unreadCount = messageService.unreadCount(mem_id);
        String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);
        model.addAttribute("unreadCount",displayCount);
		
		return "/message/waste";
	}
		
	//세부내용
	@GetMapping("/detail")
	public String detail(int mail_id, HttpSession session, Model model) throws Exception {
		
		ServletContext ctx = session.getServletContext();
		
		MemberVO member = (MemberVO)session.getAttribute("loginUser");
		String key = "mail:"+member.getMem_id()+mail_id;
		
		messageService.updateRRead(mail_id);
		
		MessageVO detail= messageService.detail(mail_id);
		model.addAttribute("mail", detail);
		
		MailFileVO mailFile = null;
		model.addAttribute("mailFile", mailFile);
		
		return "/message/detail";
	}
	
	@GetMapping("/detailwaste")
	public String detailWaste(int mail_id, HttpSession session, Model model) throws Exception {
		
		ServletContext ctx = session.getServletContext();
		
		MemberVO member = (MemberVO)session.getAttribute("loginUser");
		String key = "mail:"+member.getMem_id()+mail_id;
		
		messageService.updateRRead(mail_id);
		
		MessageVO detail= messageService.detail(mail_id);
		model.addAttribute("mail", detail);
		
		MailFileVO mailFile = null;
		model.addAttribute("mailFile", mailFile);
		
		return "/message/detail_waste";
	}
	
	@GetMapping("/getFile")
	@ResponseBody
	public ResponseEntity<Resource> getFile(int mafile_no) throws Exception {
						
		MailFileVO mailFile  = mailFileDAO.selectMailFileByMafileNo(mafile_no);
	    String filePath = mailFile.getMafile_uploadpath() + File.separator + mailFile.getMafile_name();
		
		
	    Resource resource = new UrlResource(Paths.get(filePath).toUri());
	    
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + 
				UriUtils.encode(mailFile.getMafile_name().split("\\$\\$")[1], "UTF-8") + "\"")
	            .body(resource);		
	}
	
	@GetMapping("/registForm")
	public ModelAndView registForm(ModelAndView mnv, HttpSession session) throws Exception {
		String url="/message/regist";
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
	    String mem_id = loginUser.getMem_id();

	    int unreadCount = messageService.unreadCount(mem_id);
	    String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);

	    mnv.addObject("unreadCount", displayCount);
	    mnv.setViewName(url);
		return mnv;
	}
	
	@PostMapping(value = "/regist", produces = "text/plain;charset=utf-8")
	public ModelAndView regist(MessageRegistCommand messageRegCommand, ModelAndView mnv)throws Exception{
		String url="/message/regist_success";
		
		//파일저장
		List<MultipartFile> uploadFiles  = messageRegCommand.getUploadFile();
		String uploadPath = fileUploadPath;
		
		List<MailFileVO> attaches = saveFileToAttaches(uploadFiles,uploadPath);
		
		//DB 
		MessageVO message = messageRegCommand.toMessage();
		message.setMail_name(HTMLInputFilter.htmlSpecialChars(message.getMail_name()));
		message.setMailFileList(attaches);
		messageService.registMail(message);
		
	    mnv.setViewName(url);
		
		return mnv;
	}
	
	@javax.annotation.Resource(name="messageSavedFilePath")
	private String fileUploadPath;

	private List<MailFileVO> saveFileToAttaches(List<MultipartFile> multiFiles,
												String savePath )throws Exception{
		if (multiFiles == null) return null;
		
		//저장 -> attachVO -> attachList.add
		List<MailFileVO> mailFileList = new ArrayList<MailFileVO>();
		for (MultipartFile multi : multiFiles) {
			//파일명
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String fileName = uuid+"$$"+multi.getOriginalFilename();
			
			//파일저장
			File target = new File(savePath, fileName);
			target.mkdirs();
			multi.transferTo(target);
			
			MailFileVO attach = new MailFileVO();
			attach.setMafile_uploadpath(savePath);
			attach.setMafile_name(fileName);
			attach.setMafile_type(fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase());
			
			//mailFileList 추가
			mailFileList.add(attach);
			
		}
		return mailFileList;
	}
	
	@GetMapping("/movewaste")
	public ModelAndView remove(@RequestParam("mail_id") String mail_ids, ModelAndView mnv)throws Exception{
		String url="/message/waste_success";
		
		if(mail_ids != null && !mail_ids.trim().isEmpty()) {
	        String[] mailIdArr = mail_ids.split(",");
	        for(String mailIdStr : mailIdArr) {
	            mailIdStr = mailIdStr.trim();
	            if(!mailIdStr.isEmpty()) { // 공백 체크
	                int mail_id = Integer.parseInt(mailIdStr);
	                messageService.updateWaste(mail_id);
	            }
	        }
	    }
		
		mnv.setViewName(url);
		return mnv;
	}
	
	@GetMapping("/movewaste/detail")
	public ModelAndView removeDetail(@RequestParam("mail_id") String mail_ids, ModelAndView mnv)throws Exception{
		String url="/message/waste_detail_success";
		
		if(mail_ids != null && !mail_ids.trim().isEmpty()) {
	        String[] mailIdArr = mail_ids.split(",");
	        for(String mailIdStr : mailIdArr) {
	            mailIdStr = mailIdStr.trim();
	            if(!mailIdStr.isEmpty()) { // 공백 체크
	                int mail_id = Integer.parseInt(mailIdStr);
	                messageService.updateWaste(mail_id);
	            }
	        }
	    }
		
		mnv.setViewName(url);
		return mnv;
	}
	
	@GetMapping("/backwaste")
	public ModelAndView back(@RequestParam("mail_id") String mail_ids, ModelAndView mnv)throws Exception{
		String url="/message/back_success";
		
		if(mail_ids != null && !mail_ids.trim().isEmpty()) {
	        String[] mailIdArr = mail_ids.split(",");
	        for(String mailIdStr : mailIdArr) {
	            mailIdStr = mailIdStr.trim();
	            if(!mailIdStr.isEmpty()) { // 공백 체크
	                int mail_id = Integer.parseInt(mailIdStr);
	                messageService.updateWasteBack(mail_id);
	            }
	        }
	    }
		
		mnv.setViewName(url);
		return mnv;
	}
	
	@GetMapping("/backwaste/detail")
	public ModelAndView backDetail(@RequestParam("mail_id") String mail_ids, ModelAndView mnv)throws Exception{
		String url="/message/back_detail_success";
		
		if(mail_ids != null && !mail_ids.trim().isEmpty()) {
	        String[] mailIdArr = mail_ids.split(",");
	        for(String mailIdStr : mailIdArr) {
	            mailIdStr = mailIdStr.trim();
	            if(!mailIdStr.isEmpty()) { // 공백 체크
	                int mail_id = Integer.parseInt(mailIdStr);
	                messageService.updateWasteBack(mail_id);
	            }
	        }
	    }
		
		mnv.setViewName(url);
		return mnv;
	}
	
	@GetMapping("/delete")
	public ModelAndView delete(@RequestParam("mail_id") String mail_ids, ModelAndView mnv)throws Exception{
		String url="/message/remove_success";		
		
		if(mail_ids != null && !mail_ids.trim().isEmpty()) {
	        String[] mailIdArr = mail_ids.split(",");
	        for(String mailIdStr : mailIdArr) {
	            mailIdStr = mailIdStr.trim();
	            if(!mailIdStr.isEmpty()) { // 공백 체크
	                int mail_id = Integer.parseInt(mailIdStr);
	                messageService.delete(mail_id);
	            }
	        }
	    }
		
		mnv.setViewName(url);
		return mnv;
	}
	
	@GetMapping("/delete/detail")
	public ModelAndView deleteDetail(@RequestParam("mail_id") String mail_ids, ModelAndView mnv)throws Exception{
		String url="/message/remove_detail_success";		
		
		if(mail_ids != null && !mail_ids.trim().isEmpty()) {
	        String[] mailIdArr = mail_ids.split(",");
	        for(String mailIdStr : mailIdArr) {
	            mailIdStr = mailIdStr.trim();
	            if(!mailIdStr.isEmpty()) { // 공백 체크
	                int mail_id = Integer.parseInt(mailIdStr);
	                messageService.delete(mail_id);
	            }
	        }
	    }
		
		mnv.setViewName(url);
		return mnv;
	}
	
	@GetMapping("/allWaste")
	public ModelAndView clearWaste(ModelAndView mnv) throws Exception {
	    messageService.deleteAll();
	    mnv.setViewName("/message/clear_success");
	    return mnv;
	}
	
}
