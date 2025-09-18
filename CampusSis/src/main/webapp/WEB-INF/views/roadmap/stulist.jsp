<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<jsp:useBean id="today" class="java.util.Date" />
<fmt:formatDate var="todayFormatted" value="${today}" pattern="yyyy-MM-dd" />

<div class="wrap" style="height:100vh;">
    <div class="card-header" style="border-bottom: none;">
        <c:if test="${not empty project}">
    <c:set var="projectItem" value="${project[0]}" />
    <h3 class="card-title ml-2 mt-2" style="font-size: 25px; font-weight: bold;">
        결과물 : <span>${projectItem.project_name}</span>
    </h3>
</c:if>
        <div class="card-tools">
        <sec:authorize access='hasAnyRole("ROLE_ROLE01")'>
            <div class="btn btn-primary btn-lg mt-2 mr-3 d-flex align-items-center justify-content-center"
                 onclick="location.href='<%=request.getContextPath() %>/roadmap/regist?project_id=${project[0].project_id }'; 
                          window.parent.location.hash='<%=request.getContextPath() %>/roadmap/regist?project_id=${project[0].project_id }';"
                 style="background-color:#2ec4b6; border:none; width:100px; height:40px;">
                <span style="color: #ffffff; font-size:17px; font-weight:bold;">자료 제출</span>
            </div>
            </sec:authorize>
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/roadmap/list/stu" method="get">
        <input type='hidden' name="project_id" value="${param.project_id }"/>
        <div class="mx-auto d-flex p-2 align-items-center" style="border: 1px solid #ced4da; font-size: 15px; width:96%; border-top: 2px solid #2ec4b6;">
            <div class="ml-3"><label style="margin: 0;">기간</label></div>
            <div class="input-group date mt-2 mb-2 ml-3" id="datetimepicker1" data-target-input="nearest" style="width: 130px;">
                <input type="text" name="rm_stdate" class="form-control datetimepicker-input" data-target="#datetimepicker1"
                       style="width: 90px; height: 26px; font-size: 13px; padding: 4px 6px;" value="${rm_stdate != null ? rm_stdate : ''}">
                <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
                    <div class="input-group-text" style="padding: 4px 6px;"><i class="far fa-minical"></i></div>
                </div>
            </div>
            <span class="ml-3"> ~ </span>
            <div class="input-group date mt-2 mb-2 ml-3" id="datetimepicker2" data-target-input="nearest" style="width: 130px;">
                <input type="text" name="rm_endate" class="form-control datetimepicker-input" data-target="#datetimepicker2"
                       style="width: 90px;height: 26px; font-size: 13px; padding: 4px 6px;" value="${rm_endate != null ? rm_endate : ''}">
                <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
                    <div class="input-group-text" style="padding: 4px 6px;"><i class="far fa-minical"></i></div>
                </div>
            </div>
            <button type="submit" style="margin-left:20px;width:50px; background-color:#ffffff; border:2px solid #2ec4b6;border-radius:5px; color:#2ec4b6; font-weight:bold;">적용</button>
            <div style="display: flex; align-items: center; height: 50px;width:40px; margin-left:320px;">
                <label style="margin: 0;">구분</label>
            </div>
            <div style="width:120px;">
                <select name="rm_category" style="width:110px; border: 1px solid #ced4da;">
                    <option value="" ${rm_category == null ? "selected" : ""}>전체</option>
                    <option value="회의록" ${rm_category == '회의록' ? "selected" : ""}>회의록</option>
                    <option value="업무일지" ${rm_category == '업무일지' ? "selected" : ""}>업무일지</option>
                    <option value="산출물" ${rm_category == '산출물' ? "selected" : ""}>산출물</option>
                    <option value="최종결과물" ${rm_category == '최종결과물' ? "selected" : ""}>최종결과물</option>
                </select>
            </div>
            <div class="input-group input-group-sm ml-auto" style="max-width:300px; width:auto;">
                <input id="rm_name" name="rm_name" class="form-control" type="search" value="${rm_name }" placeholder="제목을 입력해주세요." aria-label="Search"
                       style="height: 35px; font-size: 13px; padding: 4px 6px; line-height: 1.2;">
                <div class="input-group-append">
                    <button class="btn btn-navbar" type="submit" style="padding: 2px 10px;">
                        <i class="fas fa-cpsearch ml-1"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="row d-flex justify-content-end align-items-center" style="margin-top:40px;">
    <div style="width:80px;">
    <label class="custom-checkbox">
        완료
        <input type="checkbox" id="chkComplete" name="eval_status" value="1"
        ${param.eval_status == '1' ? 'checked' : ''}
        onclick="document.getElementById('chkIncomplete').checked=false; this.form.submit();"/>
        <span class="checkmark"></span>
    </label>
