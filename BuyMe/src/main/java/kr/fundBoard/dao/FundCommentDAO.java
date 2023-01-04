package kr.fundBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.fundBoard.vo.FundCommentVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class FundCommentDAO {
	
	private static FundCommentDAO instance = new FundCommentDAO();

	public static FundCommentDAO getInstance() {
		return instance;
	}
	
	private FundCommentDAO() {}
	
	public void insertFuntComment(FundCommentVO boardComment) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
		//커넥션풀로부터 커넥션 할당
		conn = DBUtil.getConnection();
		
		//SQL문 작성
		sql = "INSERT INTO fund_comment (comment_num,"
		+ "comm_content,mem_num,fund_num) "
		+ "VALUES (fund_comment_req.nextval,?,?,?)";
		
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//?에 데이터 바인딩
		pstmt.setString(1, boardComment.getComm_content());
		pstmt.setInt(2, boardComment.getMem_num());
		pstmt.setInt(3, boardComment.getFund_num());
		
		//SQL문 실행
		pstmt.executeUpdate();
		
		}
		catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	
		//댓글 개수
	public int getFundCommentCount(int fund_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			
		//커넥션풀로부터 커넥션 할당
		conn = DBUtil.getConnection();
		
		//SQL문 작성
		sql = "SELECT COUNT(*) FROM fund_comment f "
		+ "JOIN member m ON f.mem_num=m.mem_num "
		+ "WHERE f.fund_num=?";
		
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//?에 데이터 바인딩
		pstmt.setInt(1, fund_num);
		
		//SQL문을 실행해서 결과행을 ResultSet 담음
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			count = rs.getInt(1);
		}
		}
		catch(Exception e) {
			
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
		}
	
	
	
		//댓글 목록
	public List<FundCommentVO> getListFundComment(int start,int end, int fund_num)throws Exception{
			
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FundCommentVO> list = null;
		String sql = null;
		
		try {
			
		//커넥션풀로부터 커넥션을 할당
		conn = DBUtil.getConnection();
		
		//SQL문 작성
		sql = "SELECT * FROM (SELECT a.*, rownum rnum "
		+ "FROM (SELECT * FROM fund_comment f JOIN "
		+ "member m USING(mem_num) WHERE "
		+ "f.fund_num=? ORDER BY f.comment_num DESC)a) "
		+ "WHERE rnum>=? AND rnum<=?";
		
		
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//?에 데이터 바인딩
		pstmt.setInt(1, fund_num);
		pstmt.setInt(2, start);
		pstmt.setInt(3, end);
		
		//SQL문을 실행해서 결과행들을 ResultSet에 담음
		rs = pstmt.executeQuery();
		list = new ArrayList<FundCommentVO>();
		
		while(rs.next()) {
			FundCommentVO comment = new FundCommentVO();
			comment.setComment_num(rs.getInt("comment_num"));
				
			comment.setComm_reg_date( DurationFromNow.getTimeDiffLabel(rs.getString("comm_reg_date")));
			comment.setComm_content(StringUtil.useBrNoHtml( rs.getString("comm_content")));
			comment.setFund_num(rs.getInt("fund_num"));
			comment.setMem_num(rs.getInt("mem_num"));
			comment.setId(rs.getString("id"));
				
			list.add(comment);
		}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);	
		}
			return list;
	}
		
	//댓글 상세
	public FundCommentVO getFundComment(int comment_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FundCommentVO comment = null;
		String sql = null;
		
		try {
			
		//커넥션풀로부터 커넥션을 할당
		conn = DBUtil.getConnection();
		
		//SQL문 작성
		sql = "SELECT * FROM fund_comment WHERE comment_num=?";
		
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//?에 데이터 바인딩
		pstmt.setInt(1, comment_num);
		
		//SQL문을 실행해서 결과행을 ResultSet에 담음
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			comment = new FundCommentVO();
			comment.setComment_num(rs.getInt("comment_num"));
			comment.setMem_num(rs.getInt("mem_num"));
		}	
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return comment;
	}
	
	//댓글 수정
	public void updateFundComment(FundCommentVO comment) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
		//커넥션풀로부터 커넥션을 할당
		conn = DBUtil.getConnection();
		
		//SQL문 작성
		sql = "UPDATE fund_comment SET comm_content=? "
		+ "WHERE comment_num=?";
		
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//?에 데이터를 바인딩
		pstmt.setString(1, comment.getComm_content());
		pstmt.setInt(2, comment.getComment_num());
		
		//SQL문 실행
		pstmt.executeUpdate();
		
		}
		catch(Exception e) {
			
			throw new Exception(e);
		}
		finally {
			
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	//댓글 삭제
	public void deleteFundComment(int comment_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
		//커넥션풀로부터 커넥션을 할당
		conn = DBUtil.getConnection();
		
		//SQL문 작성
		sql = "DELETE FROM fund_comment WHERE comment_num=?";
		
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//?에 데이터 바인딩
		pstmt.setInt(1, comment_num);
		
		//SQL문 실행
		pstmt.executeUpdate();
		
		}
		catch(Exception e) {
			
			throw new Exception(e);
		
		}
		finally {
			
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
			
		
}
