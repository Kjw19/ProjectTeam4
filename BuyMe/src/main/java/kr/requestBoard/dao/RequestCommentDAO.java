package kr.requestBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import kr.requestBoard.vo.RequestCommentVO;
import kr.util.DBUtil;

public class RequestCommentDAO {
	/*
	 	문의게시판 상세페이지 -- ui에서 삭제 
	 	문의게시판 메뉴는 총 두 개로 1. 댓글&응원 2. 문의게시판
	 	1. 댓글&응원
	 	2. 문의게시판(main) : 상단 - 자주 묻는 질문, 하단 - 메이커에게 1:1 문의하기 
	 	3. 1:1 문의 목록 : 관리자&해당 메이커에게만 보인다.
	*/
	
	// 싱글턴 패턴
	private static RequestCommentDAO instance = new RequestCommentDAO();
	public static RequestCommentDAO getInstance() {
		return instance;
	}
	private RequestCommentDAO() {}
	
	// 1. 댓글&응원 - 보기 편하게 댓글로 명시
	// 댓글 등록
	public void insertRequestComment(RequestCommentVO requsetComment) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			// SQL문 실행
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 댓글 개수
	public int getRequestCommentCount(int request_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			// SQL문을 실행하여 결과행을 ResultSet에 담음
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	// 댓글 목록 : 부모글 식별을 위해 request_num(부모 글번호)를 넘긴다. / mem_num = 작성자
	public List<RequestCommentVO> getListRequestComment(int start, int end, int request_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RequestCommentVO> list = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			// SQL문을 실행하여 결과행을 ResultSet에 담음
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
			sql = "";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			// SQL문을 실행하여 결과행을 ResultSet에 담음
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
			sql = "";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			// SQL문 실행
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
			// SQL문 작성
			sql = "";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			// SQL문 실행
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}