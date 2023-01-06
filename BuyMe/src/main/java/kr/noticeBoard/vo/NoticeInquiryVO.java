package kr.noticeBoard.vo;

import java.sql.Date;

public class NoticeInquiryVO {
	private int inquiry_num;
	private String inqu_title;
	private String inqu_content;
	private Date inqu_reg_date;
	private String re_inqu_is_ok;
	private int mem_num;
	public int getNotice_num() {
		return notice_num;
	}
	public void setNotice_num(int notice_num) {
		this.notice_num = notice_num;
	}
	private int notice_num;
	public int getInquiry_num() {
		return inquiry_num;
	}
	public void setInquiry_num(int inquiry_num) {
		this.inquiry_num = inquiry_num;
	}
	public String getInqu_title() {
		return inqu_title;
	}
	public void setInqu_title(String inqu_title) {
		this.inqu_title = inqu_title;
	}
	public String getInqu_content() {
		return inqu_content;
	}
	public void setInqu_content(String inqu_content) {
		this.inqu_content = inqu_content;
	}
	public Date getInqu_reg_date() {
		return inqu_reg_date;
	}
	public void setInqu_reg_date(Date inqu_reg_date) {
		this.inqu_reg_date = inqu_reg_date;
	}
	public String getRe_inqu_is_ok() {
		return re_inqu_is_ok;
	}
	public void setRe_inqu_is_ok(String re_inqu_is_ok) {
		this.re_inqu_is_ok = re_inqu_is_ok;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getFund_num() {
		return notice_num;
	}
	public void setFund_num(int fund_num) {
		this.notice_num = notice_num;
	}
	
}

