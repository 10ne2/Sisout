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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import com.camp_us.command.MessageRegistCommand;
import com.camp_us.command.PageMakerWH;
import com.camp_us.dao.MailFileDAO;
import com.camp_us.dto.MailFileVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.MessageVO;
import com.camp_us.service.MessageService;
import com.josephoconnell.html.HTMLInputFilter;

@RestController
@RequestMapping("/api/message")
public class MessageController2 {

	@Autowired
	private MessageService messageService;

	@Autowired
	private MailFileDAO mailFileDAO;

	@Autowired
	public MessageController2(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping("/main")
	public ResponseEntity<Map<String, Object>> dashList(@RequestParam String memId) throws Exception {

		ResponseEntity<Map<String, Object>> result = null;

		List<MessageVO> receiveList = messageService.receiveList(memId);
		List<MessageVO> sendList = messageService.sendList(memId);
		List<MessageVO> wasteList = messageService.wasteList(memId);

		int unreadCount = messageService.unreadCount(memId);
		String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);

		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("receiveList", receiveList);
		dataMap.put("sendList", sendList);
		dataMap.put("wasteList", wasteList);
		dataMap.put("displayCount", displayCount);

		result = new ResponseEntity<Map<String, Object>>(dataMap, HttpStatus.OK);

		return result;
	}

//	--------------------------------------------------------

	// 받은메일
	@GetMapping("/receive")
	public ResponseEntity<Map<String, Object>> receiveList(@RequestParam String memId,
			@ModelAttribute PageMakerWH pageMaker, @RequestParam(required = false) String filter) throws Exception {

		ResponseEntity<Map<String, Object>> result = null;

		List<MessageVO> receiveMailList;

		if ("imp".equals(filter)) {
			receiveMailList = messageService.receiveImpList(pageMaker, memId);
		} else if ("unread".equals(filter)) {
			receiveMailList = messageService.receiveReadList(pageMaker, memId);
		} else if ("lock".equals(filter)) {
			receiveMailList = messageService.receiveLockList(pageMaker, memId);
		} else {
			receiveMailList = messageService.receiveMailList(pageMaker, memId);
		}

		int unreadCount = messageService.unreadCount(memId);
		String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);

		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("receiveMailList", receiveMailList);
		dataMap.put("pageMaker", pageMaker);
		dataMap.put("displayCount", displayCount);

		result = new ResponseEntity<Map<String, Object>>(dataMap, HttpStatus.OK);

		return result;
	}

	@PostMapping("/toggleRImp")
	@ResponseBody
	public Map<String, Object> toggleRImp(@RequestParam("mail_id") int mail_id) throws SQLException {
		messageService.updateRImp(mail_id); // DB에서 바로 토글

		// DB에서 새 상태 확인 (또는 클라이언트에서 아이콘 교체 시 단순 토글)
		MessageVO mail = messageService.getMail(mail_id);
		int newStatus = mail.getMail_rimp();

		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("newStatus", newStatus);
		return result;
	}

	@PostMapping("/toggleRLock")
	@ResponseBody
	public Map<String, Object> toggleRLock(@RequestParam("mail_id") int mail_id) throws SQLException {
		messageService.updateRLock(mail_id); // DB에서 바로 토글

		// DB에서 새 상태 확인 (또는 클라이언트에서 아이콘 교체 시 단순 토글)
		MessageVO mail = messageService.getMail(mail_id);
		int newStatus = mail.getMail_rlock();

		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("newStatus", newStatus);
		return result;
	}

//	--------------------------------------------------------

	// 보낸메일
	@GetMapping("/send")
	public ResponseEntity<Map<String, Object>> sendList(@RequestParam String memId,
			@ModelAttribute PageMakerWH pageMaker, @RequestParam(required = false) String filter) throws Exception {

		ResponseEntity<Map<String, Object>> result = null;

		List<MessageVO> sendMailList;

		if ("imp".equals(filter)) {
			sendMailList = messageService.sendImpList(pageMaker, memId);
		} else if ("lock".equals(filter)) {
			sendMailList = messageService.sendLockList(pageMaker, memId);
		} else {
			sendMailList = messageService.sendMailList(pageMaker, memId);
		}

		int unreadCount = messageService.unreadCount(memId);
		String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);

		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("sendMailList", sendMailList);
		dataMap.put("pageMaker", pageMaker);
		dataMap.put("displayCount", displayCount);

		result = new ResponseEntity<Map<String, Object>>(dataMap, HttpStatus.OK);

		return result;
	}

	@PostMapping("/toggleSImp")
	@ResponseBody
	public Map<String, Object> toggleSImp(@RequestParam("mail_id") int mail_id) throws SQLException {
		messageService.updateSImp(mail_id); // DB에서 바로 토글

		// DB에서 새 상태 확인 (또는 클라이언트에서 아이콘 교체 시 단순 토글)
		MessageVO mail = messageService.getMail(mail_id);
		int newStatus = mail.getMail_simp();

		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("newStatus", newStatus);
		return result;
	}

	@PostMapping("/toggleSLock")
	@ResponseBody
	public Map<String, Object> toggleSLock(@RequestParam("mail_id") int mail_id) throws SQLException {
		messageService.updateSLock(mail_id); // DB에서 바로 토글

		// DB에서 새 상태 확인 (또는 클라이언트에서 아이콘 교체 시 단순 토글)
		MessageVO mail = messageService.getMail(mail_id);
		int newStatus = mail.getMail_slock();

		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("newStatus", newStatus);

		return result;
	}

