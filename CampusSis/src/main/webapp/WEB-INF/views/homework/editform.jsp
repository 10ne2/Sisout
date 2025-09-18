<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>과제 수정하기</title>

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
    </style>
</head>
<body>
    <h2 class="modal-title">과제 수정하기</h2>

    <form name="editForm" method="post"
      action="${pageContext.request.contextPath}/homework/edit"
      onsubmit="return validateEditForm();">

        <input type="hidden" name="hwNo" value="${homework.hwNo}" />
        <input type="hidden" name="lecsId" value="${homework.lecsId}" />

        <!-- 제목 -->
        <input type="text" name="hwName" class="form-control notNull" title="과제명" value="${homework.hwName}" placeholder="과제명을 입력해주세요." required />

        <!-- 기간 -->
        <div class="d-flex align-items-center">
            <input type="date" name="startDate" class="form-control me-2 notNull" title="시작 날짜" value="${startDate}" />
<input type="time" name="startTime" class="form-control me-2 notNull" title="시작 시간" value="${startTime}" />
<span>~</span>
<input type="date" name="endDate" class="form-control mx-2 notNull" title="종료 날짜" value="${endDate}" />
<input type="time" name="endTime" class="form-control notNull" title="종료 시간" value="${endTime}" />
        </div>

        <!-- 내용 -->
        <textarea id="summernote" name="hwDesc" class="notNull" title="과제 설명">${homework.hwDesc}</textarea>

        <!-- 버튼 -->
        <div class="d-flex justify-content-end mt-3">
            <button type="button" class="btn btn-secondary me-2" onclick="window.close();">취소</button>
            <button type="submit" class="btn btn-primary">수정 완료</button>
        </div>
    </form>

    <script>
        $(document).ready(function () {
            $('#summernote').summernote({
                placeholder: '과제 설명을 입력해주세요.',
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

        function validateEditForm() {
            const inputs = document.querySelectorAll('.notNull');
            for (let input of inputs) {
                if (!input.value.trim()) {
                    alert(input.title + '은(는) 필수입니다.');
                    input.focus();
                    return false;
                }
            }
            return confirm("과제를 수정하시겠습니까?");
        }
    </script>
</body>
</html>