
  package com.camp_us.mapper;
  
  import java.util.List;
  
  import com.camp_us.command.PageMaker; import com.camp_us.dto.HomeworkVO;
  
  public interface HomeworkMapper {
  
  // 과제 상세 조회 
	  HomeworkVO selectHomeworkByHwNo(int hwNo);
  
  // 과제 목록 조회 (XML에 이미 존재함) 
	  List<HomeworkVO> getHomeworkList(PageMaker pageMaker);
  
  // 과제 등록 
	  void insertHomework(HomeworkVO vo);
  
  // 다음 과제 번호 조회 
	  int getNextHwNo();
  
  // 총 개수 
	  int getHomeworkTotalCount(PageMaker pageMaker);
  
  // 삭제 
	  void deleteHomework(int hwNo);
  
  // 학생 강의실의 과제 목록 
	  List<HomeworkVO> getStudentHomeworkList(String studentId);
  
  HomeworkVO getHomeworkByNo(int hwNo); }
 