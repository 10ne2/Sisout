<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="me"
	value="${empty member ? sessionScope.loginUser : member}" />

<!-- 프로필 사진 캐시버스터 -->
<c:set var="imgSrc"
	value="${ctx}/member/getPicture?id=${me.mem_id}&v=<%=System.currentTimeMillis()%>" />

<!-- 헤더 -->
<div class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1 class="m-0">마이페이지</h1>
			</div>
		</div>
	</div>
</div>

<section class="content">
	<div class="container-fluid">

		<!-- 프로필/기본정보 카드 -->
		<form id="profileForm" action="${ctx}/member/profile" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="mem_id" value="${me.mem_id}" /> <input
				type="hidden" id="pictureChanged" name="pictureChanged"
				value="false" />

			<div class="card">
				<div class="card-body">
					<div class="d-flex align-items-start">

						<!-- 좌측: 프로필 사진 + 저장 버튼 -->
						<div class="mr-4" style="width: 120px;">
							<div class="position-relative"
								style="width: 120px; height: 120px; border-radius: 8px; overflow: hidden; border: 1px solid #ddd;">
								<img id="profileImg" src="${imgSrc}" alt="프로필"
									style="width: 100%; height: 100%; object-fit: cover;">
								<!-- 사진 선택 트리거 -->
								<button type="button" class="btn btn-light btn-sm"
									style="position: absolute; right: 6px; bottom: 6px; border: 1px solid #ccc; border-radius: 50%; width: 30px; height: 30px; padding: 0;"
									title="사진 변경"
									onclick="document.getElementById('pictureFile').click()">✎</button>
								<input type="file" id="pictureFile" name="pictureFile"
									accept="image/*" style="display: none;">
							</div>

							<!-- 사진 바로 아래 저장 버튼 -->
							<div class="mt-2">
								<button type="submit" class="btn btn-teal w-100">저 장</button>
							</div>
						</div>

						<!-- 우측: 정보 그리드 -->
						<div class="flex-grow-1 w-100">
							<div class="row">
								<!-- 1행: 이름 / 이메일 -->
								<div class="col-md-6 mb-3">
									<div class="text-muted small mb-1">이름</div>
									<div class="form-control bg-light border-0">${me.mem_name}</div>
								</div>
								<div class="col-md-6 mb-3">
									<div class="text-muted small mb-1">이메일</div>
									<div class="form-control bg-light border-0">${me.mem_email}</div>
								</div>

								<!-- 역할별 번호 라벨 -->
								<sec:authorize access="hasAnyAuthority('ROLE_ROLE01','ROLE01')">
									<c:set var="idLabel" value="학번" />
								</sec:authorize>
								<sec:authorize access="hasAnyAuthority('ROLE_ROLE02','ROLE02')">
									<c:set var="idLabel" value="교수번호" />
								</sec:authorize>
								<sec:authorize access="hasAnyAuthority('ROLE_ROLE03','ROLE03')">
									<c:set var="idLabel" value="직원번호" />
								</sec:authorize>
								<sec:authorize access="hasAnyAuthority('ROLE_ROLE04','ROLE04')">
									<c:set var="idLabel" value="관리자번호" />
								</sec:authorize>

								<div class="col-md-6 mb-3">
									<div class="text-muted small mb-1">
										<c:out value="${empty idLabel ? '번호' : idLabel}" />
									</div>
									<div class="form-control bg-light border-0">
										<c:out
											value="${not empty details.number ? details.number : me.mem_id}" />
									</div>
								</div>

								<!-- 연락처 -->
								<div class="col-md-6 mb-3">
									<div class="text-muted small mb-1">연락처</div>
									<div class="form-control bg-light border-0">${me.mem_phone}</div>
								</div>

								<!-- 직위 -->
								<div class="col-md-6 mb-3">
									<div class="text-muted small mb-1">직위</div>
									<div class="form-control bg-light border-0">
										<sec:authorize
											access="hasAnyAuthority('ROLE_ROLE01','ROLE01')">학생</sec:authorize>
										<sec:authorize
											access="hasAnyAuthority('ROLE_ROLE02','ROLE02')">교수</sec:authorize>
										<sec:authorize
											access="hasAnyAuthority('ROLE_ROLE03','ROLE03')">직원</sec:authorize>
										<sec:authorize
											access="hasAnyAuthority('ROLE_ROLE04','ROLE04')">관리자</sec:authorize>
									</div>
								</div>

								<!-- 생년월일 -->
								<c:set var="birth"
									value="${empty me.birthDate ? me.birth_date : me.birthDate}" />
								<div class="col-md-6 mb-3">
									<div class="text-muted small mb-1">생년월일</div>
									<div class="form-control bg-light border-0">
										<c:choose>
											<c:when test="${not empty birth}">
												<fmt:formatDate value="${birth}" pattern="yyyy-MM-dd" />
											</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									</div>
								</div>

								<!-- 주소 -->
								<div class="col-md-12 mb-1">
									<div class="text-muted small mb-1">주소</div>
									<div class="form-control bg-light border-0">${me.mem_add}</div>
								</div>
							</div>
						</div>
					</div>

					<!-- 하단 버튼 -->
					<div class="text-center mt-3">
						<!-- BS4 트리거 사용 -->
						<button type="button" class="btn btn-teal mr-2"
							data-toggle="modal" data-target="#pwModal">비밀번호 변경</button>
						<button type="button" class="btn btn-secondary" id="btnClose">닫
							기</button>
					</div>
				</div>
			</div>
		</form>

		<!-- 학생 전용 학사정보 카드 -->
		<sec:authorize access="hasAnyAuthority('ROLE_ROLE01','ROLE01')">
			<div class="card">
				<div class="card-body">
					<h5 class="mb-3">학사정보</h5>
					<div class="row">
						<div class="col-md-4 mb-3">
							<div class="text-muted small mb-1">입학년도</div>
							<div class="form-control bg-light border-0">
								<c:out
									value="${empty student.entranceYear ? '-' : student.entranceYear}" />
							</div>
						</div>
						<div class="col-md-4 mb-3">
							<div class="text-muted small mb-1">입학일자</div>
							<div class="form-control bg-light border-0">
								<c:choose>
									<c:when test="${not empty student.entranceDate}">
										<fmt:formatDate value="${student.entranceDate}"
											pattern="yyyy-MM-dd" />
									</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="col-md-4 mb-3">
							<div class="text-muted small mb-1">입학학년</div>
							<div class="form-control bg-light border-0">
								<c:out
									value="${empty student.entryGrade ? '-' : student.entryGrade}" />
							</div>
						</div>
						<div class="col-md-4 mb-3">
							<div class="text-muted small mb-1">입학대학</div>
							<div class="form-control bg-light border-0">
								<c:out value="${empty student.college ? '-' : student.college}" />
							</div>
						</div>
						<div class="col-md-4 mb-3">
							<div class="text-muted small mb-1">전공</div>
							<div class="form-control bg-light border-0">
								<c:out value="${empty student.major ? '-' : student.major}" />
							</div>
						</div>
						<div class="col-md-4 mb-3">
							<div class="text-muted small mb-1">부전공</div>
							<div class="form-control bg-light border-0">
								<c:out value="${empty student.minor ? '-' : student.minor}" />
							</div>
						</div>
						<div class="col-md-4 mb-3">
							<div class="text-muted small mb-1">졸업년도(예정)</div>
							<div class="form-control bg-light border-0">
								<c:out
									value="${empty student.gradYear ? '-' : student.gradYear}" />
							</div>
						</div>
						<div class="col-md-4 mb-3">
							<div class="text-muted small mb-1">이수학기수</div>
							<div class="form-control bg-light border-0">
								<c:out
									value="${empty student.semesters ? '-' : student.semesters}" />
							</div>
						</div>
						<div class="col-md-4 mb-3">
							<div class="text-muted small mb-1">졸업전공</div>
							<div class="form-control bg-light border-0">
								<c:out
									value="${empty student.gradMajor ? '-' : student.gradMajor}" />
							</div>
						</div>
						<div class="col-md-4 mb-3">
							<div class="text-muted small mb-1">졸업학과</div>
							<div class="form-control bg-light border-0">
								<c:out
									value="${empty student.gradDept ? '-' : student.gradDept}" />
							</div>
						</div>
						<div class="col-md-4 mb-3">
							<div class="text-muted small mb-1">졸업일자</div>
							<div class="form-control bg-light border-0">
								<c:choose>
									<c:when test="${not empty student.gradDate}">
										<fmt:formatDate value="${student.gradDate}"
											pattern="yyyy-MM-dd" />
									</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>

	</div>
