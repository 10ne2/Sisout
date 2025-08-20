<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="wrapper">
  <!-- Preloader -->
  <div class="preloader flex-column justify-content-center align-items-center">
    <img class="animation__shake" src="<%=request.getContextPath() %>/resources/bootstrap/dist/img/Camp_usLogo.png" alt="Camp_usLogo" height="120" width="240">
  </div>

  <!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-white navbar-light" style="height:70px;">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
      
    </ul>

    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
      <!-- Navbar Search -->
  
       
      <!-- Messages Dropdown Menu -->
      <li class="nav-item">
        <a class="nav-link" style="cursor:pointer" onclick="OpenWindow('message/main','메일',1600,920);">
          <span class="custom-icon-message"></span>
          <span class="badge badge-danger navbar-badge">3D</span>
        </a>
      </li>
     <!-- Notifications Dropdown Menu -->
      <div class="ml-2"></div>
       <li>
      <form action="${pageContext.request.contextPath}/logout" method="post">
      <button type="submit" class="btn btn-block btn-info btn-flat mt-1" style="background-color:#79aaa4;border: none; width:100px;height:40px; border-radius:5px;">로그아웃</button>
       </form>
       </li>
      <li>
      <div class="row ml-4 mr-4">
      학번: ${loginUser.mem_id }
      </div>
      <div class="row ml-4 mr-4">
      이름: ${loginUser.mem_name }
      </div>
      </li>
      <li>
      <div class="image" style="cursor:pointer;" onclick="OpenWindow('mypage','글등록',800,700);">
          <img src="<%=request.getContextPath() %>/member/getPicture?id=${loginUser.mem_id}" class="img-circle img-md" alt="User Image" style="width:45px; height:45px; object-fit:cover;">
        </div>
      </li>
    </ul>
  </nav>
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
  <aside class="main-sidebar sidebar-white-primary" style="border:1px solid #dedede;background-color:#f5f5f5;">
    <!-- Brand Logo -->
   <a href="" class="brand-link">
  <img src="<%=request.getContextPath() %>/resources/bootstrap/dist/img/Camp_usLogo.png"
       alt="camp_us Logo"
       class="brand-image custom-logo" />
</a>

<!-- 디버깅용 -->

      <!-- SidebarSearch Form -->


      <!-- Sidebar Menu -->
      <nav class="mt-2" >
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="true">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
    <li class="nav-item">
<a href="" class="nav-link">
  <i class="nav-icon custom-img-icon"></i>
  &nbsp;&nbsp;&nbsp;
  <p class="fas">HOME</p>
