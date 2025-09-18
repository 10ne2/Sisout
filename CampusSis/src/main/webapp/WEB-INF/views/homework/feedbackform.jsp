<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>과제 피드백 등록</title>
<style>
body {
	font-family: 'Segoe UI', sans-serif;
	background-color: #f4f4f4;
	margin: 40px;
}

.container {
	width: 700px;
	margin: 0 auto;
	background: #fff;
	border: 1px solid #ccc;
	padding: 30px;
	box-sizing: border-box;
}

h2 {
	margin-bottom: 20px;
	color: #0eb3af;
	font-size: 22px;
}

.info-row {
	display: flex;
	margin-bottom: 10px;
	font-size: 15px;
}

.info-row .label {
	width: 80px;
	color: #333;
	font-weight: bold;
}

.info-row .value {
	flex: 1;
	color: #555;
	word-break: break-word;
}

a.file-link {
	color: #0a7ec2;
	text-decoration: underline;
}

textarea {
	width: 100%;
	height: 120px;
	font-size: 14px;
	padding: 10px;
	border: 1px solid #ccc;
	box-sizing: border-box;
	resize: vertical;
	margin-top: 5px;
}

.btn-box {
	text-align: right;
	margin-top: 20px;
}

button {
	padding: 8px 18px;
	font-size: 14px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

button.cancel {
	background-color: #ccc;
	color: #333;
	margin-right: 10px;
}

button.submit {
	background-color: #0eb3af;
	color: #fff;
}
</style>
</head>
<body>
	<div class="container">
		<h2>과제 피드백 등록</h2>

		<form
			action="${pageContext.request.contextPath}/homeworksubmit/feedback"
			method="post">
			<input type="hidden" name="hwsubHsno" value="${submit.hwsubHsno}" />

			<div class="info-row">
				<div class="label">제목:</div>
				<div class="value">${submit.hwsubComment}</div>
			</div>
			<div class="info-row">
				<div class="label">제출자:</div>
				<div class="value">${submit.writer}</div>
			</div>
			<div class="info-row">
				<div class="label">제출일:</div>
				<div class="value">
					<fmt:formatDate value="${submit.submittedAt}"
						pattern="yyyy-MM-dd HH:mm" />
				</div>
			</div>

			<c:if test="${not empty submit.hwsubFilename}">
				<div class="info-row">
					<div class="label">첨부파일:</div>
					<div class="value">
						<c:set var="origName"
							value="${fn:substringAfter(submit.hwsubFilename, '_')}" />
						<a class="file-link"
							href="${pageContext.request.contextPath}/homeworksubmit/download?filename=${submit.hwsubFilename}">
							<c:out
								value="${empty origName ? submit.hwsubFilename : origName}" />
						</a>
					</div>
				</div>
			</c:if>

			<div style="margin-top: 25px;">
				<div>
					<strong>교수 피드백</strong>
				</div>
				<textarea name="hwsubFeedback" required>${submit.hwsubFeedback}</textarea>
			</div>

			<div class="btn-box">
				<button type="button" class="cancel" onclick="window.close()">취소</button>
				<button type="submit" class="submit">등록</button>
			</div>
		</form>
	</div>
</body>
</html>
