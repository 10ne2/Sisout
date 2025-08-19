package com.camp_us.dto;

public class MailFileVO {

	private int mafile_no;
	private String mafile_vol;
	private String mafile_name;
	private String mafile_type;
	private int mail_id;
	private String mafile_uploadpath;
	
	public int getMafile_no() {
		return mafile_no;
	}
	public void setMafile_no(int mafile_no) {
		this.mafile_no = mafile_no;
	}
	public String getMafile_vol() {
		return mafile_vol;
	}
	public void setMafile_vol(String mafile_vol) {
		this.mafile_vol = mafile_vol;
	}
	public String getMafile_name() {
		return mafile_name;
	}
	public void setMafile_name(String mafile_name) {
		this.mafile_name = mafile_name;
	}
	public String getMafile_type() {
		return mafile_type;
	}
	public void setMafile_type(String mafile_type) {
		this.mafile_type = mafile_type;
	}
	public int getMail_id() {
		return mail_id;
	}
	public void setMail_id(int mail_id) {
		this.mail_id = mail_id;
	}
	public String getMafile_uploadpath() {
		return mafile_uploadpath;
	}
	public void setMafile_uploadpath(String mafile_uploadpath) {
		this.mafile_uploadpath = mafile_uploadpath;
	}
	@Override
	public String toString() {
		return this.mafile_name;
	}
	
	
}
