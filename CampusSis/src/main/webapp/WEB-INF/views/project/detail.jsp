<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="modal-body">
  <!-- 제목 + 버튼 row -->
    <c:set var="project" value="${projectList[0]}" />
    
    <div class="row d-flex justify-content-between align-items-center mb-4">
      <div class="col-4 ml-3">
        <h5 id="modifyModalLabel" style="font-size:25px; font-weight:bold; margin-left:7px;">상세정보</h5>
      </div>
      <div class="col-1">
        <button type="button" class="btn btn-info" data-dismiss="modal" onclick="remove();"
          style="background-color:#aaaaaa; border-radius:5px; width:150px; height:40px; border:none;margin-right:-20px; font-weight:bold;">
          <span style="color:#ffffff; font-size:20px;">삭제</span>
        </button>
      </div>
      <div class="col-3">
        <button type="submit" class="btn btn-info" onclick="CloseWindow();"
          style="background-color:#2ec4b6; border-radius:5px; width:150px; height:40px; border:none; font-weight:bold;">
          <span style="color:#ffffff; font-size:20px;">확인</span>
        </button>
      </div>
    </div>
	<hr/>
    <!-- 학기 -->
    <div class="row mt-4 ml-3">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:4px;">학기</h3>
      </div>
      <div class="col-10">
      <input name="samester" class="form-control" type="text" placeholder="프로젝트명을 입력해주세요." value="${project.samester}" style="width:527px;" disabled>
      </div>
    </div>

    <!-- 프로젝트명 -->
    <div class="row mt-3 ml-3">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:4px;">프로젝트명</h3>
      </div>
      <div class="col-10">
        <input name="project_name" class="form-control" type="text" placeholder="프로젝트명을 입력해주세요." value="${project.project_name}" style="width:527px;" disabled>
      </div>
    </div>

    <!-- 시작일 -->
    <div class="row mt-3 ml-3">
  <div class="col-2">
    <h3 style="font-size:20px; font-weight:bold; margin-top:13px;">시작일</h3>
  </div>
  <div class="col-10">
      <input name="project_stdate" type="text" class="form-control datetimepicker-input" data-target="#datetimepicker3"
        style="width: 527px; height:38px; font-size: 16px;" disabled
        value="<fmt:formatDate value='${project.project_stdate}' pattern='yyyy-MM-dd' />"/>
    </div>
  </div>

<!-- 마감일 -->
<div class="row mt-3 ml-3">
  <div class="col-2">
    <h3 style="font-size:20px; font-weight:bold; margin-top:13px;">마감일</h3>
  </div>
  <div class="col-10">
      <input name="project_endate" type="text" class="form-control datetimepicker-input" data-target="#datetimepicker4"
        style="width: 527px; height:38px; font-size: 16px;" disabled
        value="<fmt:formatDate value='${project.project_endate}' pattern='yyyy-MM-dd' />" />
    </div>
  </div>


    <!-- 담당교수 -->
    <div class="row mt-3 ml-3">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:4px;">담당교수</h3>
      </div>
      <div class="col-10">
        <input type="text" class="form-control" placeholder="담당교수의 변경은 불가능합니다." style="width:527px;"
          value="${project.profes_name}" disabled />
        <input type="hidden" name="profes_id" value="${project.profes_id}" />
      </div>
    </div>

    <!-- 팀장 -->
<!-- 팀장 -->
<div class="row mt-2 ml-3">
  <div class="col-2">
    <h3 style="font-size:20px; font-weight:bold; margin-top:4px; line-height:50px;">팀장</h3>
  </div>
  <div class="col-10 d-flex align-items-center">
    <!-- 숨김 필드: 실제 폼 제출 시 아이디 값 -->
    <input type="hidden" name="team_leader" id="teamLeaderIdInput" value="${project.team_leader }" />
    <input type="hidden" name="leader_name" value="${project.leader_name}" />
    <!-- 화면에 보여줄 이름 -->
    <input type="text" class="form-control" id="teamLeaderInput" placeholder="팀장을 입력해주세요" readonly style="width: 527px;" value="${project.leader_name}" />
    
  </div>
</div>


    <!-- 팀원 -->
