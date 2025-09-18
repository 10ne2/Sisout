<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
  html, body {
    margin: 0;
    padding: 0;
    overflow-x: hidden;
    height: 100%;
  }
</style>
<body>
<jsp:useBean id="today" class="java.util.Date" />
<fmt:formatDate var="todayFormatted" value="${today}" pattern="yyyy-MM-dd" />
<div class="wrap" style="height:100vh;">
		<div class="card-header" style="border-bottom: none;">
  <h3 class="card-title ml-2 mt-2" style="font-size: 25px; font-weight: bold;">팀 목록</h3>
  <div class="card-tools">
   <a class="btn btn-primary btn-lg mt-2 mr-3 d-flex align-items-center justify-content-center"  href="<%=request.getContextPath() %>/project/regist"
   style="background-color:#2ec4b6; border:none; width:100px; height:40px;">
  <span style="color: #ffffff; font-size:18px;">팀 등록</span>
</a>
  </div>
</div>
<form action="${pageContext.request.contextPath}/project/list/stu" method="get">

<div class="mx-auto d-flex p-2 align-items-center" style="border: 1px solid #ced4da; font-size: 15px; width:96%; border-top: 2px solid #2ec4b6;">
<div style="display: flex; align-items: center; height: 50px;width:40px; margin-left:20px;">
  <label style="margin: 0;">학기</label>
</div>
<div style="width:80px;">
  <select name="samester" style="width:70px; border: 1px solid #ced4da;">
    <option value="" ${selectedSamester == null ? "selected" : ""}>전체</option>
    <option value="1학기" ${selectedSamester == '1학기' ? "selected" : ""}>1학기</option>
    <option value="2학기" ${selectedSamester == '2학기' ? "selected" : ""}>2학기</option>
  </select>
 </div>
  <button type="submit" style="width:50px; background-color:#ffffff; border:2px solid #2ec4b6;border-radius:5px; color:#2ec4b6; font-weight:bold;">적용</button>
<div class="ml-3">
<label style="margin: 0;">기간</label>
</div>
<div class="input-group date mt-2 mb-2 ml-3" id="datetimepicker1" data-target-input="nearest" style="width: 130px;">
  <input type="text" name="project_stdate" class="form-control datetimepicker-input" data-target="#datetimepicker1"
         style="width: 90px; height: 26px; font-size: 13px; padding: 4px 6px;" value="${project_stdate != null ? project_stdate : ''}">
  <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
    <div class="input-group-text" style="padding: 4px 6px;"><i class="far fa-minical"></i></div>
  </div>
</div>
<span class="ml-3"> ~ </span>
<div class="input-group date mt-2 mb-2 ml-3" id="datetimepicker2" data-target-input="nearest" style="width: 130px;">
  <input type="text" name="project_endate" class="form-control datetimepicker-input" data-target="#datetimepicker2"
         style="width: 90px;height: 26px; font-size: 13px; padding: 4px 6px;" value="${project_endate != null ? project_endate : ''}">
  <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
    <div class="input-group-text" style="padding: 4px 6px;"><i class="far fa-minical"></i></div>
  </div>

</div>
<button type="submit" style="margin-left:20px;width:50px; background-color:#ffffff; border:2px solid #2ec4b6;border-radius:5px; color:#2ec4b6; font-weight:bold;">적용</button>

<div class="input-group input-group-sm ml-auto" style="max-width:300px; width:auto;">
  <input id="project_name" name="project_name" class="form-control" type="search" value="${project_name }" placeholder="프로젝트명을 입력해주세요." aria-label="Search"
         style="height: 35px; font-size: 13px; padding: 4px 6px; line-height: 1.2;">
  <div class="input-group-append">
    <button class="btn btn-navbar" type="submit" style="padding: 2px 10px;">
                <i class="fas fa-cpsearch ml-1"></i>
              </button>
              </form>
  </div>
