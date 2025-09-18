<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>강의계획서 업로드</title>
<style>
  body {
    font-family: "Noto Sans KR", sans-serif;
  }
  .container {
    width: 500px;
    height: 600px;
    border: 1px solid #ddd;
    box-sizing: border-box;
    margin: 20px auto;
    padding: 20px;
    position: relative;
  }
  .attach-box {
    border: 1px solid #ccc;
    background: #fafafa;
    padding: 12px;
    margin-bottom: 20px;
  }
  .attach-box label {
    display: inline-block;
    margin-right: 8px;
  }
  .path-field {
    width: 250px;
    height: 28px;
    border: 1px solid #ddd;
    padding: 4px 6px;
    font-size: 12px;
    vertical-align: middle;
  }
  .actions {
    position: absolute;
    right: 20px;
    bottom: 20px;
    display: flex;
    gap: 8px;
  }
  button, input[type="button"] {
    border: 1px solid #aaa;
    background: #eee;
    border-radius: 3px;
    padding: 6px 14px;
    cursor: pointer;
  }
  .btn-cancel {
    background: #f2f2f2;
  }
  .btn-submit {
    background: #00b894;
    color: #fff;
    border-color: #00b894;
  }
</style>
</head>
<body>
<div class="container">
  <h2>강의계획서 첨부</h2>

  <!-- Tomcat 모듈 경로(/camp_us)를 자동 반영하기 위해 contextPath 사용 -->
  <form id="uploadForm" action="${pageContext.request.contextPath}/lecture/finalupload"
        method="post" enctype="multipart/form-data">

    <div class="attach-box" id="attachDiv">
      <label for="planFile">[강의계획서 첨부]</label>
      <input type="file" id="planFile" name="planFile" accept="application/pdf" style="display:none;" />
      <input type="button" id="btnChooseFile" value="강의계획서 첨부" />
      <input type="text" id="filePath" class="path-field" readonly placeholder="선택된 파일 경로" />
    </div>

    <div class="actions">
      <button type="button" class="btn-cancel" id="btnCancel">취소</button>
      <button type="button" class="btn-submit" id="btnSubmit">등록</button>
    </div>
  </form>
</div>

<script>
  const planFile = document.getElementById('planFile');
  const btnChooseFile = document.getElementById('btnChooseFile');
  const filePath = document.getElementById('filePath');
  const btnCancel = document.getElementById('btnCancel');
  const btnSubmit = document.getElementById('btnSubmit');
  const uploadForm = document.getElementById('uploadForm');

  btnChooseFile.addEventListener('click', function () {
    planFile.click();
  });

  planFile.addEventListener('change', function () {
    if (!planFile.files || planFile.files.length === 0) {
      filePath.value = "";
      return;
    }

    if (planFile.files.length > 1) {
      alert("한 개의 PDF 파일만 선택하세요.");
      planFile.value = "";
      filePath.value = "";
      return;
    }

    const file = planFile.files[0];
    const name = file.name.toLowerCase();
    const isPdfExt = name.endsWith(".pdf");
    const isPdfMime = (file.type === "application/pdf");

    if (!isPdfExt || !isPdfMime) {
      alert("PDF 파일만 선택 가능합니다.");
      planFile.value = "";
      filePath.value = "";
      return;
    }

    filePath.value = file.name; // 브라우저 보안상 전체 경로는 보이지 않을 수 있음
  });

  btnCancel.addEventListener('click', function () {
    planFile.value = "";
    filePath.value = "";
  });

  btnSubmit.addEventListener('click', function () {
    if (!planFile.files || planFile.files.length === 0) {
      alert("파일을 선택하세요.");
      return;
    }
    uploadForm.submit();
  });
</script>
</body>
</html>
