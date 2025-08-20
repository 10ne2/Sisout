<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@ page import="com.camp_us.dto.MemberVO" %>

<head>
	
<!-- Summernote -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/summernote/summernote-bs4.min.js"></script>

</head>

<%@ include file="/WEB-INF/views/message/css.jsp" %>


<div style="height: 900px; padding: 15px;">
	<div>
		<span
			style="display: block; width:50px; font-size: 20pt; font-weight: bold; margin-bottom: 18px; margin-left: 5px;cursor:pointer"
			onclick="location.href='<%=request.getContextPath()%>/message/main'" >
			메일</span>
	</div>
	<div class="row" style="display:flex; flex-direction: row;">
	<!-- 카테고리 시작 -->
		<%@ include file="/WEB-INF/views/message/category.jsp" %>
	<!-- 카테고리 끝 -->
		<div class="col-md-10">
			<div style=" display:flex; flex-direction: row; margin-left:8px">
				<div style="display:block;">
					<div class="card card-outline card-primaryc" style="width: 1290px;">
						<div class="card-header" style="display:flex; flex-direction: row;">
							<div style="width: 100px; cursor:pointer" onclick="location.href='<%=request.getContextPath()%>/message/send'">
								<h3 class="card-title" style="margin-top:5px">보낸 메일함</h3>
							</div>
							<div style="width: 870px; margin-top:4px">
								<label style="margin-left: 5px; margin-bottom: -10px; font-weight: 400; color: #707070">
									<input type="radio" name="filter" value="imp" onclick="location.href='${pageContext.request.contextPath}/message/sendImp'" ${selectedFilter == 'imp' ? 'checked' : ''}>
									중요 메일
								</label>
								<label style="margin-left: 5px; margin-bottom: -10px; font-weight: 400; color: #707070">
									<input type="radio" name="filter" value="lock" onclick="location.href='${pageContext.request.contextPath}/message/sendLock'" ${selectedFilter == 'lock' ? 'checked' : ''}>
									잠긴 메일
								</label>
							</div>
							<button type="button"
								class="btn btn-default btn-sm checkbox-toggle" onclick="all_click()">
								<i class="far fa-square"></i>
							</button>
							<button type="button" class="btn btn-default btn-sm" style="margin-left: 8px" onclick="removeMail();">
								<i class="far fa-trash-alt"></i>
							</button>
							<button type="button" class="btn btn-default btn-sm" style="margin-left: 8px;  margin-right: 8px" onclick="refresh()">
									<i class="fas fa-sync-alt"></i>
							</button>
							<div class="input-group input-group-sm" style="width: 200px; margin-left:auto;">
								<input type="text" class="form-control" id="keyword" name="keyword"  placeholder="검색어를 입력해주세요." value="${pageMaker.keyword }">
								<div class="input-group-append">
									<div class="btn btn-primaryc" onclick="search_list(1)">
										<img src="<%=request.getContextPath()%>/resources/images/search.png" style="width: 15px; margin-bottom: 3px">
									</div>
								</div>
							</div>
						</div>
						<!-- /.card-header -->
						<div class="table-responsive mailbox-messages"
							style="height: 687px; overflow-y: auto;">
							<table class="mailTable table table-hover">
								<tbody>
									<c:if test="${empty sendMailList }">
										<tr>
							   	   			<div colspan="5" style="font-size: 30px; font-weight: bold; color: #e0e0e0; line-height: 300px" class="text-center" >받은 메일이 없습니다.</div>
							   	   		</tr>
									</c:if>
									<c:if test="${not empty sendMailList }">
										<c:forEach items="${sendMailList }" var="send">
											<tr style="cursor:pointer;">
												<td style="width: 100%; min-height: 60.2px; display: flex; flex-direction: column; margin:-1.6px">
													<div style="width:100%;">
														<div style="display: flex; flex-direction: row;">
															<div class="" style="display: flex; flex-direction: row;">
																<div style="margin-left:5px;">
																    <img id="lockImg_${send.mail_id}" src="<%=request.getContextPath()%>/resources/images/lock/${send.mail_slock }.png"
																      style="width:20px; cursor:pointer" onclick="toggleLock(${send.mail_id})"/>
																</div>
															</div>
															<div class="icheck-primary" style="width:43px; height:22px; margin-left: 20px">
																<input type="checkbox" name="mail_id" value="${send.mail_id}" id="check_${send.mail_id}"> <label
																	for="check_${send.mail_id}"></label>
															</div>
															<div class="" style="display: flex; flex-direction: row;">
																<div style="margin-left:10px;">
																    <img id="impImg_${send.mail_id}" src="<%=request.getContextPath()%>/resources/images/important/${send.mail_simp }.png"
																      style="width:20px; cursor:pointer" onclick="toggleImportant(${send.mail_id})"/>
																</div>
															</div>
															<div class="" style="width:150px; display:flex; flex-direction: row; margin-left:20px">
																<a style="width: 60px; line-height:30px;">
																	${send.receiver_name }
																</a>
																<a style="line-height:30px; font-size:14px; color: #999">
																	${send.mail_receiver}
																</a>
															</div>
															<div style="width: 850px; display: flex; flex-direction: row;" onclick="OpenWindow('<%=request.getContextPath()%>/message/detail?mail_id=${send.mail_id}','상세보기',1040,800);">
																<div style=" border:none; display: flex; flex-direction: row">
																	<a style="max-width: 800px; font-size:14px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; line-height: 30px">
																			${send.mail_name }</a>
																</div>
																<div class="" style="display: flex; flex-direction: row;">
																	<div style="margin-left:10px;">
																	    <c:if test="${not empty send.mailFileList}">
															                <img src="<%=request.getContextPath()%>/resources/images/att.png" style="width:15px;margin-top:4px;" alt="첨부파일 있음" />
															            </c:if>
																	</div>
																</div>
															</div>
															<div class="mailbox-date" style="margin-left:auto; line-height:30px; font-size:12px; color: #bbb">
															 	<fmt:formatDate value="${send.mail_sdate }" pattern="yy-MM-dd HH:mm" />
														 	</div>
														</div>
													</div>
												</td>	
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
							<!-- /.mail-box-messages -->
						<div class="card-footer">
						<!-- pagination.jsp -->
							<div <%-- style="display:${not empty sendMailList ? 'visible':'none'  --%>}">
								<%@ include file="/WEB-INF/views/module/pagination.jsp" %>	
							</div>	    			
	    				</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- -- -------------------------------------------------------------------------------------------------------------------------------------------------------  -->
