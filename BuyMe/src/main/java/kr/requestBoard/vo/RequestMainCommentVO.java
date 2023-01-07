package kr.requestBoard.vo;

// 테이블 request_mainComment
// 문의게시판 내의 댓글·응원 게시판 자바빈
public class RequestMainCommentVO {
	private int mainComment_num;
	private String mainComm_content;
	private String mainComm_reg_date;
	private String mainComm_modify_date;
	private String mainComm_ip;
	
	private int mem_num;
	
	// 추가적으로 필요한 변수
	private String id;

	public int getMainComment_num() {
		return mainComment_num;
	}
	public void setMainComment_num(int mainComment_num) {
		this.mainComment_num = mainComment_num;
	}
	public String getMainComm_content() {
		return mainComm_content;
	}
	public void setMainComm_content(String mainComm_content) {
		this.mainComm_content = mainComm_content;
	}
	public String getMainComm_reg_date() {
		return mainComm_reg_date;
	}
	public void setMainComm_reg_date(String mainComm_reg_date) {
		this.mainComm_reg_date = mainComm_reg_date;
	}
	public String getMainComm_modify_date() {
		return mainComm_modify_date;
	}
	public void setMainComm_modify_date(String mainComm_modify_date) {
		this.mainComm_modify_date = mainComm_modify_date;
	}
	
	public String getMainComm_ip() {
		return mainComm_ip;
	}
	public void setMainComm_ip(String mainComm_ip) {
		this.mainComm_ip = mainComm_ip;
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