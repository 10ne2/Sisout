<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="professorSelectModal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">담당교수 선택</h5>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      <div class="modal-body">
        <!-- 검색 입력 -->
        <input type="text" id="professorSearchInput" placeholder="교수 이름 검색" class="form-control mb-3">
        <!-- 학생 리스트 (예: 체크박스 없이 클릭 선택) -->
        <ul id="professorList" class="list-group" style="max-height:300px; overflow-y:auto;">
           <c:forEach var="professor" items="${professorList}">
    <li class="list-group-item professor-item" 
    	data-pro-picture="${professor.picture }"
        data-pro-id="${professor.mem_id}" 
        data-pro-name="${professor.mem_name}"
        style="cursor:pointer;">
        <img src="<%=request.getContextPath() %>/member/getPicture?id=${professor.mem_id}" class="img-circle img-md" alt="User Image" style="width:45px; height:45px; object-fit:cover;">
      ${professor.mem_name} (${professor.mem_id})
    </li>
  </c:forEach>
        </ul>
      </div>
      <div class="modal-footer">
        <button type="button" id="selectprofessorBtn" class="btn btn-primary"
        style="background-color:#2ec4b6; border-radius:5px; width:150px; height:40px; border:none; font-weight:bold;"
         disabled>선택 완료</button>
      </div>
    </div>
  </div>
</div>
<script>
$(document).ready(function() {
    // 교수 리스트 클릭 시 (단일 선택)
    $(document).on('click', '#professorList .professor-item', function() {
        $('#professorList .professor-item').removeClass('active');
        $(this).addClass('active');
        $('#selectprofessorBtn').prop('disabled', false);
    });

    // 선택 완료 버튼 클릭 시
    $('#selectprofessorBtn').on('click', function() {
        const selectedItem = $('#professorList .professor-item.active');
        const professorId = selectedItem.data('pro-id');
        const professorName = selectedItem.data('pro-name');

        $('#professorName').val(professorName);
        $('#professorId').val(professorId);

        $('#professorSelectModal').modal('hide');
//         $('.modal-backdrop').remove();
//         $('body').removeClass('modal-open');
        selectedItem.removeClass('active');
        $('#selectprofessorBtn').prop('disabled', true);
    });
    $('#professorSelectModal').on('hidden.bs.modal', function () {
        $('.modal-backdrop').remove();
        $('body').removeClass('modal-open');
    });
    // 검색 기능 (실시간 필터링 + 결과 없음 표시)
    $('#professorSearchInput').on('keyup', function() {
        const search = $(this).val().toLowerCase();
        let hasMatch = false;

        $('#professorList .professor-item').each(function() {
            const name = $(this).data('pro-name').toLowerCase();
            const isVisible = name.includes(search);
            $(this).toggle(isVisible);
            if (isVisible) hasMatch = true;
        });

        // 기존 메시지 제거
        $('#noResultMessage').remove();

        // 결과 없을 경우 메시지 추가
        if (!hasMatch) {
            $('#professorList').append(
                `<li class="list-group-item text-center text-muted" id="noResultMessage">
                    일치하는 교수가 없습니다.
                </li>`
            );
        }
    });
});
</script>
