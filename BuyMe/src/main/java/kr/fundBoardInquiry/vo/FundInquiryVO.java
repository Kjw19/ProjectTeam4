package kr.fundBoardInquiry.vo;

import java.sql.Date;

public class FundInquiryVO {
	
	private int inquiry_num;
	private String inqu_title;
	private String inqu_content;
	private Date inqu_reg_date;
	private String re_inqu_is_ok;
	
	private int mem_num;
	private int fund_num;
	private int fund_mem_num;

	private String id;
	
	
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
	public void setInqu_reg_date(Date date) {
		this.inqu_reg_date = date;
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
		return fund_num;
	}
	public void setFund_num(int fund_num) {
		this.fund_num = fund_num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getFund_mem_num() {
		return fund_mem_num;
	}
	public void setFund_mem_num(int fund_mem_num) {
		this.fund_mem_num = fund_mem_num;
	}

}