</section>

<!-- 저장 완료 알림 -->
<c:if test="${not empty msg && msg eq 'saved'}">
	<script>alert('변경사항이 저장되었습니다.');</script>
</c:if>

<!-- 스타일 -->
<style>
.form-control.bg-light {
	background: #e9ecef !important;
	color: #333;
}

.btn-teal {
	background: #2EC4B6;
	border-color: #2EC4B6;
	color: #fff;
}

.btn-teal:hover {
	background: #22A99C;
	border-color: #22A99C;
	color: #fff;
}
</style>

<script>
  // 닫기 버튼 동작
  (function () {
    const btn = document.getElementById('btnClose');
    if (!btn) return;
    btn.addEventListener('click', function () {
      if (window.opener && !window.opener.closed) {
        try { window.opener.location.reload(true); } catch(e){}
        window.close(); return;
      }
      var fallback='${ctx}/';
      var target=(document.referrer && document.referrer.indexOf(location.origin)===0)
        ? document.referrer : (location.origin+fallback);
      try{ var url=new URL(target); url.searchParams.set('v',Date.now()); location.replace(url.toString()); }
      catch(e){ location.replace(target); }
    });
  })();
</script>

<script>
  // 사진 미리보기
  (function () {
    const fileInput=document.getElementById('pictureFile');
    const imgEl=document.getElementById('profileImg');
    const changed=document.getElementById('pictureChanged');
    if(!fileInput||!imgEl) return;
    fileInput.addEventListener('change', function(){
      const f=this.files && this.files[0]; if(!f) return;
      const rd=new FileReader(); rd.onload=function(e){ imgEl.src=e.target.result; }; rd.readAsDataURL(f);
      if(changed) changed.value='true';
    });
  })();
