<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/module/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="wrapper">

	<!-- Preloader -->
	<div
		class="preloader flex-column justify-content-center align-items-center">
		<img class="animation__shake"
			src="<%=request.getContextPath()%>/resources/bootstrap/dist/img/Camp_usLogo.png"
			alt="Camp_usLogo" height="120" width="240">
	</div>

	<!-- Navbar -->
	<nav class="main-header navbar navbar-expand navbar-white navbar-light">
		<!-- Left navbar links -->
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" data-widget="pushmenu"
				href="#" role="button"><i class="fas fa-bars"></i></a></li>
			<li class="nav-item d-none d-sm-inline-block"><a
				href="index3.html" class="nav-link">Home</a></li>
			<li class="nav-item d-none d-sm-inline-block"><a href="#"
				class="nav-link">Contact</a></li>
		</ul>

		<!-- Right navbar links -->
		<ul class="navbar-nav ml-auto">
			<!-- Navbar Search -->
			<li class="nav-item"><a class="nav-link"
				data-widget="navbar-search" href="#" role="button"> <i
					class="fas fa-search"></i>
			</a>
				<div class="navbar-search-block">
					<form class="form-inline">
						<div class="input-group input-group-sm">
							<input class="form-control form-control-navbar" type="search"
								placeholder="Search" aria-label="Search">
							<div class="input-group-append">
								<button class="btn btn-navbar" type="submit">
									<i class="fas fa-search"></i>
								</button>
								<button class="btn btn-navbar" type="button"
									data-widget="navbar-search">
									<i class="fas fa-times"></i>
								</button>
							</div>
						</div>
					</form>
				</div></li>

			<!-- Messages Dropdown Menu -->
			<li class="nav-item dropdown"><a class="nav-link"
				data-toggle="dropdown" href="#"> <i class="far fa-comments"></i>
					<span class="badge badge-danger navbar-badge">3</span>
			</a>
				<div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
					<a href="#" class="dropdown-item"> <!-- Message Start -->
						<div class="media">
							<img src="dist/img/user1-128x128.jpg" alt="User Avatar"
								class="img-size-50 mr-3 img-circle">
							<div class="media-body">
								<h3 class="dropdown-item-title">
									Brad Diesel <span class="float-right text-sm text-danger"><i
										class="fas fa-star"></i></span>
								</h3>
								<p class="text-sm">Call me whenever you can...</p>
								<p class="text-sm text-muted">
									<i class="far fa-clock mr-1"></i> 4 Hours Ago
								</p>
							</div>
						</div> <!-- Message End -->
					</a>
					<div class="dropdown-divider"></div>
					<a href="#" class="dropdown-item"> <!-- Message Start -->
						<div class="media">
							<img src="dist/img/user8-128x128.jpg" alt="User Avatar"
								class="img-size-50 img-circle mr-3">
							<div class="media-body">
								<h3 class="dropdown-item-title">
									John Pierce <span class="float-right text-sm text-muted"><i
										class="fas fa-star"></i></span>
								</h3>
								<p class="text-sm">I got your message bro</p>
								<p class="text-sm text-muted">
									<i class="far fa-clock mr-1"></i> 4 Hours Ago
								</p>
							</div>
						</div> <!-- Message End -->
					</a>
					<div class="dropdown-divider"></div>
					<a href="#" class="dropdown-item"> <!-- Message Start -->
						<div class="media">
							<img src="dist/img/user3-128x128.jpg" alt="User Avatar"
								class="img-size-50 img-circle mr-3">
							<div class="media-body">
								<h3 class="dropdown-item-title">
									Nora Silvester <span class="float-right text-sm text-warning"><i
										class="fas fa-star"></i></span>
								</h3>
								<p class="text-sm">The subject goes here</p>
								<p class="text-sm text-muted">
									<i class="far fa-clock mr-1"></i> 4 Hours Ago
								</p>
							</div>
						</div> <!-- Message End -->
					</a>
					<div class="dropdown-divider"></div>
					<a href="#" class="dropdown-item dropdown-footer">See All
						Messages</a>
				</div></li>
			<!-- Notifications Dropdown Menu -->
			<li class="nav-item dropdown"><a class="nav-link"
				data-toggle="dropdown" href="#"> <i class="far fa-bell"></i> <span
					class="badge badge-warning navbar-badge">15</span>
			</a>
				<div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
					<span class="dropdown-item dropdown-header">15 Notifications</span>
					<div class="dropdown-divider"></div>
					<a href="#" class="dropdown-item"> <i
						class="fas fa-envelope mr-2"></i> 4 new messages <span
						class="float-right text-muted text-sm">3 mins</span>
					</a>
					<div class="dropdown-divider"></div>
					<a href="#" class="dropdown-item"> <i class="fas fa-users mr-2"></i>
						8 friend requests <span class="float-right text-muted text-sm">12
							hours</span>
					</a>
					<div class="dropdown-divider"></div>
					<a href="#" class="dropdown-item"> <i class="fas fa-file mr-2"></i>
						3 new reports <span class="float-right text-muted text-sm">2
							days</span>
					</a>
					<div class="dropdown-divider"></div>
					<a href="#" class="dropdown-item dropdown-footer">See All
						Notifications</a>
				</div></li>
			<li class="nav-item"><a class="nav-link"
				data-widget="fullscreen" href="#" role="button"> <i
					class="fas fa-expand-arrows-alt"></i>
			</a></li>
			<li class="nav-item"><a class="nav-link"
				data-widget="control-sidebar" data-controlsidebar-slide="true"
				href="#" role="button"> <i class="fas fa-th-large"></i>
			</a></li>
		</ul>
	</nav>
	<!-- /.navbar -->

	<!-- Main Sidebar Container -->
	<aside class="main-sidebar sidebar-white-primary elevation-4">
		<!-- Brand Logo -->
		<a href="" class="brand-link"> <img
			src="<%=request.getContextPath()%>/resources/bootstrap/dist/img/Camp_usLogo.png"
			alt="camp_us Logo" class="brand-image custom-logo" />
		</a>



		<!-- SidebarSearch Form -->


		<!-- Sidebar Menu -->
		<nav class="mt-2">
			<ul class="nav nav-pills nav-sidebar flex-column"
				data-widget="treeview" role="menu" data-accordion="false">
				<!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
				<li class="nav-item"><a href="" class="nav-link"> <span
						class="nav-icon custom-img-icon"></span> &nbsp;&nbsp;&nbsp;&nbsp;
						<p class="fas">HOME</p>
				</a></li>
				<li class="nav-item"><a href="#" class="nav-link"> <i
						class="nav-icon fas gang-img-icon"></i>
						<p class="fas">
							&nbsp; 강의실 <i class="right fas fa-angle-left"></i>
						</p>
				</a>
					<ul class="nav nav-treeview">
						<li class="nav-item"><a href="./index.html" class="nav-link">
								<i class="far fas nav-icon"></i>
								<p>&nbsp;&nbsp;&nbsp;강의계획서</p>
						</a></li>
						<li class="nav-item"><a href="./index2.html" class="nav-link">
								<i class="far fas nav-icon"></i>
								<p>&nbsp;&nbsp;&nbsp;공지사항</p>
						</a></li>
						<li class="nav-item"><a href="" class="nav-link"> <i
								class="far fas nav-icon"></i>
								<p>&nbsp;&nbsp;&nbsp;실시간 강의</p>
						</a></li>
						<li class="nav-item"><a href="" class="nav-link"> <i
								class="far nav-icon"></i>
								<p>&nbsp;&nbsp;&nbsp;온라인 강의</p>
						</a></li>
						<li class="nav-item"><a href="" class="nav-link"> <i
								class="far nav-icon"></i>
								<p>&nbsp;&nbsp;&nbsp;출결</p>
						</a></li>
						<li class="nav-item"><a href="" class="nav-link"> <i
								class="far nav-icon"></i>
								<p>&nbsp;&nbsp;&nbsp;자료실</p>
						</a></li>
						<li class="nav-item"><a href="" class="nav-link"> <i
								class="far nav-icon"></i>
								<p>&nbsp;&nbsp;&nbsp;과제제출</p>
						</a></li>
					</ul></li>
				<li class="nav-item"><a href="#" class="nav-link"> <i
						class="nav-icon fas pro-img-icon"></i>
						<p class="fas">
							&nbsp; 프로젝트 <i class="right fas fa-angle-left"></i>
						</p>
				</a>
					<ul class="nav nav-treeview">
						<li class="nav-item"><a href="./index.html" class="nav-link">
								<i class="far nav-icon"></i>
								<p>팀 목록</p>
						</a></li>
						<li class="nav-item"><a href="./index2.html" class="nav-link">
								<i class="far fas nav-icon"></i>
								<p>로드맵</p>
						</a></li>
					</ul></li>

				<li class="nav-item"><a href="pages/widgets.html"
					class="nav-link"> <i class="nav-icon fas post-img-icon"></i>
						<p class="fas">&nbsp; 게시판</p>
				</a></li>
				<li class="nav-item"><a href="pages/widgets.html"
					class="nav-link"> <i class="nav-icon fas cal-img-icon"></i>
						<p class="fas">&nbsp; 캘린더</p>
				</a></li>
				<li class="nav-item"><a href="#" class="nav-link"> <i
						class="nav-icon fas mes-img-icon"></i>
						<p class="fas">
							&nbsp; 커뮤니티 <i class="right fas fa-angle-left"></i>
						</p>
				</a>
					<ul class="nav nav-treeview">
						<li class="nav-item"><a href="./index.html" class="nav-link">
								<i class="far nav-icon"></i>
								<p>공지사항</p>
						</a></li>
						<li class="nav-item"><a href="./index2.html" class="nav-link">
								<i class="far nav-icon"></i>
								<p>질의응답</p>
						</a></li>
					</ul></li>
		</nav>
		<!-- /.sidebar-menu -->
