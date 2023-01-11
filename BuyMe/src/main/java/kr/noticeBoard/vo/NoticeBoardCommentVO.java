package kr.noticeBoard.vo;

public class NoticeBoardCommentVO {

	private int commnet_num;
	private int mem_num;
	private int noti_num;
	private String comm_content;
	private String comm_reg_date;
	private String id;

	public int getCommnet_num() {
		return commnet_num;
	}

	public void setCommnet_num(int commnet_num) {
		this.commnet_num = commnet_num;
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}

	public int getNoti_num() {
		return noti_num;
	}

	public void setNoti_num(int noti_num) {
		this.noti_num = noti_num;
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

	public void setComm_reg_date(String string) {
		this.comm_reg_date = string;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
