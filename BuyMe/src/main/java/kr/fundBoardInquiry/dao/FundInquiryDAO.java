package kr.fundBoardInquiry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.fundBoard.vo.FundBoardVO;
import kr.fundBoardInquiry.vo.FundInquiryVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class FundInquiryDAO {
	
	private static FundInquiryDAO instance = new FundInquiryDAO();

	public static FundInquiryDAO getInstance() {
		return instance;
	}
	

	//문의 등록
	public void insertFundInquiry(FundInquiryVO inquiry)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "INSERT INTO fund_inquiry (inquiry_num,"
					+ "inqu_content,mem_num,fund_num) "
					+ "VALUES (fund_inquiry_seq.nextval,?,?,?)";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setString(1, inquiry.getInqu_content());
			pstmt.setInt(2, inquiry.getMem_num());
			pstmt.setInt(3, inquiry.getFund_num());
			
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
	
	//문의 답변 등록
	public void insertFundReInquiry(FundInquiryVO inquiry)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "INSERT INTO fund_inquiry (inquiry_num,"
			    + "re_inqu_is_ok ,mem_num,fund_num) VALUES ("
			    + "fund_inquiry_seq.nextval,?,?,?)";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setString(1, inquiry.getRe_inqu_is_ok());
			pstmt.setInt(2, inquiry.getMem_num());
			pstmt.setInt(3, inquiry.getFund_num());
			
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
	
	
	//문의 목록 상세
	public List<FundInquiryVO> getListFundInquiry(int start,int end, int fund_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FundInquiryVO> list = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
				+ "FROM (SELECT * FROM fund_inquiry f JOIN "
				+ "member m USING(mem_num) WHERE "
				+ "f.fund_num=? ORDER BY f.inquiry_num DESC)a) "
				+ "WHERE rnum>=? AND rnum<=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, fund_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			//SQL문을 실행해서 결과행들을 ResultSet에 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<FundInquiryVO>();
			
			while(rs.next()) {
				FundInquiryVO inqu = new FundInquiryVO();
				inqu.setInquiry_num(rs.getInt("inquiry_num"));
				inqu.setInqu_reg_date(rs.getDate("inqu_reg_date"));
				inqu.setInqu_content(StringUtil.useBrNoHtml(
						           rs.getString("inqu_content")));
				inqu.setRe_inqu_is_ok(StringUtil.useBrNoHtml(
				           rs.getString("re_inqu_is_ok")));
				inqu.setFund_num(rs.getInt("fund_num"));
				inqu.setMem_num(rs.getInt("mem_num"));
				inqu.setId(rs.getString("id"));
				
				list.add(inqu);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//문의 목록 상세
	public FundInquiryVO getFundInquiry(int inquiry_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FundInquiryVO inquiry = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM fund_inquiry f JOIN member m "
				+ "USING(mem_num) JOIN member_detail d "
				+ "USING(mem_num) WHERE f.inquiry_num=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, inquiry_num);
			//SQL문을 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				inquiry = new FundInquiryVO();
				inquiry.setInquiry_num(rs.getInt("inquiry_num"));
				inquiry.setInqu_content(rs.getString("inqu_content"));
				inquiry.setRe_inqu_is_ok(rs.getString("re_inqu_is_ok"));
				inquiry.setInqu_reg_date(rs.getDate("inqu_reg_date"));
				inquiry.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return inquiry;
	}
	
	//문의 수정
	public void updateFundInquiry(FundInquiryVO inquiry) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
		//커넥션풀로부터 커넥션을 할당
		conn = DBUtil.getConnection();
		
		//SQL문 작성
		sql = "UPDATE fund_inquiry SET inqu_content=? "
		+ "WHERE inquiry_num=?";
		
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//?에 데이터를 바인딩
		pstmt.setString(1, inquiry.getInqu_content());
		pstmt.setInt(2, inquiry.getInquiry_num());
		
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
	
	
	//문의 답변 수정 및 삭제
	public void updateFundReInquiry(FundInquiryVO inquiry) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
		//커넥션풀로부터 커넥션을 할당
		conn = DBUtil.getConnection();
		
		//SQL문 작성
		sql = "UPDATE fund_inquiry SET re_inqu_is_ok=? "
		+ "WHERE inquiry_num=?";
		
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//?에 데이터를 바인딩
		pstmt.setString(1, inquiry.getRe_inqu_is_ok());
		pstmt.setInt(2, inquiry.getInquiry_num());
		
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
	
	
	//문의 삭제
	public void deleteFundInquiry(int inquiry_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
		//커넥션풀로부터 커넥션을 할당
		conn = DBUtil.getConnection();
		
		//SQL문 작성
		sql = "DELETE FROM fund_inquiry WHERE inquiry_num=?";
		
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//?에 데이터 바인딩
		pstmt.setInt(1, inquiry_num);
		
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
	
	//총 문의 수
	public int getFundInquCount(int fund_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//커넥셔풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM fund_inquiry f "
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
	
	
	public FundBoardVO getFund(int fund_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FundBoardVO fund = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM fund_board b JOIN member m "
				+ "USING(mem_num) JOIN member_detail d "
				+ "USING(mem_num) WHERE b.fund_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, fund_num);
			//SQL문을 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fund = new FundBoardVO();
				fund.setFund_num(rs.getInt("fund_num"));
				fund.setFund_title(rs.getString("fund_title"));
				fund.setFund_content(rs.getString("fund_content"));
				fund.setFund_hit(rs.getInt("fund_hit"));
				fund.setFund_reg_date(rs.getDate("fund_reg_date"));
				fund.setFund_modify_date(rs.getDate("fund_modify_date"));
				fund.setFund_filename(rs.getString("fund_filename"));
				fund.setMem_num(rs.getInt("mem_num"));
				fund.setId(rs.getString("id"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return fund;
	}

			
	
}
