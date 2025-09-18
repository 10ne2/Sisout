<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script>
alert("로드맵이 등록되었습니다.");

// 글 목록 페이지로 이동하면서 새로고침 (예: /project/list 로 이동)
location.href = "<%=request.getContextPath() %>/roadmap/list/stu?project_id=${project_id }";
</script>