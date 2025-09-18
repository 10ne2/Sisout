<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-body">
  <form id="modifyForm" action="${pageContext.request.contextPath}/project/modify/pro" method="post">
    <c:set var="project" value="${projectList[0]}" />
    <c:set var="edit" value="${editList[0]}" />
	
    <!-- 날짜 문자열 변수 미리 생성 -->
    <fmt:formatDate value="${edit.project_stdate}" pattern="yyyyMMdd" var="editStDateStr" />
    <fmt:formatDate value="${project.project_stdate}" pattern="yyyyMMdd" var="projectStDateStr" />
    <fmt:formatDate value="${edit.project_endate}" pattern="yyyyMMdd" var="editEndDateStr" />
    <fmt:formatDate value="${project.project_endate}" pattern="yyyyMMdd" var="projectEndDateStr" />

    <!-- 팀원 이름들 문자열 생성 (쉼표 구분) -->
    <c:set var="projectTeamMembersNames" value="" />
    <div id="teamMembersHiddenInputs"></div>
    <input type="hidden" name="beforeId" value="${edit.before_id}" />
    <c:forEach var="name" items="${projectList[0].mem_name}">
      <c:set var="projectTeamMembersNames" value="${fn:trim(projectTeamMembersNames)}${projectTeamMembersNames != '' ? ',' : ''}${name}" />
    </c:forEach>
    <c:set var="editTeamMembersNames" value="" />
    <c:forEach var="name" items="${editList[0].team_member_names}">
      <c:set var="editTeamMembersNames" value="${fn:trim(editTeamMembersNames)}${editTeamMembersNames != '' ? ',' : ''}${name}" />
    </c:forEach>

    <div class="row d-flex align-items-center mb-4">
      <div class="col-4 ml-3">
        <h5 id="modifyModalLabel" style="font-size:25px; font-weight:bold; margin-left:7px;">수정 요청서</h5>
      </div>
      <div class="col-3"></div>
      <div class="col-1">
        <button type="button" class="btn btn-info" data-dismiss="modal" onclick="reject();"
          style="background-color:#aaaaaa; border-radius:5px; width:120px; height:40px; border:none;margin-right:-20px; font-weight:bold;">
          <span style="color:#ffffff; font-size:20px;">거부</span>
        </button>
      </div>
      <div class="col-2" style="margin-left:73px;">
        <button type="submit" class="btn btn-info"
          style="background-color:#2ec4b6; border-radius:5px; width:120px; height:40px; border:none; font-weight:bold;">
          <span style="color:#ffffff; font-size:20px;">승인</span>
        </button>
      </div>
    </div>

    <!-- samester hidden input 추가 -->
    <input type="hidden" name="samester" value="${edit.samester}" />

    <!-- 학기 -->
    <div class="row mt-4 ml-4">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:4px; color:black;">
          학기
        </h3>
      </div>
      <div class="col-10" style="line-height:35px;">
        <c:choose>
          <c:when test="${edit.samester ne project.samester}">
            <div style=" width:527px; border:1px solid #CED4DA; height: 38px; border-radius:5px; background-color:#E9ECEF;">
            <span class="ml-2"style="color:#B7B7B7; text-decoration: line-through; text-decoration-color:#B7B7B7;"> ${project.samester}</span>
              <span class="ml-3">${edit.samester}</span>
            </div>
          </c:when>
          <c:otherwise>
            <div style=" width:527px; border:1px solid #CED4DA; height: 38px; border-radius:5px; background-color:#E9ECEF;">
            <span class="ml-3">${edit.samester}</span>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <!-- 프로젝트명 -->
    <div class="row mt-3 ml-4">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:4px; color:black;">
          프로젝트명
        </h3>
      </div>
      <div class="col-10" style="line-height:35px;">
        <c:choose>
          <c:when test="${edit.project_name ne project.project_name}">
          <div style=" width:527px; border:1px solid #CED4DA; height: 38px; border-radius:5px; background-color:#E9ECEF;">
            <span class="ml-2" style="color:#B7B7B7; text-decoration: line-through; text-decoration-color:#B7B7B7;">${project.project_name}</span>
             <span class="ml-3">${edit.project_name}</span>
             <input type="hidden" name="project_name" value="${edit.project_name}"/>
          </div>
          </c:when>
          <c:otherwise>
          <div style=" width:527px; border:1px solid #CED4DA; height: 38px; border-radius:5px; background-color:#E9ECEF;">
            <span class="ml-3">${edit.project_name}</span>
             <input type="hidden" name="project_name" value="${edit.project_name}"/>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <!-- 시작일 -->
    <div class="row mt-1 ml-4">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:13px; color:black;">
          시작일
        </h3>
      </div>
      <div class="col-10" style="line-height:35px;">
        <c:choose>
          <c:when test="${editStDateStr ne projectStDateStr}">
          <div style=" width:527px; border:1px solid #CED4DA; height: 38px; border-radius:5px; background-color:#E9ECEF;">
            <span class="ml-2" style="color:#B7B7B7; text-decoration: line-through; text-decoration-color:#B7B7B7;">
              <fmt:formatDate value="${project.project_stdate}" pattern="yyyy-MM-dd" />
            </span>
            <span class="ml-3">
            <fmt:formatDate value='${edit.project_stdate}' pattern='yyyy-MM-dd' />   
            </span>
              </div>
            <input type="hidden" name="project_stdate" value="<fmt:formatDate value='${edit.project_stdate}' pattern='yyyy-MM-dd' />"/>
          </c:when>
          <c:otherwise>
          <div style=" width:527px; border:1px solid #CED4DA; height: 38px; border-radius:5px; background-color:#E9ECEF;">
            <span class="ml-2"><fmt:formatDate value='${edit.project_stdate}' pattern='yyyy-MM-dd' /></span>
            <input type="hidden" name="project_stdate" value="<fmt:formatDate value='${edit.project_stdate}' pattern='yyyy-MM-dd' />"/>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <!-- 마감일 -->
    <div class="row mt-1 ml-4">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:13px; color:black;">
          마감일
        </h3>
      </div>
      <div class="col-10" style="line-height:35px;">
        <c:choose>
          <c:when test="${editEndDateStr ne projectEndDateStr}">
          <div style=" width:527px; border:1px solid #CED4DA; height: 38px; border-radius:5px; background-color:#E9ECEF;">
            <span class="ml-2" style="color:#B7B7B7; text-decoration: line-through; text-decoration-color:#B7B7B7;">
              <fmt:formatDate value="${project.project_endate}" pattern="yyyy-MM-dd" /></span>
            <input type="hidden" name="project_endate" value="<fmt:formatDate value='${edit.project_endate}' pattern='yyyy-MM-dd' />"/>
            <span class="ml-3"><fmt:formatDate value='${edit.project_endate}' pattern='yyyy-MM-dd' /></span>

              </div>
          </c:when>
          <c:otherwise>
          <div style=" width:527px; border:1px solid #CED4DA; height: 38px; border-radius:5px; background-color:#E9ECEF;">
            <span class="ml-2"><fmt:formatDate value='${edit.project_endate}' pattern='yyyy-MM-dd' /></span>
            <input type="hidden" name="project_endate" value="<fmt:formatDate value='${edit.project_endate}' pattern='yyyy-MM-dd' />"/>
              </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <!-- 담당교수 -->
    <div class="row mt-3 ml-4">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:4px;">
          담당교수
        </h3>
      </div>
      <div class="col-10">
        <!-- 담당교수는 변경 불가이므로 edit이 아닌 project 값만 보여줌 -->
        <input type="text" class="form-control" placeholder="담당교수의 변경은 불가능합니다." style="width:527px;"
          value="${project.profes_name}" readonly />
        <input type="hidden" name="profes_id" value="${project.profes_id}" />
      </div>
    </div>

    <!-- 팀장 -->
    <div class="row mt-3 ml-4">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:4px; line-height:50px; color:black;">
          팀장
        </h3>
      </div>
      <div class="col-10 d-flex align-items-center" style="line-height:35px;">
        <c:choose>
          <c:when test="${edit.leader_name ne project.leader_name}">
          <div style=" width:527px; border:1px solid #CED4DA; height: 38px; border-radius:5px; background-color:#E9ECEF;">
            <span class="ml-2" style="color:#B7B7B7; text-decoration: line-through; margin-right:10px; text-decoration-color:#B7B7B7;">
             	${project.leader_name}
            </span>
            <input type="hidden" name="team_leader" id="teamLeaderIdInput" value="${edit.team_leader }" />
            <input type="hidden" name="leader_name" value="${edit.leader_name}" />
            <span class="ml-3">${edit.leader_name}</span>
              </div>
          </c:when>
          <c:otherwise>
          <div style=" width:527px; border:1px solid #CED4DA; height: 38px; border-radius:5px; background-color:#E9ECEF;">
            <input type="hidden" name="team_leader" id="teamLeaderIdInput" value="${edit.team_leader }" />
            <input type="hidden" name="leader_name" value="${edit.leader_name}" />
            <span class="ml-2">${edit.leader_name}</span>
              </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <!-- 팀원 -->
    <div class="row mt-3 ml-4">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:4px; line-height:50px;">팀원</h3>
      </div>
      <div class="col-10 d-flex align-items-center" style="line-height:35px;">
        <c:choose>
          <c:when test="${editTeamMembersNames ne projectTeamMembersNames}">
          <div style=" width:527px; border:1px solid #CED4DA; height: 38px; border-radius:5px; background-color:#E9ECEF;">
            <span class="ml-2" style="color:#B7B7B7; text-decoration: line-through; margin-right:10px; text-decoration-color:#B7B7B7;">
              ${projectTeamMembersNames}
            </span>
            <input type="hidden" id="teamMembersIdInput" name="team_member_ids" value="${editList[0].team_member_ids}" />
            <span class="ml-3">${editTeamMembersNames}</span>
            <input type="hidden" name="team_member_names" id="teamMemberNamesInput" value="${editTeamMembersNames}" />
            </div>
          </c:when>
          <c:otherwise>
            <input type="hidden" id="teamMembersIdInput" name="team_member_ids" value="${editList[0].team_member_ids}" />
            <input type="text" class="form-control" id="teamMembersInput" placeholder="팀원을 입력해주세요" readonly
              style="width: 465px;"
              value="${editTeamMembersNames}" />
            <input type="hidden" name="team_member_names" id="teamMemberNamesInput" value="${editTeamMembersNames}" />
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <!-- 사유 영역 -->
    <div class="row mt-3 ml-4">
      <div class="col-2">
        <h3 style="font-size:20px; font-weight:bold; margin-top:4px;">사유</h3>
      </div>
    </div>

    <div class="row mt-2">
      <div class="col-12 d-flex">
        <textarea name="edit_content" class="form-control custom-textarea" rows="3" placeholder="수정 사유를 입력해주세요."
          style="width:1200px; height:130px; resize: none; margin-left:30px;" readonly>${edit.edit_content}</textarea>
      </div>
    </div>

    <!-- 숨겨진 필드로 프로젝트ID 등 꼭 넣기 -->
    <input type="hidden" name="project_id" value="${edit.project_id}" />
    <input type="hidden" name="team_id" value="${edit.team_id}" />
    <input type="hidden" name="before_id" value="${edit.before_id}" />

  </form>
