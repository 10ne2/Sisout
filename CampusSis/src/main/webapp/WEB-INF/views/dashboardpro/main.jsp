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
                    <option selected="selected" data-select2-id="27">과목을 선택해주세요</option>
                    <option data-select2-id="99">독서와 토론</option>
                    <option disabled="disabled" data-select2-id="100">글쓰기와 프레젠테이션</option>
                    <option data-select2-id="101">문제 해결 프로젝트 실습</option>
                    <option data-select2-id="102">협업과 디자인씽킹</option>
                    <option data-select2-id="103">캡스톤 디자인</option>
                    <option data-select2-id="104">AI·빅데이터 실무 워크숍</option>
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
									style="width: 20%; background-color: #f5f5f5;">제출 마감일</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center"
									style="width: 10%; background-color: #f5f5f5;">등록상태</th>
								<th
									class="jsgrid-header-cell jsgrid-align-center"
									style="width: 10%; background-color: #f5f5f5;">등록률</th>
							</tr>
						</table>
					</div>
					<div style="height: 250px; overflow-y: auto; ">
						<table class="jsgrid-table">
							<tbody>
										<tr class="clickable" style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">독서와 토론</td>
											<td class="enddate jsgrid-cell jsgrid-align-center"
												style="width: 40%;">독서 기반 토론: 사고력과 표현력의 확장</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 20%;">2025-09-01 23:59</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">진행중</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">30%</td>
										</tr>
										<tr class="clickable" style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">독서와 토론</td>
											<td class="enddate jsgrid-cell jsgrid-align-center"
												style="width: 40%;">읽기와 토론을 통한 의사소통 능력 향상</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 20%;">2025-08-25 23:59</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">진행중</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">33%</td>
										</tr>
										<tr class="clickable" style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">글쓰기와 프레젠테이션</td>
											<td class="enddate jsgrid-cell jsgrid-align-center"
												style="width: 40%;">논리적 글쓰기와 효과적인 발표 훈련</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 20%;">2025-08-20 23:59</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">진행중</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">50%</td>
										</tr>
										<tr class="clickable" style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">독서와 토론</td>
											<td class="enddate jsgrid-cell jsgrid-align-center"
												style="width: 40%;">독서와 토론: 사고 공유와 관점 확장</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 20%;">2025-08-21 23:59</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">진행중</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">60%</td>
										</tr>
										<tr class="clickable" style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">글쓰기와 프레젠테이션</td>
											<td class="enddate jsgrid-cell jsgrid-align-center"
												style="width: 40%;">효과적 메시지 전달을 위한 글쓰기와 발표</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 20%;">2025-08-13 23:59</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">진행중</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">87%</td>
										</tr>
										<tr class="clickable" style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">문제 해결 프로젝트 실습</td>
											<td class="enddate jsgrid-cell jsgrid-align-center"
												style="width: 40%;">프로젝트 중심 문제 해결 역량 강화</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 20%;">2025-08-11 23:59</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">진행중</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 10%;">90%</td>
										</tr>
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
										<tr class="clickable" 
											style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%; color:#ff0000; font-weight:bold;">D-Day</td>
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">14:00</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 60%;">독서와 토론</td>
										</tr>
										<tr class="clickable" 
											style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">D-1</td>
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">14:00</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 60%;">AI·빅데이터 실무 워크숍</td>
										</tr>
										<tr class="clickable" 
											style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">D-2</td>
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">09:00</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 60%;">글쓰기와 프레젠테이션</td>
										</tr>
										<tr class="clickable" 
											style="background-color: white; line-height: 24px; border-bottom: 1px solid #e0e0e0; width: 100%; height: 100%;cursor: pointer;">
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">D-2</td>
											<td class="dday jsgrid-cell jsgrid-align-center"
												style="width: 20%;">14:00</td>
											<td class="jsgrid-cell jsgrid-align-center"
												style="width: 60%;">캡스톤 디자인</td>
										</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div style="width: 65%; height: 100%;">
				<div>
					<span
						style="display: block; font-size: 16pt; font-weight: bold; color: #2EC4B6; margin-bottom: 10px;">출결 이의신청 내역</span>
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
										style="width: 20%; background-color: #f5f5f5;">신청날짜</th>
									<th
										class="jsgrid-header-cell jsgrid-align-center"
										style="width: 20%; background-color: #f5f5f5;">학생명</th>
									<th
										class="jsgrid-header-cell jsgrid-align-center"
										style="width: 20%; background-color: #f5f5f5;">과목명</th>
									<th
										class="jsgrid-header-cell jsgrid-align-center"
										style="width: 40%; background-color: #f5f5f5;">제목</th>
								</tr>
							</table>
						</div>
						<div style="height: 164px;">
							<table class="jsgrid-table">
								<tbody>
											<tr class="clickable jsgrid-row" data-url="homeworkDetail.do?hwId=${hw.hw_id}" style="cursor: pointer;">
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%;">
													2025. 08.13.</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%;">김원희</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%;">독서와 토론</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 40%;">출결 이의신청 있습니다.</td>
											</tr>
											<tr class="clickable jsgrid-row" data-url="homeworkDetail.do?hwId=${hw.hw_id}" style="cursor: pointer;">
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%; ">
													2025. 08.12.</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%;">김선범</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%;">AI·빅데이터 실무 워크숍</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 40%;">안녕하세요. 교수님 AI 수업 듣는 김선범입니다.</td>
											</tr>
											<tr class="clickable jsgrid-row" data-url="homeworkDetail.do?hwId=${hw.hw_id}" style="cursor: pointer;">
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%; ">
													2025. 08.12.</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%;">권오규</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%;">캡스톤 디자인</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 40%;">출결 이의신청합니다.</td>
											</tr>
											<tr class="clickable jsgrid-row" data-url="homeworkDetail.do?hwId=${hw.hw_id}" style="cursor: pointer;">
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%; ">
													2025. 08.11.</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%;">김민주</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 20%;">문제 해결 프로젝트 실습</td>
												<td class="jsgrid-cell jsgrid-align-center"
													style="width: 40%;">안녕하세요, 교수님. 출결 이의신청 있습니다.</td>
											</tr>
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
