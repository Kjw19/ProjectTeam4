package kr.requestBoard.vo;

// 테이블 request_comment
// 시퀀스 req_comm_seq
public class RequestCommentVO {
	private int comment_num;
	private String comm_content;
	private String comm_ip;
	private String comm_reg_date;
	
	private int mem_num;
	private int req_num;
	
	private String id;
	private String photo;

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
	public String getComm_ip() {
		return comm_ip;
	}
	public void setComm_ip(String comm_ip) {
		this.comm_ip = comm_ip;
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
	public int getReq_num() {
		return req_num;
	}
	public void setReq_num(int req_num) {
		this.req_num = req_num;
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
}