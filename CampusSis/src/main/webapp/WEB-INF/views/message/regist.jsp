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
			style="display: block; font-size: 20pt; font-weight: bold; margin-bottom: 18px; margin-left: 5px">
			메일</span>
	</div>
	<div class="row" style="display:flex; flex-direction: row;">
	<!-- 카테고리 시작 -->
		<div class="col-md-2" style="width:250px">
			<a class="btnw btn-primary btn-block mb-3" style="width:width:250px" onclick="location.href='<%=request.getContextPath()%>/message/registForm'">메일
				작성</a>
			<div class="card" style="width:250px">
				<div class="card-body p-0" style="width:250px !important">
					<ul class="nav flex-column" style="width:250px; height: 745px;">
						<li class="mailR <%= request.getRequestURI().contains("/message/main") ? "active" : "" %>" style="height: 50px; ">
							<button id="btnAll" type="button" data-mail="1" class="d-flex align-items-center mailR"
							style="width: 100%; height: 100%; gap: 20px; line-height: 50px; border:none; padding:15px; overflow:hidden"
							onclick="location.href='<%=request.getContextPath()%>/message/main'">
								<i class="fas fa-inbox" style=""></i>
								<span style="display: block;">전체 메일</span>
							</button>
						</li>
						<li class="mailR <%= request.getRequestURI().contains("/message/receive") ? "active" : "" %>" style="height: 50px">
							<button id="btnRecv" type="button" data-mail="2" class="d-flex align-items-center mailR"
							style="width: 100%; height: 100%; gap: 20px; line-height: 50px; border:none; padding:15px"
							onclick="location.href='<%=request.getContextPath()%>/message/receive'">
								<i class="far fa-envelope" style=""></i>
								<span style="display: block;">받은 메일함</span>
								<span id="unreadCount" class="badgec bg-primaryc" style="width:auto;display: block; margin-left: auto; padding: 0 5px 0 5px">${unreadCount}</span>
							</button>
						</li>
						<li class="mailR <%= request.getRequestURI().contains("/message/send") ? "active" : "" %>" style="height: 50px; border-bottom: 1px solid #ddd;">
							<button id="btnSent" type="button" data-mail="3" class="d-flex align-items-center mailR"
							style="width: 100%; height: 100%; gap: 24px; line-height: 50px; border:none; padding:15px"
							onclick="location.href='<%=request.getContextPath()%>/message/send'">
							<i class="far fa-file-alt" style="margin-left:2px"></i>
							<span style="display: block;margin-left:-2px">보낸 메일함</span>
							</button>
						</li>
						<li class="mailR <%= request.getRequestURI().contains("/message/waste") ? "active" : "" %>" style="height: 50px; border-bottom: 1px solid #ddd;">
							<button id="btnSent" type="button" data-mail="3" class="d-flex align-items-center mailR"
							style="width: 100%; height: 100%; gap: 24px; line-height: 50px; border:none; padding:15px"
							onclick="location.href='<%=request.getContextPath()%>/message/waste'">
							<i class="far fa-trash-alt" style="margin-left:2px"></i>
							<span style="display: block;margin-left:-2px">휴지통</span>
							</button>
						</li>
					</ul>
				</div>
				<!-- /.card-body -->
			</div>
			<!-- /.card -->
		</div>
	<!-- 카테고리 끝 -->
			<!-- /.card -->
		<div class="col-md-10 " style="margin-left:auto;" >
            <div class="card card-primaryc card-outline" style="height: 810px;">
              <div class="card-header" style="height:50px">
                <span class="card-title" style="font-weight:700;">메일 보내기</span>
                <div class="float-right">
                  <button type="reset" class="btn btn-default" style="height: 35px; margin-top:-5px; line-height: 5px"
                  onclick="closeWrite()"><i class="fas fa-times"></i>&nbsp;&nbsp;창닫기</button>
                  <button id="sendBtn" type="submit" class="btn btn-primary"
                  style="height: 33px; margin-top:-5px; line-height: 5px; background-color:#2EC4B6; border: 1px solid #2EC4B6"
                  onclick="regist_go()"><i class="far fa-envelope"></i> &nbsp;&nbsp;보내기</button>
                </div>
              </div>
              <!-- /.card-header -->
              <form role="form" method="post" action="<%=request.getContextPath()%>/message/regist" name="registForm" enctype="multipart/form-data">
	              <div class="card-body">
	                <div class="form-group" style="display: flex; flex-direction: row;">
	                  <span style="display:block; width:8%; line-height:32px">받는 사람</span>
	                  <input type="text" title="받는 사람" id="mail_receiver" name="mail_receiver"
	                  	class="form-control notNull sendMail" placeholder="받는 사람을 입력해주세요.">
	                </div>
	                <div class="form-group" style="display: none;">
	                  <span style="display:block; width:8%; line-height:32px">보내는 사람</span>
	                  <input type="hidden" title="보내는 사람" id="mail_sender" name="mail_sender"
	                  	class="form-control notNull" placeholder="받는 사람을 입력해주세요." readonly value="${sessionScope.loginUser.mem_id}">
	                </div>
	                <div class="form-group" style="display: flex; flex-direction: row;">
	                  <span style="display:block; width:8%; line-height:32px">제목</span>
	                  <input type="text" title="제목" id="mail_name" name="mail_name" 
	                  	class="form-control notNull sendMail" placeholder="제목을 입력해주세요.">
	                </div>
	                <div class="form-group">
						<textarea class="textarea sendMail" name="mail_desc" id="mail_desc" rows="30"
							cols="90" placeholder="1000자 내외로 작성하세요." ></textarea>
					</div>
					<div style="display: flex; flex-direction: row;">
		                <div class="form-group" >
		                  <div id="addFileBtn" class="btn btn-default btn-file" onclick="addFile_go();" type="file" type="file" name="attachment"
		                  style="width:130px; height: 50px; line-height: 25px">
		                    <i class="fas fa-paperclip"></i>
		                    <h5 style="display:inline;line-height:40px;">&nbsp;&nbsp;파일등록</h5>
		                  </div>
		                </div>
		                <div class="fileInput"></div>
	                </div>
	              </div>
              </form>
              <!-- /.card-body -->
           </div>
           <!-- /.card -->
         </div>
    </div>
