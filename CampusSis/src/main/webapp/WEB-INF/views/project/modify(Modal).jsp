<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!-- style 커스터마이징 -->


<div class="modal fade" id="modifyModal" tabindex="-1" role="dialog" aria-labelledby="modifyModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document" style="max-width: 800px;">
    <div class="modal-content">

     <div class="modal-header" style="border-bottom:none;">
        <!-- 닫기 버튼 제거됨 -->
      </div>

      <div class="modal-body">
        <!-- 제목 + 버튼 row -->
        <div class="row d-flex justify-content-between align-items-center mb-3">
          <div class="col-4 ml-3">
            <h5 id="modifyModalLabel" style="font-size:25px;color:#707070; font-weight:bold; margin-left:7px;">수정 요청서</h5>
          </div>
          <div class="col-1">
            <button type="button" class="btn btn-info" data-dismiss="modal"
              style="background-color:#aaaaaa; border-radius:5px; width:150px; height:40px; border:none;margin-right:-20px; font-weight:bold;">
              <span style="color:#ffffff; font-size:20px;">취소</span>
            </button>
          </div>
          <div class="col-3">
            <button type="button" class="btn btn-info"
              style="background-color:#2ec4b6; border-radius:5px; width:150px; height:40px; border:none; font-weight:bold;">
              <span style="color:#ffffff; font-size:20px;">요청</span>
            </button>
          </div>
        </div>

        <!-- 폼 시작 -->
        <form>
          <div class="row mt-4 ml-4">
            <div class="col-2">
              <h3 style="color:#707070; font-size:20px; font-weight:bold; margin-top:4px;">학기</h3>
            </div>
            <div class="col-10">
              <select style="width: 580px; font-size: 13px; padding: 2px 4px; border: 1px solid #2ec4b6; height:38px;">
    <option>학기 선택</option>
    <option>1학기</option>
    <option>2학기</option>
  </select>
            </div>
          </div>

          <div class="row mt-3 ml-4">
            <div class="col-2">
              <h3 style="color:#707070; font-size:20px; font-weight:bold; margin-top:4px;">프로젝트명</h3>
            </div>
            <div class="col-10">
              <input class="form-control" type="text" placeholder="프로젝트명을 입력해주세요." style="width:580px;">
            </div>
          </div>

          <div class="row mt-1 ml-4">
            <div class="col-2">
              <h3 style="color:#707070; font-size:20px; font-weight:bold; margin-top:13px;">시작일</h3>
            </div>
            <div class="col-10">
              <div class="input-group date mt-2" id="datetimepicker3" data-target-input="nearest" style="width: 580px; height:38px;">
                <div class="input-group-append" data-target="#datetimepicker3" data-toggle="datetimepicker">
                  <div class="input-group-text" style="padding: 4px 6px;"><i class="far fa-minical"></i></div>
                </div>
                <input type="text" class="form-control datetimepicker-input" data-target="#datetimepicker3" style="width: 90px; height:38px; font-size: 13px;">
              </div>
            </div>
          </div>

          <div class="row mt-1 ml-4">
            <div class="col-2">
              <h3 style="color:#707070; font-size:20px; font-weight:bold; margin-top:13px;">마감일</h3>
            </div>
            <div class="col-10">
              <div class="input-group date mt-2" id="datetimepicker4" data-target-input="nearest" style="width: 580px; height:38px;">
                <div class="input-group-append" data-target="#datetimepicker4" data-toggle="datetimepicker">
                  <div class="input-group-text" style="padding: 4px 6px;"><i class="far fa-minical"></i></div>
                </div>
                <input type="text" class="form-control datetimepicker-input" data-target="#datetimepicker4" style="width: 90px; height:38px; font-size: 13px;">
              </div>
            </div>
          </div>

          <div class="row mt-3 ml-4">
            <div class="col-2">
              <h3 style="color:#707070; font-size:20px; font-weight:bold; margin-top:4px;">담당교수</h3>
            </div>
            <div class="col-10">
              <input class="form-control" type="text" placeholder="담당교수의 변경은 불가능합니다." style="width:580px;"disabled>
            </div>
          </div>

          <div class="row mt-3 ml-4">
            <div class="col-2">
              <h3 style="color:#707070; font-size:20px; font-weight:bold; margin-top:4px;">팀장</h3>
            </div>
            <div class="col-10 d-flex align-items-center">
  <input type="text" class="form-control" id="teamLeaderInput" placeholder="팀장을 입력해주세요" readonly style="width: 515px;">
  <button type="button" class="btn btn-secondary ml-2" data-toggle="modal" data-target="#studentSelectModalLeader">검색</button>
</div>
</div>


          <div class="row mt-3 ml-4">
            <div class="col-2">
              <h3 style="color:#707070; font-size:20px; font-weight:bold; margin-top:4px;">팀원</h3>
            </div>
            <div class="col-10 d-flex align-items-center">
  <input type="text" class="form-control" id="teamMembersInput" placeholder="팀원을 입력해주세요" readonly style="width: 515px;">
  <button type="button" class="btn btn-secondary ml-2" data-toggle="modal" data-target="#studentSelectModalMembers">검색</button>
</div>
</div>
          <!-- 사유 영역 -->
          <div class="row mt-3 ml-4">
            <div class="col-2">
              <h3 style="color:#707070; font-size:20px; font-weight:bold; margin-top:4px;">사유</h3>
            </div>
          </div>

          <div class="row mt-2">
            <div class="col-12 d-flex justify-content-center">
              <textarea class="form-control custom-textarea" rows="3" placeholder="수정 사유를 입력해주세요." style="width:710px; height:130px; resize: none;"></textarea>
            </div>
          </div>

        </form>
      </div>
    </div>
  </div>
</div>
 <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/moment/moment.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/bootstrap/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>

  <!-- 초기화 스크립트 -->
  <script>
    $(function () {
      $('#datetimepicker3').datetimepicker({
        format: 'L' // 날짜만 (MM/DD/YYYY 형식)
      });
    $('#datetimepicker4').datetimepicker({
        format: 'L'
      });
    });
    </script>
        <jsp:include page="teamselect.jsp" />
        <jsp:include page="teamsselect.jsp"/>