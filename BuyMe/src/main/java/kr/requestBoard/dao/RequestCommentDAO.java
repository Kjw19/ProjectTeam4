package kr.requestBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.requestBoard.vo.RequestCommentVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class RequestCommentDAO {
	/* 댓글 등록·개수·목록·상세·수정·삭제 */
	
	// 싱글턴 패턴
	private static RequestCommentDAO instance = new RequestCommentDAO();
	public static RequestCommentDAO getInstance() {
		return instance;
	}
	private RequestCommentDAO() {}
	
	// 2. 문의 게시판에 댓글 작성
	// 댓글 등록
	public void insertRequestComment(RequestCommentVO requestComment) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO request_comment (comment_num,comm_content,comm_reg_date,mem_num,req_num) "
					+ "VALUES (req_comm_seq.nextval,?,SYSDATE,?,?)";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, requestComment.getComm_content());
			pstmt.setInt(2, requestComment.getMem_num());
			pstmt.setInt(3, requestComment.getReq_num());
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 댓글 개수
	public int getRequestCommentCount(int req_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT COUNT(*) FROM request_comment c "
					+ "JOIN member m ON c.mem_num=m.mem_num WHERE c.req_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, req_num);
			// SQL문을 실행하여 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	// 댓글 목록 : 부모글 식별을 위해 request_num(부모 글번호)를 넘긴다. / mem_num = 작성자
	public List<RequestCommentVO> getListRequestComment(int start, int end, int req_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RequestCommentVO> list = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성 : 댓글(c)와 id정보(m)을 읽어 온다. → 조인
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM request_comment c JOIN member m "
					+ "USING(mem_num) WHERE c.req_num=? "
					+ "ORDER BY c.comment_num DESC)a) WHERE rnum>=? AND rnum<=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, req_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			// SQL문을 실행하여 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<RequestCommentVO>();
			while(rs.next()) {
				RequestCommentVO comment = new RequestCommentVO();
				comment.setComment_num(rs.getInt("comment_num"));
				comment.setComm_content(StringUtil.useBrNoHtml(rs.getString("comm_content")));
				comment.setComm_reg_date(DurationFromNow.getTimeDiffLabel(rs.getString("comm_reg_date")));
				comment.setMem_num(rs.getInt("mem_num"));
				comment.setReq_num(rs.getInt("req_num"));
				
				list.add(comment);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	// 댓글 상세 : 페이지는 없고 한 건의 레코드가 존재하는 지 여부 체크 용도 = 작성자 회원번호를 보여주기 위해서 작성
	public RequestCommentVO getRequestComment(int comment_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RequestCommentVO comment = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT * FROM request_comment WHERE comment_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, comment_num);
			// SQL문을 실행하여 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				comment = new RequestCommentVO();
				comment.setComment_num(rs.getInt("comment_num"));
				comment.setMem_num(rs.getInt("mem_num")); // 댓글 작성자 정보
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return comment;
	}
	// 댓글 수정
	public void updateRequestComment(RequestCommentVO comment) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "UPDATE request_comment SET comm_content=? WHERE comment_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, comment.getComm_content());
			pstmt.setInt(2, comment.getComment_num());
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 댓글 삭제
	public void deleteRequestComment(int comment_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성 : 댓글 번호를 하나 전달하여 레코드를 삭제
			sql = "DELETE FROM request_comment WHERE comment_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, comment_num);
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}