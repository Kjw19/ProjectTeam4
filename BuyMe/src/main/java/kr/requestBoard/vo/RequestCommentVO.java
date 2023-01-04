package kr.requestBoard.vo;

import java.sql.Date;

public class RequestCommentVO {
	private int comment_num;
	private String comm_content;
	private Date comm_reg_date;
	
	private int mem_num;
	private int request_num;
	
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
	public Date getComm_reg_date() {
		return comm_reg_date;
	}
	public void setComm_reg_date(Date comm_reg_date) {
		this.comm_reg_date = comm_reg_date;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getRequest_num() {
		return request_num;
	}
	public void setRequest_num(int request_num) {
		this.request_num = request_num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}