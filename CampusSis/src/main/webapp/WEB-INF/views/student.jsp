<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="wrapper">
	<!-- Preloader -->
	<div
		class="preloader flex-column justify-content-center align-items-center">
		<img class="animation__shake"
			src="<%=request.getContextPath()%>/resources/bootstrap/dist/img/Camp_usLogo.png"
			alt="Camp_usLogo" height="120" width="240">
	</div>

	<!-- Navbar -->
	<nav class="main-header navbar navbar-expand navbar-white navbar-light"
		style="height: 70px;">
		<!-- Left navbar links -->
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" data-widget="pushmenu"
				href="#" role="button"><i class="fas fa-bars"></i></a></li>
		</ul>

		<!-- Right navbar links -->
		<ul class="navbar-nav ml-auto">
			<!-- Messages Dropdown Menu -->
			<li class="nav-item"><a class="nav-link" style="cursor: pointer"
				onclick="OpenWindow('message/main','메일',1600,920);"> <span
					class="custom-icon-message"></span> <span
					class="badge badge-danger navbar-badge">N</span>
			</a></li>
			<div class="ml-2"></div>
			<li>
				<form action="${pageContext.request.contextPath}/logout"
					method="post">
					<button type="submit" class="btn btn-block btn-info btn-flat mt-1"
						style="background-color: #79aaa4; border: none; width: 100px; height: 40px; border-radius: 5px;">
						로그아웃</button>
				</form>
			</li>
			<li>
				<div class="row ml-4 mr-4">학번: ${loginUser.mem_id }</div>
				<div class="row ml-4 mr-4">이름: ${loginUser.mem_name }</div>
			</li>
			<li>
				<div class="image" style="cursor: pointer;"
					onclick="OpenWindow('mypage','글등록',800,700);">
					<img
						src="<%=request.getContextPath() %>/member/getPicture?id=${loginUser.mem_id}"
						class="img-circle img-md" alt="User Image"
						style="width: 45px; height: 45px; object-fit: cover;">
				</div>
			</li>
		</ul>
	</nav>
	<!-- /.navbar -->

	<!-- Main Sidebar Container -->
	<aside class="main-sidebar sidebar-white-primary"
		style="border: 1px solid #dedede; background-color: #f5f5f5;">
		<!-- Brand Logo -->
		<a href="" class="brand-link"> <img
			src="<%=request.getContextPath()%>/resources/bootstrap/dist/img/Camp_usLogo.png"
			alt="camp_us Logo" class="brand-image custom-logo" />
		</a>

		<!-- Sidebar Menu -->
		<nav class="mt-2">
			<ul class="nav nav-pills nav-sidebar flex-column"
				data-widget="treeview" role="menu" data-accordion="true">

				<!-- HOME -->
				<li class="nav-item"><a href="#" class="nav-link"
					onclick='Iframe("<%=request.getContextPath()%>/student/home"); return false;'>
						<i class="nav-icon custom-img-icon"></i>&nbsp;&nbsp;&nbsp;
						<p class="fas">HOME</p>
				</a></li>

				<!-- 학생 -->
				<sec:authorize access='hasAnyRole("ROLE_ROLE01")'>
					<li class="nav-item">
						<!-- 🔁 /lecture/main → /student 로 교체 --> <a href="#"
						class="nav-link"
						 data-url="<%=request.getContextPath() %>/lecDashStu/main">
							<i class="nav-icon fas gang-img-icon"></i>
							<p class="fas">
								&nbsp; 강의실 <i class="right fas fa-angle-left"></i>
							</p>
					</a>
						<ul class="nav nav-treeview">
							<div class="row">
								<div class="col-sm-2"></div>
								<div class="col-sm-9">
									<div class="form-group">
										<select class="custom-select my-border"
											onchange="onLectureChange(this)">
											<option value="">전공을 선택하세요.</option>
											<c:forEach var="stu_lec" items="${stulectureList}">
												<option value="${stu_lec.lec_id}">${stu_lec.lec_name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>

							<!-- 강의계획서 -->
							<li class="nav-item"><a href="#" class="nav-link"
								onclick="goSyllabus(); return false;"> <i
									class="far fas nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;강의계획서</p>
							</a></li>

							<!-- 공지사항 -->
							<li class="nav-item"><a href="#" class="nav-link"
								onclick="goNotice(); return false;"> <i
									class="far fas nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;공지사항</p>
							</a></li>

							<li class="nav-item"><a href="#" class="nav-link"
								onclick="goOnline(); return false;"><i
									class="far fas nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;실시간 강의</p></a></li>
							<li class="nav-item"><a href="#" class="nav-link"
								onclick="goLecvid(); return false;"><i
									class="far nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;온라인 강의</p></a></li>
							<li class="nav-item"><a href="#" class="nav-link"
								onclick="alert('준비중입니다.'); return false;"><i
									class="far nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;출결</p></a></li>
							<li class="nav-item"><a href="#" class="nav-link"
								onclick="alert('준비중입니다.'); return false;"><i
									class="far nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;자료실</p></a></li>

							<!-- 과제제출 -->
							<li class="nav-item"><a href="#" class="nav-link"
								onclick="goHomework(); return false;"> <i
									class="far nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;과제제출</p>
							</a></li>
						</ul>
					</li>
				</sec:authorize>

				<!-- 교수 -->
				<sec:authorize access='hasAnyRole("ROLE_ROLE02")'>
					<li class="nav-item">
						<!-- 🔁 /lecture/main → /student 로 교체 --> <a href="#"
						class="nav-link"
						data-url="<%=request.getContextPath() %>/lecDashPro/main">
							<i class="nav-icon fas gang-img-icon"></i>
							<p class="fas">
								&nbsp; 강의실 <i class="right fas fa-angle-left"></i>
							</p>
					</a>
						<ul class="nav nav-treeview">
							<div class="row">
								<div class="col-sm-2"></div>
								<div class="col-sm-9">
									<div class="form-group">
										<select class="custom-select my-border"
											onchange="onLectureChange(this)">
											<option value="">전공을 선택하세요.</option>
											<c:forEach var="pro_lec" items="${prolectureList}">
												<option value="${pro_lec.lec_id}">${pro_lec.lec_name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>

							<li class="nav-item"><a href="#" class="nav-link"
								onclick="goSyllabus(); return false;"> <i
									class="far fas nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;강의계획서</p>
							</a></li>

							<li class="nav-item"><a href="#" class="nav-link"
								onclick="goNotice(); return false;"> <i
									class="far fas nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;공지사항</p>
							</a></li>

							<li class="nav-item"><a href="#" class="nav-link"
								onclick="goOnline(); return false;"><i
									class="far fas nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;실시간 강의</p></a></li>
							<li class="nav-item"><a href="#" class="nav-link"
								onclick="goLecvid(); return false;"><i
									class="far nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;온라인 강의</p></a></li>
							<li class="nav-item"><a href="#" class="nav-link"
								onclick="alert('준비중입니다.'); return false;"><i
									class="far nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;출결</p></a></li>

							<li class="nav-item"><a href="#" class="nav-link"
								onclick="alert('준비중입니다.'); return false;"> <i
									class="far nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;자료실</p>
							</a></li>

							<li class="nav-item"><a href="#" class="nav-link"
								onclick="goHomework(); return false;"> <i
									class="far nav-icon"></i>
									<p>&nbsp;&nbsp;&nbsp;과제제출</p>
							</a></li>
						</ul>
					</li>
				</sec:authorize>

				<!-- 프로젝트 -->
				<li class="nav-item"><a href="#" class="nav-link" 
            <sec:authorize access="hasRole('ROLE_ROLE01')"> 
            data-url="<%=request.getContextPath() %>/project/main/stu"
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ROLE02')">
            data-url="<%=request.getContextPath() %>/project/main/pro"
            </sec:authorize>
            > <i
						class="nav-icon fas pro-img-icon"></i>
						<p class="fas">
							&nbsp; 프로젝트 <i class="right fas fa-angle-left"></i>
						</p>
				</a>
					<ul class="nav nav-treeview">
						<li class="nav-item"><a href="javascript:return false;"
							class="nav-link"
							<sec:authorize access="hasRole('ROLE_ROLE01')">
                   data-url="<%=request.getContextPath() %>/project/list/stu?mem_id=${loginUser.mem_id }"
                 </sec:authorize>
							<sec:authorize access="hasRole('ROLE_ROLE02')">
                   data-url="<%=request.getContextPath() %>/project/list/pro?mem_id=${loginUser.mem_id }"
                 </sec:authorize>>
								<i class="far nav-icon"></i>
								<p>&nbsp;&nbsp;&nbsp;팀 목록</p>
						</a></li>
						<li class="nav-item"><a href="javascript:return false;"
							class="nav-link"
							<sec:authorize access="hasRole('ROLE_ROLE01')">
                   data-url="<%=request.getContextPath() %>/roadmap/projectlist/stu?mem_id=${loginUser.mem_id }"
                 </sec:authorize>
							<sec:authorize access="hasRole('ROLE_ROLE02')">
                   data-url="<%=request.getContextPath() %>/roadmap/projectlist/pro?mem_id=${loginUser.mem_id }"
                 </sec:authorize>>
								<i class="far fas nav-icon"></i>
								<p>&nbsp;&nbsp;&nbsp;결과물</p>
						</a></li>
					</ul></li>

				<li class="nav-item"><a href="#" class="nav-link"
					data-url="<%=request.getContextPath()%>/boardlist"> <i
						class="nav-icon fas post-img-icon"></i>
						<p class="fas">&nbsp; 게시판</p>
				</a></li>

				

			</ul>
		</nav>
		<!-- /.sidebar-menu -->
	</aside>
	<!-- /.sidebar -->

	<div class="content-wrapper">
		<iframe id="mainFrame" name="ifr" frameborder="0"
			style="width: 100%; height: 90vh;"></iframe>
	</div>

	<aside class="control-sidebar control-sidebar-dark"></aside>
</div>
<!-- ./wrapper -->

<!-- ===== 스크립트 영역(통째로 교체) ===== -->
<script>
  // iframe 직접 이동
  function Iframe(url) {
    document.getElementById('mainFrame').src = url;
  }
</script>
<script>
  // 초기 로딩: 기본은 대시보드
  window.addEventListener('DOMContentLoaded', () => {
    const hash = location.hash.substring(1);
    document.getElementById('mainFrame').src =
      hash ? hash : '<%=request.getContextPath()%>/student/home';
  });
</script>
<script>
  let selectedLecId = null;

  // 전공(강의) 선택
  function onLectureChange(select) {
    selectedLecId = select.value;
    if (!selectedLecId) return;

    // 🔁 /lecture/changeMajor → /lecnotice/changeMajor 로 교체
    fetch('<%=request.getContextPath()%>/lecnotice/changeMajor?lec_id=' + encodeURIComponent(selectedLecId))
      .then(r => r.json()).then(() => {}).catch(() => {});
  }

  // ===== 메뉴별 이동 함수 =====
  // 강의계획서 (너희 프로젝트에 해당 매핑 없으면 임시로 alert로 바꿔도 됨)
  function goSyllabus() {
    if (!selectedLecId) { alert("전공을 먼저 선택하세요."); return; }
    const url = '<%=request.getContextPath()%>/lecture/list?lec_id=' + encodeURIComponent(selectedLecId);
    location.hash = url;
    document.getElementById('mainFrame').src = url;
  }

  // 공지사항
  function goNotice() {
    if (!selectedLecId) { alert("전공을 먼저 선택하세요."); return; }
    const url = '<%=request.getContextPath()%>/lecnotice/list?lec_id=' + encodeURIComponent(selectedLecId);
    location.hash = url;
    document.getElementById('mainFrame').src = url;
  }

  // 과제제출
  function goHomework() {
    if (!selectedLecId) { alert("전공을 먼저 선택하세요."); return; }
    const url = '<%=request.getContextPath()%>/homework/list?lec_id=' + encodeURIComponent(selectedLecId);
    location.hash = url;
    document.getElementById('mainFrame').src = url;
  }
  
  // 실시간 강의
  function goOnline() {
	    if (!selectedLecId) { alert("전공을 먼저 선택하세요."); return; }
	    const url = 'https://192.168.0.141:3000/?roomid=' + encodeURIComponent(selectedLecId);
	    window.open(url, '실시간 강의', 'width=1350px, height=800px');
	  }
  
  function goLecvid() {
	  if (!selectedLecId) {
	    alert("전공을 먼저 선택하세요.");
	    return;
	  }
	const syllabusUrl = '<%=request.getContextPath()%>/lecture/vidlist?lec_id=' + encodeURIComponent(selectedLecId);
	location.hash = syllabusUrl;
	document.getElementById("mainFrame").src = syllabusUrl;
	}

  // data-url 공통 처리
  document.querySelectorAll('.nav-item > a[data-url]').forEach(link => {
    link.addEventListener('click', function (e) {
      e.preventDefault();
      const url = this.getAttribute('data-url');
      if (!url) return;
      document.getElementById('mainFrame').src = url;
      location.hash = url;
    });
  });
</script>

<!-- 공통/플러그인 스크립트 (기존 유지) -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery-ui/jquery-ui.min.js"></script>
<script>$.widget.bridge('uibutton', $.ui.button)</script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/chart.js/Chart.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/sparklines/sparkline.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jqvmap/jquery.vmap.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery-knob/jquery.knob.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/moment/moment.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/daterangepicker/daterangepicker.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/summernote/summernote-bs4.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/dist/js/adminlte.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/dist/js/pages/dashboard.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/common.js"></script>

<script>
  $(document).on('show.bs.modal', '.modal', function () { $('body').css('padding-right', '0px'); });
  $(document).on('hidden.bs.modal', '.modal', function () { $('body').css('padding-right', ''); });
  $(".person-info").css({ "display":"block", "width":"30px", "height":"30px", "border-radius":"10px" });
</script>