</div>

<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/moment/moment.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>

<script>
  $(function () {
    $('#datetimepicker3').datetimepicker({
      format: 'YYYY-MM-DD'
    });
    $('#datetimepicker4').datetimepicker({
      format: 'YYYY-MM-DD'
    });
    $('#datetimepicker3 input, #datetimepicker4 input').on('keydown paste', function(e) {
      e.preventDefault();
    });
  });
</script>

<script>
  // 모달에서 선택된 멤버 아이디, 이름 받아서 MODIFY 페이지에 맞게 처리하는 함수
  function setTeamMembersForModify(memberIds, memberNames) {
    let ids = Array.isArray(memberIds) ? memberIds.join(' ') : memberIds;
    let names = Array.isArray(memberNames) ? memberNames.join(' ') : memberNames;

    $('#teamMembersIdInput').val(ids);
    $('#teamMembersInput').val(names);
  }
  function setTeamLeader(id, name) {
    $('#teamLeaderIdInput').val(id);
    $('#teamLeaderInput').val(name);
  }
</script>

<script>
  $('#studentSelectModalLeader').on('hidden.bs.modal', function () {
    const selectedId = $('input[name="team_leader"]').val();
    const selectedName = $('#teamLeaderName').val();

    if (selectedId && selectedName) {
      $('#teamLeaderIdInput').val(selectedId);
      $('#teamLeaderInput').val(selectedName);
    }

    $('.modal-backdrop').remove();
    $('body').removeClass('modal-open');
  });
