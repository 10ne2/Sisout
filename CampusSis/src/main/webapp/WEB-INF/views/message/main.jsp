<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@ page import="com.camp_us.dto.MemberVO" %>

<head>
	
<!-- Summernote -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/summernote/summernote-bs4.min.js"></script>

</head>

<%@ include file="/WEB-INF/views/message/css.jsp" %>


<div style="height: 900px; padding: 15px;">
	<div>
		<span
			style="display: block; width:50px; font-size: 20pt; font-weight: bold; margin-bottom: 18px; margin-left: 5px;cursor:pointer"
			onclick="location.href='<%=request.getContextPath()%>/message/main'" >
			메일</span>
	</div>
	<div class="row" style="display:flex; flex-direction: row;">
	<!-- 카테고리 시작 -->
		<%@ include file="/WEB-INF/views/message/category.jsp" %>
	<!-- 카테고리 끝 -->
		<!-- /.col -->
	<div class="col-md-10">
		<div style=" display:flex; flex-direction: row;">
			<div class="col-md-6" style="display:block;">
				<div class="card card-outline card-primaryc" style="heigh: 500px">
					<div class="card-header" style="display:flex; flex-direction: row;">
						<div>
							<h3 class="card-title" style="margin-top:5px">받은 메일함</h3>
						</div>
						<div style="margin-left:auto">
							<button type="button" class="btn btn-default btn-sm" style=""
									onclick="location.href='<%=request.getContextPath()%>/message/receive'">
									<img id="readImg_" src="<%=request.getContextPath()%>/resources/images/go.png"
															      style="width:16px; cursor:pointer; margin-top:-2px"/>
							</button>
						</div>
					</div>
					<!-- /.card-header -->
					<div class="table-responsive mailbox-messages"
						style="height: 420px; overflow-y: auto;">
						<table class="mailTable table table-hover">
							<tbody>
								<c:if test="${empty receiveList }">
									<tr>
						   	   			<div colspan="5" style="font-size: 30px; font-weight: bold; color: #e0e0e0; line-height: 200px" class="text-center" >받은 메일이 없습니다.</div>
						   	   		</tr>
								</c:if>
								<c:if test="${not empty receiveList }">
									<c:forEach items="${receiveList }" var="receive">
										<tr onclick="OpenWindow('<%=request.getContextPath()%>/message/detail?mail_id=${receive.mail_id}','상세보기',1040,800);" style="cursor:pointer;">
											<td style="width: 100%; min-height: 48px; display: flex; flex-direction: column; margin:-1.6px">
												<div style="width:100%;">
													<div style="display: flex; flex-direction: row;">
														<div class="" style="display: flex; flex-direction: row;">
															<div style="margin-left:10px;">
															    <img id="readImg_" src="<%=request.getContextPath()%>/resources/images/read/${receive.mail_rread }.png"
															      style="width:20px; cursor:pointer"/>
															</div>
														</div>
														<div class="" style="width:150px; display:flex; flex-direction: row; margin-left:20px">
															<a style="width: 60px; line-height:30px;">
																${receive.sender_name }
															</a>
															<a style="line-height:30px; font-size:14px; color: #999">
																${receive.mail_sender}
															</a>
														</div>
														<div style=" border:none; display: flex; flex-direction: row">
															<a style="width: 300px; font-size:14px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; line-height: 30px">
																	${receive.mail_name }</a>
														</div>
														<div class="mailbox-date" style="margin-left:auto; line-height:30px; font-size:12px; color: #bbb">
														 	<fmt:formatDate value="${receive.mail_rdate }" pattern="yy-MM-dd HH:mm" />
													 	</div>
													</div>
												</div>
											</td>	
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
						<!-- /.table -->
					</div>
						<!-- /.mail-box-messages -->
					</div>
					<!-- /.card-body -->
				</div>
			<!-- /.card -->
				<div class="col-md-6" style="display:block; heigh: 500px">
					<div class="card card-outline card-primaryc">
						<div class="card-header" style="display:flex; flex-direction: row;">
							<div>
								<h3 class="card-title"  style="margin-top:5px">보낸 메일함</h3>
							</div>
							<div style="margin-left:auto">
								<button type="button" class="btn btn-default btn-sm" style=""
									onclick="location.href='<%=request.getContextPath()%>/message/send'">
									<img id="readImg_" src="<%=request.getContextPath()%>/resources/images/go.png"
															      style="width:16px; cursor:pointer; margin-top:-2px"/>
								</button>
							</div>
						</div>
						<!-- /.card-header -->
						<div class="table-responsive mailbox-messages"
							style="height: 420px; overflow-y: auto;">
							<table class="mailTable table table-hover">
								<tbody>
									<c:if test="${empty sendList }">
									<tr>
						   	   			<div colspan="5" style="font-size: 30px; font-weight: bold; color: #e0e0e0; line-height: 200px" class="text-center" >보낸 메일이 없습니다.</div>
						   	   		</tr>
									</c:if>
									<c:if test="${not empty sendList }">
										<c:forEach items="${sendList }" var="send">
											<tr onclick="OpenWindow('<%=request.getContextPath()%>/message/detail?mail_id=${send.mail_id}','상세보기',1040,800);" style="cursor:pointer;">
												<td style="width: 100%; min-height: 48px; display: flex; flex-direction: column; margin:-1.6px">
													<div style="width:100%;">
														<div style="display: flex; flex-direction: row;">
															<div class="" style="width:150px; display:flex; flex-direction: row; margin-left:10px">
																<a style="width: 60px; line-height:30px;">
																	${send.receiver_name }
																</a>
																<a style="line-height:30px; font-size:14px; color: #999">
																	${send.mail_receiver }
																</a>
															</div>
															<div style=" border:none; display: flex; flex-direction: row">
																<a style="width: 300px; font-size:14px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; line-height: 30px">
																		${send.mail_name }</a>
															</div>
															<div class="mailbox-date" style="margin-left:auto; line-height:30px; font-size:12px; color: #bbb">
															 	<fmt:formatDate value="${send.mail_sdate }" pattern="yy-MM-dd HH:mm" />
														 	</div>
														</div>
													</div>
												</td>	
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
							<!-- /.table -->
						</div>
							<!-- /.mail-box-messages -->
						</div>
					<!-- /.card-body -->
				</div>
				<!-- /.card -->
			</div>
			<div class="col-md-12"style="display:block;">
				<div class="card card-outline card-primaryc">
					<div class="card-header" style="display:flex; flex-direction: row;">
						<div>
							<h3 class="card-title" style="margin-top:5px">휴지통</h3>
						</div>
						<div style="margin-left:auto">
							<button type="button" class="btn btn-default btn-sm" style=""
									onclick="location.href='<%=request.getContextPath()%>/message/waste'">
									<img id="readImg_" src="<%=request.getContextPath()%>/resources/images/go.png"
															      style="width:16px; cursor:pointer; margin-top:-2px"/>
							</button>
						</div>
					</div>
					<!-- /.card-header -->
					<div class="table-responsive mailbox-messages"
						style=" overflow-y: auto; height:255px;">
						<table class="mailTable table table-hover">
							<tbody>
								<c:if test="${empty wasteList }">
									<tr>
						   	   			<div colspan="5" style="font-size: 30px; font-weight: bold; color: #e0e0e0; line-height: 200px" class="text-center" >휴지통에 메일이 없습니다.</div>
						   	   		</tr>
									</c:if>
									<c:if test="${not empty wasteList }">
										<c:forEach items="${wasteList }" var="waste">
											<tr onclick="OpenWindow('<%=request.getContextPath()%>/message/detail?mail_id=${waste.mail_id}','상세보기',1040,800);" style="cursor:pointer;">
												<td style="width: 100%; min-height: 48px; display: flex; flex-direction: column; margin:-2.2px">
													<div style="width:100%;">
														<div style="display: flex; flex-direction: row;">
															<div class="" style="display: flex; flex-direction: row;">
																<div style="margin-left:10px;">
																    <img id="readImg_"
																    src="<%=request.getContextPath()%>/resources/images/read/${waste.mail_sender == sessionScope.loginUser.mem_id ? waste.mail_rread : waste.mail_sread}.png"
																      style="width:20px; cursor:pointer"/>
																</div>
															</div>
															<div class="" style="width:150px; display:flex; flex-direction: row; margin-left:20px">
																<a style="width: 60px; line-height:30px;">
																	${waste.mail_sender == sessionScope.loginUser.mem_id ? waste.receiver_name : waste.sender_name}
																</a>
																<a style="line-height:30px; font-size:14px; color: #999">
																	${waste.mail_sender == sessionScope.loginUser.mem_id ? waste.mail_receiver : waste.mail_sender}
																</a>
															</div>
															<div style=" border:none; display: flex; flex-direction: row">
																<a style="width: 900px; font-size:14px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; line-height: 30px">
																		${waste.mail_name }</a>
															</div>
															<div class="mailbox-date" style="margin-left:auto; line-height:30px; font-size:12px; color: #bbb">
															 	<fmt:formatDate
															 	value="${waste.mail_sender == sessionScope.loginUser.mem_id ? waste.mail_rdate : waste.mail_sdate}" pattern="yy-MM-dd HH:mm" />
														 	</div>
														</div>
													</div>
												</td>	
											</tr>
										</c:forEach>
									</c:if>
							</tbody>
						</table>
						<!-- /.table -->
					</div>
						<!-- /.mail-box-messages -->
					</div>
				<!-- /.card-body -->
			</div>
		</div>
	</div>
		<!-- /.col -->
			<!-- /.card -->
</div>