</div>
<!-- /.sidebar -->
</aside>


<div class="wrapper">
	<div class="content-wrapper">
		<div class="content-header">
			<div class="container-fluid">
				<div class="row mb-2">
					<div class="col-sm-6">
						<h1 class="m-0">게시판</h1>
					</div>
					<div class="col-sm-6">
						<ol class="breadcrumb float-sm-right">
							<li class="breadcrumb-item"><a href="#">Home</a></li>
							<li class="breadcrumb-item active">게시판</li>
						</ol>
					</div>
				</div>
			</div>
		</div>

		<style>
table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	padding: 10px;
	border-bottom: 1px solid #ddd;
	text-align: center;
}

.top-bar {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 10px;
}

.top-bar form {
	display: flex;
	gap: 10px;
	align-items: center;
	width: 100%;
	justify-content: space-between;
}

.btn {
	padding: 6px 12px;
	background-color: #2ac1bc;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.btn:hover {
	background-color: #1aa6a1;
}

.write-btn {
	text-align: right;
	margin-top: 20px;
}

.pagination {
	margin-top: 30px;
	display: flex;
	justify-content: center;
	gap: 8px;
}

.pagination a {
	padding: 6px 12px;
	text-decoration: none;
	border: 1px solid #ddd;
	border-radius: 4px;
	color: #333;
}