//	--------------------------------------------------------

	// 휴지통
	@GetMapping("/waste")
	public ResponseEntity<Map<String, Object>> wasteList(@RequestParam String memId,
			@ModelAttribute PageMakerWH pageMaker) throws Exception {

		ResponseEntity<Map<String, Object>> result = null;

		List<MessageVO> wasteList = messageService.wasteList(pageMaker, memId);

		int unreadCount = messageService.unreadCount(memId);
		String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);

		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("wasteList", wasteList);
		dataMap.put("pageMaker", pageMaker);
		dataMap.put("displayCount", displayCount);

		result = new ResponseEntity<Map<String, Object>>(dataMap, HttpStatus.OK);

		return result;
	}

	@PostMapping("/movewaste")
	@ResponseBody
	public Map<String, Object> remove(@RequestParam("mail_id") String mail_ids) throws Exception {

		Map<String, Object> result = new HashMap<>();

		try {
			if (mail_ids != null && !mail_ids.trim().isEmpty()) {
				String[] mailIdArr = mail_ids.split(",");
				for (String mailIdStr : mailIdArr) {
					mailIdStr = mailIdStr.trim();
					if (!mailIdStr.isEmpty()) { // 공백 체크
						int mail_id = Integer.parseInt(mailIdStr);
						System.out.println("Moving to waste mail_id: " + mail_id); // 로그 확인
						messageService.updateWaste(mail_id);
					}
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("false", false);
		}

		return result;
	}
	
	@PostMapping("/movewaste/detail")
	@ResponseBody
	public  Map<String, Object> removeDetail(@RequestParam("mail_id") int mail_id) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
		try {
	        messageService.updateWaste(mail_id); // 단일 메일만 처리
	        result.put("success", true);
	    } catch (Exception e) {
	        e.printStackTrace();
	        result.put("false", false);
	    }

	return result;
	}
	
	@PostMapping("/backwaste")
	@ResponseBody
	public Map<String, Object> backDetail(@RequestParam("mail_id") String mail_ids) throws Exception {

		Map<String, Object> result = new HashMap<>();

		try {
			if (mail_ids != null && !mail_ids.trim().isEmpty()) {
				String[] mailIdArr = mail_ids.split(",");
				for (String mailIdStr : mailIdArr) {
					mailIdStr = mailIdStr.trim();
					if (!mailIdStr.isEmpty()) { // 공백 체크
						int mail_id = Integer.parseInt(mailIdStr);
						messageService.updateWasteBack(mail_id);
					}
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
		}

		return result;
	}

//	--------------------------------------------------------

	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("mail_id") String mail_ids) throws Exception {

		Map<String, Object> result = new HashMap<>();

		try {
			if (mail_ids != null && !mail_ids.trim().isEmpty()) {
				String[] mailIdArr = mail_ids.split(",");
				for (String mailIdStr : mailIdArr) {
					mailIdStr = mailIdStr.trim();
					if (!mailIdStr.isEmpty()) { // 공백 체크
						int mail_id = Integer.parseInt(mailIdStr);
						messageService.delete(mail_id);
					}
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
		}

		return result;
	}
	
	@PostMapping("/allWaste")
	@ResponseBody
	public Map<String, Object> clearWaste() throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
		messageService.deleteAll();
		
		result.put("success", true);
		
		return result;
	}
	
//	--------------------------------------------------------

	// 세부내용
	@GetMapping("/detail")
	public ResponseEntity<Map<String, Object>> detail(@RequestParam int mail_id, @RequestParam String memId) throws Exception {
		
		ResponseEntity<Map<String, Object>> result = null;

		messageService.updateRRead(mail_id);

		MessageVO detail = messageService.detail(mail_id);
		MailFileVO mailFile = null;
		
		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("detail", detail);
		dataMap.put("mailFile", mailFile);

		result = new ResponseEntity<Map<String, Object>>(dataMap, HttpStatus.OK);

		return result;
	}
	
	@GetMapping("/detailwaste")
	public ResponseEntity<Map<String, Object>> detailWaste(@RequestParam int mail_id, @RequestParam String memId) throws Exception {

		ResponseEntity<Map<String, Object>> result = null;

		messageService.updateRRead(mail_id);

		MessageVO detail = messageService.detail(mail_id);
		MailFileVO mailFile = null;
		
		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("detail", detail);
		dataMap.put("mailFile", mailFile);

		result = new ResponseEntity<Map<String, Object>>(dataMap, HttpStatus.OK);

		return result;
	}

	@GetMapping("/getFile")
	@ResponseBody
	public ResponseEntity<Resource> getFile(int mafile_no) throws Exception {

		MailFileVO mailFile = mailFileDAO.selectMailFileByMafileNo(mafile_no);
		String filePath = mailFile.getMafile_uploadpath() + File.separator + mailFile.getMafile_name();

		Resource resource = new UrlResource(Paths.get(filePath).toUri());

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\""
								+ UriUtils.encode(mailFile.getMafile_name().split("\\$\\$")[1], "UTF-8") + "\"")
				.body(resource);
	}

	@GetMapping("/registForm")
	public ModelAndView registForm(ModelAndView mnv, HttpSession session) throws Exception {
		String url = "/message/regist";
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		String mem_id = loginUser.getMem_id();

		int unreadCount = messageService.unreadCount(mem_id);
		String displayCount = unreadCount >= 1000 ? "999+" : String.valueOf(unreadCount);

		mnv.addObject("unreadCount", displayCount);
		mnv.setViewName(url);
		return mnv;
	}

	@PostMapping("/regist")
	public ResponseEntity<Map<String, Object>> regist(@ModelAttribute MessageRegistCommand messageRegCommand) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		try {
		// 파일저장
		List<MultipartFile> uploadFiles = messageRegCommand.getUploadFile();
		String uploadPath = fileUploadPath;
		List<MailFileVO> attaches = saveFileToAttaches(uploadFiles, uploadPath);

		// DB
		MessageVO message = messageRegCommand.toMessage();
		message.setMail_name(HTMLInputFilter.htmlSpecialChars(message.getMail_name()));
		message.setMailFileList(attaches);
		messageService.registMail(message);
		
		result.put("success", true);
		result.put("message", "메일 등록 성공");
		return ResponseEntity.ok(result);
		
		} catch (Exception e) {
	        e.printStackTrace(); // 서버 콘솔에 에러 출력
	        result.put("success", false);
	        result.put("message", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
	    }

	}

	@javax.annotation.Resource(name = "messageSavedFilePath")
	private String fileUploadPath;

	private List<MailFileVO> saveFileToAttaches(List<MultipartFile> multiFiles, String savePath) throws Exception {
		if (multiFiles == null)
			return null;

		// 저장 -> attachVO -> attachList.add
		List<MailFileVO> mailFileList = new ArrayList<MailFileVO>();
		for (MultipartFile multi : multiFiles) {
			// 파일명
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String fileName = uuid + "$$" + multi.getOriginalFilename();

			// 파일저장
			File target = new File(savePath, fileName);
			target.mkdirs();
			multi.transferTo(target);

			MailFileVO attach = new MailFileVO();
			attach.setMafile_uploadpath(savePath);
			attach.setMafile_name(fileName);
			attach.setMafile_type(fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase());

			// mailFileList 추가
			mailFileList.add(attach);

		}
		return mailFileList;
	}

	

	@GetMapping("/backwaste/detail")
	public ModelAndView backDetail(@RequestParam("mail_id") String mail_ids, ModelAndView mnv) throws Exception {
		String url = "/message/back_detail_success";

		if (mail_ids != null && !mail_ids.trim().isEmpty()) {
			String[] mailIdArr = mail_ids.split(",");
			for (String mailIdStr : mailIdArr) {
				mailIdStr = mailIdStr.trim();
				if (!mailIdStr.isEmpty()) { // 공백 체크
					int mail_id = Integer.parseInt(mailIdStr);
					messageService.updateWasteBack(mail_id);
				}
			}
		}

		mnv.setViewName(url);
		return mnv;
	}

	@GetMapping("/delete/detail")
	public ModelAndView deleteDetail(@RequestParam("mail_id") String mail_ids, ModelAndView mnv) throws Exception {
		String url = "/message/remove_detail_success";

		if (mail_ids != null && !mail_ids.trim().isEmpty()) {
			String[] mailIdArr = mail_ids.split(",");
			for (String mailIdStr : mailIdArr) {
				mailIdStr = mailIdStr.trim();
				if (!mailIdStr.isEmpty()) { // 공백 체크
					int mail_id = Integer.parseInt(mailIdStr);
					messageService.delete(mail_id);
				}
			}
		}

		mnv.setViewName(url);
		return mnv;
	}

	

}
