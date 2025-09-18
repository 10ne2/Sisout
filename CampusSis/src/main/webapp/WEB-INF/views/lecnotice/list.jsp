<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의 공지사항</title>
<style>
html, body {
	margin: 0;
	padding: 0;
	width: 100%;
	height: 100%;
	box-sizing: border-box;
	background-color: #f9fbfc;
}

.wrapper {
	display: flex;
	width: 100%;
	height: 100%;
}

.content-wrapper {
	flex: 1;
	padding: 40px;
	background-color: #f9fbfc;
	max-width: 100% !important;
	width: 100% !important;
	margin: 0 !important;
	box-sizing: border-box;
}

.content-wrapper>* {
	max-width: 100% !important;
	width: 100% !important;
	margin: 0 !important;
}

table {
	margin-bottom: 60px;
	width: 100%;
	table-layout: fixed;
	border-collapse: collapse;
}

th, td {
	padding: 10px;
	border-bottom: 1px solid #ddd;
	text-align: center;
	box-sizing: border-box;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

th:nth-child(1), td:nth-child(1) { width: 8%; }
th:nth-child(2), td:nth-child(2) { width: 54%; }
th:nth-child(3), td:nth-child(3) { width: 12%; }
th:nth-child(4), td:nth-child(4) { width: 18%; }
th:nth-child(5), td:nth-child(5) { width: 8%; }

.top-bar {
	display: flex;
	align-items: center;
	justify-content: flex-end;
	gap: 10px;
	margin-bottom: 10px;
	width: 100%;
}

.top-bar select { min-width: 80px; }
.top-bar input[type="text"] { flex: 0 1 260px; }

.btn {
	padding: 6px 12px;
	background-color: #2ac1bc;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

/* .btn:hover { background-color: #1aa6a1; } */

.write-btn { text-align: right; margin-top: 20px; }

.pagination {
	display: flex;
	justify-content: center;
	gap: 8px;
	margin-top: 20px;
	padding-top: 40px;
}
.pagination a {
	padding: 6px 12px;
	text-decoration: none;
	border: 1px solid #ddd;
	border-radius: 4px;
	color: #333;
}
.pagination a.active { background-color: #2ac1bc; color: white; font-weight: bold; }
.pagination a.disabled { pointer-events: none; opacity: 0.5; }

/* 제목 옆 클립 아이콘 */
.clip-icon { width: 14px; height: 14px; vertical-align: middle; margin-left: 5px; }
.title-text { vertical-align: middle; }

/* ✅ 행 hover(은은한 검정톤) */
tbody tr.hoverable{
	cursor: pointer;
	transition: background 120ms ease;
}
tbody tr.hoverable:hover{
	background: rgba(0,0,0,0.04);
}
tbody tr.hoverable:active{
	background: rgba(0,0,0,0.06);
}
</style>
<script>
	function openWritePopup() {
		window.open("${pageContext.request.contextPath}/lecnotice/write",
				"popupWrite",
				"width=800,height=600,scrollbars=yes,resizable=yes");
	}
	function goToDetail(id) {
		location.href = '${pageContext.request.contextPath}/lecnotice/detail?lecNoticeId=' + id;
	}
</script>
</head>
<body>
	<%-- 로그인 유저(세션) --%>
	<c:set var="user" value="${sessionScope.loginUser}" />
	<c:set var="isProfessor" value="${user != null && user.mem_auth eq 'ROLE02'}" />

	<div class="wrapper">
		<div class="content-wrapper">
			<h1>강의 공지사항</h1>

			<form method="get" action="${pageContext.request.contextPath}/lecnotice/list">
				<div class="top-bar">
					<select name="searchType">
						<option value="" <c:if test="${empty searchType}">selected</c:if>>전체</option>
						<option value="title"  <c:if test="${searchType == 'title'}">selected</c:if>>제목</option>
						<option value="writer" <c:if test="${searchType == 'writer'}">selected</c:if>>작성자</option>
						<option value="content"<c:if test="${searchType == 'content'}">selected</c:if>>내용</option>
					</select>
					<input type="text" name="keyword" placeholder="검색어 입력" value="${keyword}" />
					<button type="submit" class="btn">검색</button>
				</div>
			</form>

			<table>
				<thead>
					<tr>
						<th>No</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty lecNoticeList}">
						<tr>
							<td colspan="5">등록된 공지사항이 없습니다.</td>
						</tr>
					</c:if>

					<c:forEach var="notice" items="${lecNoticeList}" varStatus="status">
						<tr class="hoverable" onclick="goToDetail('${notice.lecNoticeId}')" style="cursor: pointer;">
							<td>${pageMaker.totalCount - ((page - 1) * pageMaker.perPageNum + status.index)}</td>

							<td style="text-align: left;">
								<span class="title-text">${notice.lecNoticeName}</span>
								<c:set var="hasAttach"
									value="${
										(notice.fileName ne 'none.pdf' && not empty notice.fileName)
										or (notice.fileDetail ne 'none.pdf' && not empty notice.fileDetail)
									}" />
								<c:if test="${hasAttach}">
									<img src="${pageContext.request.contextPath}/resources/images/clip.png"
									     alt="첨부" class="clip-icon">
								</c:if>
							</td>

							<td>${notice.profesId}</td>
							<td><fmt:formatDate value="${notice.lecNoticeDate}" pattern="yyyy-MM-dd" /></td>
							<td>${notice.viewCnt}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<!-- ▼ 페이지네이션: 그대로 유지 ▼ -->
			<div class="pagination">
				<!-- 이전 버튼 -->
				<c:choose>
					<c:when test="${page == 1}">
						<a class="disabled" href="#">&lt;</a>
					</c:when>
					<c:otherwise>
						<c:url var="prevUrl" value="/lecnotice/list">
							<c:param name="page" value="${page - 1}" />
							<c:param name="searchType" value="${searchType}" />
							<c:param name="keyword" value="${keyword}" />
						</c:url>
						<a href="${prevUrl}">&lt;</a>
					</c:otherwise>
				</c:choose>

				<!-- 페이지 번호 -->
				<c:forEach var="i" begin="1" end="${totalPage}">
					<c:url var="pageUrl" value="/lecnotice/list">
						<c:param name="page" value="${i}" />
						<c:param name="searchType" value="${searchType}" />
						<c:param name="keyword" value="${keyword}" />
					</c:url>
					<c:choose>
						<c:when test="${i == page}">
							<a href="${pageUrl}" class="active">${i}</a>
						</c:when>
						<c:otherwise>
							<a href="${pageUrl}">${i}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<!-- 다음 버튼 -->
				<c:choose>
					<c:when test="${page == totalPage}">
						<a class="disabled" href="#">&gt;</a>
					</c:when>
					<c:otherwise>
						<c:url var="nextUrl" value="/lecnotice/list">
							<c:param name="page" value="${page + 1}" />
							<c:param name="searchType" value="${searchType}" />
							<c:param name="keyword" value="${keyword}" />
						</c:url>
						<a href="${nextUrl}">&gt;</a>
					</c:otherwise>
				</c:choose>
			</div>
			<!-- ▲ 페이지네이션: 그대로 유지 ▲ -->

			<div class="write-btn">
				<c:if test="${isProfessor}">
					<button type="button" class="btn" onclick="openWritePopup()">작성하기</button>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>