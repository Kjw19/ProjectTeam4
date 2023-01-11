package kr.fundBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.fundBoard.vo.FundBoardLikeVO;
import kr.fundBoard.vo.FundBoardVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class FundBoardLikeDAO {
	//싱글턴 패턴?
		private static FundBoardLikeDAO instance = new FundBoardLikeDAO();
		
		public static FundBoardLikeDAO getInstance() {
			return instance;
		}
		private FundBoardLikeDAO() {}
		
		//좋아요 등록
		public void insertLike(FundBoardLikeVO likeVO)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO fund_board (like_num,"
					+ "fund_num,mem_num) VALUES ("
					+ "fundboard_seq.nextval,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, likeVO.getFund_num());
				pstmt.setInt(2, likeVO.getMem_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		//좋아요 개수
		public int selectLikeCount(int board_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM fund_like WHERE fund_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, board_num);
				//SQL문을 실행하고 결과행을 ResultSet에 담음
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
		
		//회원이 게시물을 호출했을 때 좋아요 선택 여부 표시
		public FundBoardLikeVO selectLike(FundBoardLikeVO likeVO)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			FundBoardLikeVO like = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM fund_like WHERE fund_num=? AND mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, likeVO.getFund_num());
				pstmt.setInt(2, likeVO.getMem_num());
				//SQL문을 실행해서 결과행을 ResulSet에 담음
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					like = new FundBoardLikeVO();
					like.setLike_num(rs.getInt("fav_num"));
					like.setFund_num(rs.getInt("board_num"));
					like.setMem_num(rs.getInt("mem_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return like;
		}
		
		//좋아요 삭제
		public void deleteLike(int like_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "DELETE FROM fund_like WHERE like_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, like_num);
			}catch(Exception e){
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		//내가 선택한 좋아요 목록
			public List<FundBoardVO> getListBoardLike(int start,
					                        int end,int mem_num)
					            		   throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				List<FundBoardVO> list = null;
				String sql = null;
				
				try {
					//커넥션풀로부터 커넥션 할당
					conn = DBUtil.getConnection();
					//SQL문 작성
					sql = "SELECT * FROM (SELECT a.*, rownum rnum "
						+ "FROM (SELECT * FROM fund_board b JOIN members m "
						+ "USING(mem_num) JOIN fund_like f USING(board_num) "
						+ "WHERE f.mem_num=? ORDER BY fund_num DESC)a) "
						+ "WHERE rnum >= ? AND rnum <= ?";
					//PrepardStatement 객체 생성
					pstmt = conn.prepareStatement(sql);
					//?에 데이터 바인딩
					pstmt.setInt(1, mem_num);
					pstmt.setInt(2, start);
					pstmt.setInt(3, end);
					
					//SQL문을 실행해서 결과행들을 ResultSet에 담음
					rs = pstmt.executeQuery();
					list = new ArrayList<FundBoardVO>();
					while(rs.next()) {
						FundBoardVO board = new FundBoardVO();
						board.setFund_num(rs.getInt("fund_num"));
						board.setFund_title(StringUtil.useNoHtml(
								           rs.getString("fund_title")));
						board.setFund_reg_date(rs.getDate("reg_date"));
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
}
