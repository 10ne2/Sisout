<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<head>
  <!-- ✅ jQuery (모든 것의 기반) -->
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>

  <!-- ✅ Moment.js (달력용) -->
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/moment/moment.min.js"></script>

  <!-- ✅ Bootstrap (기본 프레임워크) -->
  <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/bootstrap/plugins/bootstrap/css/bootstrap.min.css">
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- ✅ Tempus Dominus (달력) -->
  <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>

  <!-- ✅ Summernote -->
  <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/bootstrap/plugins/summernote/summernote-bs4.min.css">
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/summernote/summernote-bs4.min.js"></script>
</head>	
<div class="wrap" style="height:100%;">
<form role="form" method="post" action="regist" name="registForm">
	<div class="row">
		<div class="col-12">
			<h3 style="font-size: 25px; font-weight: bold; margin: 15px 0 0 30px; ">팀 등록</h3>
		</div>
	</div>
	<div class="row ml-5 mt-3">
		<div class="col-2">
			<span style="font-weight:bold; color:#707070;">프로젝트명</span>
		</div>
		<div class="col-2" style="margin-left:112px;">
			<span style="font-weight:bold; color:#707070;">학기</span>
		</div>
		<div class="col-3" style="margin-left:60px;">
			<span style="font-weight:bold; color:#707070;">기한</span>
		</div>
	</div>
	<div class="row ml-5">
		<div class="col-3">
			<input class="form-control notNull" name="project_name" title="프로젝트명" type="text" placeholder="프로젝트명을 입력해주세요." style="width:300px;">
		</div>
		<div class="ml-3" style="width:260px;">
			<select name="samester" class="form-control" style="width: 250px; font-size: 16px; border: 1px solid #2ec4b6; height:38px;">
                <option value="">학기 선택</option>
                <option value="1학기">1학기</option>
                <option value="2학기">2학기</option>
            </select>
		</div>
		<div class="col-4 d-flex">
			<div class="input-group date" id="datetimepicker1" data-target-input="nearest" style="width: 150px; height:38px;">
                <input name="project_stdate" type="text" class="form-control datetimepicker-input" data-target="#datetimepicker1" style="width: 90px; height:38px; font-size: 16px;" placeholder="시작일">
                <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
                  <div class="input-group-text" style="padding: 4px 6px;"><i class="far fa-calendar-alt"></i></div>
                </div>
            </div>
            <span style="line-height:38px;">&nbsp;&nbsp;&nbsp;~</span>
            <div class="input-group date ml-3" id="datetimepicker2" data-target-input="nearest" style="width: 150px; height:38px;">
                <input name="project_endate" type="text" class="form-control datetimepicker-input" data-target="#datetimepicker2" style="width: 90px; height:38px; font-size: 16px;" placeholder="종료일">
                <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
                  <div class="input-group-text" style="padding: 4px 6px;"><i class="far fa-calendar-alt"></i></div>
                </div>
            </div>
		</div>
	</div>
	<div class="row ml-5 mt-3">
		<div class="col-3">
			<span style="font-weight:bold; color:#707070;">담당교수</span>
		</div>
		<div class="col-3" style="margin-left:10px;">
			<span style="font-weight:bold; color:#707070;">팀장</span>
		</div>
		<div class="col-3 mt-1">
			<span style="font-weight:bold; color:#707070;">팀원</span>
		</div>
	</div>
	<div class="row ml-5">
		<div class="col-3 d-flex">
			<input id="professorName" class="form-control notNull" type="text" title="담당교수" placeholder="담당교수를 등록해주세요."  readonly style="width:80%;">
			<input type="hidden" id="professorId" name="profes_id">
			<button type="button" class="btn btn-secondary ml-2" data-toggle="modal" data-target="#professorSelectModal" style="width:70px; height:38px; color:#fff; background-color:#2ec4b6; border:none;">검색</button>
		</div>
		<div class="col-3 d-flex" style="margin-left:8px;">
			<!-- 팀장 이름 표시용 -->
			<input id="teamLeaderName" class="form-control" type="text" placeholder="팀장을 등록해주세요." readonly style="width:80%;">
			<!-- 실제 팀장 stu_id 전송용 -->
			<input type="hidden" name="team_leader" id="teamLeaderId">
			<button type="button" class="btn btn-secondary ml-2" data-toggle="modal" data-target="#studentSelectModalLeader" style="width:70px; height:38px; color:#fff; background-color:#2ec4b6; border:none;">검색</button>
		</div>
		<div class="col-4 d-flex">
			<!-- 팀원 이름 표시용 (콤마로 구분된 문자열) -->
			<input id="teamMembersName" name="team_member"class="form-control" type="text" placeholder="팀원을 등록해주세요." readonly style="width:80%;">
			<!-- 팀원 stu_id_list 전송용 히든 input 여러개 삽입될 영역 -->
			<div id="teamMembersHiddenInputs"></div>
			<button type="button" class="btn btn-secondary ml-2" data-toggle="modal" data-target="#studentSelectModalMembers" style="width:70px; height:38px; color:#fff; background-color:#2ec4b6; border:none;">검색</button>
		</div>
	</div>
	<div class="row">
		<div class="col-11" style="margin-left:55px; margin-top:10px;">
			<div class="form-group">
				<label for="content"><span style="font-weight:bold; color:#707070;">내용</span></label>
				<textarea class="form-control" name="project_desc" id="content" rows="5" placeholder="1000자 내외로 작성하세요."></textarea>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-6"></div>
		<div class="col-2"></div>
		<div class="col-2">
			<button type="button" class="btn btn-info" onclick="if(confirm('등록을 취소하시겠습니까?')) history.back();"
              style="background-color:#aaaaaa; border-radius:5px; width:150px; height:40px; border:none; margin-right:-20px; font-weight:bold; margin-left:40px;">
              <span style="color:#ffffff; font-size:20px;">취 소</span>
            </button>
          </div>
		<div class="col-2">
			<button type="button" class="btn btn-info" onclick="projectRegist_go();"
              style="background-color:#2ec4b6; border-radius:5px; width:150px; height:40px; border:none; font-weight:bold;">
              <span style="color:#ffffff; font-size:20px;">등 록</span>
            </button>
		</div>
	</div>
