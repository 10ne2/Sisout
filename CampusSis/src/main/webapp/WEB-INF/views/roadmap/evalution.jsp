<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
  html, body {
    margin: 0;
    padding: 0;
    overflow-x: hidden;
    height: 100%;
  }
</style>
<body>
	<div class="wrap" style="height:100vh;">
		<div class="row d-flex align-items-center">
			<div class="card-header" style="border-bottom: none;">
  			<h3 class="card-title ml-2 mt-2" style="font-size: 25px; font-weight: bold;">평가</h3>
  			</div>
  			<div class="col-1 ml-auto">
        <button type="button" class="btn btn-info" data-dismiss="modal" onclick="CloseWindow();"
          style="background-color:#aaaaaa; border-radius:5px; width:120px; height:40px; border:none;margin-right:-20px; font-weight:bold;">
          <span style="color:#ffffff; font-size:20px;">취소</span>
        </button>
      </div>
      <div class="col-2" style="margin-left:73px; margin-right:50px;">
        <button type="button" class="btn btn-info" onclick="regist_go();"
          style="background-color:#2ec4b6; border-radius:5px; width:120px; height:40px; border:none; font-weight:bold;" >
          <span style="color:#ffffff; font-size:20px;">평가</span>
        </button>
      </div>
  		</div>
  		<form role="form" method="post" action="${pageContext.request.contextPath}/roadmap/evaluation/regist" name="registForm">	
  		<input type="hidden" name="rm_id" value="${rm_id}">
  		<input type="hidden" name="profes_id" value="${loginUser.mem_id}">
  		<div class="row d-flex" style="margin-left:40px; margin-top:40px;">
  		<label>
  			<span style="font-weight:bold;">피드백</span>
  		</label>
  			
		  </div>
  			<div class="d-flex" style="border:1px solid #000000; border-top:3px solid #2ec4b6; width:625px; margin-left:40px; align-items:center;border-collapse: collapse;">
  				<div style="width:100%;">
  				<textarea class="form-control" name="eval_content" id="eval_content" rows="5"  placeholder="1000자 내외로 작성하세요." style="resize: none;"></textarea>
  				</div>
  			</div>
  			<div class="row" style="margin-left:40px; margin-top:20px; align-items: flex-end;">
  <div class="col-5">
    <label>
      <span style="font-weight:bold;">루브릭</span>
    </label>
  </div>
  <div class="col-5 ml-auto" style="font-size:12px; margin-bottom:2px; margin-right:23px;">
    <span>매우 좋음 : 5 좋음 : 4 보통 : 3 나쁨 : 2 매우 나쁨 : 1</span>
  </div>
</div>
  			<table border="1" style="font-size:14px; text-align:center; width:625px; margin-left:40px; border-collapse: collapse;">
  <tr style="background-color:#f0f0f0;">
    <th style="width:40%; height:40px;">평가 기준</th>
    <th colspan="5" style="width:30%;">${projectList[0].project_name }</th>
  </tr>
  <tr>
    <td style="width:380px;">실제 문제를 얼마나 명확하게 정의하고, 그에 따른 목표를 현실적으로 설정했는가?</td>
    <td colspan="5" style="vertical-align: middle"><input type="number" class="rubric" value="5" min="1" max="5" onchange="calculateTotal()" style="border:none; width:100%; height:100%; text-align:center;"></td>
  </tr>
  <tr>
    <td>사용자 및 관련 이해관계자의 니즈를 파악하고 반영했는가?</td>
    <td colspan="5" style="vertical-align: middle"><input type="number" class="rubric" value="5" min="1" max="5" onchange="calculateTotal()" style="border:none; width:100%; height:100%; text-align:center;"></td>
  </tr>
  <tr>
    <td>제안한 솔루션이 기존 방식을 넘어서는 창의적 접근을 포함하며, 문제 해결에 적합한가?</td>
    <td colspan="5" style="vertical-align: middle"><input type="number" class="rubric" value="5" min="1" max="5" onchange="calculateTotal()" style="border:none; width:100%; height:100%; text-align:center;"></td>
  </tr>
  <tr>
    <td>데이터, 사례, 이론 등을 활용한 논리적 의사결정을 했는가?</td>
    <td colspan="5" style="vertical-align: middle"><input type="number" class="rubric" value="5" min="1" max="5" onchange="calculateTotal()" style="border:none; width:100%; height:100%; text-align:center;"></td>
  </tr>
  <tr>
    <td>각 단계에서 어떤 결과물(문서, 시제품, 보고서 등)이 나오는지 명확하게 제시되었는가?</td>
   <td colspan="5" style="vertical-align: middle"><input type="number" class="rubric" value="5" min="1" max="5" onchange="calculateTotal()" style="border:none; width:100%; height:100%; text-align:center;"></td>
  </tr>
  <tr>
    <td style="text-align:center; background-color:#f0f0f0; height: 50px;">총점</td>
    <td colspan="5"><input type="number" id="eval_score" name="eval_score" class="form-control" value="" readonly
           style="text-align:center; font-weight:bold;"></td>
  </tr>
</table>
  		</form>
  	</div>
  <script>
  function calculateTotal() {
	  let inputs = document.querySelectorAll('.rubric');
	  let total = 0;
	  inputs.forEach(input => {
	    let val = parseInt(input.value);
	    if(!isNaN(val)) total += val;
	  });
	  // totalScore 대신 input value에 넣기
	  document.getElementById('eval_score').value = total;
	}

	// 페이지 로딩 시 초기 총점 계산
	window.onload = calculateTotal;
	
	 function regist_go() {
	      let form = document.forms["registForm"];

	      // 피드백 내용 확인
	      if (!form.eval_content.value.trim()) {
	          alert("내용을 입력해주세요.");
	          form.eval_content.focus();
	          return;
	      }


	      // 총점 계산
	      calculateTotal();

	      // 폼 제출
	      form.submit();
	  }
	</script>