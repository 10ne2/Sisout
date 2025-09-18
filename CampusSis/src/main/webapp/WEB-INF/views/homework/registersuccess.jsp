<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>등록 완료</title>
	<script type="text/javascript">
		alert("과제가 성공적으로 등록되었습니다.");
		if (window.opener) {
			window.opener.location.reload();
		}
		window.close(); 
	</script>
</head>
<body>
</body>
</html>