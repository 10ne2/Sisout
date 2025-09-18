<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>강의 공지사항</title>
<style>
body {
	font-family: '맑은 고딕', sans-serif;
	padding: 40px;
}

.title {
	font-size: 24px;
	font-weight: 700;
	margin-bottom: 10px;
}

.meta {
	color: #666;
	margin-bottom: 16px;
}

.content {
	border-top: 1px solid #ddd;
	padding: 16px 0;
	margin-bottom: 24px;
	white-space: pre-wrap;
}

.files {
	border-top: 1px solid #eee;
	padding-top: 12px;
	margin-bottom: 24px;
}

.files a {
	text-decoration: underline;
}

.btn {
	padding: 6px 16px;
	border: none;
	border-radius: 4px;
	font-size: 14px;
	cursor: pointer;
	margin-left: 6px;
}

.btn-primary {
	background: #1abc9c;
	color: #fff;
}

.btn-gray {
	background: #bdc3c7;
	color: #fff;
}

.right {
	text-align: right;
}

input[type=text], textarea {
	width: 100%;
	box-sizing: border-box;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

textarea {
	height: 320px;
	resize: vertical;
}

.row {
	margin-bottom: 12px;
}

.clearfix::after {
	content: "";
	display: block;
	clear: both;
}


.uploader {
	display: flex;
	align-items: center;
	gap: 10px;
	margin: 8px 0;
}

.btn-file {
	display: inline-block;
	padding: 6px 12px;
	background: #6c7a89;
	color: #fff;
	border-radius: 4px;
	cursor: pointer;
}

.hint {
	color: #666;
	font-size: 13px;
}

.attached-list {
	display: inline-flex;
	gap: 8px;
	flex-wrap: wrap;
	margin-left: 10px;
}

.attached-item {
	display: inline-flex;
	align-items: center;
	gap: 6px;
	background: #f5f7fa;
	padding: 4px 8px;
	border-radius: 4px;
}

.attached-item a {
	text-decoration: underline;
}

.remove-x {
	border: none;
	background: transparent;
	color: #e74c3c;
	font-weight: bold;
	cursor: pointer;
}

/* 📎 아이콘 */
.clip-icon {
	width: 14px;
	height: 14px;
	vertical-align: middle;
	margin-right: 6px;
}


.sep-and-btn {
	position: relative;
	height: 48px;
	margin-top: 24px;
}

.sep-and-btn .end-sep {
	position: absolute;
	left: 0;
	right: 0;
	top: 0;
	border-top: 1px solid #e5e7eb;
}

.sep-and-btn .list-btn-fixed {
	position: absolute;
	right: 0;
	top: 8px; /
}



.btn-primary:hover {
    background-color: #2bd6b9 !important;
    border-color: #2bd6b9 !important;
}
</style>
</head>
<body>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<c:set var="user" value="${sessionScope.loginUser}" />
	<c:set var="isProfessor"
		value="${user != null && user.mem_auth eq 'ROLE02'}" />

	<!-- 상단 버튼 -->
	<div class="right" style="margin-bottom: 12px;">
		<c:choose>
			<c:when test="${editMode == true}">
				<c:if test="${isProfessor}">
					<button class="btn btn-primary" form="editForm" type="submit">저장</button>
					<button class="btn btn-gray" onclick="toRead()">취소</button>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${isProfessor}">
					<button class="btn btn-primary" onclick="toEdit()">수정</button>
					<button class="btn btn-gray" onclick="goDelete()">삭제</button>
				</c:if>
			</c:otherwise>
		</c:choose>
	</div>

	<!-- ===== 읽기 모드 ===== -->
	<c:if test="${editMode != true}">
		<div class="title">${lecNotice.lecNoticeName}</div>
		<div class="meta">
			작성자: ${lecNotice.profesId} &nbsp;&nbsp;|&nbsp;&nbsp; 작성일:
			<fmt:formatDate value="${lecNotice.lecNoticeDate}"
				pattern="yyyy-MM-dd HH:mm" />
			&nbsp;&nbsp;|&nbsp;&nbsp; 조회수: ${lecNotice.viewCnt}
		</div>

		<div class="content">${lecNotice.lecNoticeDesc}</div>

		<c:if
			test="${
      (lecNotice.fileName ne 'none.pdf' && not empty lecNotice.fileName)
      || (lecNotice.fileDetail ne 'none.pdf' && not empty lecNotice.fileDetail)
  }">
			<div class="files">
				<img src="${ctx}/resources/images/clip.png" alt="첨부"
					class="clip-icon" />
				<c:if
					test="${lecNotice.fileName ne 'none.pdf' && not empty lecNotice.fileName}">
					<a
						href="${ctx}/lecnotice/file?lecNoticeId=${lecNotice.lecNoticeId}&which=1">${lecNotice.fileName}</a>
				</c:if>
				<c:if
					test="${
          (lecNotice.fileName ne 'none.pdf' && not empty lecNotice.fileName)
          && (lecNotice.fileDetail ne 'none.pdf' && not empty lecNotice.fileDetail)
      }">, </c:if>
				<c:if
					test="${lecNotice.fileDetail ne 'none.pdf' && not empty lecNotice.fileDetail}">
					<a
						href="${ctx}/lecnotice/file?lecNoticeId=${lecNotice.lecNoticeId}&which=2">${lecNotice.fileDetail}</a>
				</c:if>
			</div>
		</c:if>

		<!-- 본문 끝 구분선 + 목록 버튼 (선 바로 밑, 오른쪽 고정) -->
		<div class="sep-and-btn">
			<div class="end-sep"></div>
			<div class="list-btn-fixed">
				<button class="btn btn-primary" onclick="goList()">목록</button>
			</div>
		</div>
	</c:if>

	<!-- ===== 수정 모드 ===== -->
	<c:if test="${editMode == true && isProfessor}">
		<form id="editForm" method="post" action="${ctx}/lecnotice/update"
			enctype="multipart/form-data">
			<input type="hidden" name="lecNoticeId"
				value="${lecNotice.lecNoticeId}" />

			<div class="row">
				<input type="text" name="lecNoticeName"
					value="${lecNotice.lecNoticeName}" />
			</div>

			<div class="row meta">
				작성자: ${lecNotice.profesId} &nbsp;&nbsp;|&nbsp;&nbsp; 작성일:
				<fmt:formatDate value="${lecNotice.lecNoticeDate}"
					pattern="yyyy-MM-dd HH:mm" />
			</div>

			<div class="row">
				<textarea name="lecNoticeDesc">${lecNotice.lecNoticeDesc}</textarea>
			</div>

			<div class="uploader">
				<label for="files" class="btn-file">파일 선택</label> <input id="files"
					name="files" type="file" multiple style="display: none;">
				<c:set var="hasAny"
					value="${
        (lecNotice.fileName ne 'none.pdf' && not empty lecNotice.fileName)
        || (lecNotice.fileDetail ne 'none.pdf' && not empty lecNotice.fileDetail)
      }" />
				<span id="selectedNames" class="hint"
					style="<c:if test='${hasAny}'>display:none;</c:if>">선택된 파일
					없음</span>

				<div class="attached-list" id="attachedList">
					<c:if
						test="${lecNotice.fileName ne 'none.pdf' && not empty lecNotice.fileName}">
						<span class="attached-item" data-which="1"> <a
							href="${ctx}/lecnotice/file?lecNoticeId=${lecNotice.lecNoticeId}&which=1">${lecNotice.fileName}</a>
							<button type="button" class="remove-x" data-which="1">×</button>
						</span>
					</c:if>
					<c:if
						test="${lecNotice.fileDetail ne 'none.pdf' && not empty lecNotice.fileDetail}">
						<span class="attached-item" data-which="2"> <a
							href="${ctx}/lecnotice/file?lecNoticeId=${lecNotice.lecNoticeId}&which=2">${lecNotice.fileDetail}</a>
							<button type="button" class="remove-x" data-which="2">×</button>
						</span>
					</c:if>
				</div>
			</div>

			<input type="hidden" name="removeFile1" id="removeFile1"> <input
				type="hidden" name="removeFile2" id="removeFile2">
		</form>
	</c:if>

	<form id="deleteForm" method="get" action="${ctx}/lecnotice/delete"
		style="display: none;">
		<input type="hidden" name="lecNoticeId"
			value="${lecNotice.lecNoticeId}" />
	</form>

	<script>
  function toEdit() { location.href = "${ctx}/lecnotice/detail?lecNoticeId=${lecNotice.lecNoticeId}&mode=edit"; }
  function toRead() { location.href = "${ctx}/lecnotice/detail?lecNoticeId=${lecNotice.lecNoticeId}"; }
  function goDelete() { if (confirm("공지사항을 삭제하시겠습니까?")) document.getElementById("deleteForm").submit(); }
  function goList() { location.href = "${ctx}/lecnotice/list"; }

  const filesInput = document.getElementById('files');
  const attachedList = document.getElementById('attachedList');
  const selectedNames = document.getElementById('selectedNames');

  function createTempFileItem(file, index) {
    const span = document.createElement('span');
    span.className = 'attached-item temp-file';
    span.dataset.tempIndex = index;
    const nameLink = document.createElement('span');
    nameLink.style.color = '#007bff';
    nameLink.style.textDecoration = 'underline';
    nameLink.textContent = file.name;
    const removeBtn = document.createElement('button');
    removeBtn.type = 'button';
    removeBtn.className = 'remove-x';
    removeBtn.textContent = '×';
    removeBtn.addEventListener('click', function () {
      span.remove();
      updateSelectedLabel();
    });
    span.appendChild(nameLink);
    span.appendChild(removeBtn);
    return span;
  }

  function updateSelectedLabel() {
    const hasItems = attachedList.querySelectorAll('.attached-item').length > 0;
    selectedNames.style.display = hasItems ? 'none' : 'inline';
    if (!hasItems) selectedNames.textContent = '선택된 파일 없음';
  }

  if (filesInput) {
    filesInput.addEventListener('change', function () {
      Array.from(filesInput.files).forEach((file, idx) => {
        attachedList.appendChild(createTempFileItem(file, idx));
      });
      updateSelectedLabel();
    });
  }

  document.querySelectorAll('.remove-x').forEach(btn => {
    btn.addEventListener('click', function () {
      const which = this.dataset.which;
      const hidden = document.getElementById('removeFile' + which);
      if (hidden) hidden.value = 'on';
      this.closest('.attached-item').remove();
      updateSelectedLabel();
    });
  });

  updateSelectedLabel();
</script>
</body>
</html>
