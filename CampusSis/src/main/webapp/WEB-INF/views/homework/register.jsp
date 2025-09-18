<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>과제 등록하기</title>

    <!-- Bootstrap & Summernote CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">

    <!-- jQuery, Bootstrap, Summernote JS -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>

    <style>
        body {
            padding: 20px;
            background-color: #f5f7fa;
            font-family: 'Noto Sans KR', sans-serif;
        }
        .modal-title {
            font-weight: bold;
            font-size: 28px;
            margin-bottom: 20px;
        }
        .form-control, .form-select {
            margin-bottom: 15px;
        }
        .note-editor.note-frame {
            margin-bottom: 15px;
        }
        .hint {
            font-size: 0.9rem;
            color: #6c757d;
        }
    </style>
</head>
<body>
    <h2 class="modal-title">과제 등록하기</h2>

    <!-- 컨트롤러에서 ?error=chooseLecture 로 보낸 경우 경고 -->
    <c:if test="${param.error == 'chooseLecture'}">
        <div class="alert alert-warning">담당 강의가 여러 개입니다. 강의를 선택해주세요.</div>
    </c:if>

    <form name="registForm" method="post"
          action="${pageContext.request.contextPath}/homework/write"
          onsubmit="return regist_go();">

        <!-- 강의 선택 (컨트롤러: writeForm 에서 lectures, defaultLecId 세팅) -->
        <c:if test="${not empty defaultLecId}">
            <!-- 단일 강의면 숨김으로 자동 전송 -->
            <input type="hidden" name="lecId" value="${defaultLecId}">
            <div class="mb-2">
                <span class="badge bg-primary">자동 선택된 강의: ${defaultLecId}</span>
            </div>
        </c:if>

        <c:if test="${empty defaultLecId}">
            <!-- 여러 강의면 선택 UI -->
            <label class="form-label">강의 선택</label>
            <select name="lecId" class="form-select notNull" title="강의">
                <option value="">강의를 선택하세요</option>
                <c:forEach var="lec" items="${lectures}">
                    <!-- lectures는 Map 형태(lec_id, lec_name) 또는 VO여도 EL로 접근 가능 -->
                    <option value="${lec.lec_id}">${lec.lec_id} - ${lec.lec_name}</option>
                </c:forEach>
            </select>
            <div class="hint">담당 강의가 여러 개인 경우 반드시 선택해야 합니다.</div>
        </c:if>

        <!-- 제목 (VO: hwName) -->
        <input type="text" name="hwName" class="form-control notNull" title="제목" placeholder="제목을 입력해주세요.">

        <!-- 기간 (컨트롤러에서 yyyy-MM-dd / HH:mm 로 파싱) -->
        <div class="d-flex align-items-center">
            <input type="date" name="startDate" class="form-control me-2 notNull" title="시작 날짜">
            <input type="time" name="startTime" class="form-control me-2 notNull" title="시작 시간">
            <span>~</span>
            <input type="date" name="endDate" class="form-control mx-2 notNull" title="종료 날짜">
            <input type="time" name="endTime" class="form-control notNull" title="종료 시간">
        </div>

        <!-- 내용 (VO: hwDesc) -->
        <textarea id="summernote" name="hwDesc" class="notNull" title="내용"></textarea>

        <!-- 버튼 -->
        <div class="d-flex justify-content-end mt-3">
            <button type="button" class="btn btn-secondary me-2" onclick="CloseWindow();">취소</button>
            <button type="submit" class="btn btn-primary">등록</button>
        </div>
    </form>

    <script>
        $(document).ready(function () {
            $('#summernote').summernote({
                placeholder: '내용을 입력해주세요.',
                tabsize: 2,
                height: 300,
                toolbar: [
                    ['style', ['style']],
                    ['font', ['bold', 'italic', 'underline', 'clear']],
                    ['fontname', ['fontname']],
                    ['fontsize', ['fontsize']],
                    ['color', ['color']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['insert', ['link', 'picture', 'video']],
                    ['view', ['fullscreen', 'codeview']]
                ]
            });
        });

        function regist_go() {
            const inputs = document.querySelectorAll('.notNull');
            for (let input of inputs) {
                // select요소도 value 체크
                if (!input.value || !input.value.trim()) {
                    alert(input.title + '은(는) 필수입니다.');
                    input.focus();
                    return false;
                }
            }
            return confirm("과제를 등록하시겠습니까?");
        }

        function CloseWindow() {
            window.close();
        }
    </script>
</body>
</html>
