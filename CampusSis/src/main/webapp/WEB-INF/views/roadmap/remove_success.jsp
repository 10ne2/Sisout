<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	alert("${param.rm_id} 로드맵이 삭제되었습니다.");
	location.href='<%=request.getContextPath() %>/roadmap/list/stu?project_id=${project_id}'; 
        window.parent.location.hash='<%=request.getContextPath() %>/roadmap/list/stu?project_id=${project_id }';
        window.opener.location.reload();

</script>