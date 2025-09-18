<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="studentSelectModalMembers" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">팀원 선택</h5>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      <div class="modal-body">
        <!-- 검색 입력 -->
        <input type="text" id="membersSearchInput" placeholder="학생 이름 검색" class="form-control mb-3">
        <!-- 학생 리스트 (체크박스 여러 개 가능) -->
        <ul id="membersStudentList" class="list-group" style="max-height:300px; overflow-y:auto;">
           <c:forEach var="student" items="${studentList}">
    <li class="list-group-item member-student-item" 
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
        <button type="button" id="selectMembersBtn" class="btn btn-primary" 
        style="background-color:#2ec4b6; border-radius:5px; width:150px; height:40px; border:none; font-weight:bold;"
        disabled>선택 완료</button>
      </div>
    </div>
  </div>
</div>
<script>
$(document).on('click', '#membersStudentList .member-student-item', function() {
    $(this).toggleClass('active');

    const selectedCount = $('#membersStudentList .member-student-item.active').length;
    $('#selectMembersBtn').prop('disabled', selectedCount === 0);
});

$('#selectMembersBtn').on('click', function() {
    const selectedItems = $('#membersStudentList .member-student-item.active');
    const selectedMembers = [];

    selectedItems.each(function() {
        selectedMembers.push({
            stu_id: $(this).data('stu-id'),
            mem_name: $(this).data('stu-name')
        });
    });

    setTeamMembers(selectedMembers);

    $('#studentSelectModalMembers').modal('hide');
    $('.modal-backdrop').remove();
    $('body').removeClass('modal-open');
    selectedItems.removeClass('active');
    $('#selectMembersBtn').prop('disabled', true);
});
</script>