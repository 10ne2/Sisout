package com.camp_us.dto;

public class AttendanceVO {
    private String aNo;
    private String aDetail;
    private String lecsId;
    private String aCat;
    private String modPending;
    private String progress;

    public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getaNo() { return aNo; }
    public void setaNo(String aNo) { this.aNo = aNo; }

    public String getaDetail() { return aDetail; }
    public void setaDetail(String aDetail) { this.aDetail = aDetail; }

    public String getLecsId() { return lecsId; }
    public void setLecsId(String lecsId) { this.lecsId = lecsId; }

    public String getaCat() { return aCat; }
    public void setaCat(String aCat) { this.aCat = aCat; }

    public String getModPending() { return modPending; }
    public void setModPending(String modPending) { this.modPending = modPending; }
}
