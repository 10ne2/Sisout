<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>강의 동영상 수정</title>
    <link rel="stylesheet" href="${ctx}/resources/adminlte/plugins/fontawesome-free/css/all.min.css">
    <link rel="stylesheet" href="${ctx}/resources/adminlte/dist/css/adminlte.min.css">
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <section class="content mt-5">
        <div class="container">
            <div class="card card-primary">
                <div class="card-header">
                    <h3 class="card-title">강의 동영상 수정</h3>
                </div>

                <!-- ✅ form 시작 -->
                <form method="post" action="${ctx}/lecture/modify" enctype="multipart/form-data">
                    <div class="card-body">
                        <input type="hidden" name="lecId" value="${lecId}" />
                        <input type="hidden" name="lecvidId" value="${video.lecvidId}" />

                        <div class="form-group">
                            <label for="lecvidName">제목</label>
                            <input type="text" class="form-control" name="lecvidName" value="${video.lecvidName}" id="lecvidName" required>
                        </div>

                        <div class="form-group">
                            <label for="lecvidDetail">상세 내용</label>
                           <textarea class="form-control" name="lecvidDetail" id="lecvidDetail" required>${video.lecvidDetail}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="lecvidWeek">주차</label>
                            <input type="text" class="form-control" name="lecvidWeek" id="lecvidWeek" value="${video.lecvidWeek}" required>
                        </div>

                        <div class="form-group">
                            <label for="videoFile">강의 동영상 파일</label>
                            <input type="file" class="form-control-file" name="videoFile" id="videoFile" required>
                        </div>

                        <div class="form-group">
                            <label for="thumbFile">썸네일 이미지</label>
                            <input type="file" class="form-control-file" name="thumbFile" id="thumbFile" required>
                        </div>

                        <div class="form-group">
                            <label for="lecvidDeadline">마감일</label>
                            <input type="date" class="form-control" name="lecvidDeadline" id="lecvidDeadline" required>
                        </div>
                    </div>

                    <div class="card-footer text-right">
                        <button type="submit" class="btn btn-primary">수정</button>
                        <a href="${ctx}/lecture/vidlist?lec_id=${lecId}" class="btn btn-secondary">취소</a>
                    </div>
                </form>
                <!-- form 끝 -->
            </div>
        </div>
    </section>
</div>
</body>
</html>

