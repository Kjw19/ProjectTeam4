package kr.requestBoard.dao;

public class RequestCheerCommentDAO {
	/* 댓글 등록·개수·목록·상세·수정·삭제 */
	// 싱글턴 패턴
	private static RequestCheerCommentDAO instance = new RequestCheerCommentDAO();
	public static RequestCheerCommentDAO getInstance() {
		return instance;
	}
	private RequestCheerCommentDAO() {}
	
}