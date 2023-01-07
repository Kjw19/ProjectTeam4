package kr.requestBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.requestBoard.vo.RequestMainCommentVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;

public class RequestMainCommentDAO {
	// 싱글턴 패턴
	private static RequestMainCommentDAO instance = new RequestMainCommentDAO();
	public static RequestMainCommentDAO getInstance() {
		return instance;
	}
	private RequestMainCommentDAO() {}
	
	// 1. 댓글&응원 게시판
	// 댓글&응원 등록
	public void insertMainCommentBoard(RequestMainCommentVO mainComment) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO request_mainComment (mainComment_num,mainComm_content,mainComm_ip,mem_num) "
					+ "VALUES (req_mainComm_seq.nextval,?,?,?)";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, mainComment.getMainComm_content());
			pstmt.setString(2, mainComment.getMainComm_ip());
			pstmt.setInt(3, mainComment.getMem_num());
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 댓글&응원 개수
	public int getMainCommentListCount() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT COUNT(*) FROM request_mainComment";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// SQL문을 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1); // 컬럼이 하나이고 기호가 들어가 있으니 컬럼인덱스를 사용하면 더 심플
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	// 댓글&응원 목록
	public List<RequestMainCommentVO> getMainCommentBoardList(int startRow,int endRow) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RequestMainCommentVO> list = null;
		String sql = null;
		
		// 첫 레코드는 자바빈에 담고, 두,세,… 번째 레코드 역시 자바빈에 담는다.
		// 자바빈은 여러 개를 담을 수 없다. → ArrayList에 보낸다. → list타입으로 반환
		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			// SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM request_mainComment c JOIN member m "
					+ "USING(mem_num) ORDER BY c.mainComment_num DESC)a) "
					+ "WHERE rnum>=? AND rnum<=?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			// SQL문을 테이블에 반영하고 결과행들을 ResultSet에 담는다.
			rs = pstmt.executeQuery();
			list = new ArrayList<RequestMainCommentVO>();
			while(rs.next()) { // 여러 개의 레코드를 자바빈에 담는다. jsp에선 필요한 데이터만 담으면 된다.
				RequestMainCommentVO mainComment = new RequestMainCommentVO(); // 자바빈 생성
				mainComment.setMainComment_num(rs.getInt("mainComment_num"));
				mainComment.setMainComm_content(rs.getString("mainComm_content"));
				mainComment.setMainComm_reg_date(DurationFromNow.getTimeDiffLabel(rs.getString("mainComm_reg_date")));
				if(rs.getString("mainComm_modify_date")!=null) {
					mainComment.setMainComm_modify_date(DurationFromNow.getTimeDiffLabel(rs.getString("mainComm_modify_date")));
				}
				mainComment.setMem_num(rs.getInt("mem_num"));
				mainComment.setId(rs.getString("id"));
				
				// 자바빈(VO)를 ArrayList에 저장
				list.add(mainComment);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	// 댓글&응원 상세
	public RequestMainCommentVO getMainCommentDetailBoard(int mainComment_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RequestMainCommentVO mainComment = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받는다.
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT * FROM request_mainComment c JOIN member m USING(mem_num) "
					+ "JOIN member_detail d USING(mem_num) WHERE c.mainComment_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, mainComment_num);
			// SQL문 실행
			pstmt.executeUpdate();
			// SQL문을 실행해서 결과행을 ResultSet에 담는다.
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mainComment = new RequestMainCommentVO();
				mainComment.setMainComment_num(rs.getInt("mainComment_num"));
				mainComment.setMainComm_content(rs.getString("mainComm_content"));
				mainComment.setMainComm_reg_date(rs.getString("mainComm_reg_date"));
				mainComment.setMainComm_modify_date(rs.getString("mainComm_modify_date"));
				mainComment.setMem_num(rs.getInt("mem_num"));
				mainComment.setId(rs.getString("id"));
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return mainComment;
	}
	
	// 댓글&응원 삭제
	public void MainCommentDeleteBoard(int mainComment_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받는다.
			conn = DBUtil.getConnection();
			// SQL문 작성 : 댓글 번호를 하나 전달하여 레코드를 삭제
			sql = "DELETE FROM request_mainComment WHERE mainComment_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, mainComment_num);
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}