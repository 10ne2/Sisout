<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

<style>
.clickable-row {
	cursor: pointer;
}

.clickable-row:hover {
	background-color: #f8f9fa;
}

.badge-submitted {
	background-color: #2EC4B6;
	color: #fff;
}

.btn-success {
	background-color: #2EC4B6;
	border-color: #2EC4B6;
	color: #fff;
}

.btn-success:hover {
	background-color: #22A99C;
	border-color: #22A99C;
	color: #fff;
}
</style>

<!-- 목록/삭제에 사용할 URL 미리 구성 -->
<c:url var="backUrl" value="/homework/list">
	<c:param name="lec_id"
		value="${not empty param.lec_id ? param.lec_id : homework.lecId}" />
	<c:if test="${not empty param.page}">
		<c:param name="page" value="${param.page}" />
	</c:if>
	<c:if test="${not empty param.perPageNum}">
		<c:param name="perPageNum" value="${param.perPageNum}" />
	</c:if>
	<c:if test="${not empty param.keyword}">
		<c:param name="keyword" value="${param.keyword}" />
	</c:if>
</c:url>

<c:url var="delUrl" value="/homework/delete">
	<c:param name="hwNo" value="${homework.hwNo}" />
	<c:param name="lec_id"
		value="${not empty param.lec_id ? param.lec_id : homework.lecId}" />
	<c:if test="${not empty param.page}">
		<c:param name="page" value="${param.page}" />
	</c:if>
	<c:if test="${not empty param.perPageNum}">
		<c:param name="perPageNum" value="${param.perPageNum}" />
	</c:if>
	<c:if test="${not empty param.keyword}">
		<c:param name="keyword" value="${param.keyword}" />
	</c:if>
</c:url>

<section class="content-header">
	<div class="container-fluid">
		<h1>과제 제출</h1>
	</div>
</section>

<section class="content">
	<div class="container-fluid">

		<!-- 과제 정보 -->
		<div class="card">
			<div class="card-body">
				<h4>[${homework.hwNo}주차] ${homework.hwName}</h4>

				<div class="text-end mb-2">
					<!-- 수정 팝업 -->
					<button type="button" class="btn btn-warning btn-sm"
						onclick="openEditPopup(${homework.hwNo})">수정</button>

					<!-- 삭제(모달 열기) -->
					<button type="button" class="btn btn-danger btn-sm"
						data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">
						삭제</button>
				</div>

				<p>
					<strong>기간:</strong>
					<fmt:formatDate value="${homework.hwStartDate}"
						pattern="yyyy-MM-dd HH:mm" />
					~
					<fmt:formatDate value="${homework.hwEndDate}"
						pattern="yyyy-MM-dd HH:mm" />
				</p>
				<p>${homework.hwDesc}</p>
			</div>
		</div>

		<!-- 제출 목록 -->
		<div class="card">
			<div class="card-header">
				<h3 class="card-title mb-0">제출 목록</h3>
			</div>
			<div class="card-body">
				<table class="table table-bordered text-center align-middle">
					<colgroup>
						<col style="width: 5%">
						<col style="width: 35%">
						<col style="width: 15%">
						<col style="width: 15%">
						<col style="width: 10%">
						<col style="width: 10%">
					</colgroup>
					<thead class="table-light">
						<tr>
							<th>No.</th>
							<th>제목</th>
							<th>제출자</th>
							<th>제출일자</th>
							<th>첨부파일</th>
							<th>평가여부</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="submit" items="${submitList}" varStatus="loop">
							<tr class="clickable-row"
								onclick="openDetailPopup('${submit.hwsubHsno}')">
								<td>${loop.index + 1}</td>
								<td class="text-center">${submit.hwsubComment}</td>
								<td>${submit.writer}</td>
								<td><fmt:formatDate value="${submit.submittedAt}"
										pattern="yyyy-MM-dd HH:mm" /></td>

								<!-- 첨부파일 -->
								<td onclick="event.stopPropagation();"><c:choose>
										<c:when test="${not empty submit.hwsubFilename}">
											<a
												href="${ctx}/homeworksubmit/download?filename=${submit.hwsubFilename}"
												target="_blank" title="${submit.hwsubFilename}"> <i
												class="fas fa-paperclip"></i>
											</a>
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose></td>

								<!-- 평가여부 -->
								<td><c:choose>
										<c:when test="${not empty submit.hwsubFeedback}">
											<span class="badge badge-submitted">완료</span>
										</c:when>
										<c:otherwise>
											<span class="badge bg-warning">미등록</span>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- 목록 버튼 -->
				<div class="text-end mt-3">
					<a href="${backUrl}" class="btn btn-success">목록</a>
				</div>
			</div>
		</div>
	</div>
</section>

<!-- 삭제 확인 모달 -->
<div class="modal fade" id="deleteConfirmModal" tabindex="-1"
	aria-labelledby="deleteModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header bg-danger text-white">
				<h5 class="modal-title" id="deleteModalLabel">삭제 확인</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal" aria-label="닫기"></button>
			</div>
			<div class="modal-body">
				정말 이 과제를 삭제하시겠습니까?<br> 삭제하면 복구할 수 없습니다.
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">아니오</button>
				<!-- 실제 삭제 이동: 파라미터 보존 -->
				<a class="btn btn-danger" href="${delUrl}">예, 삭제합니다</a>
			</div>
		</div>
	</div>
</div>

<script>
  function openDetailPopup(submitId) {
    const url = "${ctx}/homeworksubmit/detail?submitId=" + submitId;
    window.open(url, "제출 상세보기", "width=600,height=400,scrollbars=yes,resizable=no");
  }

  function openEditPopup(hwNo) {
    const url = "${ctx}/homework/edit?hwNo=" + hwNo;
    window.open(url, "과제수정", "width=800,height=600,scrollbars=yes");
  }
</script>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
