<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
    
<!-- pagination -->
<nav aria-label="Navigation">
	<ul class="pagination justify-content-center m-0">
		<li class="page-item">
			<a class="page-link" href="javascript:search_list(1);">
				<i class="fas fa-angle-double-left" style="color:#707070"></i>
			</a>
		</li>
		<li class="page-item">
			<a class="page-link" href="javascript:search_list(${pageMaker.prev ? pageMaker.startPage-1 : pageMaker.page});">
				<i class="fas fa-angle-left" style="color:#707070"></i>
			</a>
		</li>
		<c:forEach var="pageNum" begin="${pageMaker.startPage }" end="${pageMaker.endPage }" >
			<li class="page-item ${pageMaker.page == pageNum?'active':''}">
				<a class="page-link" href="javascript:search_list(${pageNum });">
					${pageNum }
				</a>
			</li>
		</c:forEach>
		
		<li class="page-item">
			<a class="page-link" href="javascript:search_list(${pageMaker.next ? pageMaker.endPage+1 : pageMaker.page});">
				<i class="fas fa-angle-right" style="color:#707070"></i>
			</a>
		</li>
		<li class="page-item">
			<a class="page-link" href="javascript:search_list(${pageMaker.realEndPage });">
				<i class="fas fa-angle-double-right" style="color:#707070"></i>
			</a>
		</li>			
	</ul>
</nav>


<form id="jobForm" style="display:none;">
  <input type='hidden' name="page" value="1" />
  <input type='hidden' name="perPageNum" value="3" />
  <input type='hidden' name="samester" value="" />
  <input type='hidden' name="project_name" value="" />
  <input type='hidden' name="project_stdate" value="" />
  <input type='hidden' name="project_endate" value="" />
</form>
<script>
function search_list(page){
	console.log("page:", page);
	  let samester = document.querySelector('select[name="samester"]').value;
	  let project_name = document.querySelector('input[name="project_name"]').value;
	  let project_stdate = document.querySelector('input[name="project_stdate"]').value;
	  let project_endate = document.querySelector('input[name="project_endate"]').value;
	  let perPageNum = document.querySelector('input[name="perPageNum"]').value; // 이 부분

	  let form = document.querySelector("#jobForm");
	  form.samester.value = samester;
	  form.project_name.value = project_name;
	  form.project_stdate.value = project_stdate;
	  form.project_endate.value = project_endate;
	  form.perPageNum.value = perPageNum;  // 여기에 넣기
	  form.page.value = page;

	  form.submit();
	}
</script>




