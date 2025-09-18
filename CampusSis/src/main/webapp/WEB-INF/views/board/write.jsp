<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>게시글 작성</title>

    <!-- jQuery & Summernote CDN -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.js"></script>

    <style>
        body { font-family: '맑은 고딕', sans-serif; padding: 20px; }
        h2 { margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        select, input[type="text"], input[type="file"] {
            width: 100%; padding: 8px; box-sizing: border-box;
        }
        #summernote { margin-top: 10px; }
        .btn-box { text-align: right; margin-top: 20px; }
        .btn { padding: 8px 16px; border: none; border-radius: 4px; margin-left: 10px; cursor: pointer; }
        .btn-cancel { background-color: #ccc; color: #333; }
        .btn-submit { background-color: #2ac1bc; color: white; }
        .btn-submit:hover { background-color: #1aa6a1; }
        .file-upload { display: flex; align-items: center; border: 1px solid #ccc; padding: 6px; width: 100%; box-sizing: border-box; }
        .file-upload input[type="file"] { display: none; }
        .file-upload label {
            background-color: #e0e0e0; border: 1px solid #aaa; padding: 4px 10px; border-radius: 4px;
            cursor: pointer; margin-right: 10px; font-size: 14px;
        }
        .file-upload .file-name { color: #666; font-size: 14px; }
    </style>
</head>

<body>

<h2>작성하기</h2>

<form action="${pageContext.request.contextPath}/board/write" method="post" enctype="multipart/form-data">

    <!-- 말머리 -->
    <div class="form-group">
        <label>말머리</label>
        <select name="boardCat" required>
            <option value="" disabled selected>말머리 선택</option>
            <option value="공지">공지</option>
            <option value="자유">자유</option>
            <option value="토론">토론</option>
            <option value="시험">시험</option>
        </select>
    </div>

    <!-- 제목 -->
    <div class="form-group">
        <label>제목</label>
        <input type="text" name="boardName" placeholder="제목을 입력해주세요." required />
    </div>

    <!-- 내용 (Summernote) -->
    <div class="form-group">
        <label>내용</label>
        <textarea id="summernote" name="boardContent"></textarea>
    </div>

    <!-- 첨부파일 1 -->
    <div class="form-group">
        <label>첨부파일 1</label>
        <div class="file-upload">
            <label for="file1">파일 선택</label>
            <input type="file" id="file1" name="file1" />
            <span class="file-name">선택된 파일 없음</span>
        </div>
    </div>

    <!-- 첨부파일 2 -->
    <div class="form-group">
        <label>첨부파일 2</label>
        <div class="file-upload">
            <label for="file2">파일 선택</label>
            <input type="file" id="file2" name="file2" />
            <span class="file-name">선택된 파일 없음</span>
        </div>
    </div>

    <!-- 버튼 -->
    <div class="btn-box">
        <button type="button" class="btn btn-cancel" onclick="window.close();">취소</button>
        <button type="submit" class="btn btn-submit">등록</button>
    </div>
</form>

<!-- Summernote 초기화 + 파일명 표시 -->
<script>
    $(document).ready(function () {
        $('#summernote').summernote({
            placeholder: '내용을 입력해주세요.',
            tabsize: 2,
            height: 300,
            toolbar: [
                ['style', ['style']],
                ['font', ['bold', 'italic', 'underline', 'clear']],
                ['fontsize', ['fontsize']],
                ['color', ['color']],
                ['para', ['ul', 'ol', 'paragraph']],
                ['insert', ['link', 'picture', 'video']],
                ['view', ['fullscreen', 'codeview', 'help']]
            ]
        });

        $('input[type="file"]').on('change', function () {
            const fileName = this.files.length > 0 ? this.files[0].name : "선택된 파일 없음";
            $(this).closest('.file-upload').find('.file-name').text(fileName);
        });
    });
</script>

<!-- 등록 성공 시 alert + 부모창 새로고침 + 팝업창 닫기 -->
<c:if test="${success == true}">
    <script>
        window.onload = function () {
            alert("등록이 완료되었습니다.");
            if (window.opener) {
                window.opener.location.reload();
            }
            window.close();
        };
    </script>
</c:if>

</body>
</html>
