<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>강의 계획서 PDF 저장</title>

<!-- html2pdf, html에 있는 특정 구간을 pdf로써 저장 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>

<style>
  :root {
    --border: #aaa;
    --shade: #f7f7f7;
    --title: #333;
    --label: #555;
    --A4W: 794px;
    --A4H: 1123px;
  }
  body {
    font-family: "Noto Sans KR", sans-serif;
    background: #fafafa;
    margin: 0;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .page {
    width: var(--A4W);
    background: #fff;
    border: 1px solid var(--border);
    box-sizing: border-box;
    padding: 20px;
    position: relative;
  }
  .header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 20px;
  }
  h1 {
    font-size: 28px;
    font-weight: bold;
    letter-spacing: 8px;
    margin: 0;
  }
  .prof-info {
    border: 1px solid #aaa;
    border-collapse: collapse;
    font-size: 14px;
    width: 180px;
  }
  .prof-info td {
    border: 1px solid #aaa;
    padding: 6px 12px;
  }
  .prof-info .title {
    color: #d9534f;
    font-weight: bold;
  }
  table {
    border-collapse: collapse;
    width: 100%;
    font-size: 14px;
    margin-bottom: 15px;
    table-layout: fixed;
  }
  th, td {
    border: 1px solid #aaa;
    padding: 6px 10px;
    text-align: left;
  }
  th {
    width: 120px;
    background: #f9f9f9;
  }
  .equal-cols td {
    width: 50%;
  }
  input[type="text"] {
    border: none;
    width: 100%;
    background: transparent;
    font-size: 14px;
    outline: none;
  }
  .checkboxes label {
    display: inline-block;
    margin-right: 20px;
    cursor: pointer;
  }
  .checkboxes input[type="checkbox"] {
    appearance: none;
    width: 14px;
    height: 14px;
    border: 1px solid #333;
    vertical-align: middle;
    margin-right: 4px;
  }
  .checkboxes input[type="checkbox"]:checked {
    background: #000;
  }
  .weeks-table th {
    text-align: center;
    background: #f1f1f1;
  }
  .weeks-table td input {
    border: none;
    width: 100%;
    background: transparent;
    padding: 4px;
  }
  .weeks-table td.seq {
    text-align: center;
    width: 40px;
    background: #f9f9f9;
  }
  .actions {
    margin-top: 15px;
    display: flex;
    justify-content: flex-end;
    gap: 8px;
    width: var(--A4W);
  }
  button {
    padding: 9px 16px;
    font-size: 14px;
    border: 1px solid var(--border);
    cursor: pointer;
    border-radius: 4px;
    background: #eee;
  }
  button.primary {
    background: #00b894;
    color: #fff;
    border-color: #00b894;
  }
  
  .btn-upload {
  background: #e74c3c;
  color: #fff;
  border-color: #e74c3c;
}
</style>
</head>
<body>
  <div class="page" id="printArea">
    <div class="header">
      <h1>강 의 계 획 서</h1>
      <table class="prof-info">
        <tr><td class="title">교수명</td></tr>
        <tr><td>교수학과</td></tr>
        <tr><td>연락처</td></tr>
      </table>
    </div>

    <table class="equal-cols">
      <tr>
        <th>수업 ID</th>
        <td><input type="text"></td>
        <th>수강대상</th>
        <td><input type="text"></td>
      </tr>
      <tr>
        <th>교과목명</th>
        <td><input type="text"></td>
        <th>교과시간</th>
        <td><input type="text"></td>
      </tr>
      <tr>
        <th>수업목표</th>
        <td colspan="3"><input type="text"></td>
      </tr>
      <tr>
        <th>수업개요</th>
        <td colspan="3"><input type="text"></td>
      </tr>
      <tr>
        <th>수업방식</th>
        <td colspan="3">
          <div class="checkboxes">
            <label><input type="checkbox">강의</label>
            <label><input type="checkbox">토의</label>
            <label><input type="checkbox">조별발표</label>
            <label><input type="checkbox">실험실습</label>
            <label><input type="checkbox">기타</label>
          </div>
        </td>
      </tr>
      <tr>
        <th>핵심역량</th>
        <td colspan="3"><input type="text"></td>
      </tr>
    </table>

    <table class="weeks-table">
      <thead>
        <tr>
          <th>순번</th>
          <th>강의주제 및 내용</th>
          <th>과제</th>
        </tr>
      </thead>
      <tbody id="weeks-body">
      </tbody>
    </table>
  </div>

  <div class="actions">
  <button type="button" class="btn-upload" id="btnUpload">업로드</button>
  <button type="button" class="btn-cancel" id="btnCancel">취소</button>
  <button type="button" class="btn-submit" id="btnSubmit">다운로드</button>
</div>

<script>
  const weeksBody = document.getElementById('weeks-body');
  for (let i = 1; i <= 16; i++) {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td class="seq">\${i}</td>
      <td><input type="text"></td>
      <td><input type="text"></td>
    `;
    weeksBody.appendChild(tr);
  }

  document.getElementById('btnSubmit').addEventListener('click', function () {
    const element = document.getElementById('printArea');
    const A4_W = 794;
    const A4_H = 1123;

    const contentW = element.scrollWidth;
    const contentH = element.scrollHeight;
    const scale = Math.min(A4_W / contentW, A4_H / contentH, 1);

    element.style.transformOrigin = 'top left';
    element.style.transform = `scale(\${scale})`;

    const opt = {
      margin: 0,
      filename: '강의계획서.pdf',
      image: { type: 'jpeg', quality: 0.98 },
      html2canvas: { scale: 3, useCORS: true, scrollY: 0 },
      jsPDF: { unit: 'px', format: [A4_W, A4_H], orientation: 'portrait' },
      pagebreak: { mode: ['avoid-all'] }
    };
    
    const btnUpload = document.getElementById('btnUpload');
    btnUpload.addEventListener('click', function () {
      window.location.href = '${pageContext.request.contextPath}/lecture/finalupload';
    });

    html2pdf().set(opt).from(element).save().then(() => {
      element.style.transform = 'none';
    });
  });
</script>
</body>
</html>
