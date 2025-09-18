<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Modal -->
<div id="modifyModal" class="modal modal-default fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" style="display:none;"></h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      <div class="modal-body" data-rno>
        <p>
          <input type="text" id="eval_content" class="form-control">
        </p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-info" id="replyModBtn" onclick="replyModify_go();">Modify</button>
        <button type="button" class="btn btn-danger" id="replyDelBtn" onclick="replyRemove_go();">DELETE</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.8/handlebars.min.js"></script>

<script type="text/x-handlebars-template" id="reply-list-template">
{{#each .}}
  <div class="replyLi">
    <div class="user-block">
      <img src="<%=request.getContextPath() %>/member/getPicture?id={{profes_id}}" class="img-circle img-bordered-sm"/>
    </div>
    <div class="timeline-item">
      <span class="time">
        <i class="fa fa-clock"></i>{{prettifyDate eval_regdate}}
        <!-- 수정 버튼 (모달) -->
        <a class="btn btn-primary btn-xs" style="display:{{VisibleByLoginCheck profes_id}};" onclick="evaluationModifyModal_go('{{eval_id}}');">
          Modify
        </a>
        
        <button class="btn btn-danger btn-xs"
        data-eval_id="{{eval_id}}"
        data-rm_id="{{rm_id}}"  
        onclick="replyRemove_go(this)">
  Delete
</button>
      </span>
      <h3 class="timeline-header">
        <strong style="display:none;">{{eval_id}}</strong>{{profes_id}}
      </h3>
      <div class="timeline-body" id="{{eval_id}}-eval_content">{{eval_content}}</div>
    </div>
  </div>
{{/each}}
</script>

<script type="text/x-handlebars-template" id="reply-pagination-template">
  <li class="page-item">
    <a class="page-link" href="{{goPage 1}}">
      <i class="fas fa-angle-double-left"></i>
    </a>
  </li>
  <li class="page-item">
    <a class="page-link" href="{{#if prev}}{{goPage prevPageNum}}{{else}}{{goPage 1}}{{/if}}">
      <i class="fas fa-angle-left"></i>
    </a>
  </li>
  {{#each pageNum}}
    <li class="paginate_button page-item {{signActive this}}">
      <a href="{{goPage this}}" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">{{this}}</a>
    </li>
  {{/each}}
  <li class="page-item">
    <a class="page-link" href="{{#if next}}{{goPage nextPageNum}}{{else}}{{goPage realEndPage}}{{/if}}">
      <i class="fas fa-angle-right"></i>
    </a>
  </li>
  <li class="page-item">
    <a class="page-link" href="{{goPage realEndPage}}">
      <i class="fas fa-angle-double-right"></i>
    </a>
  </li>
</script>

<script>
Handlebars.registerHelper({
  "prettifyDate": function(timeValue){
    var dateObj = new Date(timeValue);
    var year = dateObj.getFullYear();
    var month = dateObj.getMonth() + 1;
    var date = dateObj.getDate();
    return year + "/" + month + "/" + date;
  },
  "VisibleByLoginCheck": function(profes_id){
    var result = "none";
    if(profes_id == "${loginUser.mem_id}") result = "visible";
    return result;
  },
  "goPage": function(pageNum){
    return 'javascript:getPage(' + pageNum + ');';
  },
  "signActive": function(pageNum){
    if(pageNum == currentPage) return 'active';
  }
});

var reply_list_func = Handlebars.compile($("#reply-list-template").html());
var pagination_func = Handlebars.compile($("#reply-pagination-template").html());
</script>

<script>
var currentPage = 1;

function getPage(page){
  $.ajax({
    url: "<%=request.getContextPath()%>/evaluation/list?page=" + page+"&rm_id=${roadMap.rm_id}",
    method: "get",
    success: function(data){
      let reply_html = reply_list_func(data.evaluationList);
      $('.replyLi').remove();
      $('#repliesDiv').after(reply_html);

      if(page) currentPage = page;

      let pageMaker = data.pageMaker;
      let pageNumArray = new Array(pageMaker.endPage - pageMaker.startPage + 1);
      for(let i = pageMaker.startPage; i <= pageMaker.endPage; i++){
        pageNumArray[i - pageMaker.startPage] = i;
      }

      pageMaker.pageNum = pageNumArray;
      pageMaker.prevPageNum = pageMaker.startPage - 1;
      pageMaker.nextPageNum = pageMaker.endPage + 1;

      let pagination_html = pagination_func(pageMaker);
      $("#pagination").html(pagination_html);
    }
  });
}

getPage(1);

function replyRegist_go(){
  let eval_content = $('#newEval_content').val();

  var data = {
    "rm_id": "${roadMap.rm_id}",
    "profes_id": "${loginUser.mem_id}",
    "eval_content": eval_content
  };

  $.ajax({
    url: "<%=request.getContextPath()%>/evaluation/regist",
    method: "post",
    data: JSON.stringify(data),
    contentType: 'application/json',
    success: function(data){
      currentPage = data;
      getPage(data);
      $('#newEval_content').val("");
    },
    error: function(error){
      alert("댓글 등록이 불가합니다.");
    }
  });
}

function evaluationModifyModal_go(eval_id){
  $('#eval_content').val($('div#' + eval_id + '-eval_content').text());
  $('h4.modal-title').text(eval_id);
}

function replyModify_go(){
  let eval_id = $('h4.modal-title').text();
  let eval_content = $('#eval_content').val();

  let sendData = {
    "eval_id": eval_id,
    "eval_content": eval_content
  };

  $.ajax({
    url: "<%=request.getContextPath()%>/evaluation/modify",
    method: "PUT",
    data: JSON.stringify(sendData),
    contentType: "application/json",
    headers:{
      "X-HTTP-Method-Override": "PUT"
    },
    success: function(result){
      alert("수정되었습니다.");
      getPage(currentPage);
    },
    error: function(error){
      alert("댓글 수정이 불가합니다.");
    },
    complete: function(){
      $('button[data-dismiss="modal"]').click();
    }
  });
}

function replyRemove_go(){
  let eval_id = $('h4.modal-title').text();

  $.ajax({
    url: "<%=request.getContextPath()%>/evaluation/remove?eval_id=" + eval_id + "&page=" + currentPage + "&rm_id=${roadMap.rm_id}",
    type: "delete",
    headers: {"X-HTTP-Method-Override": "DELETE"},
    success: function(page){
      alert("삭제되었습니다.");
      currentPage = page;
      getPage(page, rm_id);
    },
    error: function(error){
      alert("현재 댓글 삭제가 불가합니다.");
    },
    complete: function(){
      $('button[data-dismiss="modal"]').click();
    }
  });
}
</script>