</script>

<!-- 비밀번호 변경 모달 (Bootstrap 4 규격) -->
<div class="modal fade" id="pwModal" tabindex="-1"
	aria-labelledby="pwModalLabel" aria-hidden="true" data-backdrop="false">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<form id="pwForm">
				<div class="modal-header">
					<h5 class="modal-title" id="pwModalLabel">비밀번호 변경</h5>
					<!-- X 닫기 (BS4) -->
					<button type="button" class="close" data-dismiss="modal"
						aria-label="닫기">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>

				<div class="modal-body">
					<div class="mb-2">
						<input class="form-control form-control-sm" type="password"
							name="currentPw" placeholder="현재 비밀번호" required>
					</div>
					<div class="mb-2">
						<input class="form-control form-control-sm" type="password"
							name="newPw" placeholder="새 비밀번호(영문+숫자 8~16)"
							pattern="(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}" required>
					</div>
					<div>
						<input class="form-control form-control-sm" type="password"
							name="newPw2" placeholder="새 비밀번호 확인" required>
					</div>
					<div class="small text-muted mt-2">• 비밀번호는 영문+숫자 조합, 8~16자</div>
				</div>

				<div class="modal-footer py-2">
					<button id="pwSubmitBtn" class="btn btn-primary btn-sm"
						type="button">변경하기</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
  // 비밀번호 변경 제출 (BS4 방식으로 모달 닫기)
  (function(){
    var btn=document.getElementById('pwSubmitBtn');
    if(!btn) return;
    btn.addEventListener('click', async function(){
      var f=document.getElementById('pwForm');
      if(!f.reportValidity()) return;
      var cur=f.currentPw.value.trim();
      var npw=f.newPw.value.trim();
      var npw2=f.newPw2.value.trim();
      if(npw!==npw2){ alert('새 비밀번호가 일치하지 않습니다.'); return; }

      try{
        const res=await fetch('${ctx}/member/change-password',{
          method:'POST',
          headers:{'Content-Type':'application/json'},
          body: JSON.stringify({currentPw:cur,newPw:npw})
        });
        const data=await res.json();
        if(data.success){
          alert('비밀번호가 변경되었습니다. 다시 로그인해주세요.');
          $('#pwModal').modal('hide'); // BS4
          f.reset();
        }else{
          alert(data.message || '변경에 실패했습니다.');
        }
      }catch(e){
        alert('서버 통신 중 오류가 발생했습니다.');
      }
    });
  })();
</script>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Bootstrap 4 bundle (Popper 포함) -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
