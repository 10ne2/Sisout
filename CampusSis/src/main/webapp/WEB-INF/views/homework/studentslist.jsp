<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<jsp:useBean id="now" class="java.util.Date" />

<!-- 사용자 역할 정보 전달 -->
<sec:authorize access="hasAuthority('ROLE_ROLE01')">
	<span id="userRole" data-role="student" style="display: none;"></span>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_ROLE02')">
	<span id="userRole" data-role="professor" style="display: none;"></span>
</sec:authorize>

<!-- Content Header -->
<div class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1 class="m-0">
					과제제출
					<c:if test="${not empty lecName}">
						<span class="text-muted"> &gt; </span>
						<span>${lecName}</span>
					</c:if>
				</h1>
			</div>
		</div>
	</div>
</div>

<!-- Main content -->
<section class="content">
	<div class="container-fluid">

		<!-- 검색창 -->
		<div class="d-flex justify-content-end mb-3">
			<form method="get" class="form-inline">
				<input type="text" name="keyword" class="form-control mr-2"
					placeholder="제목을 입력해주세요" value="${pageMaker.keyword}"> <input
					type="hidden" name="lec_id" value="${pageMaker.lecId}">
				<button type="submit" class="btn btn-primary">
					<i class="fas fa-search" style="color: white;"></i>
				</button>
			</form>
		</div>

		<!-- 과제 목록 테이블 -->
		<div class="card">
			<div class="card-body table-responsive p-0">
				<table class="table table-hover text-center">
					<thead class="thead-light">
						<tr>
							<th>과제번호</th>
							<th>제목</th>
							<th>기간</th>
							<sec:authorize access="hasAuthority('ROLE_ROLE01')">
								<th>제출여부</th>
							</sec:authorize>
							<th>진행</th>
							<th>평가</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="homework" items="${homeworklist}" varStatus="st">
							<tr class="clickable-row" data-hwno="${homework.hwNo}"
								style="cursor: pointer;">
								<!-- ✅ 표시용 번호: 내림차순 계산 -->
								<td>${pageMaker.totalCount - (pageMaker.startRow + st.index) + 1}</td>

								<td>${homework.hwName}</td>
								<td><c:if test="${not empty homework.hwStartDate}">
										<fmt:formatDate value="${homework.hwStartDate}"
											pattern="yyyy-MM-dd HH:mm" />
									</c:if> ~ <c:if test="${not empty homework.hwEndDate}">
										<fmt:formatDate value="${homework.hwEndDate}"
											pattern="yyyy-MM-dd HH:mm" />
									</c:if></td>

								<sec:authorize access="hasAuthority('ROLE_ROLE01')">
									<td><c:choose>
											<c:when test="${submitStatusMap[homework.hwNo]}">
												<span class="badge badge-success">제출</span>
											</c:when>
											<c:otherwise>
												<span class="badge badge-danger">미제출</span>
											</c:otherwise>
										</c:choose></td>
								</sec:authorize>

								<td><c:choose>
										<c:when test="${homework.hwEndDate.time lt now.time}">
											<span class="badge badge-secondary">종료</span>
										</c:when>
										<c:otherwise>
											<span class="badge badge-info">진행중</span>
										</c:otherwise>
									</c:choose></td>

								<td><c:choose>
										<c:when test="${feedbackMap[homework.hwNo]}">평가완료</c:when>
										<c:otherwise>미평가</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
			</div>
		</div>

		<!-- 페이징 -->
		<nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center mt-4">
				<c:if test="${pageMaker.prev}">
					<li class="page-item"><a class="page-link"
						href="?page=${pageMaker.startPage - 1}&keyword=${pageMaker.keyword}&lec_id=${pageMaker.lecId}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>

				<c:forEach var="i" begin="${pageMaker.startPage}"
					end="${pageMaker.endPage}">
					<li class="page-item ${i == pageMaker.page ? 'active' : ''}">
						<a class="page-link"
						href="?page=${i}&keyword=${pageMaker.keyword}&lec_id=${pageMaker.lecId}">${i}</a>
					</li>
				</c:forEach>

				<c:if test="${pageMaker.next}">
					<li class="page-item"><a class="page-link"
						href="?page=${pageMaker.endPage + 1}&keyword=${pageMaker.keyword}&lec_id=${pageMaker.lecId}"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
			</ul>
		</nav>
	</div>
</section>

<!-- 하단 작성 버튼 -->
<div class="d-flex justify-content-end mb-3">
	<sec:authorize access="hasAuthority('ROLE_ROLE02')">
		<button type="button" class="btn custom-write-btn floating-write-btn"
			onclick="window.open('${pageContext.request.contextPath}/homework/write','과제작성','width=800,height=600,scrollbars=yes');">
			작성하기</button>
	</sec:authorize>
</div>

<!-- 스타일 -->
<style>
.custom-write-btn {
	background-color: #2EC4B6;
	color: #fff;
	border: none;
}

.custom-write-btn:hover {
	background-color: #22A99C;
}

.form-inline .btn-primary {
	background-color: #2EC4B6;
	border-color: #2EC4B6;
}

.form-inline .btn-primary:hover {
	background-color: #22A99C;
	border-color: #22A99C;
}

.badge-info {
	background-color: #2EC4B6 !important;
	color: #fff;
}

.page-item.active .page-link {
	background-color: #2EC4B6;
	border-color: #2EC4B6;
	color: #fff;
}

.page-link:hover {
	background-color: #22A99C;
	border-color: #22A99C;
	color: #fff;
}
</style>

<!-- 스크립트 -->
<script>
	const contextPath = '${pageContext.request.contextPath}';

	document.addEventListener('DOMContentLoaded', function() {
		const userRoleSpan = document.getElementById('userRole');
		const userRole = userRoleSpan ? userRoleSpan.dataset.role : 'unknown';

		const rows = document.querySelectorAll('.clickable-row');
		rows.forEach(function(row) {
			const hwNo = row.getAttribute('data-hwno');
			const currentPage = ${pageMaker.page};
			const lecId = '${pageMaker.lecId}';

			row.addEventListener('click', function() {
				let targetUrl = '#';
				if (userRole === 'student') {
					targetUrl = contextPath + '/homework/studentsdetail?hwNo='
							+ hwNo + '&page=' + currentPage + '&lec_id='
							+ lecId;
				} else if (userRole === 'professor') {
					targetUrl = contextPath + '/homework/professordetail?hwNo='
							+ hwNo + '&page=' + currentPage + '&lec_id='
							+ lecId;
				}
				window.location.href = targetUrl;
			});
		});
	});
</script>
