<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>업로드 성공</title>

<style>
  :root {
    --border: #aaa;
  }
  body {
    font-family: "Noto Sans KR", sans-serif;
    background: #fafafa;
    margin: 0;
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .container {
    background: #fff;
    border: 1px solid var(--border);
    border-radius: 8px;
    padding: 40px 32px;
    box-shadow: 0 4px 12px rgba(0,0,0,.05);
    text-align: center;
    width: 420px;
  }
  h1 {
    margin: 0 0 20px;
    font-size: 22px;
    color: #333;
  }
  p.desc {
    margin: 0 0 24px;
    color: #666;
    font-size: 14px;
  }
  button {
    padding: 9px 16px;
    font-size: 14px;
    border: 1px solid var(--border);
    cursor: pointer;
    border-radius: 4px;
    background: #eee;
  }
  button.primary {
    background: #00b894;   /* 첨부 HTML의 [완료] 버튼 색상 */
    color: #fff;
    border-color: #00b894;
  }
</style>
</head>
<body>
  <div class="container">
    <h1>업로드에 성공하였습니다.</h1>
    <p class="desc">강의계획서 업로드에 성공했습니다.</p>
    <button type="button" class="primary" id="btnGoUpload">목록</button>
  </div>

<script>
  document.getElementById('btnGoUpload').addEventListener('click', function () {
    // LectureUplaodController.java 의 /lecture/upload 로 이동
    window.location.href = '${ctx}/lecture/upload';
  });
</script>
</body>
</html>
