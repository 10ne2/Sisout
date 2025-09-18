<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="deadline" value="${video.lecvidDeadline}" />

<!DOCTYPE html>
<html>
<head>
    <title>${video.lecvidName}</title>
    <!-- AdminLTE 3.2.0 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/adminlte/plugins/fontawesome-free/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/adminlte/dist/css/adminlte.min.css">
    <style>
        .video-container {
            width: 100%;
            background-color: #e0e0e0;
            height: 320px;
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .video-container video {
            max-width: 100%;
            max-height: 100%;
        }

        .info-panel {
            background-color: #f9f9f9;
            border-radius: 10px;
            padding: 15px;
            border: 1px solid #ccc;
            min-height: 320px;
        }

        .info-panel h5 {
            font-weight: bold;
            border-bottom: 2px solid #007bff;
            padding-bottom: 8px;
            margin-bottom: 12px;
            color: #007bff;
        }

        .btn-custom {
            width: 100px;
        }

        .lecture-title {
            font-size: 20px;
            font-weight: bold;
        }

        .date-badge {
            background-color: #f4f4f4;
            border-radius: 5px;
            padding: 6px 12px;
            display: inline-block;
            margin-top: 8px;
        }
    </style>
</head>

<body class="hold-transition layout-top-nav">
<div class="wrapper">
    <div class="content-wrapper pt-4">
        <div class="container">

            <!-- 강의 제목 및 출석 버튼 -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div class="lecture-title">${video.lecvidName}</div>
                <div class="date-badge">
                    기간 :
                    <fmt:formatDate value="${video.lecvidDeadline}" pattern="yyyy. MM. dd" />
                </div>
            </div>

            <div class="row">
                <!-- 동영상 영역 -->
                <div class="col-md-8">
                    <div class="video-container">
                        <video id="lectureVideo" width="100%" height="100%" controls>
                            <source src="${pageContext.request.contextPath}${video.lecvidVidpath}" type="video/mp4" />
                            브라우저가 비디오를 지원하지 않습니다.
                        </video>
                    </div>
                </div>

                <!-- 상세 설명 영역 -->
                <div class="col-md-4">
                    <div class="info-panel">
                        <h5>${video.lecvidWeek}</h5>
                        <p>${video.lecvidDetail}</p>
                    </div>
                </div>
            </div>

            <!-- 버튼 영역 -->
            <div class="d-flex justify-content-center mt-4">
                <button class="btn btn-info btn-custom" onclick="handleExit()">목 록</button>
            </div>
        </div>
    </div>
</div>

<!-- AdminLTE Scripts -->
<script src="${pageContext.request.contextPath}/resources/adminlte/plugins/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/adminlte/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/adminlte/dist/js/adminlte.min.js"></script>

<script>
    const video = document.getElementById('lectureVideo');

    function getProgressPercent() {
        if (!video.duration || isNaN(video.duration)) return 0;
        const percent = Math.floor((video.currentTime / video.duration) * 100);
        return Math.min(percent, 100);
    }

    function handleExit() {
        const progress = getProgressPercent();
        const lecvidId = "${video.lecvidId}";
        const lecId = "${lecId}";
        const memId = "${memId}";
        const aNo = lecId + memId + lecvidId;
        const deadlineStr = "<fmt:formatDate value='${deadline}' pattern='yyyy-MM-dd' />";
        const today = new Date();
        const deadline = new Date(deadlineStr);

        if (today > deadline) {
            alert("마감일이 지났습니다. 출석 정보는 저장되지 않습니다.");
            window.close();
            return;
        }

        const data = new URLSearchParams();
        data.append("aNo", aNo);
        data.append("progress", progress);

        fetch("${pageContext.request.contextPath}/lecture/progress", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: data.toString()
        }).then(() => {
            window.close();
        });
    }
</script>
</body>
</html>
