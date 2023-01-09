package kr.noticeInquiry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import kr.noticeBoard.vo.NoticeInquiryVO;
import kr.util.DBUtil;

import kr.util.StringUtil;

public class NoticeInquiryDAO {
	//싱글턴 패턴
	private static NoticeInquiryDAO instance = new NoticeInquiryDAO();
	public static NoticeInquiryDAO getInstance() {
		return instance;
	}
	private NoticeInquiryDAO() {}
	//공지사항 문의 게시판 글 등록
	public void insertNoticeInquiry(NoticeInquiryVO noticeinquiry) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql ="INSERT INTO notice_inquiry(inquiry_num, "
					+ "inqu_title,inqu_content,mem_num,notice_num) VALUES( "
					+ "notice_inquiry_seq.nextval,?,?,?,?)";
			//PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, noticeinquiry.getInqu_title());
			pstmt.setString(2, noticeinquiry.getInqu_content());
			pstmt.setInt(3, noticeinquiry.getMem_num());
			pstmt.setInt(4, noticeinquiry.getNotice_num());
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

//공지사항 문의 답글 입력
public void insertNoticeReInquiry(NoticeInquiryVO noticeinquiry)throws Exception{
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = null;
	try {
		//커넥션풀러부터 커넥션 할당
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql ="INSERT INTO notice_inquiry(inquiry_num,inqu_title, "
				+ "re_inqu_is_ok,mem_num,notice_num) VALUE( "
				+ "notice_inquiry_seq.nextval,?,?,?,?)";
		//PrepardeStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//?에 데이터 바인딩
		pstmt.setString(1, noticeinquiry.getInqu_title());
		pstmt.setString(2, noticeinquiry.getRe_inqu_is_ok());
		pstmt.setInt(3, noticeinquiry.getMem_num());
		pstmt.setInt(4, noticeinquiry.getNotice_num());
		
		//SQL문 실행
		pstmt.executeUpdate();
	}catch(Exception e) {
		throw new Exception (e);
	}finally {
		DBUtil.executeClose(null, pstmt, conn);
	
	}
	
 }

//공지사항 문의글 상세
public List<NoticeInquiryVO> getListNoticeInquiry(int start,int end, int inquiry_num) throws Exception{
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	List<NoticeInquiryVO> list = null;
	String sql = null;
	
	try {
		//커넥션풀로부터 커넥션을 할당
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "SELECT * FROM (SELECT a.*, rownum rnum "
			+ "FROM (SELECT * FROM notice_inquiry n JOIN "
			+ "member m USING(mem_num) WHERE "
			+ "n.notice_num=? ORDER BY n.inquiry_num DESC)a) "
			+ "WHERE rnum>=? AND rnum<=?";
		
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//?에 데이터 바인딩
		pstmt.setInt(1, inquiry_num);
		pstmt.setInt(2, start);
		pstmt.setInt(3, end);
		
		//SQL문을 실행해서 결과행들을 ResultSet에 담음
		rs = pstmt.executeQuery();
		list = new ArrayList<NoticeInquiryVO>();
		
		while(rs.next()) {
			NoticeInquiryVO inqu = new NoticeInquiryVO();
			inqu.setInquiry_num(rs.getInt("inquiry_num"));
			
			inqu.setInqu_content(StringUtil.useBrNoHtml(
					           rs.getString("inqu_content")));
			inqu.setRe_inqu_is_ok(StringUtil.useBrNoHtml(
			           rs.getString("re_inqu_is_ok")));
			inqu.setNotice_num(rs.getInt("notice_num"));
			inqu.setMem_num(rs.getInt("inquiry_num"));
		
			
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
	public NoticeInquiryVO getNoticeInquiry(int inquiry_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NoticeInquiryVO inquiry = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM notice_inquiry n JOIN member m "
				+ "USING(mem_num) JOIN member_detail d "
				+ "USING(mem_num) WHERE n.inquiry_num=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, inquiry_num);
			//SQL문을 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				inquiry = new NoticeInquiryVO();
				inquiry.setInquiry_num(rs.getInt("inquiry_num"));
				inquiry.setInqu_title(rs.getString("inqu_title"));
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
//공지사항 문의 수정
	public void updateNoticeInquiry(NoticeInquiryVO inquiry) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
		//커넥션풀로부터 커넥션을 할당
		conn = DBUtil.getConnection();
		
		//SQL문 작성
		sql = "UPDATE notice_inquiry SET inqu_content=? "
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
	//공지사항 문의 댓글 수정 및 삭제
	public void updateNoticeReInquiry(NoticeInquiryVO inquiry) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
		//커넥션풀로부터 커넥션을 할당
		conn = DBUtil.getConnection();
		
		//SQL문 작성
		sql = "UPDATE notice_inquiry SET re_inqu_is_ok=? "
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
		//공지사항 문의  삭제
		public void deleteNoticeInquiry(int inquiry_num) throws Exception{
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "DELETE FROM notice_inquiry WHERE inquiry_num=?";
			
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
		//공지사항 게시판 총 문의 수
		public int getNoticeInquCount() throws Exception{
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			
			try {
				//커넥셔풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM notice b JOIN member m USING(mem_num) ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
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
				
		
	}
	
	

