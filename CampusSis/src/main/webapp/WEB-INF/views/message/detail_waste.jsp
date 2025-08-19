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

<style>
.btnw {
	padding: 10px 16px;
	border: none;
	background-color: #2EC4B6;
	color: white;
	border-radius: 4px;
	font-size: 18px;
	text-align: center;
	cursor: pointer;
	font-weight: 600;
}

.btnw:hover {
	background-color: #22A99C;
}

.badgec {
	width: 30px;
	height: 23px;
	font-size: 12px;
	font-weight: 700;
	line-height: 22px;
	text-align: center;
	vertical-align: baseline;
	border-radius: 0.375rem;
}

.bg-primaryc {
	background-color: #2EC4B6; /* Bootstrap 기본 파란색 */
	color: #fff;
}

.cardc {
	word-wrap: break-word;
	margin-bottom: 10px
}
.mailbox-subjectc {
  width: 90px;
}
.card-primaryc {
  border-top: 3px solid #2EC4B6;
}
.btn-primaryc {
  color: #fff;
  background-color: #2EC4B6;
  border-color: #2EC4B6;
}

.btn-primaryc:hover {
  color: #fff;
  background-color: #22A99C;
  border-color: #22A99C;
}

.btn-primaryc:focus, .btn-primary.focus {
  box-shadow: 0 0 0 0.2rem rgba(0,123,255,.5);
}

.btn-primaryc.disabled, .btn-primaryc:disabled {
  background-color: #2EC4B6;
  border-color: #2EC4B6;
  opacity: 0.65;
}
.table-hover tbody tr:hover {
  background-color: #EAF5F4 !important; /* 민트색 배경 예시 */
  cursor: pointer; /* 커서 손가락으로 */
}
.selected  {
  background-color: #EAF5F4 !important; /* 민트색 배경 예시 */
}
.mailR{
	background-color: transparent;
	
}
.mailR:hover{
	background-color: #EAF5F4;
	overflow: hidden;
	font-weight: bold;
}
.mailR.active{
	background-color: #EAF5F4;
	font-weight: bold;
}
.mailT{
	background-color: transparent;
}
.mailT:hover span{
	font-weight:bold;
	overflow: hidden;
	color: #22A99C;
}
.mailT.active{
	font-weight:bold;
	overflow: hidden;
	color: #22A99C;
}
</style>


<div style="height: 790px; padding: 15px;">
	<div class="row" style="display:flex; flex-direction: row;">
		<div class="col-md-7 mailDetail" style="display:block">
			<div class="card card-primaryc card-outline" style="width: 1000px; height: 740px; overflow-y: auto;">
				<div class="card-body p-0">
					<div class="mailListItem">
						<div class="mailbox-read-info" style="padding:15px">
							<div style="display: flex; flex-direction: row;">
								<h5 id="mailName" style="width: 850px; margin-bottom:15px; line-height: 35px">${mail.mail_name}</h5>
								<span id="mailDate" class="mailbox-read-time float-right" style="display:block; width: 90px; margin-left:20px; margin-top:12px; text-align:right;">
									<fmt:formatDate value="${mail.mail_ddate }" pattern="yy-MM-dd HH:mm" />
								</span>
							</div>
							<div style="display: flex; flex-direction: row; margin-bottom: 10px">
								<span style="width: 80px; display:block; line-height: 28px">보낸 사람</span>
								<div style=" height: 30px; background-color: #DFFCF9; border-radius:15px; display: flex; flex-direction: row;">
									<span id="mailTargetName" style="display:block; margin-left:15px; line-height: 28px">
										${mail.mail_sender == sessionScope.loginUser.mem_id ? mail.receiver_name : mail.sender_name}</span>
									<span id="mailTargetEmail" style="display:block; margin-left:15px; line-height: 28px; margin-right:15px">
										${mail.mail_sender == sessionScope.loginUser.mem_id ? mail.receiver_email : mail.sender_email}</span>
								</div>
								<div style="margin-left:auto; display: flex; flex-direction: row;">
									<button type="button" class="btn btn-default btn-sm" data-container="body" title="Delete" onclick="backMail(${mail.mail_id})">
										<span>복구</span>
									</button>
									<button style="margin-left:10px" type="button" class="btn btn-default btn-sm" data-container="body" title="Delete" onclick="remove(${mail.mail_id});">
										<span>영구삭제</span>
									</button>
								</div>
							</div>
							<div style="display: flex; flex-direction: row;">
								<span style="width: 80px; display:block; line-height: 28px">받는 사람</span>
								<div style=" height: 30px; background-color: #DFFCF9; border-radius:15px; display: flex; flex-direction: row;">
									<span id="mailTargetName" style="display:block; margin-left:15px; line-height: 28px">
										${mail.mail_sender == sessionScope.loginUser.mem_id ? mail.sender_name : mail.receiver_name}</span>
									<span id="mailTargetEmail" style="display:block; margin-left:15px; line-height: 28px; margin-right:15px">
										${mail.mail_sender == sessionScope.loginUser.mem_id ? mail.sender_email : mail.receiver_email}</span>
								</div>
							</div>
						</div>
						<div id="mailDesc" class="mailbox-read-message" style="padding:15px">
							<div>${mail.mail_desc}</div>
						</div>
					</div>
				</div>
				<div>
					<c:forEach items="${mail.mailFileList }" var="mailFile">
						<div style="width: 968px; cursor:pointer; margin-left: 15px; margin-bottom: 15px;" onclick="location.href='getFile?mafile_no=${mailFile.mafile_no}';" >
							<div style="height: 50px; display: flex; flex-direction: row; border: 1px solid #ebebeb">	
								<div style="width: 10px; margin-left:13px; margin-top: 11px">
									<img src="<%=request.getContextPath()%>/resources/images/att.png" style="width:20px;">
								</div>
								<div style=" margin-left:20px; margin-top: 11px">														
									<span style="display:block; ">${mailFile.mafile_name.split('\\$\\$')[1] }</span>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
				<!-- /.card-footer -->
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
window.onunload = function() {
    if (window.opener && !window.opener.closed) {
        window.opener.location.reload();
    }
};
</script>

<script>
function remove(mail_id){
	if (!confirm("복구가 불가능합니다. 정말 삭제하시겠습니까?")) return;
	
	location.href = "delete/detail?mail_id=" + mail_id;
}
</script>

<script>
function backMail(mail_id){
	if (!confirm("복구하시겠습니까?")) return;
	
	location.href = "backwaste/detail?mail_id=" + mail_id;
}
</script>
