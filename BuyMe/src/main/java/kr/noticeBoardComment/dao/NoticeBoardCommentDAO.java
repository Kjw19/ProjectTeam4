package kr.noticeBoardComment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import kr.noticeBoard.vo.NoticeBoardCommentVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class NoticeBoardCommentDAO {
	//싱글턴 패턴 : 최초 1회 객체 생성,이후 호출에서는 생성된 인스턴스를 활용 
	private static NoticeBoardCommentDAO instance = new NoticeBoardCommentDAO();
	
	public static NoticeBoardCommentDAO getInstance() {
		return instance;
	}
	private NoticeBoardCommentDAO() {
	//생성자는 외부에서 호출하지 못하도록 private 지정함으로써 new 연산자에 제약 부여 
	} 
 
//댓글 등록 메소드 
public void insertNoticeBoardComment(NoticeBoardCommentVO noticeBoardComment) throws Exception {
	Connection conn = null;
	PreparedStatement pstmt = null; 
	String sql = null;
	
	try {
		conn = DBUtil.getConnection();
		sql = "INSERT INTO notice_comment (comment_num, comm_content, mem_num, noti_num) VALUES (notice_comment_seq.nextval, ?, ?, ?) ";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,noticeBoardComment.getComm_content());
		pstmt.setInt(2, noticeBoardComment.getMem_num());
		pstmt.setInt(3, noticeBoardComment.getNoti_num());
		pstmt.executeUpdate();
	} catch (Exception e) {
		throw new Exception(e);
	} finally {
		DBUtil.executeClose(null, pstmt, conn);
	}
}

//댓글 개수 확인 메소드 
public int getNoticeBoardCommentCount(int noti_num) throws Exception {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	int count = 0;
	
	try {
		conn = DBUtil.getConnection();
		sql = "SELECT COUNT(*) FROM notice_comment c JOIN member b ON c.mem_num=b.mem_num WHERE c.noti_num=?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, noti_num);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			count = rs.getInt(1);
		}
	} catch (Exception e) {
		throw new Exception(e);
	} finally {
		DBUtil.executeClose(rs, pstmt, conn);
	}
	return count;
}

//댓글 목록 메소드
public List<NoticeBoardCommentVO> getListNoticeBoardComment(int start, int end, int noti_num) throws Exception {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	List<NoticeBoardCommentVO> list = null;
	String sql = null;
	
	try {
		conn = DBUtil.getConnection();
		sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM notice_comment c JOIN member b USING(mem_num) WHERE c.noti_num=? ORDER BY c.comment_num DESC)a) WHERE rnum >= ? AND rnum <= ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, noti_num);
		pstmt.setInt(2, start);
		pstmt.setInt(3, end);
		rs = pstmt.executeQuery();
		
		list = new ArrayList<NoticeBoardCommentVO>();
		
		while(rs.next()) {
			NoticeBoardCommentVO comment = new NoticeBoardCommentVO();
			comment.setCommnet_num(rs.getInt("comment_num"));
			comment.setComm_reg_date(DurationFromNow.getTimeDiffLabel(rs.getString("comm_reg_date")));
			comment.setComm_content(StringUtil.useBrNoHtml(rs.getString("comm_content")));
			comment.setNoti_num(rs.getInt("noti_num"));
			comment.setMem_num(rs.getInt("mem_num"));
			
			
			list.add(comment);
			
		}
		
	} catch (Exception e) {
		throw new Exception(e);
	} finally {
		DBUtil.executeClose(rs, pstmt, conn);
	}
	return list; 
}

//댓글 상세 메소드 	
public NoticeBoardCommentVO getNoticeBoardComment(int comment_num) throws Exception{
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	NoticeBoardCommentVO comment = null;
	String sql = null;
	
	try {
		conn = DBUtil.getConnection();
		sql = "SELECT* FROM notice_comment WHERE comment_num = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, comment_num);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			comment = new NoticeBoardCommentVO();
			comment.setCommnet_num(rs.getInt("comment_num"));
			comment.setMem_num(rs.getInt("mem_num"));
		}
	} catch (Exception e) {
		throw new Exception(e);
	} finally {
		DBUtil.executeClose(rs, pstmt, conn);
	}
	return comment;
}

//댓글 수정 메소드
public void updateNoticeBoardComment(NoticeBoardCommentVO comment) throws Exception {
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	try {
		conn = DBUtil.getConnection();
		sql = "UPDATE notice_comment SET comm_content = ? WHERE comment_num = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, comment.getComm_content());
		pstmt.setInt(2, comment.getCommnet_num());
		
		pstmt.executeUpdate();
		
	} catch (Exception e) {
		throw new Exception(e);
	} finally {
		DBUtil.executeClose(null, pstmt, conn);
	}
}

//댓글 삭제 메소드 
public void deleteNoticeBoardComment(int comment_num) throws Exception {
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = null;
	 
	try {
		conn = DBUtil.getConnection();
		sql = "DELETE FROM notice_comment WHERE comment_num = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, comment_num);
		pstmt.executeUpdate();
	} catch (Exception e) {
		throw new Exception(e);
	} finally {
		DBUtil.executeClose(null, pstmt, conn);
	}
}



























}