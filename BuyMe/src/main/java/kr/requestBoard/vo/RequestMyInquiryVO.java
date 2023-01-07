package kr.requestBoard.vo;

public class RequestMyInquiryVO {
	private int myInquiry_num;
	private String myInqu_title;
	private String myInqu_content; // 문의 내용
	private String myInqu_reg_date;
	private String myInqu_modify_date;
	private String re_myInqu_is_ok; // 문의 답변 내용
	private String myInqu_filename;
	private String myInqu_ip;
	
	private int mem_num;
	
	// 추가적으로 필요한 변수
	private String id;
	private String photo;
	
	public int getMyInquiry_num() {
		return myInquiry_num;
	}
	public void setMyInquiry_num(int myInquiry_num) {
		this.myInquiry_num = myInquiry_num;
	}
	public String getMyInqu_title() {
		return myInqu_title;
	}
	public void setMyInqu_title(String myInqu_title) {
		this.myInqu_title = myInqu_title;
	}
	public String getMyInqu_content() {
		return myInqu_content;
	}
	public void setMyInqu_content(String myInqu_content) {
		this.myInqu_content = myInqu_content;
	}
	public String getMyInqu_reg_date() {
		return myInqu_reg_date;
	}
	public void setMyInqu_reg_date(String myInqu_reg_date) {
		this.myInqu_reg_date = myInqu_reg_date;
	}
	public String getMyInqu_modify_date() {
		return myInqu_modify_date;
	}
	public void setMyInqu_modify_date(String myInqu_modify_date) {
		this.myInqu_modify_date = myInqu_modify_date;
	}
	public String getRe_myInqu_is_ok() {
		return re_myInqu_is_ok;
	}
	public void setRe_myInqu_is_ok(String re_myInqu_is_ok) {
		this.re_myInqu_is_ok = re_myInqu_is_ok;
	}
	public String getMyInqu_filename() {
		return myInqu_filename;
	}
	public void setMyInqu_filename(String myInqu_filename) {
		this.myInqu_filename = myInqu_filename;
	}
	public String getMyInqu_ip() {
		return myInqu_ip;
	}
	public void setMyInqu_ip(String myInqu_ip) {
		this.myInqu_ip = myInqu_ip;
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
}