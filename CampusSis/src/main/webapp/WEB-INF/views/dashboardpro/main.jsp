<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>

</style>

	<div style="height: 900px">
		<div class="card-body" style="margin-bottom: -25px">
			<span style="font-size: 18pt; font-weight: bold;">강의실</span>
		</div>
		<div class="card-body" style=" margin-bottom: -25px;">
			<div class="form-group" data-select2-id="98" style="display: flex; flex-direction: row;">
                  <select class="form-control select2bs4 select2-hidden-accessible" style="width: 300px;" data-select2-id="25" tabindex="-1" aria-hidden="true">
                    <option selected="selected" data-select2-id="27">Alabama</option>
                    <option data-select2-id="99">Alaska</option>
                    <option disabled="disabled" data-select2-id="100">California (disabled)</option>
                    <option data-select2-id="101">Delaware</option>
                    <option data-select2-id="102">Tennessee</option>
                    <option data-select2-id="103">Texas</option>
                    <option data-select2-id="104">Washington</option>
                  </select>
                  <button style="width:180px; height:38px; margin-left: 10px; text-align:center; line-height: 18px; background-color:#2EC4B6; border:#2EC4B6"
                  type="button" class="btn btn-block btn-primary btn-lg">실시간 강의 개설</button>
            </div>
			<div>
				<span
					style="display: block; font-size: 16pt; font-weight: bold; color: #2EC4B6; margin-bottom: 10px;">미등록 피드백</span>
			</div>
			<div style="border: 1px solid #b5b5b5; border-top: 3px solid #2EC4B6;">
				<div style="position: relative; height: 100%; width: 100%;">
					<div class="jsgrid-header-scrollbar"style="border-bottom: 1px solid #b5b5b5;">
						<table class="jsgrid-table" style="width: 100%;">
							<tr class="jsgrid-header-row ">
								<th
									class="jsgrid-header-cell jsgrid-align-center"
									style="width: 20%; background-color: #f5f5f5;">과목명</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center"
									style="width: 40%; background-color: #f5f5f5;">과제명</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center"
									style="width: 10%; background-color: #f5f5f5;">제출 마감일</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center"
									style="width: 20%; background-color: #f5f5f5;">등록상태</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center"
									style="width: 10%; background-color: #f5f5f5;">등록률</th>
							</tr>
						</table>
					</div>
					<div style="height: 250px; overflow-y: auto; ">
						<table class="jsgrid-table">
							<tbody>
								<c:if test="${empty unsubmitList }">
									<tr>
										<td colspan="5" class="text-center" style="font-size: 30px; font-weight: bold; color: #e0e0e0; line-height: 122px">미등록 피드백이 없습니다.</td>
									</tr>
								</c:if>
								<c:if test="${not empty unsubmitList }">
									<c:forEach var="hw" items="${unsubmitList}">
										<tr class="clickable" style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 10%;">D-${hw.d_day }</td>
											<td class="enddate jsgrid-cell jsgrid-align-center"
												style="width: 20%;">${hw.hw_enddateStr}</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 40%;">${hw.hw_name}</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 20%;">${hw.lec_name}</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">${hw.mem_name}</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="card-body"
			style="height: 320px; display: flex; flex-direction: row; justify-content: space-around; gap: 3px; margin-bottom: -25px;">
			<div style="width: 35%; height: 100%; margin-right: 20px">
				<div>
					<span
						style="display: block; font-size: 16pt; font-weight: bold; color: #2EC4B6; margin-bottom: 10px;">예정된
						강의</span>
				</div>
				<div id="예정된 강의" class="jsgrid"
					style="position: relative; height: 100%; width: 100%; border: 1px solid #b5b5b5; border-top: 3px solid #2EC4B6;">
					<div style="height: 230px; overflow-y: auto;">
						<table class="jsgrid-table">
							<tbody>
								<c:if test="${empty comingleclist }">
									<tr>
										<td colspan="5" class="text-center" style="font-size: 30px; font-weight: bold; color: #e0e0e0; line-height: 204px">예정된 강의가 없습니다.</td>
									</tr>
								</c:if>
								<c:if test="${not empty comingleclist }">
									<c:forEach var="cl" items="${comingleclist}">
										<tr class="clickable" 
											style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">D-${cl.d_day }</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 50%;">${cl.lec_name }</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 30%;">${cl.mem_name }</td>
										</tr>
									</c:forEach>
								</c:if>
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
										class="jsgrid-header-cell jsgrid-align-center"
										style="width: 10%; background-color: #f5f5f5;"></th>
									<th
										class="jsgrid-header-cell jsgrid-align-center"
										style="width: 20%; background-color: #f5f5f5;">과목명</th>
									<th
										class="jsgrid-header-cell jsgrid-align-center"
										style="width: 40%; background-color: #f5f5f5;">제목</th>
									<th
										class="jsgrid-header-cell jsgrid-align-center"
										style="width: 10%; background-color: #f5f5f5;">교수명</th>
									<th
										class="jsgrid-header-cell jsgrid-align-center"
										style="width: 20%; background-color: #f5f5f5;">등록일</th>
								</tr>
							</table>
						</div>
						<div style="height: 164px;">
							<table class="jsgrid-table">
								<tbody>
									<c:if test="${empty noticeList }">
										<tr>
											<td colspan="5" class="text-center" style="font-size: 30px; font-weight: bold; color: #e0e0e0; line-height: 164px">등록된 공지사항이 없습니다.</td>
										</tr>
									</c:if>
									<c:if test="${not empty noticeList }">
										<c:forEach var="no" items="${noticeList}">
											<tr class="clickable jsgrid-row" data-url="homeworkDetail.do?hwId=${hw.hw_id}" style="cursor: pointer;">
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 10%; color: #F46060; font-weight: bold;">
													${no.isNew == 0 ? 'NEW' : ''}</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%;">${no.subjectName}</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 40%;">${no.title }</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 10%;">${no.professorName }</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%;">${no.regDateStr }</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
	document.querySelectorAll(".dday").forEach(td => {
	    let text = td.textContent.trim();
	
	    if (text === "D-0") {
	      td.textContent = "D-Day";
	      td.style.color = "#F46060";
	      td.style.fontWeight = "bold";
	      text = "D-Day";
	    }
	    
		if (text === "D-Day") {
			const tr = td.closest("tr")
			tr.style.backgroundColor = "#DFFCF9";
		}
	});
</script>

</body>
</html>