</form>
</div>
<!-- 초기화 스크립트 -->
<script>
  $(function () {
    // 달력 초기화
    $('#datetimepicker1').datetimepicker({ format: 'YYYY-MM-DD' });
    $('#datetimepicker2').datetimepicker({ format: 'YYYY-MM-DD' });

    // 썸머노트 초기화
    $('#content').summernote({
      height: 300,
      placeholder: '내용을 입력해주세요.'
    });
  });
  function projectRegist_go() {
	    let form = document.forms["registForm"];

	    // 필수 입력값 검사
	    let requiredFields = document.querySelectorAll(".notNull");
	    for (let input of requiredFields) {
	      if (!input.value.trim()) {
	        alert(input.getAttribute("title") + "은(는) 필수 입력 항목입니다.");
	        input.focus();
	        return;
	      }
	    }

	    // 학기 선택 확인
	    if (!form.samester.value) {
	      alert("학기를 선택해주세요.");
	      form.samester.focus();
	      return;
	    }

	    // 날짜 입력 확인
	    if (!form.project_stdate.value || !form.project_endate.value) {
	      alert("프로젝트 시작일과 종료일을 모두 입력해주세요.");
	      return;
	    }

	    // 담당교수 확인
	    if (!form.profes_id.value) {
	      alert("담당교수를 선택해주세요.");
	      return;
	    }

	    // 팀장 확인
	    if (!form.team_leader.value) {
	      alert("팀장을 선택해주세요.");
	      return;
	    }

	    // 팀원 확인
	    const teamMemberInputs = document.getElementsByName("stu_id_list");
	    if (teamMemberInputs.length === 0) {
	      alert("팀원을 1명 이상 등록해주세요.");
	      return;
	    }

	    // 최종 제출
	    form.action = "regist";
	    form.method = "post";
	    form.submit();
	  }

  // 팀장 모달에서 선택 완료 시 호출할 함수 (예시)
  function setTeamLeader(stuId, stuName) {
    document.getElementById("teamLeaderId").value = stuId;
    document.getElementById("teamLeaderName").value = stuName;
  }

  // 팀원 모달에서 선택 완료 시 호출할 함수 (예시)
  // selectedMembers: [{stu_id:'1', mem_name:'김학생'}, ...]
  function setTeamMembers(selectedMembers) {
    var names = selectedMembers.map(m => m.mem_name).join(", ");
    document.getElementById("teamMembersName").value = names;

    var container = document.getElementById("teamMembersHiddenInputs");
    container.innerHTML = "";
    selectedMembers.forEach(m => {
      var input = document.createElement("input");
      input.type = "hidden";
      input.name = "stu_id_list";
      input.value = m.stu_id;
      container.appendChild(input);
    });
  }
  $('#selectLeaderBtn').on('click', function () {
	    const stuId = $(this).data('selected-id');
	    const stuName = $(this).data('selected-name');

	    if (stuId && stuName) {
	        setTeamLeader(stuId, stuName); // <<< 이 부분 꼭 있어야 합니다
	    }

	    $('#studentSelectModalLeader').modal('hide');
	});
//예시: 팀장 선택 후 Ajax로 팀 생성 요청
  function selectTeamLeaderAndCreateTeam(stuId, stuName) {
    $.ajax({
      url: '/team/create',
      method: 'POST',
      data: { team_leader: stuId },
      success: function(response) {
        // response에 생성된 team_id 포함
        document.getElementById("teamLeaderId").value = stuId;
        document.getElementById("teamLeaderName").value = stuName;
        // 숨겨진 팀 아이디 input에 저장
        if(!document.getElementById('teamId')) {
          var input = document.createElement("input");
          input.type = "hidden";
          input.name = "team_id";
          input.id = "teamId";
          input.value = response.team_id;
          document.forms["registForm"].appendChild(input);
        } else {
          document.getElementById('teamId').value = response.team_id;
        }
      },
      error: function() {
        alert("팀 생성 실패");
      }
    });
  }
  function setTeamMembers(selectedMembers) {
	    var names = selectedMembers.map(m => m.mem_name).join(", ");
	    document.getElementById("teamMembersName").value = names;

	    var container = document.getElementById("teamMembersHiddenInputs");
	    container.innerHTML = "";
	    selectedMembers.forEach(m => {
	        var input = document.createElement("input");
	        input.type = "hidden";
	        input.name = "stu_id_list"; // 팀원 id를 여러개 받는 파라미터명
	        input.value = m.stu_id;
	        container.appendChild(input);
	    });
	}
</script>

<jsp:include page="profselect.jsp" />
<jsp:include page="teamselect.jsp" />
<jsp:include page="membersSelect.jsp" />