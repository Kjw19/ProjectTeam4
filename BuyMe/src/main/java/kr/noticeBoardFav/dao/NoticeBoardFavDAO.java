package kr.noticeBoardFav.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.noticeBoard.vo.NoticeBoardFavsVO;
import kr.noticeBoard.vo.NoticeBoardVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class NoticeBoardFavDAO {
	//싱글턴 패턴 : 최초 1회 객체 생성,이후 호출에서는 생성된 인스턴스를 활용 
		private static NoticeBoardFavDAO instance = new NoticeBoardFavDAO();
		
		public static NoticeBoardFavDAO getInstance() {
			return instance;
		}
		
		private NoticeBoardFavDAO() {
		//생성자는 외부에서 호출하지 못하도록 private 지정함으로써 new 연산자에 제약 부여
	}
				
	
	//좋아요 등록 메소드 
	public void insertNoticeFav(NoticeBoardFavsVO favVO) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO notice_like(like_num, noti_num, mem_num) VALUES(notice_like_seq.nextval, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,favVO.getNoti_num() );
			pstmt.setInt(2,favVO.getMem_num());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//좋아요 등록 메소드
	public int selectNoticeFavCount(int noti_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM notice_like WHERE noti_num = ?";
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
	
	//회원이 게시물을 호출했을 때 좋아요 선택 여부 표시 
	public NoticeBoardFavsVO selectNoticeFav(NoticeBoardFavsVO favVO) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NoticeBoardFavsVO fav = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM notice_like WHERE noti_num =? AND mem_num = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, favVO.getNoti_num());
			pstmt.setInt(2, favVO.getMem_num());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fav = new NoticeBoardFavsVO();
				fav.setLike_num(rs.getInt("like_num"));
				fav.setNoti_num(rs.getInt("noti_num"));
				fav.setMem_num(rs.getInt("mem_num"));
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return favVO;
	}
	
	//좋아요 삭제 메소드 
	public void deleteNoticeFav(int like_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
				
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM notice_like WHERE like_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, like_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//내가 선택한 좋아요 목록 메소드
	public List<NoticeBoardVO> getListNoticeFav(int start, int end, int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NoticeBoardVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM notice_board a JOIN member b USING(mem_num) JOIN notice_like c USING(noti_num) WHERE c.mem_num =? ORDER BY noti_num DESC)a)WHERE rnum >=? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<NoticeBoardVO>();
			
			while(rs.next()) {
				NoticeBoardVO board = new NoticeBoardVO();
				board.setNoti_num(rs.getInt("noti_num"));
				board.setNoti_title(StringUtil.useBrNoHtml(rs.getString("noti_title")));
				board.setNoti_reg_date(rs.getDate("noti_reg_date"));
				
				list.add(board);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	






}