</div>
</div>
<div class= "d-flex" style="width:100%;">
				   <c:if test="${empty projectList }">
				   	   <div class="row">
				   	   <div class="col-4"></div>
				   	   <div class="col-4" style="width:1600px; margin-top:150px; text-aline:center;">
				   	   		<span style="font-size:25px; font-weight:bold; color:#e0e0e0;">해당 내용이 없습니다.</span>
				   	   		</div>
				   	   		<div class="col-4"></div>
				   	   </div>
				   	 
				   </c:if>	
		<c:forEach items="${projectList}" var="stulist">
  <fmt:formatDate var="endDateStr" value="${stulist.project_endate}" pattern="yyyy-MM-dd" />
  <fmt:formatDate var="todayStr" value="${today}" pattern="yyyy-MM-dd" />
  <c:choose>
    <c:when test="${endDateStr lt todayStr}">
              <div class="card card-primary" style="max-height:530px;min-width: 20%; width:30%; margin:30px 19px 10px 26px; box-shadow: none;border: 2px solid #E7E7E7;  border-radius: 0 !important;">
              <div class="card-header d-flex align-items-center justify-content-between" 
              style="background-color:#f5f5f5; height:60px;border:none;box-shadow: none; border-radius: 0 !important;">
                
                <h3 class="card-title" style="font-size:18px; width:120px;color:#707070;">2025-${stulist.samester }</h3>

                <div class="card-tools d-flex justify-content-end align-items-center" style="width:300px;">
                <span style="font-weight:bold; color:#707070;">${stulist.project_name }</span>	
                </div>
              </div>
              <div class="card-body p-0" style="width: 100%; height:450px;">
				<div class="row text-center p-2 mt-2">
				<div class="col-6">
				<h2 style="font-size:16px; font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;시작기한</h2>
				</div>
				<div class="col-6">
				<h2 style="font-size:16px; font-weight:bold;">마감기한&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h2>
				</div>
				</div>
				<div class="row text-center">
				<div class="col-6">
				<span style="font-size:18px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${stulist.project_stdate }" pattern="yyyy-MM-dd"/></span>
				</div>
				<div class="col-6">
				<span style="font-size:18px;"><fmt:formatDate value="${stulist.project_endate }" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</div>
				</div>
				<div class="row">
				<div class="col-10 mx-auto">
  <hr>
</div>
				</div>
				<div class="row p-2">
				<div class="col-12 pl-5">
				<h3 style="font-size:16px; font-weight:bold;">금주 제출현황</h3>
				</div>
				</div>
				<div class="row p-1">
				<div class="col-6 pl-5">
				<span>&nbsp;로드맵</span></div>
				<div class="col-5 d-flex justify-content-end">
				<span class=""style="font-weight:bold; color:#2ec4b6;">${empty stulist.rm_status ? "미제출" : stulist.rm_status}</span></div>
				</div>
				<div class="row p-1">
				<div class="col-6 pl-5">
				<span>&nbsp;피드백</span></div>
				<div class="col-5 d-flex justify-content-end">
				<span class="" style="font-weight:bold; color:#a6a6a6;"> ${empty eval_status ? "미등록" : eval_status}</span></div></div>
				
				<div class="row mx-0">
				<div class="col-12 px-0">
					<hr style="height: 1px; background-color: #E7E7E7; border: none; width: 100%;">
					</div>
					</div>
				<div class="row pb-1 pl-1">
				<div class="col-6 pl-5">
				<h3 style="font-size:16px; font-weight:bold; ">담당 교수</h3>
				</div>
				<div class="col-6 pr-5 text-right">${stulist.profes_name }</div>
				</div>
				<div class="row pb-1 pl-1">
				<div class="col-6 pl-5">
				<h3 style="font-size:16px; font-weight:bold;">팀장</h3>
				</div>
				<div class="col-6 pr-5 text-right" >${stulist.leader_name }</div>
				</div>
				<div class="row pl-1 pb-3">
				<div class="col-5 pl-5">
				<h3 style="font-size:16px; font-weight:bold;">팀원</h3>
				</div>
				<div class="col-7 pr-5 text-right">  
					<c:forEach var="member" items="${projectTeamMembersMap[stulist.project_id]}">
   					 ${member}&nbsp;
  					</c:forEach>
 				 </div>
				</div>
				<div class="row pt-2">
				<div class="col-1"></div>
				<div class="col-10">
				<button type="button" class="btn btn-block btn-secondary btn-flat" style="background-color: #e7e7e7; color:#707070; font-weight:bold;" 
				onclick="location.href='<%=request.getContextPath() %>/roadmap/list/stu?project_id=${stulist.project_id }'; window.parent.location.hash='<%=request.getContextPath() %>/roadmap/list/stu?project_id=${stulist.project_id }';"
				>로드맵</button>
				</div>
				<div class="col-1"></div>
				</div>
              </div>
              </div>
              </c:when>
              <c:otherwise>
            <div class="card card-primary" 
            style="min-width: 20%;max-height:530px; width:30%; margin:30px 19px 10px 26px;border: 1px solid #e7e7e7;  box-shadow: none;  border-radius: 0 !important;">
              <div class="card-header d-flex align-items-center justify-content-between" 
              style="background-color:#2ec4b6; height:60px;border:none;box-shadow: none;border-radius: 0 !important;">
                
                <h3 class="card-title" style="font-size:18px; width:120px;">2025-${stulist.samester }</h3>

                <div class="card-tools d-flex justify-content-end align-items-center" style="width:300px;">
                <span style="font-weight:bold; color:#ffffff;">${stulist.project_name }</span>	
                </div>
              </div>
              <div class="card-body p-0" style="width: 100%; height:450px; ;border: 1px solid #e7e7e7;">
				<div class="row text-center p-2 mt-2">
				<div class="col-6">
				<h2 style="font-size:16px; font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;시작기한</h2>
				</div>
				<div class="col-6">
				<h2 style="font-size:16px; font-weight:bold; ">마감기한&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h2>
				</div>
				</div>
				<div class="row text-center">
				<div class="col-6">
				<span style="font-size:18px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${stulist.project_stdate }" pattern="yyyy-MM-dd"/></span>
				</div>
				<div class="col-6">
				<span style="font-size:18px;"><fmt:formatDate value="${stulist.project_endate }" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</div>
				</div>
				<div class="row">
				<div class="col-10 mx-auto">
  <hr>
