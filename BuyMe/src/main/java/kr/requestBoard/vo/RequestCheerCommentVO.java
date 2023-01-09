package kr.requestBoard.vo;

// 테이블 request_cheerComment
// 시퀀스 req_cheerComm_seq
// request_cheer의 cheer_num 참조
public class RequestCheerCommentVO {
	private int cheerComment_num;
	private String cheerComm_title;
	private String cheerComm_content;
	private String cheerComm_ip;
	private String cheerComm_reg_date;
	private String cheerComm_filename;
	
	private int mem_num;
	private int cheer_num;
	// 추가 변수
	private String id;
	private String photo;
	
	public int getCheerComment_num() {
		return cheerComment_num;
	}
	public void setCheerComment_num(int cheerComment_num) {
		this.cheerComment_num = cheerComment_num;
	}
	public String getCheerComm_title() {
		return cheerComm_title;
	}
	public void setCheerComm_title(String cheerComm_title) {
		this.cheerComm_title = cheerComm_title;
	}
	public String getCheerComm_content() {
		return cheerComm_content;
	}
	public void setCheerComm_content(String cheerComm_content) {
		this.cheerComm_content = cheerComm_content;
	}
	public String getCheerComm_ip() {
		return cheerComm_ip;
	}
	public void setCheerComm_ip(String cheerComm_ip) {
		this.cheerComm_ip = cheerComm_ip;
	}
	public String getCheerComm_reg_date() {
		return cheerComm_reg_date;
	}
	public void setCheerComm_reg_date(String cheerComm_reg_date) {
		this.cheerComm_reg_date = cheerComm_reg_date;
	}
	public String getCheerComm_filename() {
		return cheerComm_filename;
	}
	public void setCheerComm_filename(String cheerComm_filename) {
		this.cheerComm_filename = cheerComm_filename;
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getCheer_num() {
		return cheer_num;
	}
	public void setCheer_num(int cheer_num) {
		this.cheer_num = cheer_num;
	}
	
}