<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:authorize access="hasRole('ROLE_ROLE01')">
<script>
alert("프로젝트가 등록되었습니다.");

// 글 목록 페이지로 이동하면서 새로고침 (예: /project/list 로 이동)
location.href = "<%=request.getContextPath() %>/project/list/stu?mem_id=${loginUser.mem_id }";
</script>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ROLE02')">
<script>
alert("프로젝트가 등록되었습니다.");

// 글 목록 페이지로 이동하면서 새로고침 (예: /project/list 로 이동)
location.href = "<%=request.getContextPath() %>/project/list/pro?mem_id=${loginUser.mem_id }";
</script>
</sec:authorize>