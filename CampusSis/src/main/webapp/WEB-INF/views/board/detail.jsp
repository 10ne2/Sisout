<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
<title>게시판 - 상세보기</title>
<style>
body {
	font-family: '맑은 고딕', sans-serif;
	padding: 30px;
}
.title {
	font-size: 20px;
	font-weight: bold;
	margin-bottom: 10px;
}
.meta {
	color: gray;
	margin-bottom: 20px;
}
.content {
	border-top: 1px solid #ddd;
	padding: 20px 0;
	margin-bottom: 20px;
	white-space: pre-wrap;
}
.attachment {
	margin-bottom: 30px;
	color: #444;
}
.btn-box {
	text-align: right;
	margin-bottom: 30px;
}
.btn {
	padding: 6px 14px;
	border: none;
	border-radius: 4px;
	margin-left: 8px;
	cursor: pointer;
}
.btn-edit {
	background-color: #20c997;
	color: white;
}
.btn-delete {
	background-color: #adb5bd;
	color: white;
}
.footer-btns {
	text-align: right;
	margin-top: 30px;
}
.btn-back {
	background-color: #20c997;
	color: white;
}
.report {
	text-align: right;
	margin-bottom: 10px;
}
.report a {
	cursor: pointer;
	margin-left: 10px;
}
.report a:hover {
	color: #20c997;
}
.report-popup {
	display: none;
	position: fixed;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	background: white;
	border: 1px solid #ccc;
	padding: 30px;
	width: 500px;
	z-index: 9999;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}
.report-popup h3 {
	font-size: 20px;
	margin-bottom: 20px;
}
.report-popup select, .report-popup textarea {
	width: 100%;
	padding: 12px;
	font-size: 14px;
	border: 1px solid #ddd;
	border-radius: 4px;
	margin-top: 10px;
}
.report-popup .desc {
	font-size: 13px;
	color: #666;
	margin-top: 8px;
}
.popup-buttons {
	margin-top: 20px;
	text-align: right;
}
.popup-buttons button {
	padding: 6px 14px;
	border: none;
	border-radius: 4px;
	margin-left: 10px;
	cursor: pointer;
}
.popup-buttons button:first-child {
	background-color: #ccc;
	color: black;
}
.popup-buttons button:last-child {
	background-color: #20c997;
	color: white;
}
.comment-section {
	margin-top: 40px;
}
.comment {
	border: 1px solid #ccc;
	padding: 12px;
	border-radius: 4px;
	margin-bottom: 10px;
	position: relative;
}
.comment-meta {
	font-weight: bold;
	margin-bottom: 6px;
}
.comment-btns {
	position: absolute;
	top: 12px;
	right: 12px;
}
.comment-btns button {
	margin-left: 5px;
	padding: 4px 8px;
	border: none;
	border-radius: 4px;
	background-color: #aaa;
	color: white;
	cursor: pointer;
}
.comment-form textarea {
	width: 100%;
	height: 80px;
	padding: 10px;
	box-sizing: border-box;
	margin-bottom: 10px;
}
.comment-form .btn {
	background-color: #20c997;
	color: white;
}
</style>
<script>
let editMode = false;
const boardCat = "${fn:escapeXml(board.boardCat)}";
function toggleEditMode() {
	if (!editMode) {
		const titleText = document.getElementById("postTitle").innerText;
		const contentText = document.getElementById("postContent").innerText;
		document.getElementById("postTitle").innerHTML = `<input type="text" id="editTitle" value="${titleText}" style="width:100%; font-size:20px; font-weight:bold;">`;
		document.getElementById("postContent").innerHTML = `<textarea id="editContent" style="width:100%; height:200px;">${contentText}</textarea>`;
		document.getElementById("editButton").innerText = "저장";
		editMode = true;
	} else {
		document.getElementById("editButton").disabled = true;
		const newTitle = document.getElementById("editTitle").value;
		const newContent = document.getElementById("editContent").value;
		fetch("${pageContext.request.contextPath}/board/update", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({
				boardId: "${board.boardId}",
				boardName: newTitle,
				boardDesc: newContent,
				boardCat: boardCat
			})
		}).then(() => {
			location.href = "${pageContext.request.contextPath}/board/detail?bno=${board.boardId}";
		});
	}
}
function openReportPopup() {
	document.getElementById("reportPopup").style.display = "block";
}
function closeReportPopup() {
	document.getElementById("reportPopup").style.display = "none";
}
function toggleTextarea() {
	const select = document.getElementById("reasonSelect");
	const textareaBox = document.getElementById("customReasonBox");
	textareaBox.style.display = select.value.includes("기타") ? "block" : "none";
}
function submitReport() {
	const reason = document.getElementById("reasonSelect").value;
	const custom = document.getElementById("customReasonText").value.trim();
	if (!reason || (reason.includes("기타") && !custom)) {
		alert("신고 사유를 확인해주세요.");
		return;
	}
	alert("신고가 정상적으로 접수되었습니다.");
	closeReportPopup();
}
</script>
</head>



