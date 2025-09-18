<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style>
.page-item.active .page-link {
  background-color: #2ec4b6;  /* 원하는 색상 */
  border-color: #2ec4b6;
  color: #ffffff;
  font-weight: bold;
  border-radius: 8px;
}

.page-link {
  color: #707070;
  border-radius: 8px;
  margin: 0 4px;
  transition: background-color 0.3s ease;
}

.page-link:hover {
  background-color: #22a99c;
  text-decoration: none;
}
.fa-angle-left, .fa-angle-right, .fa-angle-double-right, .fa-angle-double-left {
    font-size: 18px;  /* 아이콘 크기 */
    color: #707070;   /* 아이콘 색상 */
    cursor: pointer;  /* 클릭 가능하게 */
}
.page-link:hover {
    color: #ffffff;   /* 아이콘 색상 */
    cursor: pointer;  /* 클릭 가능하게 */
}
</style> 
<!-- pagination -->
<nav aria-label="Navigation">
	<ul class="pagination justify-content-center m-0">
		<li class="page-item">
			<a class="page-link" href="javascript:search_list(1);" style="border-radius:10px">
				<i class="fas fa-angle-double-left"></i>
			</a>
		</li>
		<li class="page-item">
			<a class="page-link" href="javascript:search_list(${pageMaker.prev ? pageMaker.startPage-1 : pageMaker.page});">
				<i class="fas fa-angle-left"></i>
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
				<i class="fas fa-angle-right"></i>
			</a>
		</li>
		<li class="page-item">
			<a class="page-link" href="javascript:search_list(${pageMaker.realEndPage });" style="border-radius:10px">
				<i class="fas fa-angle-double-right"></i>
			</a>
		</li>			
	</ul>
</nav>