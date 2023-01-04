package kr.requestBoard.dao;

public class RequestCommentDAO {
	// 싱글턴 패턴
	private static RequestCommentDAO instance = new RequestCommentDAO();
	public static RequestCommentDAO getInstance() {
		return instance;
	}
	private RequestCommentDAO() {}
	
	/*
	 	문의게시판 상세페이지 -- ui에서 삭제 
	 	문의게시판 메뉴는 총 두 개로 1. 댓글&응원 2. 문의게시판
	 	1. 댓글&응원
	 	2. 문의게시판(main) : 상단 - 자주 묻는 질문, 하단 - 메이커에게 1:1 문의하기 
	 	3. 1:1 문의 목록 : 관리자&해당 메이커에게만 보인다.
	 */
	
	// 1. 댓글&응원 - 보기 편하게 댓글로 명시
	// 댓글 등록
	// 댓글 개수
	// 댓글 목록 : 부모글 식별을 위해 request_num(부모 글번호)를 넘긴다. / mem_num = 작성자
	// 댓글 상세 : 페이지는 없고 한 건의 레코드가 존재하는 지 여부 체크 용도 = 작성자 회원번호를 보여주기 위해서 작성
	// 댓글 수정
	// 댓글 삭제
}