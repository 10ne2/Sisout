<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>LECTURE_PLANMAKER PDF Viewer</title>
<style>
  .pdf-wrap {
    width: 100%;
    height: 100vh;
    border: 0;
  }
  .no-data {
    padding: 40px;
    text-align: center;
    font-size: 18px;
    color: #888;
  }
</style>
</head>
<body>

<c:choose>
  <c:when test="${!hasPdf}">
    <div class="no-data">
      LECTURE_PLANMAKER 테이블에 데이터가 없습니다.
    </div>
  </c:when>
  <c:otherwise>
    <iframe
      class="pdf-wrap"
      src="${pageContext.request.contextPath}/resources/pdfjs/web/viewer.html?file=${encodedFileUrl}">
    </iframe>
  </c:otherwise>
</c:choose>

</body>
</html>