<div class="row  ml-3">
  <div class="col-2">
    <h3 style="font-size:20px; font-weight:bold; margin-top:4px; line-height:50px;">팀원</h3>
  </div>
  <div class="col-10 d-flex align-items-center">
 <c:set var="teamMembersNames" value="" />
<c:forEach var="name" items="${projectList[0].mem_name}">
  <c:set var="teamMembersNames" value="${fn:trim(teamMembersNames)}${teamMembersNames != '' ? ',' : ''}${name}" />
</c:forEach>
<input type="hidden" id="teamMembersIdInput" name="team_member_ids" value="${projectList[0].mem_id}" />
    <input type="text" class="form-control" id="teamMembersInput" placeholder="팀원을 입력해주세요" readonly style="width: 527px;"
      value="${fn:trim(teamMembersNames)}" />
     <input type="hidden" name="team_member_names" id="teamMemberNamesInput" 
       value="${fn:trim(teamMembersNames)}" />
  </div>
</div>

    <!-- 사유 영역 -->
    <div class="row mt-3 ml-3">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:4px;">내용</h3>
      </div>
    </div>

    <div class="row mt-2">
      <div class="col-12 d-flex">
       <div
  style="width:637px; height:160px;margin-left:25px; border:1px solid #2ec4b6;">
  ${project.project_desc}
</div>
      </div>
    </div>

    <!-- 숨겨진 필드로 프로젝트ID 등 꼭 넣기 -->
    <input type="hidden" name="project_id" value="${project.project_id}" />
    <input type="hidden" name="team_id" value="${project.team_id}" />
    <input type="hidden" name="before_id" value="${project.project_id}" /> <!-- 수정 전 ID -->

</div>

<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/moment/moment.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>

<!-- 초기화 스크립트 -->
<script>
  $(function () {
    $('#datetimepicker3').datetimepicker({
      format: 'YYYY-MM-DD' // 날짜만 (MM/DD/YYYY 형식)
    });
    $('#datetimepicker4').datetimepicker({
      format: 'YYYY-MM-DD'
    });
    $('#datetimepicker3 input, #datetimepicker4 input').on('keydown paste', function(e) {
        // 백스페이스, 방향키 등은 허용할 수도 있음 (선택 사항)
        // 여기서는 모든 키 입력 차단
        e.preventDefault();
      });
  });
</script>
<script>
  // 모달에서 선택된 멤버 아이디, 이름 받아서 MODIFY 페이지에 맞게 처리하는 함수
  function setTeamLeader(id, name) {
  $('#teamLeaderIdInput').val(id);
  $('#teamLeaderInput').val(name);
  $('input[name="leader_name"]').val(name);  // 이거 꼭 추가!
}

function setTeamMembersForModify(memberIds, memberNames) {
  let ids = Array.isArray(memberIds) ? memberIds.join(' ') : memberIds;
  let names = Array.isArray(memberNames) ? memberNames.join(', ') : memberNames;

  $('#teamMembersIdInput').val(ids);
  $('#teamMembersInput').val(names);
  $('input[name="team_member_names"]').val(names); // 이거도 꼭 추가!
}
</script>
<script>
  // 모달 닫힐 때마다 호출해서 수정폼 필드에 값 반영
  $('#studentSelectModalLeader').on('hidden.bs.modal', function () {
    // 모달에서 값 가져오기
    const selectedId = $('input[name="team_leader"]').val();   // 모달 공용 필드 (숨김)
    const selectedName = $('#teamLeaderName').val();            // 모달 공용 필드 (보이는 이름)

    // 수정폼 필드에 맞게 값 넣기
    if (selectedId && selectedName) {
      $('#teamLeaderIdInput').val(selectedId);
      $('#teamLeaderInput').val(selectedName);
    }

    // 기존 모달 닫힐 때 동작 유지
    $('.modal-backdrop').remove();
    $('body').removeClass('modal-open');
  });
</script>
<script>
function setTeamMembers(selectedMembers) {
  const ids = selectedMembers.map(m => m.stu_id);
  const names = selectedMembers.map(m => m.mem_name);

  $('#teamMembersIdInput').val(ids.join(' '));
  $('#teamMemberNamsInput').val(names.join(' '));
}
</script>
<script>
function remove(){
	location.href="remove?team_id=${project.team_id}";
}
</script>
<jsp:include page="teamselect.jsp" />
<jsp:include page="teamsselect.jsp" />
