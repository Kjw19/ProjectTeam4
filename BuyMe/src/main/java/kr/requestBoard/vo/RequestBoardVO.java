package kr.requestBoard.vo;

import java.util.Date;
   
public class RequestBoardVO {
	private int req_num;
	
	 private String req_title;
	 
	 private String req_content;
	 private int req_hit;
	 private Date req_reg_date;
	 private Date req_modify_date;
	 
	 private String req_ip;
	 private String req_filename; 
	 private int mem_num;
	 private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getReq_num() {
		return req_num;
	}
	public void setReq_num(int req_num) {
		this.req_num = req_num;
	}
	public String getReq_title() {
		return req_title;
	}
	public void setReq_title(String req_title) {
		this.req_title = req_title;
	}
	public String getReq_content() {
		return req_content;
	}
	public void setReq_content(String req_content) {
		this.req_content = req_content;
	}
	public int getReq_hit() {
		return req_hit;
	}
	public void setReq_hit(int req_hit) {
		this.req_hit = req_hit;
	}
	public Date getReq_reg_date() {
		return req_reg_date;
	}
	public void setReq_reg_date(Date req_reg_date) {
		this.req_reg_date = req_reg_date;
	}
	public Date getReq_modify_date() {
		return req_modify_date;
	}
	public void setReq_modify_date(Date req_modify_date) {
		this.req_modify_date = req_modify_date;
	}
	public String getReq_ip() {
		return req_ip;
	}
	public void setReq_ip(String req_ip) {
		this.req_ip = req_ip;
	}
	public String getReq_filename() {
		return req_filename;
	}
	public void setReq_filename(String req_filename) {
		this.req_filename = req_filename;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	@Override
	public String toString() {
		return "RequestBoardVO [req_num=" + req_num + ", req_title=" + req_title + ", req_content=" + req_content
				+ ", req_hit=" + req_hit + ", req_reg_date=" + req_reg_date + ", req_modify_date=" + req_modify_date
				+ ", req_ip=" + req_ip + ", req_filename=" + req_filename + ", mem_num=" + mem_num + ", id=" + id + "]";
	}

}
