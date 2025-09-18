<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
  <!-- ✅ jQuery (모든 것의 기반) -->
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>

  <!-- ✅ Moment.js (달력용) -->
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/moment/moment.min.js"></script>

  <!-- ✅ Bootstrap (기본 프레임워크) -->
  <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/bootstrap/plugins/bootstrap/css/bootstrap.min.css">
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- ✅ Tempus Dominus (달력) -->
  <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>

  <!-- ✅ Summernote -->
  <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/bootstrap/plugins/summernote/summernote-bs4.min.css">
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/summernote/summernote-bs4.min.js"></script>
</head>	
<form enctype="multipart/form-data"  role="form" method="post" action="regist" name="registForm">
<div class="wrap" style="height:100%;">
<c:set var="project" value="${projectList[0]}" />
    <fmt:formatDate value="${project.project_stdate}" pattern="yyyyMMdd" var="projectStDateStr" />
    <fmt:formatDate value="${project.project_endate}" pattern="yyyyMMdd" var="projectEndDateStr" />
    <c:set var="projectTeamMembersNames" value="" />
    <div id="teamMembersHiddenInputs"></div>
    <input type="hidden" name="beforeId" value="${edit.before_id}" />
    <c:set var="projectTeamMembersNames" value="" />
<c:forEach var="name" items="${teamMembersList}">
  <c:set var="projectTeamMembersNames" 
         value="${fn:trim(projectTeamMembersNames)}${projectTeamMembersNames != '' ? ',' : ''}${name}" />
</c:forEach>
<input type="hidden" id="writer" name="writer" value="${loginUser.mem_id }"/>
<input type="hidden" name="project_id" value="${project.project_id}" />
<input type="hidden" name="team_id" value="${project.team_id}" />
<input type="hidden" name="eval_status" value="0" />

	<div class="row">
		<div class="col-12">
			<h3 style="font-size: 25px; font-weight: bold; margin: 15px 0 0 30px; ">평가 자료 제출</h3>
		</div>
	</div>
	<div class="row ml-5 mt-3">
		<div class="col-3">
			<span style="font-weight:bold;">프로젝트명</span>
		</div>
		<div class="col-3">
			<span style="font-weight:bold;">기한</span>
		</div>
	</div>
	<div class="row ml-5">
		<div class="col-3">
			<input class="form-control notNull" name="project_name" title="프로젝트명" type="text" placeholder="프로젝트명을 입력해주세요." style="width:300px;" value="${project.project_name }" readonly>
		</div>
		
		<div class="col-4 d-flex">
			<div class="input-group date"  style="width: 150px; height:38px;">
                <input name="project_stdate" type="text" class="form-control" style="width: 90px; height:38px; font-size: 16px;" placeholder="시작" value="<fmt:formatDate value="${project.project_stdate}" pattern="yyyy-MM-dd" />" readonly>
                
            </div>
            <span style="line-height:38px;">&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;</span>
            <div class="input-group date"  style="width: 150px; height:38px;">
                <input name="project_endate" type="text" class="form-control"style="width: 90px; height:38px; font-size: 16px;" placeholder="시작" value="<fmt:formatDate value="${project.project_endate}" pattern="yyyy-MM-dd" />" readonly>
                
            </div>
            
                </div>
            </div>

		</div>
	</div>
	<div class="row ml-5 mt-3">
		<div class="col-3">
			<span style="font-weight:bold;">담당교수</span>
		</div>
		<div class="col-3" style="margin-left:10px;">
			<span style="font-weight:bold;">팀장</span>
		</div>
		<div class="col-3 mt-1">
			<span style="font-weight:bold;">팀원</span>
		</div>
	</div>
	<div class="row ml-5">
		<div class="col-3 d-flex">
			<input id="professorName" class="form-control notNull" type="text" title="담당교수" placeholder="담당교수를 등록해주세요."  readonly style="width:80%;"  value="${project.profes_name}" >
			
		</div>
		<div class="col-3 d-flex" style="margin-left:8px;">
			<!-- 팀장 이름 표시용 -->
			<input id="teamLeaderName" class="form-control" type="text" placeholder="팀장을 등록해주세요." readonly style="width:80%;"value="${project.leader_name}" >
			<!-- 실제 팀장 stu_id 전송용 -->
			<input type="hidden" name="team_leader" id="teamLeaderId">
			<button type="button" class="btn btn-secondary ml-2" data-toggle="modal" data-target="#studentSelectModalLeader" style="width:70px; height:38px; color:#fff; background-color:#2ec4b6; border:none;">검색</button>
		</div>
		<div class="col-4 d-flex">
			<!-- 팀원 이름 표시용 (콤마로 구분된 문자열) -->
			<input id="teamMembersName" name="team_member" class="form-control" type="text" 
       placeholder="팀원을 등록해주세요." readonly 
       value="${teammembers}">
			<!-- 팀원 stu_id_list 전송용 히든 input 여러개 삽입될 영역 -->
			<div id="teamMembersHiddenInputs"></div>
			<button type="button" class="btn btn-secondary ml-2" data-toggle="modal" data-target="#studentSelectModalMembers" style="width:70px; height:38px; color:#fff; background-color:#2ec4b6; border:none;">검색</button>
		</div>
	</div>
	<div style="margin-left:55px; margin-top:15px;">
	<div class="row">
		<div class="col-2">
			<span style="font-weight:bold;">구분</span>
		</div>
	<div class="col-8">
	<span style="font-weight:bold;">제목</span></div>
	</div>
	<div class="row">
	<div class="col-2">
			<select name="rm_category" class="form-control" style="font-size: 16px; border: 1px solid #2ec4b6; height:38px;">
                <option value="">카테고리</option>
                <option value="회의록">회의록</option>
                <option value="업무일지">업무일지</option>
                <option value="산출물">산출물</option>
                <option value="최종결과물">최종결과물</option>
            </select>
            </div>
	<div class="col-10">
	<input id="rm_name" name="rm_name" class="form-control" type="text" placeholder="제목을 입력해주세요." style="width:80%;">
	</div>
	</div>
	</div>
	<div class="row">
		<div class="col-11" style="margin-left:55px; margin-top:15px;">
			<div class="form-group">
				<label for="rm_content"><span style="font-weight:bold;">내용</span></label>
				<textarea class="form-control" name="rm_content" id="rm_content" rows="5"  placeholder="1000자 내외로 작성하세요." ></textarea>
			</div>
		</div>
	</div>
	<div class="row">
	<div class="form-group" style="width:1176px; border:1px solid #ced4da; margin-left:63px; border-radius:5px; border-top:4px solid #2ec4b6;">								
			<div class="card-header d-flex" style="width:100%; align-items: center;">
			<div class="card-footer fileInput">
			</div>
					<div class="ml-auto"><button class="btn btn-primary"
					onclick="addFile_go();"	type="button" id="addFileBtn" style="background-color:#2ec4b6; color:#ffffff; border:1px solid #2ec4b6">파일 추가</button></div>
			</div>									
	</div>
	</div>
	</div>
	<div class="row">
		<div class="col-6"></div>
		<div class="col-2"></div>
		<div class="col-2">
			<button type="button" class="btn btn-info" onclick="if(confirm('등록을 취소하시겠습니까?')) history.back();"
              style="background-color:#aaaaaa; border-radius:5px; width:150px; height:40px; border:none; margin-right:-20px; font-weight:bold; margin-left:40px;">
              <span style="color:#ffffff; font-size:20px;">취 소</span>
            </button>
          </div>
		<div class="col-2">
			<button type="button" class="btn btn-info" onclick="roadMapRegist_go();"
              style="background-color:#2ec4b6; border-radius:5px; width:150px; height:40px; border:none; font-weight:bold;">
              <span style="color:#ffffff; font-size:20px;">등 록</span>
            </button>
		</div>
	</div>
