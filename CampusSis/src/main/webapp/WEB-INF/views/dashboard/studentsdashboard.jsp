<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
  String ctx = request.getContextPath();
%>

<div class="container-fluid">

  <div class="row g-3">
    <!-- 좌측: 수강 강의 및 프로젝트 -->
    <div class="col-12 col-lg-6">
      <div class="card shadow-sm border-0">
        <div class="card-header bg-white position-relative">
          <h5 class="mb-0 fw-bold pe-5">수강 강의 및 프로젝트</h5>
          <div class="header-actions btn-group btn-group-sm" role="group">
            <button type="button" class="btn btn-outline-secondary" title="새로고침" onclick="location.reload()">↻</button>
            <a href="<%=ctx%>/lecture" class="btn btn-outline-secondary" title="바로가기">＋</a>
          </div>
        </div>

        <div class="card-body">
          <div class="row">
            <c:forEach var="c" items="${stulectureList}">
              <div class="col-md-6 mb-3">
                <div class="card h-100 border-0 shadow-sm course-card"
                     onclick="location.href='<%=ctx%>/lecture/live?lec_id=${c.lec_id}'" style="cursor:pointer;">
                  <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start">
                      <h6 class="fw-semibold mb-2 text-dark">${c.lec_name}</h6>
                      <span class="rounded-circle d-inline-block" style="width:14px;height:14px;background:#6bc4b3;"></span>
                    </div>
                    <p class="text-muted small mb-3 line-2">${c.lec_desc}</p>
                    <div class="text-secondary small">
                      <i class="bi bi-calendar3 me-1"></i>
                      <fmt:formatDate value="${c.lec_date}" pattern="yyyy.MM.dd"/>
                    </div>
                  </div>
                </div>
              </div>
            </c:forEach>

            <c:if test="${empty stulectureList}">
              <div class="col-12 text-muted small">수강 중인 과목이 없습니다.</div>
            </c:if>
          </div>
        </div>
      </div>
    </div>

    <!-- 우측: 공지 & 일정 -->
    <div class="col-12 col-lg-6">
      <!-- 공지사항 -->
      <div class="card shadow-sm border-0 mb-3">
        <div class="card-header bg-white position-relative">
          <h5 class="mb-0 fw-bold pe-5">공지사항</h5>
          <div class="header-actions btn-group btn-group-sm" role="group">
            <a href="<%=ctx%>/notice" class="btn btn-outline-secondary" title="공지사항 추가">＋</a>
          </div>
        </div>

        <div class="list-group list-group-flush">
          <c:forEach var="n" items="${noticeList}">
            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
               href="<%=ctx%>/notice/detail?id=${n.cono_id}">
              <span class="text-truncate" style="max-width:75%;">${n.cono_name}</span>
              <span class="text-muted small">
                <fmt:formatDate value="${n.cono_date}" pattern="yyyy.MM.dd"/>
              </span>
            </a>
          </c:forEach>
          <c:if test="${empty noticeList}">
            <div class="list-group-item text-muted small">등록된 공지사항이 없습니다.</div>
          </c:if>
        </div>
      </div>

      <!-- 일정: 좌측 리스트 / 우측 달력 -->
      <div class="card shadow-sm border-0">
        <div class="card-header bg-white position-relative">
          <h5 class="mb-0 fw-bold pe-5">일정</h5>
          <div class="header-actions btn-group btn-group-sm" role="group">
            <!-- 새로고침 버튼 제거 -->
            <a href="<%=ctx%>/calendar" class="btn btn-outline-secondary" title="캘린더로 이동">＋</a>
          </div>
        </div>

        <div class="card-body">
          <div class="row">
            <!-- 왼쪽: 과제(이벤트) 리스트 -->
            <div class="col-12 col-md-6">
              <div id="eventListPanel" class="pe-md-2">
                <div id="eventEmpty" class="text-muted small">다가오는 일정이 없습니다.</div>
                <ul id="eventList" class="list-unstyled m-0"></ul>
              </div>
            </div>

            <!-- 오른쪽: 달력 -->
            <div class="col-12 col-md-6">
              <div class="calendar p-2 border rounded" id="calendarBox">
                <div class="d-flex justify-content-between align-items-center mb-2">
                  <button class="btn btn-sm btn-light" id="btnPrevMonth" type="button">〈</button>
                  <div id="calMonth" class="fw-semibold"></div>
                  <button class="btn btn-sm btn-light" id="btnNextMonth" type="button">〉</button>
                </div>
                <table class="table table-sm text-center mb-0">
                  <thead>
                    <tr class="text-muted small">
                      <th>일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th>토</th>
                    </tr>
                  </thead>
                  <tbody id="calBody"></tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- 데이터: JS 배열 -->
<script>
  // 서버에서 내려준 과제/일정 데이터 -> JS 배열
  // 필드: id, title, date(yyyy-MM-dd), type
  var allEvents = [
  <c:forEach var="e" items="${eventList}" varStatus="s">
    { id: ${e.hw_no}, title: '<c:out value="${e.title}"/>',
      date: '<fmt:formatDate value="${e.hw_enddate}" pattern="yyyy-MM-dd"/>',
      type: '<c:out value="${e.type}"/>' }<c:if test="${!s.last}">,</c:if>
  </c:forEach>
  ];
</script>

