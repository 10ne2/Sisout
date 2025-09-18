<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.scroll-table-wrapper-wu {
	/* 테이블 최대 높이 */
	overflow-y: auto; /* 세로 스크롤 */
	/* 선택: 테두리 */
}

.scroll-table-wrapper-wc {
	/* 테이블 최대 높이 */
	overflow-y: auto; /* 세로 스크롤 */
	/* 선택: 테두리 */
}
</style>

<body>
	<div class="card-body" style="margin-bottom: -20px">
		<span style="font-size: 18pt; font-weight: bold;">강의실</span>
	</div>
	<div class="card-body" style="margin-bottom: -20px;">
		<div>
			<span
				style="display: block; font-size: 16pt; font-weight: bold; color: #2EC4B6; margin-bottom: 10px;">미제출
				과제</span>
		</div>
		<div id="homeworkGrid"
			style="border: 1px solid #b5b5b5; border-top: 3px solid #2EC4B6;">
			<div class="jsgrid"
				style="position: relative; height: 100%; width: 100%;">
				<div class="jsgrid-header" style="border-bottom: 1px solid #b5b5b5;">
					<table class="jsgrid-table" style="width: 100%;">
						<tr class="jsgrid-header-row">
							<th
								class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
								style="background-color: #f5f5f5;">D-day</th>
							<th
								class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
								style="width: 20%; background-color: #f5f5f5;">마감일</th>
							<th
								class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
								style="width: 40%; background-color: #f5f5f5;">과제명</th>
							<th
								class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
								style="width: 20%; background-color: #f5f5f5;">과목명</th>
							<th
								class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
								style="width: 10%; background-color: #f5f5f5;">교수명</th>
						</tr>
					</table>
				</div>
				<div class="jsgrid-body " style="height: 124px;">
					<table class="jsgrid-table scroll-table-wrapper-wu">
						<tbody>
							<c:forEach var="hw" items="${unsubmitList}">
								<tr class="unsubmit jsgrid-row"
									style="width: 100%; height: 100%;">
									<td class="dday jsgrid-cell jsgrid-align-center"
										style="width: 10%;">D-${hw.d_day }</td>
									<td class="enddate jsgrid-cell jsgrid-align-center"
										style="width: 20%;">${hw.hw_enddateStr}</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 40%;">${hw.hw_name}</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">${hw.lec_name}</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 10%;">${hw.mem_name}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="card-body"
		style="display: flex; flex-direction: row; justify-content: space-around; gap: 3px; margin-bottom: -20px;">
		<div style="width: 35%; height: 100%; margin-right: 20px">
			<div>
				<span
					style="display: block; font-size: 16pt; font-weight: bold; color: #2EC4B6; margin-bottom: 10px;">예정된
					강의</span>
			</div>
			<div id="예정된 강의" class="jsgrid"
				style="position: relative; height: 100%; width: 100%; border: 1px solid #b5b5b5; border-top: 3px solid #2EC4B6;">
				<div class="jsgrid-body" style="height: 205px;">
					<table class="jsgrid-table scroll-table-wrapper-wc">
						<tbody>
							<c:forEach var="cl" items="${comingleclist}">
								<tr class="jsgrid-row">
									<td class="dday jsgrid-cell jsgrid-align-center"
										style="width: 20%;">D-${cl.d_day }</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 50%;">${cl.lec_name }</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 30%;">${cl.mem_name }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div style="width: 65%; height: 100%;">
			<div>
				<span
					style="display: block; font-size: 16pt; font-weight: bold; color: #2EC4B6; margin-bottom: 10px;">공지사항</span>
			</div>
			<div id="공지사항" class="jsgrid"
				style="position: relative; height: 100%; width: 100%; border: 1px solid #b5b5b5; border-top: 3px solid #2EC4B6;">
				<div class="jsgrid-body" style="height: 100%;">
					<div class="jsgrid-header"
						style="border-bottom: 1px solid #b5b5b5;">
						<table class="jsgrid-table" style="">
							<tr class="jsgrid-header-row">
								<th
									class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
									style="width: 10%; background-color: #f5f5f5;"></th>
								<th
									class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
									style="width: 20%; background-color: #f5f5f5;">과목명</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
									style="width: 40%; background-color: #f5f5f5;">제목</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
									style="width: 10%; background-color: #f5f5f5;">교수명</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
									style="width: 20%; background-color: #f5f5f5;">등록일</th>
							</tr>
						</table>
					</div>
					<div class="jsgrid-body" style="height: 164px;">
					<table class="jsgrid-table" >
						<tbody>
							<c:forEach var="no" items="${noticeList}">
								<tr class="jsgrid-row">
									<td class="jsgrid-cell jsgrid-align-center" style="width: 10%;">${no.inNew }</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">${no.subjectName}</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 40%;">${no.title }</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 10%;">${no.professorName }</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">${no.regDateStr }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="card-body"
		style="display: flex; flex-direction: row; justify-content: space-around; gap: 3px;">
		<div style="width: 35%; height: 100%; margin-right: 20px">
			<div>
				<span
					style="display: block; font-size: 16pt; font-weight: bold; color: #2EC4B6; margin-bottom: 10px;">금주의
					출결</span>
			</div>
			<div class="progress progress-sm"
				style="height: 30px; margin-bottom: 10px; border-radius: 15px">
				<div class="progress-bar"
					style="width: 80%; height: 100%; border-radius: 15px; background-color: #2EC4B6">
					<span
						style="display: block; width: 100%; height: 5%; text-align: right; font-size: 17px; margin-right: 10px;">80%</span>
				</div>
			</div>
			<div id="금주의 출결" class="jsgrid"
				style="position: relative; height: 100%; width: 100%; border: 1px solid #b5b5b5; border-top: 3px solid #2EC4B6;">
				<div class="jsgrid-body" style="height: 100%;">
					<table class="jsgrid-table">
						<tbody>
							<c:forEach var="att" items="${attendenceList}">
								<tr class="jsgrid-row">
									<td class="jsgrid-cell jsgrid-align-center" style="width: 25%;">${att.classDateStr }</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 35%;">${att.subjectName }</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">${att.professorName }</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">${att.attendanceStatus }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div style="width: 65%; height: 100%;">
			<div>
				<span
					style="display: block; font-size: 16pt; font-weight: bold; color: #2EC4B6; margin-bottom: 10px;">자료실</span>
			</div>
			<div id="자료실" class="jsgrid"
				style="position: relative; height: 100%; width: 100%; border: 1px solid #b5b5b5; border-top: 3px solid #2EC4B6;">
				<div class="jsgrid-body" style="height: 100%;">
					<div class="jsgrid-header"
						style="border-bottom: 1px solid #b5b5b5;">
						<table class="jsgrid-table">
							<tr class="jsgrid-header-row">
								<th
									class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
									style="width: 10%; background-color: #f5f5f5;"></th>
								<th
									class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
									style="width: 20%; background-color: #f5f5f5;">과목명</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
									style="width: 40%; background-color: #f5f5f5;">제목</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
									style="width: 10%; background-color: #f5f5f5;">교수명</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center jsgrid-header-sortable"
									style="width: 20%; background-color: #f5f5f5;">등록일</th>
							</tr>
						</table>
					</div>
					<table class="jsgrid-table">
						<tbody>
							
								<tr class="jsgrid-row">
									<td class="jsgrid-cell jsgrid-align-center" style="width: 10%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 40%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 10%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">zz</td>
								</tr>
							<tr class="jsgrid-row">
									<td class="jsgrid-cell jsgrid-align-center" style="width: 10%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 40%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 10%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">zz</td>
								</tr><tr class="jsgrid-row">
									<td class="jsgrid-cell jsgrid-align-center" style="width: 10%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 40%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 10%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">zz</td>
								</tr><tr class="jsgrid-row">
									<td class="jsgrid-cell jsgrid-align-center" style="width: 10%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 40%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 10%;">zz</td>
									<td class="jsgrid-cell jsgrid-align-center" style="width: 20%;">zz</td>
								</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script>
document.querySelectorAll(".dday").forEach(td => {
    let text = td.textContent.trim();

    // D-0 → D-Day로 변경
    if (text === "D-0") {
      td.textContent = "D-Day";
      text = "D-Day";
    }

    // D-Day인 경우, 해당 <tr> 배경색 변경
    if (text === "D-Day") {
      const tr = td.parentElement; // <td>의 부모인 <tr>
      if (tr) {
        tr.style.backgroundColor = "#ffefef";
      }
    }
  });
</script>

</body>
