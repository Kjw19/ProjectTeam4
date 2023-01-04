package kr.fundBoard.vo;

import java.sql.Date;
     
public class FundBoardVO {
	private int fund_num;
	private String fund_title;
	private String fund_content;
	private int fund_hit;
	private Date fund_reg_date;
	private Date fund_modify_date;
	private String fund_ip;
	private String fund_filename;
	
	private int category_num;
	private int mem_num;
	
	
	public int getFund_num() {
		return fund_num;
	}
	public void setFund_num(int fund_num) {
		this.fund_num = fund_num;
	}
	public String getFund_title() {
		return fund_title;
	}
	public void setFund_title(String fund_title) {
		this.fund_title = fund_title;
	}
	public String getFund_content() {
		return fund_content;
	}
	public void setFund_content(String fund_content) {
		this.fund_content = fund_content;
	}
	public int getFund_hit() {
		return fund_hit;
	}
	public void setFund_hit(int fund_hit) {
		this.fund_hit = fund_hit;
	}
	public Date getFund_reg_date() {
		return fund_reg_date;
	}
	public void setFund_reg_date(Date fund_reg_date) {
		this.fund_reg_date = fund_reg_date;
	}
	public Date getFund_modify_date() {
		return fund_modify_date;
	}
	public void setFund_modify_date(Date fund_modify_date) {
		this.fund_modify_date = fund_modify_date;
	}
	public String getFund_ip() {
		return fund_ip;
	}
	public void setFund_ip(String fund_ip) {
		this.fund_ip = fund_ip;
	}
	public String getFund_filename() {
		return fund_filename;
	}
	public void setFund_filename(String fund_filename) {
		this.fund_filename = fund_filename;
	}
	public int getCategory_num() {
		return category_num;
	}
	public void setCategory_num(int category_num) {
		this.category_num = category_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
}
