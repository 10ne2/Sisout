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
  <input type='hidden' name="perPageNum" value="7" />
  <input type='hidden' name="rm_category" value="" />
  <input type='hidden' name="rm_name" value="" />
  <input type='hidden' name="rm_stdate" value="" />
  <input type='hidden' name="rm_endate" value="" />
  <input type='hidden' name="eval_status" value=""/>
  <input type='hidden' name="project_id" value="${param.project_id }"/>
</form>
<script>
function search_list(page){
	console.log("page:", page);
	  let rm_category = document.querySelector('select[name="rm_category"]').value;
	  let rm_name = document.querySelector('input[name="rm_name"]').value;
	  let rm_stdate = document.querySelector('input[name="rm_stdate"]').value;
	  let rm_endate = document.querySelector('input[name="rm_endate"]').value;
	  let evalStatusCheckbox = document.querySelector('input[name="eval_status"]:checked');
	  let eval_status = evalStatusCheckbox ? evalStatusCheckbox.value : "";
	  let perPageNum = document.querySelector('input[name="perPageNum"]').value; // 이 부분
	  
	  let form = document.querySelector("#jobForm");
	  form.rm_category.value = rm_category;
	  form.rm_name.value = rm_name;
	  form.rm_stdate.value = rm_stdate;
	  form.rm_endate.value = rm_endate;
	  form.eval_status.value = eval_status;
	  form.project_id.value = "${param.project_id }";
	  form.perPageNum.value = perPageNum;  // 여기에 넣기
	  form.page.value = page;

	  form.submit();
	}
</script>




