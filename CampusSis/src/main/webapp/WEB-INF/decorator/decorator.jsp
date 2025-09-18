<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title><decorator:title default="캠퍼스 시스템" /></title>
    <decorator:head />
</head>
<body>

    <%@ include file="/WEB-INF/views/module/header.jsp" %>

    <decorator:body />

    <%@ include file="/WEB-INF/views/module/footer.jsp" %>

</body>
</html>