</script>

<script>
  function setTeamMembers(selectedMembers) {
    const ids = selectedMembers.map(m => m.stu_id);
    const names = selectedMembers.map(m => m.mem_name);

    $('#teamMembersIdInput').val(ids.join(' '));
    $('#teamMembersInput').val(names.join(' '));
  }
</script>

<script>
  $('#modifyForm').on('submit', function(e) {
    const samester = $('input[name="samester"]').val();
    const projectName = $('input[name="project_name"]').val().trim();
    const stDate = $('input[name="project_stdate"]').val().trim();
    const endDate = $('input[name="project_endate"]').val().trim();
    const editContent = $('textarea[name="edit_content"]').val().trim();
    const teamLeaderId = $('#teamLeaderIdInput').val();
    const teamMembersIds = $('#teamMembersIdInput').val();

    if (!samester) {
      alert('학기를 선택해주세요.');
      e.preventDefault();
      return false;
    }
    if (!projectName) {
      alert('프로젝트명을 입력해주세요.');
      e.preventDefault();
      return false;
    }
    if (!stDate) {
      alert('시작일을 선택해주세요.');
      e.preventDefault();
      return false;
    }
    if (!endDate) {
      alert('마감일을 선택해주세요.');
      e.preventDefault();
      return false;
    }
    if (new Date(stDate) > new Date(endDate)) {
      alert('시작일은 마감일보다 늦을 수 없습니다.');
      e.preventDefault();
      return false;
    }
    if (!teamLeaderId) {
      alert('팀장을 선택해주세요.');
      e.preventDefault();
      return false;
    }
    if (!teamMembersIds) {
      alert('팀원을 선택해주세요.');
      e.preventDefault();
      return false;
    }
    if (!editContent) {
      alert('수정 사유를 입력해주세요.');
      e.preventDefault();
      return false;
    }
    const teamMemberIdsStr = $('#teamMembersIdInput').val();
    const teamMemberIds = teamMemberIdsStr.trim().split(/\s+/);
    
    $('#teamMembersHiddenInputs').empty();
    teamMemberIds.forEach(id => {
        const input = $('<input>').attr({
          type: 'hidden',
          name: 'memberIds',  // 컨트롤러 파라미터명에 맞춤
          value: id
        });
        $('#teamMembersHiddenInputs').append(input);
      });
  });
</script>
<script>
function reject(){
	location.href="reject?before_id=${edit.before_id}";
}
</script>
<!-- 여기서 teamselect.jsp 와 teamsselect.jsp 포함 -->
<jsp:include page="teamselect.jsp" />
<jsp:include page="teamsselect.jsp" />
