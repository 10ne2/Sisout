<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>제출 완료</title>
<script>
	alert("과제가 성공적으로 제출되었습니다.");
	window.location.href = "${pageContext.request.contextPath}/homework/studentsdetail?hwNo=${hwNo}";
</script>
</head>
<body>
</body>
</html>