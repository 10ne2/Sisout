<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 목록</title>
    <style>
/* 레이아웃/배경 */
html, body{
  margin:0; padding:0; width:100%; height:100%;
  box-sizing:border-box; background:#f9fbfc;
}
.wrapper{ display:flex; width:100%; height:100%; }
.content-wrapper{
  flex:1; padding:40px; background:#f9fbfc;
  max-width:100% !important; width:100% !important; margin:0 !important; box-sizing:border-box;
}
.content-wrapper > *{ max-width:100% !important; width:100% !important; margin:0 !important; }

/* 상단 바 */
.top-bar{ display:flex; justify-content:space-between; align-items:center; margin-bottom:10px; width:100%; }
.top-bar .top-left{ flex:1; }
.top-bar .top-right{ display:flex; justify-content:flex-end; align-items:center; gap:10px; }

/* 버튼 */
.btn{ padding:6px 12px; background:#2ac1bc; color:#fff; border:none; border-radius:4px; cursor:pointer; }
.btn:hover{ background:#1aa6a1; }
.write-btn{ text-align:right; margin-top:20px; }

/* 테이블 */
table{ width:100%; border-collapse:collapse; margin-bottom:60px; border-top:1px solid #e6eaee; table-layout:fixed; }
th, td{
  padding:12px 10px; border-bottom:1px solid #e6eaee; text-align:center;
  white-space:nowrap; overflow:hidden; text-overflow:ellipsis;
}
th{ font-weight:700; }
td:nth-child(3){ text-align:left; } /* 제목 칸은 좌측 정렬 */

/* 행 전체 클릭 & hover 효과 */
tbody tr.row-link{ cursor:pointer; transition:background 120ms ease; }
tbody tr.row-link:hover{ background:#f5f7fa; }
tbody tr.row-link:active{ background:#eef3f7; }

/* 제목 링크를 검정/밑줄없음으로 통일 */
td a{
  color:#111 !important;
  text-decoration:none !important;
}
td a:hover{ text-decoration:none; }

/* 페이지네이션 */
.pagination{ display:flex; justify-content:center; gap:8px; margin-top:20px; padding-top:40px; }
.pagination a{
  padding:6px 12px; text-decoration:none; border:1px solid #ddd; border-radius:4px; color:#333;
}
.pagination a.active{ background:#2ac1bc; color:#fff; font-weight:700; border-color:#2ac1bc; }
.pagination a.disabled{ pointer-events:none; opacity:.5; }
    </style>
    <script>
      function submitCategoryForm(){ document.getElementById('filterForm').submit(); }
      function openWritePopup(){
        window.open(
          "${pageContext.request.contextPath}/board/write",
          "popupWrite",
          "width=800,height=600,scrollbars=yes,resizable=yes"
        );
      }
      function goToDetail(boardId){
        location.href='${pageContext.request.contextPath}/board/detail?bno=' + boardId;
      }
    </script>
</head>
<body>

<div class="wrapper">
  <div class="content-wrapper">
    <h1>게시판</h1>

    <form id="filterForm" method="get" action="${pageContext.request.contextPath}/boardlist">
      <div class="top-bar">
        <div class="top-left">
          <select name="category" onchange="submitCategoryForm()">
            <option value="">전체</option>
            <option value="공지" ${category == '공지' ? 'selected' : ''}>공지</option>
            <option value="자유" ${category == '자유' ? 'selected' : ''}>자유</option>
            <option value="토론" ${category == '토론' ? 'selected' : ''}>토론</option>
            <option value="시험" ${category == '시험' ? 'selected' : ''}>시험</option>
          </select>
        </div>
        <div class="top-right">
          <select name="searchType">
            <option value="">전체</option>
            <option value="title"  ${searchType == 'title'  ? 'selected' : ''}>제목</option>
            <option value="writer" ${searchType == 'writer' ? 'selected' : ''}>작성자</option>
            <option value="content"${searchType == 'content'? 'selected' : ''}>내용</option>
          </select>
          <input type="text" name="keyword" placeholder="검색어를 입력해 주세요." value="${keyword}"/>
          <button type="submit" class="btn">검색</button>
        </div>
      </div>
    </form>

    <table>
      <thead>
        <tr>
          <th>번호</th>
          <th>말머리</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일</th>
        </tr>
      </thead>
      <tbody>
        <c:if test="${empty boardList}">
          <tr><td colspan="5">게시글이 없습니다.</td></tr>
        </c:if>

        <c:forEach var="board" items="${boardList}" varStatus="status">
          <tr class="row-link" onclick="goToDetail(${board.boardId})">
            <td>${pageMaker.totalCount - ((pageMaker.page - 1) * pageMaker.perPageNum + status.index)}</td>
            <td>${board.boardCat}</td>
            <td>
              <a href="${pageContext.request.contextPath}/board/detail?bno=${board.boardId}">
                ${board.boardName}
              </a>
              <c:if test="${not empty board.pfileName}">
                <i class="fas fa-paperclip" title="${board.pfileName}"></i>
              </c:if>
            </td>
            <td>${board.memName}</td>
            <td><fmt:formatDate value="${board.boardDate}" pattern="yyyy-MM-dd"/></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

    <div class="pagination">
      <!-- 이전 -->
      <c:choose>
        <c:when test="${page == 1}">
          <a class="disabled" href="#">&lt;</a>
        </c:when>
        <c:otherwise>
          <c:url var="prevUrl" value="/boardlist">
            <c:param name="page" value="${page - 1}"/>
            <c:param name="searchType" value="${pageMaker.searchType}"/>
            <c:param name="keyword" value="${pageMaker.keyword}"/>
            <c:param name="category" value="${pageMaker.category}"/>
            <c:param name="hideMine" value="${hideMine}"/>
          </c:url>
          <a href="${prevUrl}">&lt;</a>
        </c:otherwise>
      </c:choose>

      <!-- 페이지 번호 -->
      <c:forEach var="i" begin="1" end="${totalPage}">
        <c:url var="pageUrl" value="/boardlist">
          <c:param name="page" value="${i}"/>
          <c:param name="searchType" value="${pageMaker.searchType}"/>
          <c:param name="keyword" value="${pageMaker.keyword}"/>
          <c:param name="category" value="${pageMaker.category}"/>
          <c:param name="hideMine" value="${hideMine}"/>
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

      <!-- 다음 -->
      <c:choose>
        <c:when test="${page == totalPage}">
          <a class="disabled" href="#">&gt;</a>
        </c:when>
        <c:otherwise>
          <c:url var="nextUrl" value="/boardlist">
            <c:param name="page" value="${page + 1}"/>
            <c:param name="searchType" value="${pageMaker.searchType}"/>
            <c:param name="keyword" value="${pageMaker.keyword}"/>
            <c:param name="category" value="${pageMaker.category}"/>
            <c:param name="hideMine" value="${hideMine}"/>
          </c:url>
          <a href="${nextUrl}">&gt;</a>
        </c:otherwise>
      </c:choose>
    </div>

    <div class="write-btn">
      <button type="button" class="btn btn-primary" onclick="openWritePopup()">작성하기</button>
    </div>
  </div>
</div>

</body>
</html>