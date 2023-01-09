package kr.members.vo;

import java.sql.Date;

public class MemberVO {
	private int mem_num;
	private String id;
	private int auth;
	private String name;
	private String passwd;
	private String email;
	private String phone;
	private String zipcode;
	private String address1;
	private String address2;
	private String photo;
	private Date reg_date;
	private Date modify_date;
	
	//비밀번호 일치 여부 체크
	public boolean isCheckedPassword(String userPasswd) {
		//회원 등급(auth) : /*회원등급:0 비회원,1회원,2탈퇴,5관리자*/
		if(auth == 1 && passwd.equals(userPasswd)) {
			//비밀번호 일치
			return true;
		}
		if(auth == 5 && passwd.equals(userPasswd)) {
			//비밀번호 일치
			return true;
		}
		//비밀번호 불일치
		return false;
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
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
}
