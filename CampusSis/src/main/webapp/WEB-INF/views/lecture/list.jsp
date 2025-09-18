<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>강의계획서</title>
<style>
  :root { --border: #aaa; }
  body { font-family:"Noto Sans KR",sans-serif; background:#fafafa; margin:0; min-height:100vh; }
  .center-wrap { min-height:100vh; display:flex; align-items:center; justify-content:center; }
  .container { background:#fff; border:1px solid var(--border); border-radius:8px; padding:40px 32px; box-shadow:0 4px 12px rgba(0,0,0,.05); text-align:center; width:420px; }
  h1 { margin:0 0 20px; font-size:22px; color:#333; }
  p.desc { margin:0 0 24px; color:#666; font-size:14px; }
  button { padding:9px 16px; font-size:14px; border:1px solid var(--border); cursor:pointer; border-radius:4px; background:#eee; }
  button.primary { background:#00b894; color:#fff; border-color:#00b894; }
  .pdf-view { max-width:960px; margin:24px auto; padding:16px; background:#fff; border:1px solid #ddd; border-radius:8px; }
  .pdf-page { margin:0 auto 16px; box-shadow:0 2px 8px rgba(0,0,0,.1); border-radius:4px; overflow:hidden; background:#fff; display:block; }
  .toolbar { max-width:960px; margin:12px auto 0; display:flex; gap:8px; align-items:center; justify-content:flex-end; }
  .toolbar input[type="range"] { width:180px; }
</style>
</head>
<body>

<c:choose>
  <%-- PDF가 있는 경우 --%>
<c:when test="${hasPlan}">
  <iframe
    src="${pageContext.request.contextPath}/lecture/list?lec_id=${lec_id}&stream=1"
    style="width:100%;height:90vh;border:0"
  ></iframe>
</c:when>

  <%-- PDF가 없는 경우: 기존 안내 박스 --%>
  <c:otherwise>
    <div class="center-wrap">
      <div class="container">
        <h1>강의계획서가 없습니다.</h1>
        <p class="desc">강의계획서가 현재 등록되어있지 않습니다.</p>
        <sec:authorize access="hasRole('ROLE_ROLE02')">
          <button type="button" class="primary" id="btnGoUpload">등록하기</button>
        </sec:authorize>
      </div>
    </div>
    <script>
      (function(){
        var btn = document.getElementById('btnGoUpload');
        if (btn) btn.addEventListener('click', function () {
          window.location.href = '${ctx}/lecture/upload';
        });
      })();
    </script>
  </c:otherwise>
</c:choose>

</body>
</html>
