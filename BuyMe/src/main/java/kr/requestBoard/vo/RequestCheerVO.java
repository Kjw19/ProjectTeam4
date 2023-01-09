package kr.requestBoard.vo;

// 테이블 request_cheer
// 시퀀스 req_cheer_seq
// 문의게시판 내의 응원 게시판 자바빈
public class RequestCheerVO {
	private int cheer_num;
	private String cheer_content;
	private String cheer_filename;
	private String cheer_reg_date;
	private String cheer_modify_date;
	private String cheer_ip;
	
	private int mem_num;
	
	// 추가적으로 필요한 변수
	private String id;

	public int getCheer_num() {
		return cheer_num;
	}
	public void setCheer_num(int cheer_num) {
		this.cheer_num = cheer_num;
	}
	public String getCheer_content() {
		return cheer_content;
	}
	public void setCheer_content(String cheer_content) {
		this.cheer_content = cheer_content;
	}
	public String getCheer_filename() {
		return cheer_filename;
	}
	public void setCheer_filename(String cheer_filename) {
		this.cheer_filename = cheer_filename;
	}
	public String getCheer_reg_date() {
		return cheer_reg_date;
	}
	public void setCheer_reg_date(String cheer_reg_date) {
		this.cheer_reg_date = cheer_reg_date;
	}
	public String getCheer_modify_date() {
		return cheer_modify_date;
	}
	public void setCheer_modify_date(String cheer_modify_date) {
		this.cheer_modify_date = cheer_modify_date;
	}
	public String getCheer_ip() {
		return cheer_ip;
	}
	public void setCheer_ip(String cheer_ip) {
		this.cheer_ip = cheer_ip;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}