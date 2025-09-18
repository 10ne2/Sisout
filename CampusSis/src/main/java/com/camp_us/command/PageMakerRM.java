package com.camp_us.command;




public class PageMakerRM {
	private String searchType = "";
	private String keyword = "";
	private String rm_stdate;
	private String rm_endate;
	private String project_stdate;
	private String project_endate;
	private String project_name;
	private String rm_name;
	private String rm_category;
	private String project_id;
	private int page = 1; // 페이지 번호
	private int perPageNum = 7; // 리스트 개수
	private int totalCount; // 전체 행의 개수
	private int displayPageNum = 10; // 한 페이지에 보여줄 페이지번호 개수
	private String eval_status;   // 완료 체크박스
	private int startPage = 1; // 시작 페이지 번호
	private int endPage = 1; // 마지막 페이지 번호
	private int realEndPage; // 끝 페이지 번호
	private boolean prev; // 이전페이지 버튼 유무
	private boolean next; // 다음페이지 버튼 유무

	


	public String getEval_status() {
		return eval_status;
	}
	public void setEval_status(String eval_status) {
		this.eval_status = eval_status;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getRm_category() {
		return rm_category;
	}
	public void setRm_category(String rm_category) {
		this.rm_category = rm_category;
	}
	public String getRm_name() {
		return rm_name;
	}
	public void setRm_name(String rm_name) {
		this.rm_name = rm_name;
	}
	public String getRm_stdate() {
		return rm_stdate;
	}
	public void setRm_stdate(String rm_stdate) {
		this.rm_stdate = rm_stdate;
	}
	public String getRm_endate() {
		return rm_endate;
	}
	public void setRm_endate(String rm_endate) {
		this.rm_endate = rm_endate;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getProject_stdate() {
		return project_stdate;
	}
	public void setProject_stdate(String project_stdate) {
		this.project_stdate = project_stdate;
	}
	public String getProject_endate() {
		return project_endate;
	}
	public void setProject_endate(String project_endate) {
		this.project_endate = project_endate;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPerPageNum() {
		return perPageNum;
	}
	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public int getStartRow() {
		return (this.page - 1) * this.perPageNum+1;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		calcData();
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getRealEndPage() {
		return realEndPage;
	}
	public void setRealEndPage(int realEndPage) {
		this.realEndPage = realEndPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	
	// starPage,endPage, prev, next 설정. by totalCount
	private void calcData() {
		endPage = (int) (Math.ceil(page / (double) displayPageNum) * displayPageNum);
		startPage = (endPage - displayPageNum) + 1;

		realEndPage = (int) (Math.ceil(totalCount / (double) perPageNum));

		if (startPage < 0) {
			startPage = 1;
		}
		if (endPage > realEndPage) {
			endPage = realEndPage;
		}

		prev = startPage == 1 ? false : true;
		next = endPage < realEndPage ? true : false;
	}

}