<!-- -- -------------------------------------------------------------------------------------------------------------------------------------------------------  -->
<!-- -- -------------------------------------------------------------------------------------------------------------------------------------------------------  -->
<!-- -- -------------------------------------------------------------------------------------------------------------------------------------------------------  -->

<form id="jobForm" style="display:none;">	
	<input type='text' name="page" value="1" />
	<input type='text' name="keyword" value="" />
	<input type='text' name="perPageNum" value="" />
</form>
<script>
function search_list(page){
	let keyword = document.querySelector('#keyword').value;
	let perPageNum = 12;
	let form = document.querySelector("#jobForm");
	
	//alert(perPageNum+":"+searchType+":"+keyword);
	
	form.keyword.value = keyword;
	form.page.value = page;
	form.perPageNum.value = perPageNum;
	
	//console.log($(form).serialize());
	form.submit();
	
}
</script>

<script>
Summernote_go($("textarea#mail_desc"),"<%=request.getContextPath() %>") ;

function regist_go(){
	/* alert("click regist"); */
	var form = document.registForm;
	
	var inputNotNull = document.querySelectorAll(".notNull");
	for(var input of inputNotNull){
		if(!input.value){
			alert(input.getAttribute("title")+"은 필수입니다.");
			input.focus();
			return;
		}
	}
	
	form.submit();
}
</script>

<script>
function refresh() {
    location.href = "${pageContext.request.contextPath}/message/send";
}
</script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
function toggleImportant(mailId) {
    $.post('${pageContext.request.contextPath}/message/toggleSImp', { mail_id: mailId }, function(data) {
        if(data.success) {
            let iconSrc = data.newStatus === 1
                ? '${pageContext.request.contextPath}/resources/images/important/1.png'
                : '${pageContext.request.contextPath}/resources/images/important/0.png';
            $('#impImg_' + mailId).attr('src', iconSrc);
            window.location.reload();
        }
    });
}

function toggleLock(mailId) {
    $.post('${pageContext.request.contextPath}/message/toggleSLock', { mail_id: mailId }, function(data) {
        if(data.success) {
            let iconSrc = data.newStatus === 1
                ? '${pageContext.request.contextPath}/resources/images/lock/1.png'
                : '${pageContext.request.contextPath}/resources/images/lock/0.png';
            $('#lockImg_' + mailId).attr('src', iconSrc);
            window.location.reload();
        }
    });
}
</script>

<script>
function removeMail(){
	let checked = document.querySelectorAll('input[name="mail_id"]:checked');
	
	if (checked.length === 0) {
        alert("삭제할 메일을 선택하세요.");
        return;
    }
	
	let answer = confirm("정말 삭제하시겠습니까?");
	if(!answer) return;
	
	let mail_id = Array.from(checked).map(cb => cb.value);
	let params = mail_id.join(",")
	
	location.href = "movewaste?mail_id=" + params;
}
</script>

<script>
function all_click(){
	const checkboxes = document.querySelectorAll('input[name="mail_id"]');
	const allChecked = Array.from(checkboxes).every(cb => cb.checked);
	  
	  if (allChecked) {
	    // 모두 체크되어 있으면 해제
	    checkboxes.forEach(cb => {
	    	cb.checked = false;
	    	cb.closest('tr').style.backgroundColor = '';
	    });
	  } else {
	    // 하나라도 체크 안 되어 있으면 모두 선택
	    checkboxes.forEach(cb => {
	    	cb.checked = true;
	    	cb.closest('tr').style.backgroundColor = '#EAF5F4'; // 선택된 행 배경색
	    });
	  }
	  
}
</script>

<script>
const checkboxes = document.querySelectorAll('input[name="mail_id"]');

checkboxes.forEach(cb => {
    cb.addEventListener('change', function() {
        const row = this.closest('tr');
        if (this.checked) {
            row.classList.add('selected');   // 체크 시 배경 변경
        } else {
            row.classList.remove('selected'); // 체크 해제 시 원래대로
        }
    });
});
</script>