</a>
</li>
          <li class="nav-item" >            
            <a href="#" class="nav-link" 
            	<sec:authorize access="hasRole('ROLE_1')">
		              data-url="<%=request.getContextPath() %>/lectureStu/main"
		            </sec:authorize>
		            <sec:authorize access="hasRole('ROLE_2')">
		              data-url="<%=request.getContextPath() %>/lecturePro/main"
		            </sec:authorize>
		           >
              <i class="nav-icon fas gang-img-icon"></i>
              <p class="fas" >&nbsp;
                강의실
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            <div class="row">
            <div class="col-sm-2">
            </div>
            <div class="col-sm-9">
            <div class="form-group">
                       <select class="custom-select my-border" onchange="onLectureChange(this)">
						  <option value="">전공을 선택하세요.</option>
						  <c:forEach var="stu_lec" items="${stulectureList}">
						    <option value="${stu_lec.lec_id}">${stu_lec.lec_name}</option>
						    <c:out value="${lectureList}" />
						    
						</c:forEach>
						</select>
                      </div>
                      </div>
                      </div>
              <li class="nav-item">
                <a href="#" class="nav-link" onclick="goSyllabus(); return false;">
                  <i class="far fas nav-icon" ></i>
                  <p>&nbsp;&nbsp;&nbsp;강의계획서</p>
                </a>
              </li>
              <li class="nav-item menu" data-url="" onclick="goSyllabus(); return false;">
                <a href="#" class="nav-link">
                  <i class="far fas nav-icon"></i>
                  <p>&nbsp;&nbsp;&nbsp;공지사항</p>
                </a>
              </li>
              <li class="nav-item" data-url="" onclick="goSyllabus(); return false;">
                <a href="" class="nav-link">
                  <i class="far fas nav-icon"></i>
                  <p>&nbsp;&nbsp;&nbsp;실시간 강의</p>
                </a>
              </li>
              
              <li class="nav-item" data-url="" onclick="goSyllabus(); return false;">
                <a href="" class="nav-link">
                  <i class="far nav-icon"></i>
                  <p>&nbsp;&nbsp;&nbsp;온라인 강의</p>
                </a>
              </li>
             
              <li class="nav-item" data-url="" onclick="goSyllabus(); return false;">
                <a href="" class="nav-link">
                  <i class="far nav-icon"></i>
                  <p>&nbsp;&nbsp;&nbsp;출결</p>
                </a>
              </li>
              <li class="nav-item" data-url="" onclick="goSyllabus(); return false;">
                <a href="" class="nav-link">
                  <i class="far nav-icon"></i>
                  <p>&nbsp;&nbsp;&nbsp;자료실</p>
                </a>
              </li>
              <li class="nav-item" data-url="" onclick="goSyllabus(); return false;">
                <a href="" class="nav-link">
                  <i class="far nav-icon"></i>
                  <p>&nbsp;&nbsp;&nbsp;과제제출</p>
                </a>
              </li>
            </ul>
          </li>
          <li class="nav-item">            
            <a href="#" class="nav-link">
              <i class="nav-icon fas pro-img-icon"></i>
              <p class="fas">&nbsp;
                프로젝트
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
           	  <li class="nav-item" >
	             <a href="javascript:return false;" class="nav-link"  
		            <sec:authorize access="hasRole('ROLE_1')">
		              data-url="<%=request.getContextPath() %>/project/list/stu?mem_id=${loginUser.mem_id }"
		            </sec:authorize>
		            <sec:authorize access="hasRole('ROLE_2')">
		              data-url="<%=request.getContextPath() %>/project/list/pro?mem_id=${loginUser.mem_id }"
		            </sec:authorize>
	              >
                  <i class="far nav-icon"></i>
                  <p>&nbsp;&nbsp;&nbsp;팀 목록</p>
                </a>
              </li>
              <li class="nav-item" >
                <a href="javascript:return false;" class="nav-link"
                  <sec:authorize access="hasRole('ROLE_1')">
	                data-url="<%=request.getContextPath() %>/roadmap/projectlist/stu?mem_id=${loginUser.mem_id }"
	              </sec:authorize>
	              <sec:authorize access="hasRole('ROLE_2')">
	                data-url="<%=request.getContextPath() %>/roadmap/projectlist/pro?mem_id=${loginUser.mem_id }"
	              </sec:authorize>
                >
                  <i class="far fas nav-icon"></i>
                  <p>&nbsp;&nbsp;&nbsp;로드맵</p>
                </a>
              </li>
            </ul>
          </li>
          
          <li class="nav-item" data-url="">
            <a href="#" class="nav-link">
              <i class="nav-icon fas post-img-icon"></i>
              <p class="fas">&nbsp;
                게시판
              </p>
            </a>
          </li>
		    <li class="nav-item" data-url="">
            <a href="pages/widgets.html" class="nav-link">
              <i class="nav-icon fas cal-img-icon"></i>
              <p class="fas">&nbsp;
                캘린더
              </p>
            </a>
          </li>
          <li class="nav-item" data-url="">            
            <a href="#" class="nav-link">
              <i class="nav-icon fas mes-img-icon"></i>
              <p class="fas">&nbsp;
                커뮤니티
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview" data-url="">
              <li class="nav-item">
                <a href="#" class="nav-link">
                  <i class="far nav-icon"></i>
                  <p>&nbsp;&nbsp;&nbsp;공지사항</p>
                </a>
              </li>
              <li class="nav-item" data-url="">
                <a href="#" class="nav-link">
                  <i class="far nav-icon"></i>
                  <p>&nbsp;&nbsp;&nbsp;질의응답</p>
                </a>
              </li>
            </ul>
          </li>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>

  <div class="content-wrapper">
 <iframe id="mainFrame" name="ifr" frameborder="0" style="width:100%;height:90vh;"></iframe> 	 
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
 

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->
<script>
  function Iframe(url) {
    document.getElementById('mainFrame').src = url;
  }
