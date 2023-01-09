package kr.requestBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.requestBoard.vo.RequestCheerCommentVO;
import kr.util.DBUtil;

public class RequestCheerCommentDAO {
	/* 댓글 등록·개수·목록·상세·수정·삭제 */
	// 싱글턴 패턴
	private static RequestCheerCommentDAO instance = new RequestCheerCommentDAO();
	public static RequestCheerCommentDAO getInstance() {
		return instance;
	}
	private RequestCheerCommentDAO() {}
	
	// 문의 게시판 내 이야기 한마당에 대한 댓글
	// 댓글 작성
		public void insertCheerCommentBoard(RequestCheerCommentVO boardCheerComment) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				// 커넥션풀로부터 커넥션을 할당 받음
				conn = DBUtil.getConnection();
				// SQL문 작성
				sql = "INSERT INTO request_cheerComment (cheerComment_num,cheerComm_title,cheerComm_content,cheerComm_filename,cheerComm_ip,mem_num,cheer_num) "
						+ "VALUES (req_cheerComm_seq.nextval,?,?,?,?,?,?)";
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				pstmt.setString(1, boardCheerComment.getCheerComm_title());
				pstmt.setString(2, boardCheerComment.getCheerComm_content());
				pstmt.setString(3, boardCheerComment.getCheerComm_filename());
				pstmt.setString(4, boardCheerComment.getCheerComm_ip());
				pstmt.setInt(5, boardCheerComment.getMem_num());
				pstmt.setInt(6, boardCheerComment.getCheer_num());
				// SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
}