<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<c:if test="${submitted}">
  <script>alert("과제가 성공적으로 제출되었습니다.");</script>
</c:if>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
/* 기존 버튼 */
.btn-edit-custom { background-color:#2EC4B6; border-color:#2EC4B6; color:#fff; }
.btn-edit-custom:hover { background-color:#22A99C; border-color:#22A99C; color:#fff; }

/* 제목/본문 간격 */
.hw-title{margin:0 0 14px 0; line-height:1.25;}
.table td .hw-deadline{margin:14px 0 36px !important; font-size:1rem !important; color:#555 !important; display:block !important;}
.table td .hw-desc{margin:0 0 56px !important; font-size:1.6rem !important; line-height:1.8 !important;}
.table td .hw-guidelines{margin-top:0 !important; margin-bottom:44px !important;}
.table td .hw-guidelines li{margin:10px 0 !important;}

/* 섹션 카드(제출/피드백 분리) */
.section-card{border:1px solid #e5e7eb; border-radius:10px; padding:18px; background:#fff;}
.section-title{font-weight:700; margin-bottom:12px;}
.section-meta{color:#6b7280; font-size:.95rem; margin-bottom:10px;}
.feedback-card{background:#f8fffe;}
.feedback-header{display:flex; align-items:center; gap:10px; margin-bottom:8px;}
.feedback-header img{width:46px; height:46px; border-radius:50%; object-fit:cover;}

/* 내용/피드백 줄바꿈 유지 */
.prewrap{white-space:pre-wrap;}
</style>

<div class="wrapper">
  <div class="content-header">
    <div class="container-fluid">
      <div class="row mb-2">
        <div class="col-sm-6"><h1 class="m-0">과제제출</h1></div>
        <div class="col-sm-6">
        </div>
      </div>
    </div>
  </div>

  <section class="content">
    <div class="container-fluid">
      <table class="table">
        <tr>
          <td style="color:#444; background-color:#f8f9fa;">
            <div class="d-flex justify-content-between align-items-center">
              <h1 class="hw-title">
                <c:if test="${submit != null}">
                  <c:url var="detailUrl" value="/homeworksubmit/detail">
                    <c:param name="submitId" value="${submit.hwsubHsno}" />
                  </c:url>
                  <a href="${detailUrl}" style="text-decoration:none; color:inherit;">${homework.hwName}</a>
                </c:if>
                <c:if test="${submit == null}">${homework.hwName}</c:if>
              </h1>
            </div>
            <span class="hw-deadline">
              기한 :
              <fmt:formatDate value="${homework.hwStartDate}" pattern="yyyy-MM-dd HH:mm" />
              ~
              <fmt:formatDate value="${homework.hwEndDate}" pattern="yyyy-MM-dd HH:mm" />
            </span>

            <div class="hw-desc">${homework.hwDesc}</div>

            <ul class="hw-guidelines">
              <li>파일명은 학번_이름_과제명 형식으로 저장해주세요.</li>
              <li>내용을 간단히 요약해서 제출란에 기입 바랍니다.</li>
              <li>제출 마감일을 꼭 지켜주세요.</li>
            </ul>
          </td>
        </tr>

        <tr>
          <td>
            <c:choose>
              <c:when test="${submit != null}">

                <div class="section-card mb-3">
                  <div class="section-title">제출한 과제</div>

                  <div class="section-meta">
                    <strong>제출일시:</strong>
                    <fmt:formatDate value="${submit.submittedAt}" pattern="yyyy-MM-dd HH:mm:ss" />
                  </div>

                  <c:set var="rawName" value="${submit.hwsubFilename}" />
                  <c:choose>
                    <c:when test="${not empty rawName && fn:contains(rawName, '_')}">
                      <c:set var="uscore" value="${fn:indexOf(rawName, '_')}" />
                      <c:set var="displayName" value="${fn:substring(rawName, uscore + 1, fn:length(rawName))}" />
                    </c:when>
                    <c:otherwise>
                      <c:set var="displayName" value="${rawName}" />
                    </c:otherwise>
                  </c:choose>
                  <c:url var="dlUrl" value="/homeworksubmit/download">
                    <c:param name="filename" value="${submit.hwsubFilename}" />
                  </c:url>

                  <p class="mb-1">
                    <strong>파일명:</strong>
                    <c:choose>
                      <c:when test="${not empty rawName}">
                        <a href="${dlUrl}">${displayName}</a>
                      </c:when>
                      <c:otherwise>없음</c:otherwise>
                    </c:choose>
                  </p>

                  <div class="mt-2">
                    <strong>내용</strong>
                    <div class="mt-1 prewrap">${submit.hwsubComment}</div>
                  </div>
                </div>

                <c:if test="${not empty submit.hwsubFeedback}">
                  <div class="section-card feedback-card mb-3">
                    <div class="feedback-header">
                      <img src="${ctx}/member/getPicture?id=${submit.professorMemId}" alt="교수 사진">
                      <div class="feedback-name">${submit.professorName} 교수님</div>
                    </div>
                    <div class="prewrap">${submit.hwsubFeedback}</div>
                  </div>
                </c:if>

                <div class="d-flex justify-content-end mt-3">
                  <c:if test="${nowDate >= startDate && nowDate <= endDate}">
                    <button type="button" class="btn btn-edit-custom me-2"
                            data-bs-toggle="modal" data-bs-target="#editModal">수정</button>
                  </c:if>
                  <a href="${ctx}/homework/list" class="btn btn-secondary">목록</a>
                </div>
              </c:when>

              <c:otherwise>
                <form method="post" action="${ctx}/homeworksubmit/submit"
                      enctype="multipart/form-data"
                      onsubmit="return confirm('과제를 제출하시겠습니까?');">

                  <input type="hidden" name="hwNo" value="${homework.hwNo}" />
                  <input type="hidden" name="lecsId" value="${homework.lecsId}" />

                  <textarea name="hwsubComment" class="form-control mb-2"
                            rows="6" placeholder="내용을 입력하세요" required></textarea>

                  <div class="mb-2">
                    <label class="btn btn-outline-secondary">파일선택
                      <input type="file" name="uploadFile" id="uploadFile" hidden>
                    </label>
                    <span id="file-name">선택된 파일이 없습니다.</span>
                  </div>

                  <div class="d-flex justify-content-end">
                    <button type="submit" class="btn btn-info me-2">제출</button>
                    <a href="${ctx}/homework/list" class="btn btn-secondary">목록</a>
                  </div>
                </form>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </table>
    </div>
  </section>
</div>

<!-- 수정 모달 -->
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true" data-bs-backdrop="false">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <form id="editForm" enctype="multipart/form-data">
        <div class="modal-header">
          <h5 class="modal-title" id="editModalLabel">과제 수정</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="hwNo" value="${submit.hwNo}" />
          <input type="hidden" name="lecsId" value="${submit.lecsId}" />

          <div class="mb-3">
            <label>내용</label>
            <textarea name="hwsubComment" rows="6" class="form-control" required>${submit.hwsubComment}</textarea>
          </div>

          <!-- 모달에서도 원본 파일명만 노출 -->
          <c:set var="editRaw" value="${submit.hwsubFilename}" />
          <c:choose>
            <c:when test="${not empty editRaw && fn:contains(editRaw, '_')}">
              <c:set var="editPos" value="${fn:indexOf(editRaw, '_')}" />
              <c:set var="editDisplay" value="${fn:substring(editRaw, editPos + 1, fn:length(editRaw))}" />
            </c:when>
            <c:otherwise>
              <c:set var="editDisplay" value="${editRaw}" />
            </c:otherwise>
          </c:choose>

          <div class="mb-3">
            <label class="btn btn-outline-secondary">파일 선택
              <input type="file" name="uploadFile" id="editFileInput" hidden>
            </label>
            <span>
              <c:choose>
                <c:when test="${not empty editDisplay}">
                  ${editDisplay}
                </c:when>
                <c:otherwise>(첨부 없음)</c:otherwise>
              </c:choose>
            </span>
          </div>
        </div>

        <div class="modal-footer">
          <button type="submit" class="btn btn-edit-custom">수정 완료</button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
  const fileInput = document.getElementById('uploadFile');
  const fileNameSpan = document.getElementById('file-name');
  if (fileInput) {
    fileInput.addEventListener('change', function() {
      const fileName = this.files.length ? this.files[0].name : '선택된 파일이 없습니다.';
      fileNameSpan.textContent = fileName;
    });
  }

  const editForm = document.getElementById('editForm');
  if (editForm) {
    editForm.addEventListener('submit', function(e) {
      e.preventDefault();
      if (!confirm("과제를 수정하시겠습니까?")) return;

      const formData = new FormData(editForm);
      fetch('${ctx}/homeworksubmit/edit', { method: 'POST', body: formData })
        .then(r => { if (!r.ok) throw new Error(); return r.text(); })
        .then(() => {
          alert("과제가 성공적으로 수정되었습니다.");
          const modalEl = document.getElementById('editModal');
          const modalInstance = bootstrap.Modal.getInstance(modalEl);
          modalInstance && modalInstance.hide();
          location.reload();
        })
        .catch(() => alert("수정 중 오류 발생"));
    });
  }
});
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>