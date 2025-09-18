<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>    
<style>
  html, body {
    margin: 0;
    padding: 0;
    overflow-x: hidden;
    height: 100%;
  }
</style>
<body>
<div class="wrap" style="height:100vh;">
    <div class="card-header" style="border-bottom: none;">
        <h3 class="card-title ml-2 mt-2" style="font-size: 25px; font-weight: bold;">
            결과물 : <span>${projectList[0].project_name }</span>
        </h3>
    </div>
    <div class="row" style="margin-top:40px;">
        <div class="col-8" style="margin-left:40px;">
            <h3>${roadMap.rm_name }</h3>
        </div>
    </div>
    <div class="row mb-2" style="margin-left:40px; line-height:30px;">
        <div class="col-10 mt-1">
            <span>${roadMap.rm_category } | 작성자 : ${mem_name } | 
            <fmt:formatDate value="${roadMap.rm_regdate }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
        </div>
        <div class="col-1 mb-1 mt-1">
                <button type="button" 
            onclick="location.href='<%=request.getContextPath() %>/roadmap/list/stu?project_id=${projectList[0].project_id }'; 
                     window.parent.location.hash='<%=request.getContextPath() %>/roadmap/list/stu?project_id=${projectList[0].project_id }';"  
            style="background-color:#2ec4b6; border-radius:5px; width:90px; height:35px; border:none; font-weight:bold; font-size:17px; color:#ffffff; margin-left:55px;">
        목록
    </button>
        </div>
    </div>
    <div class="row">
        <div class="col-11" style="width:100%; height:250px; border-top:3px solid #aaaaaa; margin-left:50px;">     
            ${roadMap.rm_content }
        </div>
    </div>
    <div class="row">
        <div class="col-5">
            <label style="margin-left:40px;"><span style="font-weight:bold;">첨부파일</span></label>
        </div>
    </div>
    <div class="row">
        <div class="col-11" style="width:100%; border-top:3px solid #aaaaaa; margin-left:49px;">    
            <c:forEach items="${rdm.attachList }" var="attach">
                <div class="col-sm-4 file-select" style="cursor:pointer;"
                     onclick="location.href='getFile?ano=${attach.ano }';">
                    <div style="width:100%;">    
                        <i class="fa file-icon"></i>
                        <span class="selectedfile">${attach.fileName.split('\\$\\$')[1]}</span>
                    </div>
                </div>
            </c:forEach>                
        </div>
    </div>

    <label style="margin-left:40px; margin-top:40px;"><span style="font-weight:bold;">피드백</span></label>
<div class="row d-flex" style="border-top:3px solid #2ec4b6; width:1179px; margin-left:47px; align-items:center;">
    <c:if test="${not empty eval}">
        <c:forEach items="${eval}" var="e">
            <div class="col-3 d-flex" style="width:50%; align-items:flex-start; gap:10px; margin-bottom:10px;">
                <img src="<%=request.getContextPath() %>/member/getPicture?id=${e.profes_id}" 
                     class="img-circle img-md" alt="User Image" 
                     style="width:45px; height:45px; object-fit:cover; margin-top:5px;"/>
                <div style="display:flex; flex-direction:column; width:100%;">
                    <div style="font-size:17px; display:flex; align-items:center;">
                        <h4 style="font-size:20px; margin:0; display:inline-block;">
                            ${evalProfessorNames[e.profes_id]}
                        </h4>
                        &nbsp;&nbsp;평가 점수: ${e.eval_score}
                        <c:if test="${e.profes_id == loginUser.mem_id}">
                            <form action="${pageContext.request.contextPath}/roadmap/evaluation/remove" method="post" style="display:inline; margin-left:5px;">
                                <input type="hidden" name="eval_id" value="${e.eval_id}">
                                <input type="hidden" name="rm_id" value="${roadMap.rm_id}">
                                
                            </form>
                        </c:if>
                    </div>
                    <div style="margin-top:5px;">${e.eval_content}</div>
                </div>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${empty eval}">
        <div style="margin-left:480px; margin-top:10px; color:#999; width:307px; margin-top:30px;">
            등록된 평가가 없습니다.
        </div>
    </c:if>

   <div class="d-flex ml-auto" style="width:auto;">
    <sec:authorize access='hasAnyRole("ROLE_ROLE02")'>
        <c:set var="myEvalExists" value="false" />
<c:set var="myEvalId" value="" />
<c:forEach items="${eval}" var="e">
    <c:if test="${e.profes_id == loginUser.mem_id}">
        <c:set var="myEvalExists" value="true" />
        <c:set var="myEvalId" value="${e.eval_id}" />
    </c:if>
</c:forEach>

<c:choose>
    <c:when test="${myEvalExists and not empty myEvalId}">
        <button type="button" 
                onclick="OpenWindow('${pageContext.request.contextPath}/roadmap/evaluation/modify?rm_id=${roadMap.rm_id }&profes_id=${loginUser.mem_id}&eval_id=${myEvalId}','평가 수정',700,800)"
                style="background-color:#2ec4b6; border-radius:5px; width:90px; height:35px; margin-right:20px; border:none; font-weight:bold; font-size:17px; color:#ffffff;">
            수정
        </button>
    </c:when>
    <c:otherwise>
        <button type="button" 
                onclick="OpenWindow('${pageContext.request.contextPath}/roadmap/evaluation/regist?rm_id=${roadMap.rm_id }','평가',700,800)"
                style="background-color:#2ec4b6; border-radius:5px; width:90px; height:35px;  border:none; font-weight:bold; font-size:17px; color:#ffffff;">
            평가
        </button>
    </c:otherwise>
</c:choose>
    </sec:authorize>
</div>
</div>
</div>

<script>
function remove(){
    if(!confirm("로드맵을 삭제하시겠습니까?")) return;
    location.href="remove?rm_id=${roadMap.rm_id}&project_id=${roadMap.project_id}";
}
</script>
</body>