<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <script>
    window.onload = function () {
      alert("과제가 수정되었습니다.");
      window.opener.location.reload(); // 부모창 새로고침
      window.close(); // 팝업창 닫기
    }
  </script>
</head>
<body></body>
</html>