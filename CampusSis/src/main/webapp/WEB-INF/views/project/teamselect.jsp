<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="studentSelectModalLeader" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">팀장 선택</h5>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      <div class="modal-body">
        <!-- 검색 입력 -->
        <input type="text" id="leaderSearchInput" placeholder="학생 이름 검색" class="form-control mb-3">
        <!-- 학생 리스트 (예: 체크박스 없이 클릭 선택) -->
        <ul id="leaderStudentList" class="list-group" style="max-height:300px; overflow-y:auto;">
  <c:forEach var="student" items="${studentList}">
    <li class="list-group-item leader-student-item" 
        data-stu-picture="${student.picture }"
        data-stu-id="${student.mem_id}" 
        data-stu-name="${student.mem_name}"
        style="cursor:pointer;">
        <img src="<%=request.getContextPath() %>/member/getPicture?id=${student.mem_id}" class="img-circle img-md" alt="User Image" style="width:45px; height:45px; object-fit:cover;">
      ${student.mem_name} (${student.mem_id})
    </li>
  </c:forEach>
</ul>
      </div>
      <div class="modal-footer">
        <button type="button" id="selectLeaderBtn" class="btn btn-primary"
        style="background-color:#2ec4b6; border-radius:5px; width:150px; height:40px; border:none; font-weight:bold;"
         disabled>선택 완료</button>
      </div>
    </div>
  </div>
</div>
<script>
$(document).ready(function() {
    // 리스트 항목 클릭 시 선택 동작
    $(document).on('click', '.leader-student-item', function () {
        // 이전 선택 해제
        $('.leader-student-item').removeClass('active');

        // 현재 항목 선택 표시
        $(this).addClass('active');

        // 선택된 학생 정보 추출
        const stuId = $(this).data('stu-id');
        const stuName = $(this).data('stu-name');

        // 버튼 활성화
        $('#selectLeaderBtn').prop('disabled', false);

        // 버튼에 선택 정보 저장 (또는 외부 input에 set)
        $('#selectLeaderBtn').data('selected-id', stuId);
        $('#selectLeaderBtn').data('selected-name', stuName);
    });
    $('#studentSelectModalLeader').on('hidden.bs.modal', function () {
        $('.modal-backdrop').remove();
        $('body').removeClass('modal-open');
    });
    // "선택 완료" 버튼 클릭 시 폼에 반영
    $('#selectLeaderBtn').on('click', function () {
        const stuId = $(this).data('selected-id');
        const stuName = $(this).data('selected-name');

        if (stuId && stuName) {
            $('input[name="team_leader"]').val(stuId);
            // id가 teamLeaderName인 input에 값 넣기
            $('#teamLeaderName').val(stuName);
        }

        $('#studentSelectModalLeader').modal('hide');
        $('.modal-backdrop').remove();
        $('body').removeClass('modal-open');
    });
    
    $('#leaderSearchInput').on('keyup', function() {
        const search = $(this).val().toLowerCase();
        let hasMatch = false;

        $('#leaderStudentList .leader-student-item').each(function() {
            const name = $(this).data('stu-name').toLowerCase();
            const isVisible = name.includes(search);
            $(this).toggle(isVisible);
            if (isVisible) hasMatch = true;
        });

        // 기존 메시지 제거
        $('#noResultMessage').remove();

        // 결과 없을 경우 메시지 추가
        if (!hasMatch) {
            $('#leaderStudentList').append(
                `<li class="list-group-item text-center text-muted" id="noResultMessage">
                    일치하는 팀장이 없습니다.
                </li>`
            );
        }
    });
});
$('#selectLeaderBtn').on('click', function () {
    const stuId = $(this).data('selected-id');
    const stuName = $(this).data('selected-name');

    if (stuId && stuName) {
        if (window.setTeamLeader) {
            window.setTeamLeader(stuId, stuName); // 수정폼에 바로 값 전달
        }
    }
    $('#studentSelectModalLeader').modal('hide');
});
</script>