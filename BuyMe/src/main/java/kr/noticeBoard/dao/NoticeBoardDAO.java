package kr.noticeBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.noticeBoard.vo.NoticeBoardVO;
import kr.util.DBUtil;

public class NoticeBoardDAO {
	//싱글턴 패턴
	private static NoticeBoardDAO instance = new NoticeBoardDAO();

	public static NoticeBoardDAO getInstance(){
		return instance;
	}
	private NoticeBoardDAO() {}
	

	//공지사항 게시판 글 등록
	public void insertNoticeBoard(NoticeBoardVO noticeboard) throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql=null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn=DBUtil.getConnection();
			//SQL문 작성
			sql="INSERT INTO notice_board (noti_num,noti_title,"
				    + "noti_content,noti_ip,mem_num) VALUES ("
				    + "noticeboard_seq.nextval,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, noticeboard.getNoti_title());
			pstmt.setString(2, noticeboard.getNoti_content());
			pstmt.setString(3, noticeboard.getNoti_ip());
			pstmt.setInt(4, noticeboard.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//공지사항 게시판 글(검색글) 목록 - 임시
	public List<NoticeBoardVO> getListNoticeBoard(int startRow, int endRow) throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<NoticeBoardVO> list=null;
		String sql=null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn=DBUtil.getConnection();
			
			//SQL문 작성
			//임시!
			sql="SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM notice_board ORDER BY num DESC)a) WHERE rnum>=? AND rnum<=?";
			
			//PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			//SQL문을 테이블에 반영하고 결과행들을 ResultSet에 담음
			//글번호 제목 작성일 사진 조회수 아이디
			rs=pstmt.executeQuery();
			list=new ArrayList<NoticeBoardVO>();
			while(rs.next()) {
				NoticeBoardVO noticeboardVO=new NoticeBoardVO();
				noticeboardVO.setNoti_num(rs.getInt("noti_num"));
				noticeboardVO.setNoti_title(rs.getString("noti_title"));
				noticeboardVO.setNoti_reg_date(rs.getDate("noti_reg_date"));
				noticeboardVO.setNoti_filename(rs.getString("noti_filename"));
				noticeboardVO.setNoti_hit(rs.getInt("noti_hit"));
				//+아이디
				
				//자바빈(VO)를 ArrayList에 저장
				list.add(noticeboardVO);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//공지사항 게시판 글 상세정보
	public NoticeBoardVO getNoticeBoard(int noti_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		NoticeBoardVO noticeboard=null;
		String sql=null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn=DBUtil.getConnection();
			
			//SQL문 작성
			sql="SELECT * FROM notice_board b JOIN member m USING(mem_num) JOIN member_detail d USING(mem_num) WHERE b.noti_num=?";
			//PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, noti_num);
			//SQL문을 실행해서 결과행을 rs에 담음
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				noticeboard=new NoticeBoardVO();
				noticeboard.setNoti_num(rs.getInt("noti_num"));
				noticeboard.setNoti_title(rs.getString("noti_title"));
				noticeboard.setNoti_content(rs.getString("noti_content"));
				noticeboard.setNoti_hit(rs.getInt("noti_hit"));
				noticeboard.setNoti_reg_date(rs.getDate("noti_reg_date"));
				noticeboard.setNoti_modify_date(rs.getDate("noti_modify_date"));
				noticeboard.setNoti_filename(rs.getString("noti_filename"));
				noticeboard.setMem_num(rs.getInt("mem_num"));
				//+아이디
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return noticeboard;
	}
	
	//조회수 증가
	public void updateNoticeReadCount(int noti_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE notice_board SET noti_hit=noti_hit+1 WHERE noti_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터를 바인딩
			pstmt.setInt(1, noti_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//파일 삭제
	public void deleteNoticeFile(int noti_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE notice_board SET noti_filename='' WHERE noti_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, noti_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//공지사항 게시판 글 수정
	public void updateNoticeBoard(NoticeBoardVO noticeboard)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//전송된 파일 여부 체크
			if(noticeboard.getNoti_filename()!=null) {
				sub_sql += ",noti_filename=?";
			}
			
			sql = "UPDATE notice_board SET noti_title=?,noti_content=?,"
				+ "noti_modify_date=SYSDATE" + sub_sql 
				+ ",noti_ip=? WHERE noti_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, noticeboard.getNoti_title());
			pstmt.setString(++cnt, noticeboard.getNoti_content());
			if(noticeboard.getNoti_filename()!=null) {
				pstmt.setString(++cnt, noticeboard.getNoti_filename());
			}
			pstmt.setString(++cnt, noticeboard.getNoti_ip());
			pstmt.setInt(++cnt, noticeboard.getNoti_num());
			
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//공지사항 게시판 글 삭제
	public void deleteNoticeBoard(int noti_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			
			//좋아요 삭제
			sql="DELETE FROM notice_like WHERE noti_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noti_num);
			pstmt.executeUpdate();
			
			//댓글 삭제
			sql="DELETE FROM notice_comment WHERE noti_num=?";
			pstmt2=conn.prepareStatement(sql);
			pstmt2.setInt(1, noti_num);
			pstmt2.executeUpdate();
			
			//부모글 삭제
			sql = "DELETE FROM notice_board WHERE noti_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, noti_num);
			pstmt3.executeUpdate();
			
			//예외 발생 없이 정상적으로 SQL문이 실행
			conn.commit();
		}catch(Exception e) {
			//예외 발생
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}