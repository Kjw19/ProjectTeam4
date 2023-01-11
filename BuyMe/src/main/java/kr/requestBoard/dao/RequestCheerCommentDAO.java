package kr.requestBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.requestBoard.vo.RequestCheerCommentVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

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
			sql = "INSERT INTO request_cheerComment (cheerComment_num,cheerComm_title,cheerComm_content,cheerComm_filename,mem_num,cheer_num) "
					+ "VALUES (req_cheerComm_seq.nextval,?,?,?,?,?)";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, boardCheerComment.getCheerComm_title());
			pstmt.setString(2, boardCheerComment.getCheerComm_content());
			pstmt.setString(3, boardCheerComment.getCheerComm_filename());
			pstmt.setInt(4, boardCheerComment.getMem_num());
			pstmt.setInt(5, boardCheerComment.getCheer_num());
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 댓글 개수
	public int getCheerCountBoard(int cheer_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT COUNT(*) FROM request_cheerComment c "
					+ "JOIN member m ON c.mem_num=m.mem_num WHERE c.cheer_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, cheer_num);
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
	// 댓글 목록 : 부모글 식별을 위해 req_num(부모 글번호)를 넘긴다. / mem_num = 작성자
		public List<RequestCheerCommentVO> getListCheerCommentBoard(int start, int end, int cheer_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<RequestCheerCommentVO> list = null;
			String sql = null;
			
			try {
				// 커넥션풀로부터 커넥션을 할당 받음
				conn = DBUtil.getConnection();
				// SQL문 작성 : 댓글(c)와 id정보(m)을 읽어 온다. → 조인
				sql = "SELECT * FROM (SELECT a.*, rownum rnum "
						+ "FROM (SELECT * FROM request_cheerComment c JOIN member m "
						+ "USING(mem_num) WHERE c.cheer_num=? "
						+ "ORDER BY c.cheerComment_num DESC)a) WHERE rnum>=? AND rnum<=?";
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				pstmt.setInt(1, cheer_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				// SQL문을 실행하여 결과행을 ResultSet에 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<RequestCheerCommentVO>();
				while(rs.next()) {
					RequestCheerCommentVO cheerComment = new RequestCheerCommentVO();
					cheerComment.setCheerComment_num(rs.getInt("cheerComment_num"));
					cheerComment.setCheerComm_title(StringUtil.useBrNoHtml(rs.getString("cheerComm_title")));
					cheerComment.setCheerComm_content(StringUtil.useBrNoHtml(rs.getString("cheerComm_content")));
					cheerComment.setCheerComm_reg_date(rs.getString("cheerComm_reg_date"));
					cheerComment.setMem_num(rs.getInt("mem_num"));
					cheerComment.setCheer_num(rs.getInt("cheer_num"));
					cheerComment.setId(rs.getString("id"));
					cheerComment.setCheerComm_filename(rs.getString("cheerComm_filename"));
					//cheerComment.setPhoto(rs.getString("photo"));
					
					
					list.add(cheerComment);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		// 댓글 상세 : 페이지는 없고 한 건의 레코드가 존재하는 지 여부 체크 용도 = 작성자 회원번호를 보여주기 위해서 작성
		public RequestCheerCommentVO getCheerCommentDetailBoard (int cheerComment_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			RequestCheerCommentVO cheerComment = null;
			String sql = null;
			
			try {
				// 커넥션풀로부터 커넥션을 할당 받음
				conn = DBUtil.getConnection();
				// SQL문 작성
				sql = "SELECT * FROM request_cheerComment WHERE cheerComment_num=?";
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				pstmt.setInt(1, cheerComment_num);
				// SQL문을 실행하여 결과행을 ResultSet에 담음
				rs = pstmt.executeQuery();
				if(rs.next()) {
					cheerComment = new RequestCheerCommentVO();
					cheerComment.setCheerComment_num(rs.getInt("cheerComment_num"));
					cheerComment.setMem_num(rs.getInt("mem_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return cheerComment;
		}
		// 댓글 수정
		public void updateCheerCommentBoard(RequestCheerCommentVO cheerComment) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				// 커넥션풀로부터 커넥션을 할당 받음
				conn = DBUtil.getConnection();
				// SQL문 작성
				sql = "UPDATE request_cheerComment SET cheerComm_title=?,cheerComm_content=?,cheerComm_filename "
						+ "WHERE cheerComment_num=?";
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				pstmt.setString(1, cheerComment.getCheerComm_title());
				pstmt.setString(2, cheerComment.getCheerComm_content());
				pstmt.setString(3, cheerComment.getCheerComm_filename());
				pstmt.setInt(4, cheerComment.getCheerComment_num());
				// SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		// 댓글 삭제
		public void deleteCheerCommentBoard(int cheerComment_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				// 커넥션풀로부터 커넥션을 할당 받음
				conn = DBUtil.getConnection();
				// SQL문 작성 : 댓글 번호를 하나 전달하여 레코드를 삭제
				sql = "DELETE FROM request_cheerComment WHERE cheerComment_num=?";
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				pstmt.setInt(1, cheerComment_num);
				// SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}

}