<body>

	<h1>게시판</h1>
<form id="editForm" action="${pageContext.request.contextPath}/board/update" method="post" enctype="multipart/form-data">
  <input type="hidden" name="boardId" value="${board.boardId}" />
  <input type="hidden" name="boardCat" value="${board.boardCat}" />

  <div class="btn-box" style="text-align: right;">
    <button type="button" class="btn btn-edit" id="editButton" onclick="toggleEditMode()">수정</button>
    <button type="submit" class="btn btn-edit d-none" id="saveButton">저장</button>
    <button type="button" class="btn btn-delete" onclick="deletePost()">삭제</button>
  </div>

  <!-- 제목 -->
  <div id="postTitle" class="title">${board.boardName}</div>
  <input type="text" name="boardName" id="postTitleInput" class="d-none" value="${board.boardName}" style="width:100%; font-size:20px; font-weight:bold;" />

  <!-- 작성자 / 날짜 -->
  <div class="meta">
    작성자: ${board.memId}
    <fmt:formatDate value="${board.boardDate}" pattern="yyyy-MM-dd HH:mm" />
  </div>

  <!-- 본문 -->
  <div id="postContent" class="content">${board.boardDesc}</div>
  <textarea name="boardDesc" id="postContentInput" class="d-none" rows="10" style="width:100%;">${board.boardDesc}</textarea>

  <!-- 파일 다운로드 -->
  <c:if test="${not empty board.pfileName}">
    <div class="attachment" id="fileDisplay" style="margin-top: 20px;">
      📎 <a href="${pageContext.request.contextPath}/upload/${board.pfileName}" download style="color: blue; text-decoration: underline;">
        ${board.pfileName}
      </a>
    </div>
  </c:if>

  <!-- 항상 존재하는 keepFile hidden input -->
  <input type="hidden" name="keepFile" id="keepFile" value="true" />

  <!-- 파일 수정 input -->
  <div class="attachment">
    <div id="fileInputWrapper" class="d-none" style="display: flex; align-items: center; gap: 10px;">
      <input type="file" name="file1" id="fileInput" style="display: none;" onchange="handleFileChange()" />
      <label for="fileInput" class="btn btn-sm" style="background-color: #6c757d; color: white;">파일 선택</label>

      <span id="fileNameText" style="display: flex; align-items: center; gap: 5px;">
        <c:if test="${not empty board.pfileName}">
          <a href="${pageContext.request.contextPath}/upload/${board.pfileName}" download id="existingFileLink" style="color: blue; text-decoration: underline;">
            ${board.pfileName}
          </a>
          <button type="button" id="removeFileBtn" onclick="removeExistingFile()" style="background: none; border: none; color: black; font-size: 14px; cursor: pointer;">✕</button>
        </c:if>
      </span>
    </div>
  </div>
</form>

<form id="deleteForm" action="${pageContext.request.contextPath}/board/delete" method="post">
  <input type="hidden" name="boardId" value="${board.boardId}" />
</form>

	<div class="report">
		<a onclick="openReportPopup()">[신고]</a>
	</div>
	<!-- 댓글 -->
