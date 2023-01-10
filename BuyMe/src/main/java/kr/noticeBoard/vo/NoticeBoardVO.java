package kr.noticeBoard.vo;

import java.sql.Date;

public class NoticeBoardVO {
	private int noti_num;
	private String noti_title;
	private String noti_content;
	private int noti_hit;
	private Date noti_reg_date;
	private Date noti_modify_date;
	private String noti_filename;
	private String noti_ip;
	private int mem_num;
	
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNoti_num() {
		return noti_num;
	}
	public void setNoti_num(int noti_num) {
		this.noti_num = noti_num;
	}
	public String getNoti_title() {
		return noti_title;
	}
	public void setNoti_title(String noti_title) {
		this.noti_title = noti_title;
	}
	public String getNoti_content() {
		return noti_content;
	}
	public void setNoti_content(String noti_content) {
		this.noti_content = noti_content;
	}
	public int getNoti_hit() {
		return noti_hit;
	}
	public void setNoti_hit(int noti_hit) {
		this.noti_hit = noti_hit;
	}
	public Date getNoti_reg_date() {
		return noti_reg_date;
	}
	public void setNoti_reg_date(Date noti_reg_date) {
		this.noti_reg_date = noti_reg_date;
	}
	public Date getNoti_modify_date() {
		return noti_modify_date;
	}
	public void setNoti_modify_date(Date noti_modify_date) {
		this.noti_modify_date = noti_modify_date;
	}
	public String getNoti_filename() {
		return noti_filename;
	}
	public void setNoti_filename(String noti_filename) {
		this.noti_filename = noti_filename;
	}
	public String getNoti_ip() {
		return noti_ip;
	}
	public void setNoti_ip(String noti_ip) {
		this.noti_ip = noti_ip;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
}
