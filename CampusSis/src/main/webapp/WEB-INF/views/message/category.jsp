<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
			<div class="col-md-2" style="width:250px">
			<a class="btnw btn-primary btn-block mb-3" style="width:width:250px" onclick="location.href='<%=request.getContextPath()%>/message/registForm'">메일
				작성</a>
			<div class="card" style="width:250px">
				<div class="card-body p-0" style="width:250px !important">
					<ul class="nav flex-column" style="width:250px; height: 745px;">
						<li class="mailR <%= request.getRequestURI().contains("/message/main") ? "active" : "" %>" style="height: 50px; ">
							<button id="btnAll" type="button" data-mail="1" class="d-flex align-items-center mailR"
							style="width: 100%; height: 100%; gap: 20px; line-height: 50px; border:none; padding:15px; overflow:hidden"
							onclick="location.href='<%=request.getContextPath()%>/message/main'">
								<i class="fas fa-inbox" style=""></i>
								<span style="display: block;">전체 메일</span>
							</button>
						</li>
						<li class="mailR <%= request.getRequestURI().contains("/message/receive") ? "active" : "" %>" style="height: 50px">
							<button id="btnRecv" type="button" data-mail="2" class="d-flex align-items-center mailR"
							style="width: 100%; height: 100%; gap: 20px; line-height: 50px; border:none; padding:15px"
							onclick="location.href='<%=request.getContextPath()%>/message/receive'">
								<i class="far fa-envelope" style=""></i>
								<span style="display: block;">받은 메일함</span>
								<span id="unreadCount" class="badgec bg-primaryc" style="width:auto;display: block; margin-left: auto; padding: 0 5px 0 5px">${unreadCount}</span>
							</button>
						</li>
						<li class="mailR <%= request.getRequestURI().contains("/message/send") ? "active" : "" %>" style="height: 50px; border-bottom: 1px solid #ddd;">
							<button id="btnSent" type="button" data-mail="3" class="d-flex align-items-center mailR"
							style="width: 100%; height: 100%; gap: 24px; line-height: 50px; border:none; padding:15px"
							onclick="location.href='<%=request.getContextPath()%>/message/send'">
							<i class="far fa-file-alt" style="margin-left:2px"></i>
							<span style="display: block;margin-left:-2px">보낸 메일함</span>
							</button>
						</li>
						<li class="mailR <%= request.getRequestURI().contains("/message/waste") ? "active" : "" %>" style="height: 50px; border-bottom: 1px solid #ddd;">
							<button id="btnSent" type="button" data-mail="3" class="d-flex align-items-center mailR"
							style="width: 100%; height: 100%; gap: 24px; line-height: 50px; border:none; padding:15px"
							onclick="location.href='<%=request.getContextPath()%>/message/waste'">
							<i class="far fa-trash-alt" style="margin-left:2px"></i>
							<span style="display: block;margin-left:-2px">휴지통</span>
							</button>
						</li>
					</ul>
				</div>
				<!-- /.card-body -->
			</div>
			<!-- /.card -->
		</div>
	<!-- 카테고리 끝 -->
	
<script>
window.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll(".mailR");
    const currentPath = "<%= request.getRequestURI() %>"; // 현재 요청 URI

    buttons.forEach(btn => {
      // onclick 속성에서 이동할 URL 뽑기
      const match = btn.getAttribute("onclick").match(/'(.*?)'/);
      if (match) {
        const btnPath = match[1];
        // 현재 URI가 버튼의 path를 포함하면 활성화
        if (currentPath.includes(btnPath)) {
          btn.classList.add("active");
        }
      }
    });
  });
</script>