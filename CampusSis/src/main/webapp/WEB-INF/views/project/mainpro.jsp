<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<style>
html, body {
	margin: 0;
	padding: 0;
	overflow-x: hidden;
	height: 100%;
}
.table-custom thead tr:first-child th {
    border-top: 3px solid #2ec4b6;
}

/* 행 사이 구분선 */
.table-custom tbody tr {
    border-bottom: 1px solid #dee2e6; /* 원하는 색상으로 조절 가능 */
}

/* 셀 내부 선 제거 */
.table-custom th,
.table-custom td {
    border: none;
    padding: 8px 12px;
}
.table-custom tbody tr:nth-child(n+1){
	cursor:pointer;
}
.table-custom tbody tr:nth-child(n+1):hover {
    background-color: #dffcf9; /* 원하는 색상으로 조절 */
     font-weight: bold; /* 글자 굵게 */
}
</style>
</head>
<body>
	<div class="wrap" style="height: 100vh;">
		<div class="card-header" style="border-bottom: none;">
			<h3 class="card-title ml-2 mt-2 mb-2"
				style="font-size: 25px; font-weight: bold;">프로젝트</h3>
		</div>
		
		<div class="row ml-2 mt-3">
		<div style="width:20px;"></div>
			<h4 style="font-size:18px; color:#2ec4b6; font-weight:600;">최근 업데이트 결과물</h4>
		<div class="ml-auto" style="width:255px;">
		<h4 style="font-size:16px; font-weight:600;">
		현재 평가가 필요한 평가자료 : 3</h4></div>
		</div>
		
		<table class="table table-sm table-custom text-center" style="width:1220px; margin-left:28px; table-layout:fixed;">
    <thead>
        <tr style="background-color:#ebebeb;">
            <th style="width:400px;">프로젝트명</th>
            <th>마감기한</th>
            <th>제출 명</th>
            <th>팀장</th>
            <th>평가현황</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Global Impact Studio</td>
            <td>2025-07-24 23:59</td>
            <td>독서와 토론</td>
            <td>장소진</td>
            <td style="color:#e63232;">미평가</td>
        </tr>
        <tr>
            <td>Climate Adaptation Framework</td>
            <td>2025-07-26 23:59</td>
            <td>도시문화와 디자인</td>
            <td>한노아</td>
            <td style="color:#e63232;">미평가</td>
        </tr>
        <tr>
            <td>Urban Learning Lab</td>
            <td>2025-07-27 23:59</td>
            <td>디지털 이론</td>
            <td>유하민</td>
            <td style="color:#27b863;">평가완료</td>
        </tr>
    </tbody>
</table>
		<div class="row ml-2 mt-3">
		<div style="width:20px;"></div>
			<h4 style="font-size:18px; color:#2ec4b6; font-weight:600;">루브릭</h4>
			<div class="mx-auto">
				<h4 style="font-size:18px; color:#2ec4b6; font-weight:600;">
					루브릭 성장역량</h4>
			</div>
		</div>
		<div class="row" style="width:580px; height:300px; margin-left:20px; border:1px solid #212121;">
<div class="d-flex" style="width:580px; height:40px; border:1px solid #aaaaaa;justify-content: flex-end; align-items: center; padding: 5px;">
    <select name="samester"
            style="margin-right:10px; width:180px; border:1px solid #ced4da; height:100%;">
        <option>루브릭 높은 순</option>
        <option>루브릭 낮은 순</option>
    </select>

    <div style="display:flex; width: 300px;">
        <input name="project_name" class="form-control"
            type="search"
            placeholder="프로젝트명을 입력해주세요." aria-label="Search"
            style="border-top-right-radius:0; border-bottom-right-radius:0;">
        <button class="btn btn-info search-btn-icon" type="submit"
            style="border-top-left-radius:0; border-bottom-left-radius:0; width:40px; height:40px;">
            
        </button>
    </div>
</div>
<table class="table table-sm table-custom text-center" style="width:580px; table-layout:fixed; font-size:12px;">
    <thead>
        <tr style="background-color:#ebebeb;">
            <th style="width:160px;">프로젝트명</th>
            <th>팀장</th>
            <th>최근 제출자료</th>
            <th>제출일</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Global Impact Studio</td>
            <td>김원희</td>
            <td>독서와 토론</td>
            <td>2025-08-27</td>
        </tr>
        <tr>
            <td>Climate Adaptation Framework</td>
            <td>김원희</td>
            <td>도시문화와 디자인</td>
            <td>2025-08-27</td>
        </tr>
        <tr>
            <td>Urban Learning Lab</td>
            <td>김원희</td>
            <td>디지털 이론</td>
            <td>2025-08-27</td>
        </tr>
    </tbody>
</table>
</div>
	</div>