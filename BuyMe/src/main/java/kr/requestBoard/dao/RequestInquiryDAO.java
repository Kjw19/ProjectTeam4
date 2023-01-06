package kr.requestBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.requestBoard.vo.RequestInquiryVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class RequestInquiryDAO {
	// 싱글턴 패턴
	private static RequestInquiryDAO instance = new RequestInquiryDAO();
	public static RequestInquiryDAO getInstance() {
		return instance;
	}
	private RequestInquiryDAO() {}
	
	// 2. 문의게시판(main)
	// 문의에 대한 문의 등록
	public void insertRequestInquiry(RequestInquiryVO Inquiry) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO request_inquiry (inquiry_num,inqu_title,inqu_content,mem_num,req_num) "
					+ "VALUES (req_inqu_seq.nextval,?,?,?,?)";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, Inquiry.getInqu_title());
			pstmt.setString(2, Inquiry.getInqu_content());
			pstmt.setInt(3, Inquiry.getMem_num());
			pstmt.setInt(4, Inquiry.getReq_num());
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 문의 답변 등록
	public void insertReRequestInquiry(RequestInquiryVO ReInquiry) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO request_inquiry "
					+ "(inquiry_num,inqu_title,re_inqu_is_ok,inqu_reg_date,mem_num,req_num) "
					+ "VALUES (req_inqu_seq.nextval,?,?,SYSDATE,?,?)";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩 - 답변에 제목은 필요 없을지도?
			pstmt.setString(1, ReInquiry.getInqu_title());
			pstmt.setString(2, ReInquiry.getRe_inqu_is_ok());
			pstmt.setInt(3, ReInquiry.getMem_num());
			pstmt.setInt(4, ReInquiry.getReq_num());
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 문의 총 레코드 수(검색 레코드 수)
	public int getRequestInquiryCount(String keyfield, String keyword) throws Exception{
		Connection conn = null;;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if(keyword!=null && !"".equals(keyword)) {
				// 검색글 개수
				if(keyfield.equals("1")) sub_sql += "WHERE i.inqu_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE m.id LIKE ?"; // 지울 수도 있음. 필요 없어 보임.
				else if(keyfield.equals("3")) sub_sql += "WHERE i.inqu_content LIKE ?";
			}

			// SQL문 작성
			sql = "SELECT COUNT(*) FROM request_inquiry i JOIN member m USING(mem_num) " + sub_sql;

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(1, "%" + keyword + "%"); // 가변문자
			}

			// SQL문을 실행하고 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1); // 컬럼 인덱스
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	// 문의 목록 (검색 글 목록)
	public List<RequestInquiryVO> getListRequestInquiry(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RequestInquiryVO> list = null;
		String sql = null;
		String sub_sql = ""; // 검색 시 사용
		int cnt = 0; // 동적 변수
		
		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			if(keyword!=null && !"".equals(keyword)) {
				// 검색글 보기
				if(keyfield.equals("1")) sub_sql += "WHERE i.inqu_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE m.id LIKE ?"; // 지울 수도 있음. 필요 없어 보임.
				else if(keyfield.equals("3")) sub_sql += "WHERE i.inqu_content LIKE ?";
			}

			// SQL문 작성 : 앞뒤공백 조심
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM request_inquiry i "
					+ "JOIN member m USING(mem_num) " + sub_sql + " ORDER BY i.req_num DESC)a) "
					+ "WHERE rnum>=? AND rnum<=?";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			// SQL문을 실행해서 결과행들을 ResultSet에 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<RequestInquiryVO>();
			while(rs.next()) {
				RequestInquiryVO inquiry = new RequestInquiryVO();
				inquiry.setReq_num(rs.getInt("req_num"));
				inquiry.setInqu_title(StringUtil.useNoHtml(rs.getString("inqu_title")));
				inquiry.setId(rs.getString("id"));

				list.add(inquiry);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return list;
	}
	// 문의 상세
	public RequestInquiryVO getRequestInquiry(int inquiry_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RequestInquiryVO Inquiry = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT * FROM request_inquiry WHERE inquiry_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, inquiry_num);
			// SQL문을 실행하여 ResultSet에 결과행을 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Inquiry = new RequestInquiryVO();
				Inquiry.setInquiry_num(rs.getInt("comment_num"));
				Inquiry.setMem_num(rs.getInt("mem_num")); // 댓글 작성자 정보
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return Inquiry;
	}
	// 문의 수정
	public void updateRequestInquiry(RequestInquiryVO Inquiry) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "UPDATE request_inquiry SET re_inqu_is_ok=? WHERE inquiry_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, Inquiry.getRe_inqu_is_ok());
			pstmt.setInt(2, Inquiry.getInquiry_num());
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 문의 삭제
	public void deleteRequestInquiry(int inquiry_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "DELETE FROM request_inquiry WHERE inquiry_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, inquiry_num);
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 3. 문의 게시판 - 내 문의
	// 내 문의 등록
	public void insertMyInquiryBoard(RequestInquiryVO myInquiryBoard) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받는다.
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO request_inquiry (inquiry_num,inqu_title,inqu_content,inqu_reg_date,inqu_filename,inqu_ip,mem_num) "
					+ "VALUES (req_inqu_seq.nextval,?,?,SYSDATE,?,?,?)";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, myInquiryBoard.getInqu_title());
			pstmt.setString(2, myInquiryBoard.getInqu_content());
			pstmt.setString(3, myInquiryBoard.getInqu_filename());
			pstmt.setString(4, myInquiryBoard.getInqu_ip());
			pstmt.setInt(5, myInquiryBoard.getMem_num());
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 내 문의 상세
	public RequestInquiryVO getMyInquiryBoard(int inquiry_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RequestInquiryVO myInquiry = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션을 할당 받는다.
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT * FROM request_inquiry i JOIN member m USING(mem_num) JOIN member_detail d "
					+ "USING(mem_num) WHERE i.inquiry_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, inquiry_num);
			// SQL문을 실행해서 결과행을 ResultSet에 담는다.
			rs = pstmt.executeQuery();
			if(rs.next()) {
				myInquiry = new RequestInquiryVO(); // 한 건의 레코드를 자바빈에 담아서 전달한다.
				myInquiry.setInquiry_num(rs.getInt(inquiry_num));
				myInquiry.setInqu_title(rs.getString("inqu_title"));
				myInquiry.setInqu_content(rs.getString("inqu_content"));
				myInquiry.setInqu_reg_date(rs.getString("inqu_reg_date"));
				myInquiry.setInqu_filename(rs.getString("inqu_filename"));
				myInquiry.setMem_num(rs.getInt("mem_num"));
				myInquiry.setId(rs.getString("id"));
				myInquiry.setPhoto(rs.getString("photo"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return myInquiry;
	}
	
	
	
	
	
	
	
	
	
	
	
}