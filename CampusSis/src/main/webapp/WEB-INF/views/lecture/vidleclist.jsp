<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page session="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<title>${week} 온라인강의</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/bootstrap/plugins/fontawesome-free/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/bootstrap/dist/css/adminlte.min.css">

<style>
.video-box {
	display: flex;
	border-bottom: 1px solid #ddd;
	padding: 10px 0;
}

.thumbnail {
	width: 180px;
	height: 120px;
	object-fit: cover;
}

.video-info {
	margin-left: 20px;
	flex: 1;
}

.video-title {
	font-size: 18px;
	font-weight: bold;
}

.video-summary {
	font-size: 14px;
	color: #666;
	margin-top: 5px;
}

.status {
	display: inline-block;
	padding: 2px 8px;
	font-size: 12px;
	border-radius: 4px;
	margin-left: 10px;
}

.status.출석 {
	background: #d0f0c0;
	color: #2e7d32;
}

.status.지각 {
	background: #fff4b2;
	color: #f57f17;
}

.status.결석 {
	background: #ffcdd2;
	color: #c62828;
}

.progress-wrap {
	margin-top: 8px;
	width: 100%;
}

.progress-bar {
	height: 20px;
	background: #ddd;
	border-radius: 10px;
	overflow: hidden;
}

.progress-fill {
	height: 100%;
	background: #66cccc;
	text-align: center;
	color: white;
	line-height: 20px;
}

.video-footer {
	display: flex;
	justify-content: space-between;
	margin-top: 5px;
	font-size: 13px;
	color: #888;
}

.btn-modify {
	background-color: #66cccc;
	color: white;
	padding: 6px 12px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

.btn-remove {
	background-color: #cc6666;
	color: white;
	padding: 6px 12px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

.btn-register {
	float: right;
	margin-top: 20px;
	background-color: #66cccc;
	color: white;
	padding: 10px 16px;
	border-radius: 5px;
	text-decoration: none;
}

.no-data {
	text-align: center;
	padding: 80px 0;
	font-size: 24px;
	color: #999;
	font-weight: bold;
}

.pagination {
	justify-content: center;
}
</style>
</head>
<body>

	<div class="container mt-4">
		<h2 class="mb-4">${week} 온라인강의</h2>

		<!-- ✅ 강의 없을 경우 -->
		<c:if test="${empty videoList}">
			<div class="no-data">강의가 없습니다</div>
		</c:if>

		<!-- ✅ 강의 목록 -->
		<c:forEach var="video" items="${videoList}">
			<div class="video-box">
				<img class="thumbnail"
					src="${pageContext.request.contextPath}${video.lecvidThumbnail}"
					alt="썸네일" />
				<div class="video-info">
					<div class="video-title">
						<a
							href="${pageContext.request.contextPath}/lecture/detail?lec_id=${lecId}&lecvid_id=${video.lecvidId}"
							onclick="window.open(this.href, '_blank', 'width=800,height=500'); return false;">
							${video.lecvidName} </a>
						<sec:authorize access="hasRole('ROLE_01')">
							<span class="status ${video.status}">${video.status}</span>
						</sec:authorize>
					</div>
					<div class="video-summary">${video.lecvidDetail}</div>

					<!-- 학생: 진행도 / 마감일 -->
					<sec:authorize access="hasRole('ROLE_ROLE01')">
						<div class="progress-wrap">
							<div class="progress-bar">
								<div class="progress-fill" style="width:${vidprogress}%"> ${vidprogress} % </div>
							</div>
						</div>
						<div class="video-footer">
							<span></span> <span><fmt:formatDate
									value="${video.lecvidDeadline}" pattern="yyyy-MM-dd" /> 까지</span>
						</div>
					</sec:authorize>

					<!-- 교수: 수정 버튼 -->
					<sec:authorize access="hasRole('ROLE_ROLE02')">
						<form method="get"
							action="${pageContext.request.contextPath}/lecture/modify">
							<input type="hidden" name="lec_id" value="${lecId}" /> <input
								type="hidden" name="lecvid_id" value="${video.lecvidId}" />
							<button class="btn-modify" type="submit">수정하기</button>
						</form>
						<form method="get"
							action="${pageContext.request.contextPath}/lecture/remove">
							<input type="hidden" name="lec_id" value="${lecId}" /> <input
								type="hidden" name="lecvid_id" value="${video.lecvidId}" />
							<button class="btn-remove" type="submit">삭제하기</button>
						</form>
					</sec:authorize>
				</div>
			</div>
		</c:forEach>

		<!-- ✅ 등록 버튼 (교수만) -->
		<sec:authorize access="hasRole('ROLE_ROLE02')">
			<div>
				<a
					href="${pageContext.request.contextPath}/lecture/register?lec_id=${lecId}"
					class="btn-register">강의 등록하기</a>
			</div>
		</sec:authorize>

		<!-- ✅ AdminLTE 스타일 페이징 -->
		<div class="mt-4">
			<ul class="pagination">
				<li class="page-item"><a class="page-link" href="#">«</a></li>
				<li class="page-item active"><a class="page-link" href="#">1</a></li>
				<li class="page-item"><a class="page-link" href="#">»</a></li>
			</ul>
		</div>
	</div>

	<!-- AdminLTE 필수 JS -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/dist/js/adminlte.min.js"></script>
	

</body>
</html>