</div>
				</div>
				<div class="row p-2">
				<div class="col-12 pl-5">
				<h3 style="font-size:16px; font-weight:bold;">금주 제출현황</h3>
				</div>
				</div>
				<div class="row p-1">
				<div class="col-6 pl-5">
				<span>&nbsp;로드맵</span></div>
				<div class="col-5 d-flex justify-content-end">
				<span class="" style="font-weight:bold; color:#2ec4b6;">
  				${empty stulist.rm_status ? "미제출" : stulist.rm_status}
				</span></div>
				</div>
				<div class="row p-1">
				<div class="col-6 pl-5">
				<span>&nbsp;피드백</span></div>
				<div class="col-5 d-flex justify-content-end">
				<span class="" style="font-weight:bold; color:#a6a6a6;">
 				 ${empty eval_status ? "미등록" : eval_status}
				</span></div></div>
				
				<div class="row mx-0">
				<div class="col-12 px-0">
					<hr style="height: 1px; background-color: #E7E7E7; border: none; width: 100%;">
					</div>
					</div>
				<div class="row pb-1 pl-1">
				<div class="col-6 pl-5">
				<h3 style="font-size:16px; font-weight:bold;">담당 교수</h3>
				</div>
				<div class="col-6 pr-5 text-right">${stulist.profes_name }</div>
				</div>
				<div class="row pb-1 pl-1">
				<div class="col-6 pl-5">
				<h3 style="font-size:16px; font-weight:bold;">팀장</h3>
				</div>
				<div class="col-6 pr-5 text-right">${stulist.leader_name }</div>
				</div>
				<div class="row pl-1 pb-3">
				<div class="col-5 pl-5">
				<h3 style="font-size:16px; font-weight:bold;">팀원</h3>
				</div>
				<div class="col-7 pr-5 text-right">
  <c:forEach var="member" items="${projectTeamMembersMap[stulist.project_id]}">
    ${member}&nbsp;
  </c:forEach>
</div>
				</div>
				<div class="row pt-2">
				<div class="col-1"></div>
				<div class="col-10">
<c:choose>
  <c:when test="${fn:contains(projectEditStatusMap[stulist.project_id], '요청중')}">
    <button type="button" class="btn btn-block btn-warning" style="background-color:#9FCEC9; border:1px solid #9fcec9; font-weight:bold; color:#ffffff;" disabled>요청중</button>
  </c:when>
  <c:otherwise>
    <button type="button" class="btn btn-block custom-btn"
      onclick="OpenWindow('${pageContext.request.contextPath}/project/modify/stu?project_id=${stulist.project_id}','수정 요청',700,800)">
      수정 요청
    </button>
  </c:otherwise>
</c:choose>
				</div>
				<div class="col-1"></div>
				</div>
              </div>
              </div>
              </c:otherwise>
              </c:choose>
              </c:forEach>
                       
            </div>
   <div class="row">
   <div class="col-4"></div>
   <!--페이지  -->
   <div class="col-4">
<c:if test="${not empty projectList}">
  <%@ include file="/WEB-INF/views/module/paginationStu.jsp" %>
</c:if></div>
<div class="col-4"></div>
</div>
</div>
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/moment/moment.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>

  <!-- 초기화 스크립트 -->
  <script>
    $(function () {
      $('#datetimepicker1').datetimepicker({
        format: 'YYYY-MM-DD' // 날짜만 (MM/DD/YYYY 형식)
      });
    $('#datetimepicker2').datetimepicker({
        format: 'YYYY-MM-DD'
      });
    });
    </script>
<%--     <jsp:include page="modify.jsp" /> --%>
<script>
$(document).on('show.bs.modal', '.modal', function () {
	  $('body').css('padding-right', '0px');
	});
	$(document).on('hidden.bs.modal', '.modal', function () {
	  $('body').css('padding-right', '');
	});
</script>
<script>
  const semesterSelect = document.getElementById('semesterSelect');
  const keywordInput = document.getElementById('keyword');

  semesterSelect.addEventListener('change', () => {
    keywordInput.value = semesterSelect.value; // 선택한 학기를 keyword에 넣음
  });
</script>

</body>
