package kr.fundBoard.vo;

import java.sql.Date;

public class FundCommentVO {
	private int comment_num;
	private String comm_content;
	private String comm_reg_date;
	
	private int mem_num;
	
	private int fund_num;
	
	private String id;

	public int getComment_num() {
		return comment_num;
	}

	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}

	public String getComm_content() {
		return comm_content;
	}

	public void setComm_content(String comm_content) {
		this.comm_content = comm_content;
	}

	public String getComm_reg_date() {
		return comm_reg_date;
	}

	public void setComm_reg_date(String comm_reg_date) {
		this.comm_reg_date = comm_reg_date;
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
}