</script>
<script>
  let selectedLecId = null;

  function onLectureChange(select) {
    selectedLecId = select.value;

    if (!selectedLecId) return;

    // 선택된 전공 정보 불러오기 (필요시)
    fetch('<%=request.getContextPath()%>/lecture/changeMajor?lec_id=' + encodeURIComponent(selectedLecId))
      .then(response => response.json())
      .then(data => {
        console.log("선택된 강의 정보:", data);
        // 이 시점에서는 iframe 이동 X → 버튼 누를 때 이동
      })
      .catch(err => {
        console.error("강의 정보 로딩 실패:", err);
      });
  }

  function goSyllabus() {
    if (!selectedLecId) {
      alert("전공을 먼저 선택하세요.");
      return;
    }

    const listUrl = '<%=request.getContextPath()%>/lecture/list?lec_id=' + encodeURIComponent(selectedLecId);
    document.getElementById("mainFrame").src = listUrl;
  }
</script>
<!-- commons.js -->

<!-- jQuery -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
  $.widget.bridge('uibutton', $.ui.button)
</script>
<!-- Bootstrap 4 -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- ChartJS -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/chart.js/Chart.min.js"></script>
<!-- Sparkline -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/sparklines/sparkline.js"></script>
<!-- JQVMap -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/jqvmap/jquery.vmap.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
<!-- jQuery Knob Chart -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/jquery-knob/jquery.knob.min.js"></script>
<!-- daterangepicker -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/moment/moment.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/daterangepicker/daterangepicker.js"></script>
<!-- Tempusdominus Bootstrap 4 -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
<!-- Summernote -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/summernote/summernote-bs4.min.js"></script>
<!-- overlayScrollbars -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/dist/js/adminlte.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/dist/js/pages/dashboard.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/common.js"></script>
<script>
$(".person-info").css({
	"display":"block",
	"width":"30px",
	"height":"30px",
	"border-radius":"10px"
});

document.querySelectorAll('.nav-item > a').forEach(link => {
    link.addEventListener('click', function (e) {
      //e.preventDefault();
      const url = this.getAttribute('data-url');
      if (url) {
        location.hash = url;               // 주소창 해시(#) 변경
        document.getElementById('mainFrame').src = url;  // iframe src 변경
      }
    });
  });
 window.addEventListener('DOMContentLoaded', () => {
	  const hash = location.hash.substring(1); // # 뗀 나머지
	  if (hash) {
	    document.getElementById('mainFrame').src = hash;
	  } else {
	    // 기본 페이지 지정 (원하는 초기 URL)
	    document.getElementById('mainFrame').src = '<%=request.getContextPath()%>/lecture/main';
	  }
	}); 
function goSyllabus() {
	  if (!selectedLecId) {
	    alert("전공을 먼저 선택하세요.");
	    return;
	  }
	  const listUrl = '<%=request.getContextPath()%>/lecture/list?lec_id=' + encodeURIComponent(selectedLecId);
	  location.hash = listUrl;    // 해시 변경
	  document.getElementById("mainFrame").src = listUrl;
	}
</script>
<script>
$(document).on('show.bs.modal', '.modal', function () {
	  $('body').css('padding-right', '0px');
	});
	$(document).on('hidden.bs.modal', '.modal', function () {
	  $('body').css('padding-right', '');
	});
</script>
</body>
</html>