<div class="comment-section">

	<!-- 🧩 여기에 댓글 목록이 JS로 동적으로 붙을 예정 -->
	<div id="repliesDiv"></div>

<div class="comment-form" style="margin-top: 20px;">
	<div class="comment-meta">
		${loginUser}
		<fmt:formatDate value="${regdate}" pattern="yyyy-MM-dd HH:mm" />
	</div>
	<textarea id="newReplyText" placeholder="댓글을 입력해주세요." style="width:100%; height:80px; margin-top:10px;"></textarea>
	<button class="btn" onclick="replyRegist_go()" style="margin-top:10px;">등록</button>
</div>

	<div class="footer-btns">
		<button class="btn btn-back" onclick="location.href='${pageContext.request.contextPath}/boardlist'">목록</button>
	</div>
	<div id="reportPopup" class="report-popup">
		<h3>신고 글 작성</h3>
		<select id="reasonSelect" onchange="toggleTextarea()">
			<option selected disabled>삭제 사유를 선택하세요</option>
			<option>1. 욕설/비방</option>
			<option>2. 음란물/선정적인 내용</option>
			<option>3. 정치적 선동/특정 단체 홍보</option>
			<option>4. 개인정보 노출</option>
			<option>5. 타인 사칭/허위정보</option>
			<option>6. 다른 이용자 신고 누적</option>
			<option>7. 기타 (직접 입력)</option>
		</select>
		<div id="customReasonBox" style="display: none;">
			<textarea id="customReasonText" placeholder="[직접 입력란]"></textarea>
		</div>
		<div class="desc">* 선택된 사유는 관리자에게 전달됩니다.</div>
		<div class="popup-buttons">
			<button onclick="closeReportPopup()">취소</button>
			<button onclick="submitReport()">보내기</button>
		</div>
	</div>
	
<!-- 댓글 수정/삭제 모달 -->
<div id="modifyModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">댓글 수정/삭제</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>        
      </div>
      <div class="modal-body" id="modal-body" data-rno="">
        <input type="text" id="replytext" class="form-control">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-info" onclick="replyModify_go()">수정</button>
        <button type="button" class="btn btn-danger" onclick="replyRemove_go()">삭제</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>

