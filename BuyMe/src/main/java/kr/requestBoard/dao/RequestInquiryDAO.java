package kr.requestBoard.dao;

public class RequestInquiryDAO {
	/*
	 	문의게시판 상세페이지 -- ui에서 삭제 
	 	문의게시판 메뉴는 총 두 개로 1. 댓글&응원 2. 문의게시판
	 	1. 댓글&응원
	 	2. 문의게시판(main) : 상단 - 자주 묻는 질문, 하단 - 메이커에게 1:1 문의하기
	 	3. 1:1 문의 목록 : 관리자, 해당 메이커, 해당 서포터에게만 보인다. 
	*/
	
	// 싱글턴 패턴
	private static RequestInquiryDAO instance = new RequestInquiryDAO();
	public static RequestInquiryDAO getInstance() {
		return instance;
	}
	private RequestInquiryDAO() {}
	
	
	// 2. 문의게시판(main)
	// 2-1. 관리자만 가능
	// 자주 묻는 질문 등록
	// 자주 묻는 질문 개수
	// 자주 묻는 질문 목록
	// 자주 묻는 질문 상세
	// 자주 묻는 질문 수정
	// 자주 묻는 질문 삭제

	// 2-2. 일반 회원만 가능
	// 1:1 문의 등록
	// 1:1 문의 총 레코드 수
	// 1:1 문의 목록
	// 1:1 문의 상세
	// 1:1 문의 수정
	// 1:1 문의 삭제
	
	// 3. 1:1 문의 목록 (2-2의 1:1 문의 관련 활용)
	// 1:1 문의 총 레코드 수 - 일반회원(문의한 서포터, 문의받은 메이커), 관리자
	// 1:1 문의 목록 - 일반회원(문의한 서포터, 문의받은 메이커), 관리자
	// 1:1 문의 상세 - 일반회원(문의한 서포터, 문의받은 메이커), 관리자
	// 1:1 문의 수정 - 관리자
	// 1:1 문의 삭제 - 관리자
}