</form>
</div>
<!-- 초기화 스크립트 -->
<script>
  $(function () {

    // 썸머노트 초기화
    $('#rm_content').summernote({
      height: 150,
      placeholder: '내용을 입력해주세요.'
    });
  });
  function roadMapRegist_go() {
	    let form = document.forms["registForm"];

	    // 필수 입력값 검사
	    let requiredFields = document.querySelectorAll(".notNull");
	    for (let input of requiredFields) {
	      if (!input.value.trim()) {
	        alert(input.getAttribute("title") + "은(는) 필수 입력 항목입니다.");
	        input.focus();
	        return;
	      }
	    }

	    // 학기 선택 확인
	    if (!form.rm_category.value) {
	      alert("카테고리를 선택해주세요.");
	      form.rm_category.focus();
	      return;
	    }
	    // 담당교수 확인
	    if (!form.rm_name.value) {
	      alert("제목을 입력해주세요.");
	      return;
	    }
		if (!form.rm_content.value){
			 alert("내용을 입력해주세요.");
		      return;
		    }
		if($('input[name="uploadFile"]').length < 1){
			alert("1개 이상의 첨부파일은 필수입니다.");
			return;
		}
	    // 최종 제출
	    form.action = "regist";
	    form.method = "post";
	    form.submit();
	  }

  // 팀장 모달에서 선택 완료 시 호출할 함수 (예시)
 
</script>
<script>
var dataNum=0;

function addFile_go(){
	//alert("!!!!");
	if($('input[name="uploadFile"]').length >=5){
		alert("파일추가는 5개까지만 가능합니다.");
		return;
	}
	
	let div=$('<div>').addClass("inputRow").attr("data-no",dataNum);
	let input=$('<input>').attr({"type":"file","name":"uploadFile"}).css("display","inline");
	let button = "<button onclick='remove_go("+dataNum+");' style='border:0;outline:0;' class='badge bg-red' type='button'>X</button>";

	div.append(input).append(button);
	$('.fileInput').append(div);
	
	dataNum++;
}

function remove_go(num){
	$('div[data-no="'+num+'"]').remove();
}

$('.fileInput').on('change',"input[name='uploadFile']",function(event){
	if(this.files[0].size > 1024*1024*40){
		alert("첨부파일크기는 40MB 이하만 가능합니다.");
		this.value="";				
	}
});

</script>