.pagination a.active {
	background-color: #2ac1bc;
	color: white;
	font-weight: bold;
}

.pagination a:hover {
	background-color: #e6f7f6;
}
</style>

		<script>
			function submitCategoryForm() {
				document.getElementById('filterForm').submit();
			}
		</script>

		<form id="filterForm" method="get"
			action="${pageContext.request.contextPath}/boardlist">

			<div class="top-bar">
				<div class="top-left">
					<select name="category" onchange="submitCategoryForm()">
						<option value="">전체</option>
						<option value="공지" ${category == '공지' ? 'selected' : ''}>공지</option>
						<option value="자유" ${category == '자유' ? 'selected' : ''}>자유</option>
						<option value="토론" ${category == '토론' ? 'selected' : ''}>토론</option>
						<option value="시험" ${category == '시험' ? 'selected' : ''}>시험</option>
					</select>
				</div>
				<div class="top-right">
					<label><input type="checkbox" name="hideMine"
						${hideMine == 'on' ? 'checked' : ''}> 내 글 숨기기</label> <select
						name="searchType">
						<option value="">전체</option>
						<option value="title" ${searchType == 'title' ? 'selected' : ''}>제목</option>
						<option value="writer" ${searchType == 'writer' ? 'selected' : ''}>작성자</option>
						<option value="content"
							${searchType == 'content' ? 'selected' : ''}>내용</option>
					</select> <input type="text" name="keyword" placeholder="검색어를 입력해 주세요."
						value="${keyword}" />
					<button type="submit" class="btn">검색</button>
				</div>
			</div>
		</form>

		<table>
			<thead>
				<tr>
					<th>번호</th>
					<th>말머리</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty boardList}">
					<tr>
						<td colspan="5">게시글이 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach var="board" items="${boardList}" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${board.boardCat}</td>
						<td><a
							href="${pageContext.request.contextPath}/board/detail?bno=${board.boardId}">
								${board.boardName} </a></td>
						<td>${board.memId}</td>
						<td>${board.boardDate}</td>
					</tr>
				</c:forEach>
			</tbody>

		</table>

		<div class="pagination">
			<a href="?page=1">&laquo;</a> <a href="?page=${page-1}"
				${page == 1 ? 'class="disabled"' : ''}>&lt;</a>
			<c:forEach var="i" begin="1" end="${totalPage}">
				<a href="?page=${i}" class="${i == page ? 'active' : ''}">${i}</a>
			</c:forEach>
			<a href="?page=${page+1}"
				${page == totalPage ? 'class="disabled"' : ''}>&gt;</a> <a
				href="?page=${totalPage}">&raquo;</a>
		</div>

		<!-- 작성 버튼 영역 -->
		<div class="write-btn">
			<button type="button" class="btn btn-primary"
				onclick="openWritePopup()"
				style="padding: 6px 12px; font-size: 14px; border-radius: 4px; background-color: #2ac1bc; color: white; border: none; height: 36px;">
				작성하기</button>
		</div>

	</div>