<script>
  // ===== 달력/일정 렌더링 =====
  const calBody   = () => document.getElementById('calBody');
  const calMonth  = () => document.getElementById('calMonth');
  const evList    = () => document.getElementById('eventList');
  const evEmpty   = () => document.getElementById('eventEmpty');
  const calBox    = () => document.getElementById('calendarBox');
  const evPanel   = () => document.getElementById('eventListPanel');

  // 오늘 00:00 로 고정 (마감 지난 건 숨기기용)
  const TODAY = (() => { const t = new Date(); t.setHours(0,0,0,0); return t; })();

  let view = (function(){
    const d = new Date();
    return { y: d.getFullYear(), m: d.getMonth() }; // 0~11
  })();

  function pad2(n){ return (n<10?'0':'')+n; }
  function ymLabel(y,m){ return y + '.' + pad2(m+1); }

  // 달력 렌더
  function renderCalendar(){
    calMonth().textContent = ymLabel(view.y, view.m);

    const first = new Date(view.y, view.m, 1);
    const last  = new Date(view.y, view.m+1, 0);
    const startWeek = first.getDay();
    const endDate   = last.getDate();

    // 이번 달 & 오늘 이후 일정만 도트 표시
    const dots = {};
    allEvents.forEach(ev=>{
      const dt = new Date(ev.date.replace(/-/g,'/'));
      dt.setHours(0,0,0,0);
      if(dt < TODAY) return;                   // 과거 마감 숨김
      if(dt.getFullYear()===view.y && dt.getMonth()===view.m){
        const key = dt.getDate();
        dots[key] = (dots[key]||0)+1;
      }
    });

    let html = '';
    let day = 1;
    for(let r=0; r<6; r++){
      let row = '<tr>';
      for(let c=0; c<7; c++){
        if(r===0 && c<startWeek || day>endDate){
          row += '<td class="py-2">&nbsp;</td>';
        }else{
          const has = dots[day]||0;
          const isToday = (new Date().toDateString() === new Date(view.y, view.m, day).toDateString());
          row += '<td class="py-2 align-middle">'
              +  '<div class="position-relative d-inline-block" style="min-width:28px;">'
              +  '<span class="'+(isToday?'badge bg-secondary':'')+'">'+day+'</span>'
              +  (has>0 ? '<span class="dot" title="일정 '+has+'개"></span>' : '')
              +  '</div>'
              +  '</td>';
          day++;
        }
      }
      row += '</tr>';
      html += row;
      if(day> endDate) break;
    }
    calBody().innerHTML = html;
  }

  // 리스트 렌더 (이번 달 & 오늘 이후만)
  function renderEventList(){
    const list = allEvents
      .map(ev => {
        const d = new Date(ev.date.replace(/-/g,'/')); d.setHours(0,0,0,0);
        return { ...ev, _d: d, _y: d.getFullYear(), _m: d.getMonth(), _day: d.getDate() };
      })
      .filter(e => e._y===view.y && e._m===view.m && e._d >= TODAY) // 과거 숨김
      .sort((a,b)=> a._d - b._d);

    evList().innerHTML = '';
    if(list.length===0){
      evEmpty().style.display = 'block';
      return;
    }
    evEmpty().style.display = 'none';

    const today = TODAY;

    list.forEach(e=>{
      const diff = Math.floor((e._d - today) / (1000*60*60*24)); // 0 이상만 남음
      const dstr = diff===0 ? 'D-Day' : 'D-'+diff;
      const when = e._y + '.' + pad2(e._m+1) + '.' + pad2(e._day);

      const li = document.createElement('li');
      li.className = 'd-flex align-items-center border rounded px-3 py-2 mb-2';
      li.innerHTML =
        '<div class="me-auto">'+
          '<div class="fw-semibold small text-truncate" style="max-width:240px;">'+e.title+'</div>'+
          '<div class="text-muted tiny">'+when+'</div>'+
        '</div>'+
        '<span class="badge bg-light text-dark">'+dstr+'</span>';
      evList().appendChild(li);
    });
  }

  // 리스트 높이를 달력 높이에 자동 맞춤
  function syncHeights(){
    // 달력 전체 높이 기준
    const h = calBox().offsetHeight;
    evPanel().style.maxHeight = h + 'px';
  }

  function renderAll(){
    renderCalendar();
    renderEventList();
    // 렌더 후 높이 싱크
    syncHeights();
  }

  document.getElementById('btnPrevMonth').addEventListener('click', function(){
    if(view.m===0){ view.y--; view.m=11; } else { view.m--; }
    renderAll();
  });
  document.getElementById('btnNextMonth').addEventListener('click', function(){
    if(view.m===11){ view.y++; view.m=0; } else { view.m++; }
    renderAll();
  });

  // 창 크기 바뀌면 다시 맞춤
  window.addEventListener('resize', syncHeights);

  // 최초 렌더
  renderAll();
</script>

<style>
/* 공통 */
.card-header { position: relative; }
.card-header .header-actions { position:absolute; right:12px; top:8px; }
.course-card:hover { transform: translateY(-2px); box-shadow: 0 .5rem 1rem rgba(0, 0, 0, .08) !important; }
.line-2 { display:-webkit-box; -webkit-line-clamp:2; -webkit-box-orient:vertical; overflow:hidden; }
.tiny { font-size:.75rem; }

/* 일정 리스트: 스크롤 + 달력 높이에 맞춤(자바스크립트가 max-height를 넣어줌) */
#eventListPanel{
  overflow-y: auto;
  overscroll-behavior: contain;
  padding-right: .25rem;
}
#eventListPanel::-webkit-scrollbar{ width: 8px; }
#eventListPanel::-webkit-scrollbar-thumb{ border-radius: 8px; background: rgba(0,0,0,.15); }
#eventListPanel::-webkit-scrollbar-track{ background: transparent; }

/* 달력 점 표시 */
.calendar .dot{
  position:absolute; right:-2px; bottom:-2px; width:8px; height:8px;
  border-radius:50%; background:#6bc4b3;
}
.calendar .badge.bg-secondary{ border-radius:6px; padding:.15rem .35rem; }
</style>