</div>
<div style="width:130px;">
    <label class="custom-checkbox">
        미완료
        <input type="checkbox" id="chkIncomplete" name="eval_status" value="0"
        ${param.eval_status == '0' ? 'checked' : ''}
        onclick="document.getElementById('chkComplete').checked=false; this.form.submit();"/>
        <span class="checkmark"></span>
    </label>
</div>
        </div>
    </form>



    <c:set var="hasRmId" value="false" />

<c:forEach items="${roadMapList}" var="rm">
    <c:if test="${not empty rm.rm_id}">
        <c:set var="hasRmId" value="true" />
    </c:if>
</c:forEach>

        <div class="mx-auto align-items-center" style="border: 1px solid #ced4da; font-size: 15px; width:96%;">
            <div class="row d-flex p-2 align-items-center" style="width:100%; margin-left:1px; background-color:#f5f5f5;">
                <div class="col-1 text-center" style="font-weight:bold;">No</div>
                <div class="col-2 text-center" style="font-weight:bold;">구분</div>
                <div class="col-4 text-center" style="font-weight:bold;">제목</div>
                <div class="col-2 text-center" style="font-weight:bold;">작성자</div>
                <div class="col-1 text-center" style="font-weight:bold;">작성일</div>
                <div class="col-1"></div>
                <div class="col-1 text-center" style="font-weight:bold;">평가여부</div>
            </div>
<c:choose>
    <c:when test="${empty roadMapList or not hasRmId}">
        <div class="row">
            <div class="col-12 text-center" style="margin-top:60px; margin-bottom:60px; font-size:25px; font-weight:bold; color:#e0e0e0;">
                해당 내용이 없습니다.
            </div>
        </div>
    </c:when>

    <c:otherwise>

            <c:forEach items="${roadMapList}" var="rm">
            
                <c:if test="${not empty rm.rm_id}">
                    <div class="row p-2 align-items-center" style="cursor:pointer; width:1232px; margin-left:1px;"
                    data-status="${rm.eval_status == 0 ? 'incomplete' : 'complete'}"
                         onclick="location.href='<%=request.getContextPath() %>/roadmap/detail?rm_id=${rm.rm_id}';
                                  window.parent.location.hash='<%=request.getContextPath() %>/roadmap/detail?rm_id=${rm.rm_id}';">
                        <div class="col-1 text-center">${rm.rm_id}</div>
                        <div class="col-2 text-center">${rm.rm_category}</div>
                        <div class="col-4 text-center">${rm.rm_name}</div>
                        <div class="col-2 text-center">${rm.mem_name}</div>
                        <div class="col-1 text-center"><fmt:formatDate value="${rm.rm_regdate}" pattern="yyyy-MM-dd"/></div>
                        <div class="col-1 text-center"></div>

                        <c:choose>
                            <c:when test="${rm.eval_status == 1}">
                                <div class="col-1 text-center">
                                    <button type="button" style="width: 80px; background-color: #2ec4b6; border: 2px solid #2ec4b6; border-radius: 5px; color: #ffffff; font-weight: bold;">완료</button>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="col-1 text-center">
                                    <button type="button" style="width: 80px; background-color: #ff7a7a; border: 2px solid #ff7a7a; border-radius: 5px; color: #ffffff; font-weight: bold;">미완료</button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

    <c:if test="${not empty roadMapList}">
        <%@ include file="/WEB-INF/views/module/paginationrm.jsp" %>
    </c:if>
</div>

<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/moment/moment.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>

<script>
$(function () {
    $('#datetimepicker1').datetimepicker({ format: 'YYYY-MM-DD' });
    $('#datetimepicker2').datetimepicker({ format: 'YYYY-MM-DD' });
});
</script>

<script>
const rm_category = document.getElementById('rm_category');
const keywordInput = document.getElementById('keyword');
rm_category.addEventListener('change', () => {
    keywordInput.value = rm_category.value;
});
</script>
</body>