</div>

<%@ include file="/WEB-INF/views/module/footer.jsp"%>

<!-- Control Sidebar -->
<aside class="control-sidebar control-sidebar-dark">
	<!-- Control sidebar content goes here -->
</aside>
<!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->
<%@ include file="/WEB-INF/views/module/footer.jsp"%>
<!-- jQuery -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
	$.widget.bridge('uibutton', $.ui.button)
</script>
<!-- Bootstrap 4 -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- ChartJS -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/chart.js/Chart.min.js"></script>
<!-- Sparkline -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/sparklines/sparkline.js"></script>
<!-- JQVMap -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jqvmap/jquery.vmap.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
<!-- jQuery Knob Chart -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery-knob/jquery.knob.min.js"></script>
<!-- daterangepicker -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/moment/moment.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/daterangepicker/daterangepicker.js"></script>
<!-- Tempusdominus Bootstrap 4 -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
<!-- Summernote -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/summernote/summernote-bs4.min.js"></script>
<!-- overlayScrollbars -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/dist/js/adminlte.js"></script>
<!-- AdminLTE for demo purposes -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/dist/js/demo.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<script
	src="<%=request.getContextPath()%>/resources/bootstrap/dist/js/pages/dashboard.js"></script>
<script>
	function openWritePopup() {
		window.open('<c:url value="/board/write"/>', 'writePopup',
				'width=1000,height=800,scrollbars=yes');
	}
</script>

</body>
</html>