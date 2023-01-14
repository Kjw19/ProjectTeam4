package kr.fundBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.fundBoard.vo.FundBoardVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class FundBoardDAO {
	//싱글턴 패턴 : 다른 곳에서 이용하기 쉽게 해준
	private static FundBoardDAO instance = new FundBoardDAO();
	
	public static FundBoardDAO getInstance() {
		return instance;
	}
	private FundBoardDAO() {}
	
	//글등록
	public void insertBoard(FundBoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO fund_board (fund_num,fund_title,"
			    + "fund_content,fund_ip,fund_filename,category_num,mem_num) VALUES ("
			    + "fund_board_seq.nextval,?,?,?,?,?,?)";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, board.getFund_title());
			pstmt.setString(2, board.getFund_content());
			pstmt.setString(3, board.getFund_ip());
			pstmt.setString(4, board.getFund_filename());
			pstmt.setInt(5, board.getCategory_num());
			pstmt.setInt(6, board.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//총 레코드 수(검색 레코드 수)
		public int getFundBoardCount(String keyfield, 
				                 String keyword)
		                                    throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";//검색시 사용
			int count = 0;
			
			try {
				//커넥셔풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				if(keyword != null && !"".equals(keyword)) {
					//검색글 개수
					if(keyfield.equals("1")) sub_sql += "WHERE b.fund_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql += "WHERE m.fund_id LIKE ?";
					else if(keyfield.equals("3")) sub_sql += "WHERE b.fund_content LIKE ?";
					else if(keyfield.equals("4")) sub_sql += "WHERE b.category_num LIKE ?";
				}
				
				
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM fund_board b JOIN member m USING(mem_num) " + sub_sql;
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				if(keyword !=null && !"".equals(keyword)) {
					pstmt.setString(1, "%" + keyword + "%");
				}

				//SQL문을 실행하고 결과행을 ResultSet 담음
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
	//글목록(검색 글 목록)
	public List<FundBoardVO> getListFundBoard(int start, int end,
									String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FundBoardVO> list = null;
		String sql = null;
		String sub_sql = "";//검색시 사용
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색 글 보기
				if(keyfield.equals("1")) sub_sql += "WHERE b.fund_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE m.id LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE b.fund_content LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE b.category_num LIKE ?";
			}
			
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
				+ "FROM (SELECT * FROM fund_board b JOIN "
				+ "member m USING(mem_num)" + sub_sql + "ORDER BY b.fund_num DESC)a) "
				+ "WHERE rnum >=? AND rnum <=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword +"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			//SQL문을 실행해서 결과행들을 ResultSet에 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<FundBoardVO>();
			while(rs.next()) {
				FundBoardVO board = new FundBoardVO();
				board.setFund_num(rs.getInt("fund_num"));
				board.setFund_title(StringUtil.useNoHtml(rs.getString("fund_title")));
				board.setFund_hit(rs.getInt("fund_hit"));
				board.setFund_reg_date(rs.getDate("fund_reg_date"));
				board.setId(rs.getString("id"));
				board.setCategory_num(rs.getInt("Category_num"));
				
				list.add(board);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//글상세
	public FundBoardVO getFundBoard(int fund_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FundBoardVO board = null;
		String sql = null;
		
		try {
			board = new FundBoardVO();
			board.setFund_num(rs.getInt("fund_num"));
			board.setFund_title(rs.getString("fund_title"));
			board.setFund_content(rs.getString("fund_content"));
			board.setFund_hit(rs.getInt("fund_hit"));
			board.setFund_reg_date(rs.getDate("fund_reg_date"));
			board.setFund_modify_date(rs.getDate("fund_modify_date"));
			board.setFund_filename(rs.getString("fund_filename"));//사진
			board.setMem_num(rs.getInt("mem_num"));
			board.setId(rs.getString("id"));
			board.setCategory_num(rs.getInt("Category_num"));
			board.setFund_photo(rs.getString("fund_photo")); //Fund_filename로 대체
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return board;
	}
	
	//조회수 증가
	public void updateReadcount(int fund_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE fund_board SET hit=hit+1 WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터를 바인딩
			pstmt.setInt(1, fund_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//펀딩 게시판 게시물 삭제
	public void deleteFile(int fund_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE fund_board SET fund_filename='' WHERE fund_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, fund_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//펀딩 게시판 수정
	public void updateBoard(FundBoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//전송된 파일 여부 체크
			if(board.getFund_filename()!=null) {
				sub_sql += ",fund_filename=?";
			}
			
			//SQL문 작성
			sql = "UPDATE fund_board SET fund_title=?,fund_content=?,"
				+ "fund_modify_date=SYSDATE" + sub_sql
				+ ",fund_ip=?, category_num=? WHERE fund_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, board.getFund_title());
			pstmt.setString(++cnt, board.getFund_content());
			if(board.getFund_filename()!=null) {
				pstmt.setString(++cnt, board.getFund_filename());
			}
			pstmt.setString(++cnt, board.getFund_ip());
			pstmt.setInt(++cnt, board.getFund_num());
			pstmt.setInt(++cnt, board.getCategory_num());
			
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//글삭제
	public void deleteBoard(int fund_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			
			//좋아요 삭제
			sql = "DELETE FROM fund_like WHERE fund_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fund_num);
			pstmt.executeUpdate();
			
			//좋아요 삭제
			sql = "DELETE FROM fund_comment WHERE fund_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, fund_num);
			pstmt2.executeUpdate();
			
			//좋아요 삭제
			sql = "DELETE FROM fund_inquiry WHERE fund_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, fund_num);
			pstmt3.executeUpdate();
			
			//부모글 삭제
			sql = "DELETE FROM fund_board WHERE fund_num=?";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setInt(1, fund_num);
			pstmt4.executeUpdate();
			
			//예외 발생 없이 정상적으로 SQL문이 실행
			conn.commit();
		}catch(Exception e) {
			//예외발생
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}