</div>

<script>
var dataNum=0;

function addFile_go(){
	//alert("!!!!");
	if($('input[name="uploadFile"]').length >=5){
		alert("파일추가는 5개까지만 가능합니다.");
		return;
	}
	
	let div=$('<div>').addClass("inputRow").attr("data-no",dataNum);
	let input=$('<input>').attr({"type":"file","name":"uploadFile"}).css("display","inline");
	let button = "<button onclick='remove_go("+dataNum+");' style='border:0;outline:0;' class='badge bg-red' type='button'>X</button>";

	div.append(input).append(button);
	$('.fileInput').append(div);
	
	dataNum++;
}

function remove_go(num){
	$('div[data-no="'+num+'"]').remove();
}

$('.fileInput').on('change',"input[name='uploadFile']",function(event){
	if(this.files[0].size > 1024*1024*40){
		alert("첨부파일크기는 40MB 이하만 가능합니다.");
		this.value="";				
	}
});

</script>

<script>
Summernote_go($("textarea#mail_desc"),"<%=request.getContextPath() %>") ;
</script>

<script>
function regist_go(){
	//alert("click regist btn");
	if($("input[name='title']").val()==""){ 
		alert("제목은 필수입니다.");
		$("input[name='title']").focus();
		return;
	}
	
	var inputs = $('input[name="uploadFile"]');
	for(var input of inputs){
		if(input.value==""){
			input.disabled=true;
		}
	}
	
	$("form[role='form']").submit();
	
}
</script>

<script>
window.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll(".mailR");
    const currentPath = "<%= request.getRequestURI() %>"; // 현재 요청 URI

    buttons.forEach(btn => {
      // onclick 속성에서 이동할 URL 뽑기
      const match = btn.getAttribute("onclick").match(/'(.*?)'/);
      if (match) {
        const btnPath = match[1];
        // 현재 URI가 버튼의 path를 포함하면 활성화
        if (currentPath.includes(btnPath)) {
          btn.classList.add("active");
        }
      }
    });
  });
</script>

<script>
let isFormDirty = false;

// 입력값이 바뀌면 true
document.querySelectorAll(".sendMail").forEach(el => {
    el.addEventListener("input", () => {
        isFormDirty = true;
    });
});

// 페이지 떠나기 직전에 confirm
window.addEventListener("beforeunload", function (e) {
    if (isFormDirty) {
        e.preventDefault();
        e.returnValue = "작성중인 내용이 사라집니다. 페이지를 떠나시겠습니까?";
        return "작성중인 내용이 사라집니다. 페이지를 떠나시겠습니까?";
    }
});

// 보내기 버튼 클릭 시 dirty 해제만 따로 처리
document.addEventListener("DOMContentLoaded", () => {
    const sendBtn = document.querySelector("#sendBtn"); // 버튼에 id 주는 게 안전
    if (sendBtn) {
        sendBtn.addEventListener("click", () => {
            isFormDirty = false; // confirm 안 뜨게 플래그 해제
        });
    }
});
</script>

<script>
function closeWrite(){
    history.back();
}
</script>

