package kr.fundBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardVO;
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
				if(keyfield.equals("1")) sub_sql += "WHERE b.title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE m.id LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE b.content LIKE ?";
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
			board.setFund_filename(rs.getString("fund_filename"));
			board.setMem_num(rs.getInt("mem_num"));
			board.setId(rs.getString("id"));
			//board.setPhoto(rs.getString("photo")); //이게 모야 씨발래미새키
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
			
			//SQL문 작성
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, board.getTitle());
			pstmt.setString(++cnt, board.getContent());
			if(board.getFilename()!=null) {
				pstmt.setString(++cnt, board.getFilename());
			}
			pstmt.setString(++cnt, board.getIp());
			pstmt.setInt(++cnt, board.getBoard_num());
			
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}











