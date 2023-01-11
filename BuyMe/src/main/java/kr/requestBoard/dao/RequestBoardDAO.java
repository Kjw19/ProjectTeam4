package kr.requestBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import kr.requestBoard.vo.RequestBoardVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class RequestBoardDAO { 
	//싱글턴 패턴
		private static RequestBoardDAO instance = new RequestBoardDAO();
		
		public static RequestBoardDAO getInstance() {
			return instance;
		}
		private RequestBoardDAO() {}
		
		//글등록
		public void insertBoard(RequestBoardVO board)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO request_board (req_num,req_title,"
				    + "req_content,req_filename,req_ip,mem_num) VALUES ("
				    + "requestboard_seq.nextval,?,?,?,?,?)";
				
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(1, board.getReq_title());
				pstmt.setString(2, board.getReq_content());
				pstmt.setString(3, board.getReq_filename());
				pstmt.setString(4, board.getReq_ip());
				pstmt.setInt(5, board.getMem_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//총 레코드 수(검색 레코드 수)
		public int getBoardCount(String keyfield, 
				                 String keyword)
		                                    throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";//검색시 사용
			int count = 0;
			
			try {
				//커넥셔풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				if(keyword !=null&&!"".equals(keyword)) {
					//검색글 개수
					if(keyfield.equals("1"))sub_sql+="where req_title like ?";
					else if(keyfield.equals("2"))sub_sql+="where m.id like ?";
					else if(keyfield.equals("3"))sub_sql+="where req_content like ?";
				}
				
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM request_board b JOIN member m USING(mem_num) " +sub_sql;
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				if(keyword!=null&& !"".equals(keyword)) {
					pstmt.setString(1, "%"+keyword+"%");
					
				}
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
		//글목록(검색글 목록)
		public List<RequestBoardVO> getListBoard(int start, int end,
				             String keyfield, String keyword)
		                                   throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<RequestBoardVO> list = null;
			String sql = null;
			String sub_sql = "";//검색시 사용
			int cnt=0;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				
				if(keyword !=null&&!"".equals(keyword)) {
					//검색글 보기
					if(keyfield.equals("1"))sub_sql+="where req_title like ?";
					else if(keyfield.equals("2"))sub_sql+="where m.id like ?";
					else if(keyfield.equals("3"))sub_sql+="where req_content like ?";
				}
				
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM request_board b JOIN "
					+ "member m USING(mem_num) " + sub_sql + " ORDER BY b.req_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				if(keyword !=null && !"".equals(keyword)) {
					pstmt.setString(++cnt,"%" + keyword + "%" );
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				//SQL문을 실행해서 결과행들을 ResultSet에 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<RequestBoardVO>();
				while(rs.next()) {
					RequestBoardVO board = new RequestBoardVO();
					board.setReq_num(rs.getInt("req_num"));
					board.setMem_num(rs.getInt("mem_num"));
					board.setId(rs.getString("id"));
					board.setReq_title(StringUtil.useNoHtml(rs.getString("req_title")));
					board.setReq_hit(rs.getInt("req_hit"));
					board.setReq_reg_date(rs.getDate("req_reg_date"));
					
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
		public RequestBoardVO getBoard(int req_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			RequestBoardVO board = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM request_board b JOIN member m "
					+ "USING(mem_num) JOIN member_detail d "
					+ "USING(mem_num) WHERE b.req_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, req_num);
				//SQL문을 실행해서 결과행을 ResultSet에 담음
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					board = new RequestBoardVO();
					board.setReq_num(rs.getInt("req_num"));
					board.setReq_title(rs.getString("req_title"));
					board.setReq_content(rs.getString("req_content"));
					board.setReq_hit(rs.getInt("req_hit"));
					board.setReq_reg_date(rs.getDate("req_reg_date"));
					board.setReq_modify_date(rs.getDate("req_modify_date"));
					board.setReq_filename(rs.getString("req_filename"));
					board.setMem_num(rs.getInt("mem_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return board;
		}
		//조회수 증가
		public void updateReadcount(int req_num)
				                       throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "UPDATE request_board SET req_hit=req_hit+1 WHERE req_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터를 바인딩
				pstmt.setInt(1, req_num);
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//파일 삭제
		public void deleteFile(int request_board_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "UPDATE request_board SET filename='' WHERE req_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터를 바인딩
				pstmt.setInt(1, request_board_num);
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//글수정
		public void updateBoard(RequestBoardVO board)throws Exception{
			System.out.println("~~~~~~~~~~~~~~~~" + board);
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			String sub_sql = "";
			int cnt=0;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//전송된 파일 여부 체크
				if(board.getReq_filename()!=null) {
					sub_sql+=",req_filename=?";
				}
				
				//SQL문 작성
				sql = "UPDATE request_board SET req_title=?,req_content=?,"
						+"req_modify_date=SYSDATE" + sub_sql
						+",req_ip=? WHERE req_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터를 바인딩
				pstmt.setString(++cnt, board.getReq_title());
				pstmt.setString(++cnt, board.getReq_content());
				if(board.getReq_filename()!=null) {
					pstmt.setString(++cnt, board.getReq_filename());
				}
				pstmt.setString(++cnt, board.getReq_ip());
				pstmt.setInt(++cnt, board.getReq_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//글삭제
		public void deleteBoard(int request_board_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//오토커밋 해체
				conn.setAutoCommit(false);
				//부모글 삭제
				sql="delete from request_board where req_num=?";
				pstmt3=conn.prepareStatement(sql);
				pstmt3.setInt(1, request_board_num);
				pstmt3.executeUpdate();
				
				//예외 없이 sql문 실행
				conn.commit();
							
			}catch(Exception e) {
				//예외발생
				conn.rollback();
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt3, null);
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(null, pstmt, conn);
			}
		}

}
