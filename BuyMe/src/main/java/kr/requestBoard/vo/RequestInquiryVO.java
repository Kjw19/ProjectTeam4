package kr.requestBoard.vo;

public class RequestInquiryVO {
	private int inquiry_num;
	private String inqu_title;
	private String inqu_content; // 문의 내용
	private String inqu_reg_date;
	private String inqu_modify_date;
	private String re_inqu_is_ok; // 문의 답변 내용
	private String inqu_filename;
	private String inqu_ip;
	
	private int mem_num;
	private int req_num;
	
	// 추가적으로 필요한 변수
	private String id;
	private String photo;
	
	public int getInquiry_num() {
		return inquiry_num;
	}
	public void setInquiry_num(int inquiry_num) {
		this.inquiry_num = inquiry_num;
	}
	public String getInqu_title() {
		return inqu_title;
	}
	public void setInqu_title(String inqu_title) {
		this.inqu_title = inqu_title;
	}
	public String getInqu_content() {
		return inqu_content;
	}
	public void setInqu_content(String inqu_content) {
		this.inqu_content = inqu_content;
	}
	public String getInqu_reg_date() {
		return inqu_reg_date;
	}
	public void setInqu_reg_date(String inqu_reg_date) {
		this.inqu_reg_date = inqu_reg_date;
	}
	public String getInqu_modify_date() {
		return inqu_modify_date;
	}
	public void setInqu_modify_date(String inqu_modify_date) {
		this.inqu_modify_date = inqu_modify_date;
	}
	public String getRe_inqu_is_ok() {
		return re_inqu_is_ok;
	}
	public void setRe_inqu_is_ok(String re_inqu_is_ok) {
		this.re_inqu_is_ok = re_inqu_is_ok;
	}
	public String getInqu_filename() {
		return inqu_filename;
	}
	public void setInqu_filename(String inqu_filename) {
		this.inqu_filename = inqu_filename;
	}
	public String getInqu_ip() {
		return inqu_ip;
	}
	public void setInqu_ip(String inqu_ip) {
		this.inqu_ip = inqu_ip;
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