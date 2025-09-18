package com.camp_us.command;

public class PageMakerMJ {

    private String searchType = "";
    private String keyword = "";
    private String category = ""; // 예: 공지, 자유, 토론 등

    private int page = 1; // 현재 페이지 번호
    private int perPageNum = 10; // 한 페이지당 게시물 수
    private int totalCount; // 전체 게시물 수
    private int displayPageNum = 10; // 페이지네이션에 보여줄 페이지 번호 개수

    private int startPage = 1; // 시작 페이지 번호
    private int endPage = 1; // 끝 페이지 번호
    private int realEndPage; // 실제 끝 페이지 번호
    private boolean prev; // 이전 버튼 유무
    private boolean next; // 다음 버튼 유무
    private String lecId;

    // --- 기본 getter/setter ---

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcData(); // 전체 개수 설정 시 페이징 계산
    }

    public int getDisplayPageNum() {
        return displayPageNum;
    }

    public void setDisplayPageNum(int displayPageNum) {
        this.displayPageNum = displayPageNum;
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

    // --- 페이징 계산용 ---

    // Oracle 페이징 시 필요한 시작 행 번호
    public int getStartRow() {
        return (this.page - 1) * this.perPageNum + 1;
    }

    // Oracle 페이징 시 필요한 끝 행 번호
    public int getEndRow() {
        return getStartRow() + perPageNum - 1;
    }
    

    public String getLecId() {
		return lecId;
	}

	public void setLecId(String lecId) {
		this.lecId = lecId;
	}

	// 페이지 블럭 계산 로직
    private void calcData() {
        endPage = (int) Math.ceil((double) page / displayPageNum) * displayPageNum;
        startPage = (endPage - displayPageNum) + 1;

        realEndPage = (int) Math.ceil((double) totalCount / perPageNum);

        if (startPage < 1) {
            startPage = 1;
        }

        if (endPage > realEndPage) {
            endPage = realEndPage;
        }

        prev = startPage > 1;
        next = endPage < realEndPage;
    }
}
