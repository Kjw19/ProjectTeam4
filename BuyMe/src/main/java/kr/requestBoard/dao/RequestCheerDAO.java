package kr.requestBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.requestBoard.vo.RequestCheerVO;
import kr.util.DBUtil;

public class RequestCheerDAO {
	// 싱글턴 패턴
	private static RequestCheerDAO instance = new RequestCheerDAO();
	public static RequestCheerDAO getInstance() {
		return instance;
	}
	private RequestCheerDAO() {} 

	// 응원 게시판 - 메인글 작성
	public void insertCheerBoard(RequestCheerVO cheerBoard) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당 받는다.
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO request_cheer (cheer_num,cheer_content,cheer_filename,mem_num)"
					+ "VALUES (req_cheer_seq.nextval,?,?,?)";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, cheerBoard.getCheer_content());
			pstmt.setString(2, cheerBoard.getCheer_filename());
			pstmt.setInt(3, cheerBoard.getMem_num());
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 응원 게시판 - 글의 총 개수
	public int getCheerBoardCount() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당 
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT COUNT(*) FROM request_cheer";

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
	// 응원 게시판 - 목록
	public List<RequestCheerVO> getCheerList(int startRow,int endRow) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RequestCheerVO> list = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();

			// SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM"
					+ "(SELECT * FROM request_cheer ORDER BY cheer_num DESC)a) "
					+ "WHERE rnum>=? AND rnum<=?";
			//sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
			//		+ "(SELECT * FROM request_cheer c JOIN member m USING(mem_num) ORDER BY c.cheer_num DESC)a) "
			//		+ "WHERE rnum>=? AND rnum<=?";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			// SQL문을 테이블에 반영하고 결과행들을 ResultSet에 담는다.
			rs = pstmt.executeQuery();
			list = new ArrayList<RequestCheerVO>();
			while(rs.next()) { // 여러 개의 레코드를 자바빈에 담는다. jsp에선 필요한 데이터만 담으면 된다.
				RequestCheerVO cheerBoardVO = new RequestCheerVO(); // 자바빈 생성
				cheerBoardVO.setCheer_num(rs.getInt("cheer_num"));
				cheerBoardVO.setCheer_content(rs.getString("cheer_content"));

				list.add(cheerBoardVO);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	// 응원게시판 - 메인 응원글 상세
	public RequestCheerVO getCheerBoard(int cheer_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RequestCheerVO cheerBoard = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT * FROM request_cheer WHERE cheer_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, cheer_num);
			// SQL문을 테이블에 반영하고 하나의 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cheerBoard = new RequestCheerVO();
				cheerBoard.setCheer_num(rs.getInt("cheer_num"));
				cheerBoard.setCheer_content(rs.getString("cheer_content"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return cheerBoard; // 자바빈에 담아서 반환
	}
}