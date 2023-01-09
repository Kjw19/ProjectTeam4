package kr.requestBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.requestBoard.vo.RequestInquiryVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
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
	// 문의 개수
	public int getRequestInquiryCount(int req_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT COUNT(*) FROM request_inquiry i "
					+ "JOIN member m ON i.mem_num=m.mem_num WHERE i.req_num=?";
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
	// 문의 목록 : 부모글 식별을 위해 req_num(부모 글번호)를 넘긴다. / mem_num = 작성자
	public List<RequestInquiryVO> getListRequestInquiry(int start, int end, int req_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RequestInquiryVO> list = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당 받음
			conn = DBUtil.getConnection();
			// SQL문 작성 : 댓글(c)와 id정보(m)을 읽어 온다. → 조인
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM request_inquiry i JOIN member m "
					+ "USING(mem_num) WHERE i.req_num=? "
					+ "ORDER BY i.inquiry_num DESC)a) WHERE rnum>=? AND rnum<=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, req_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			// SQL문을 실행하여 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<RequestInquiryVO>();
			while(rs.next()) {
				RequestInquiryVO inquiry = new RequestInquiryVO();
				inquiry.setInquiry_num(rs.getInt("inquiry_num"));
				inquiry.setInqu_content(StringUtil.useBrNoHtml(rs.getString("inqu_content")));
				inquiry.setInqu_reg_date(DurationFromNow.getTimeDiffLabel(rs.getString("inqu_reg_date")));
				inquiry.setMem_num(rs.getInt("mem_num"));
				inquiry.setReq_num(rs.getInt("req_num"));

				list.add(inquiry);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
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


	// 2. 문의 게시판 - 자주 묻는 질문에 대한 내 문의 등록
	// 문의 등록
	public void insertInquiryBoard(RequestInquiryVO inquiryBoard) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당 받는다.
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO request_inquiry (inquiry_num,inqu_title,inqu_content,inqu_reg_date,inqu_filename,inqu_ip,mem_num,req_num) "
					+ "VALUES (req_inqu_seq.nextval,?,?,SYSDATE,?,?,?,?)";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, inquiryBoard.getInqu_title());
			pstmt.setString(2, inquiryBoard.getInqu_content());
			pstmt.setString(3, inquiryBoard.getInqu_filename());
			pstmt.setString(4, inquiryBoard.getInqu_ip());
			pstmt.setInt(5, inquiryBoard.getMem_num());
			pstmt.setInt(6, inquiryBoard.getReq_num());
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 문의 상세
	public RequestInquiryVO getDetailInquiryBoard(int inquiry_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RequestInquiryVO inquiry = null;
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
				inquiry = new RequestInquiryVO(); // 한 건의 레코드를 자바빈에 담아서 전달한다.
				inquiry.setInquiry_num(rs.getInt("inquiry_num"));
				inquiry.setInqu_title(rs.getString("inqu_title"));
				inquiry.setInqu_content(rs.getString("inqu_content"));
				inquiry.setInqu_reg_date(rs.getString("inqu_reg_date"));
				inquiry.setInqu_filename(rs.getString("inqu_filename"));
				inquiry.setMem_num(rs.getInt("mem_num"));
				inquiry.setId(rs.getString("id"));
				inquiry.setPhoto(rs.getString("photo"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return inquiry;
	}
}