<!-- jQuery 먼저 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Handlebars 그 다음 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.7/handlebars.min.js"></script>
<!-- Bootstrap JS 추가 (이거 없으면 모달 작동안 함) -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<!-- 댓글 템플릿 -->
<script type="text/x-handlebars-template" id="reply-list-template">
{{#each replyList}}
  <div class="comment replyLi" data-rno="{{rno}}">
    <div class="comment-meta">
      <strong>{{replyer}}</strong>
      <small>{{formattedRegdate}}</small>
      <div class="comment-btns">
        <button class="btn btn-sm btn-primary btn-edit" data-rno="{{rno}}">수정</button>
        <button class="btn btn-sm btn-danger btn-delete" data-rno="{{rno}}">삭제</button>
      </div>
    </div>
    <div class="comment-text" id="{{rno}}-replytext">{{replytext}}</div>
  </div>
{{/each}}
</script>

<!-- 페이징 템플릿 -->
<script type="text/x-handlebars-template" id="reply-pagination-template">
  <li class="page-item"><a class="page-link" href="{{goPage 1}}">«</a></li>
  <li class="page-item"><a class="page-link" href="{{#if prev}}{{goPage prevPageNum}}{{else}}{{goPage 1}}{{/if}}">‹</a></li>
  {{#each pageNum}}
    <li class="page-item {{signActive this}}"><a class="page-link" href="{{goPage this}}">{{this}}</a></li>
  {{/each}}
  <li class="page-item"><a class="page-link" href="{{#if next}}{{goPage nextPageNum}}{{else}}{{goPage realEndPage}}{{/if}}">›</a></li>
  <li class="page-item"><a class="page-link" href="{{goPage realEndPage}}">»</a></li>
</script>

<script>
function deletePost() {
  if (!confirm("게시글을 삭제하시겠습니까?")) return;
  document.getElementById("deleteForm").submit();
}
</script>

<!-- 댓글 목록 + 페이징 -->
<script>
const reply_list_func = Handlebars.compile($("#reply-list-template").html());
const pagination_func = Handlebars.compile($("#reply-pagination-template").html());
let currentPage = 1;
function getPage(page) {
	
  $.ajax({
    url: "${pageContext.request.contextPath}/reply/list?page=" + page + "&bno=${board.boardId}",
    method: "get",
    success: function(data) {
      Handlebars.registerHelper("goPage", function(page) {
        return "javascript:getPage(" + page + ")";
      });
      Handlebars.registerHelper("signActive", function(pageNum) {
        return pageNum === currentPage ? "active" : "";
      });

      const replyHtml = reply_list_func({ replyList: data.replyList });
      $("#repliesDiv").html(replyHtml);

      currentPage = page;

      $(".btn-edit").off("click").on("click", function () {
        const rno = $(this).data("rno");
        startReplyEdit(rno);
      });

      $(".replyLi .btn-delete").off("click").on("click", function () {
    	    const rno = $(this).data("rno");
    	    replyRemove_go(rno);
    	});



      let pageMaker = data.pageMaker;
      let pageNumArray = new Array(pageMaker.endPage - pageMaker.startPage + 1);
      for (let i = pageMaker.startPage; i <= pageMaker.endPage; i++) {
        pageNumArray[i - pageMaker.startPage] = i;
      }
      pageMaker.pageNum = pageNumArray;
      pageMaker.prevPageNum = pageMaker.startPage - 1;
      pageMaker.nextPageNum = pageMaker.endPage + 1;

      const paginationHtml = pagination_func(pageMaker);
      $("#pagination").html(paginationHtml);
    },
    error: function() {
      alert("댓글 불러오기 실패");
    }
  });
}
</script>

<!-- 댓글 등록 -->
<script>
function replyRegist_go() {
  const replytext = $("#newReplyText").val();
  const replyer = "${loginUser}";

  if (!replytext.trim()) {
    alert("댓글 내용을 입력하세요.");
    return;
  }

  const data = {
    boardId: "${board.boardId}",
    replytext: replytext,
    replyer: replyer
  };

  $.ajax({
    url: "${pageContext.request.contextPath}/reply/regist",
    method: "post",
    data: JSON.stringify(data),
    contentType: "application/json",
    success: function(data) {
      currentPage = data;
      getPage(currentPage);
      $("#newReplyText").val("");
    },
    error: function() {
      alert("댓글 등록 실패");
    }
  });
}
</script>

<!-- 댓글 수정: input으로 전환하고 다시 수정 -->
<script>
function startReplyEdit(rno) {
	  const textDiv = $("#" + rno + "-replytext");
	  const currentText = textDiv.text().trim();

	  if (textDiv.find("input").length > 0) {
	    const editedText = textDiv.find("input").val().trim();

	    if (!editedText) {
	      alert("댓글 내용을 입력하세요.");
	      return;
	    }

	    $.ajax({
	      url: "${pageContext.request.contextPath}/reply/modify",
	      method: "POST",
	      headers: {
	        "X-HTTP-Method-Override": "PUT"
	      },
	      data: JSON.stringify({
	        rno: rno,
	        replytext: editedText
	      }),
	      contentType: "application/json",
	      success: function () {
	        // 댓글 수정 후 목록 새로고침
	        getPage(currentPage);
	      },
	      error: function () {
	        alert("댓글 수정 실패");
	      }
	    });

	    return;
	  }

	  // 처음 수정 클릭 → input 전환
	  textDiv.html(`<input type="text" class="form-control" id="edit-${rno}" value="${currentText}">`);
	  $(`.btn-edit[data-rno='${rno}']`).text("저장");
	}

</script>

<!-- 댓글 삭제 -->
<script>
function replyRemove_go(rno) {
	  if (!confirm("댓글을 삭제하시겠습니까?")) return;

	  $.ajax({
	    url: "${pageContext.request.contextPath}/reply/remove",
	    method: "post",
	    contentType: "application/json",
	    data: JSON.stringify({ rno: rno }), // ✅ 오직 rno만 전달
	    headers: {
	      "X-HTTP-Method-Override": "DELETE"
	    },
	    success: function () {
	      getPage(currentPage); // 댓글 새로고침
	    },
	    error: function () {
	      alert("댓글 삭제 실패");
	    }
	  });
	}


</script>

<!-- 페이지 로드시 댓글 로드 -->
<script>
$(document).ready(function() {
  getPage(1);
});
</script>
<script>
function toggleEditMode() {
    const isEditing = $("#postTitleInput").hasClass("d-none") === false;

    if (!isEditing) {
        // 수정 모드 진입
        $("#postTitle").addClass("d-none");
        $("#postContent").addClass("d-none");
        $("#fileDisplay").addClass("d-none");

        $("#postTitleInput").removeClass("d-none");
        $("#postContentInput").removeClass("d-none").val($("#postContent").text());
        $("#fileInputWrapper").removeClass("d-none");

        $("#editButton").addClass("d-none");
        $("#saveButton").removeClass("d-none");
    } else {
        // 저장 → form 전송
        document.getElementById("editForm").submit();
    }
}



</script>
<script>
function handleFileChange() {
	  const fileInput = document.getElementById("fileInput");
	  const fileNameText = document.getElementById("fileNameText");
	  const keepFileInput = document.getElementById("keepFile");

	  if (fileInput.files.length > 0) {
	    const file = fileInput.files[0];
	    const fileName = file.name;

	    fileNameText.innerHTML = "";

	    // 파란색 링크 스타일로 파일 이름 표시
	    const link = document.createElement("a");
	    link.textContent = fileName;
	    link.style.color = "blue";
	    link.style.textDecoration = "underline";
	    link.href = "#";  // 그냥 스타일을 위해
	    fileNameText.appendChild(link);

	    // 기존 파일 삭제 표시
	    if (keepFileInput) {
	      keepFileInput.value = "false";
	    }
	  }
	}

</script>

<script>
function removeExistingFile() {
  const fileNameText = document.getElementById("fileNameText");
  const keepFileInput = document.getElementById("keepFile");

  // 파일 링크 제거
  fileNameText.innerHTML = "<span style='color:gray;'>파일이 삭제됩니다.</span>";

  // keepFile 값을 false로 변경해서 서버가 삭제하도록 유도
  if (keepFileInput) {
    keepFileInput.value = "false";
  }
}
</script>
<script>
function deletePost() {
  const loginUser = "${loginUser}";
  const writer = "${board.memId}";
  const userAuth = "${loginUserAuth}"; // 예: 관리자 또는 직원이면 ROLE_ADMIN, ROLE_EMP 등으로 분기

  // 관리자, 직원 또는 작성자 본인만 삭제 가능
  if (loginUser === writer || userAuth === "ROLE_ADMIN" || userAuth === "ROLE_EMP") {
    if (!confirm("게시글을 삭제하시겠습니까?")) return;
    document.getElementById("deleteForm").submit();
  } else {
    alert("삭제 권한이 없습니다.");
  }
}
</script>

<script>
function applyPermissionControl() {
  const loginUser = "${loginUser}";
  const writer = "${board.memId}";
  const userAuth = "${loginUserAuth}"; // 관리자/직원 권한 구분용

  // 게시글 수정/삭제 버튼
  if (!(loginUser === writer || userAuth === "ROLE_ADMIN" || userAuth === "ROLE_EMP")) {
    document.getElementById("editButton").style.display = "none";
    document.querySelector(".btn-delete").style.display = "none";
  }

  // 댓글 수정/삭제 버튼 권한 제어
  $(document).on("mouseenter", ".replyLi", function () {
    const replyer = $(this).find("strong").text().trim();
    if (replyer !== loginUser && userAuth !== "ROLE_ADMIN" && userAuth !== "ROLE_EMP") {
      $(this).find(".btn-edit").hide();
      $(this).find(".btn-delete").hide();
    }
  });
}
</script>

<script>
$(document).ready(function () {
  getPage(1);
  applyPermissionControl();
});
</script>

</body>
</html>
