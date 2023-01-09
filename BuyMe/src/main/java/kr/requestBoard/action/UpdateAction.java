package kr.requestBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;


import kr.controller.Action;
import kr.requestBoard.dao.RequestBoardDAO;
import kr.requestBoard.vo.RequestBoardVO;
import kr.util.FileUtil;
  
public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		MultipartRequest multi = 
				            FileUtil.createFile(request);
		int board_num = Integer.parseInt(
				          multi.getParameter("board_num"));
		String filename = multi.getFilesystemName("filename");
		
		RequestBoardDAO dao = RequestBoardDAO.getInstance();
		//수정전 데이터 호출
		RequestBoardVO db_board = dao.getBoard(board_num);
		if(user_num != db_board.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			FileUtil.removeFile(request, filename);//업로드된 파일이 있으면 파일 삭제
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		RequestBoardVO board = new RequestBoardVO();
		board.setReq_num(user_num);
		board.setReq_title(multi.getParameter("title"));
		board.setReq_content(multi.getParameter("content"));
		board.setReq_ip(request.getRemoteAddr());
		board.setReq_filename(filename);
		
		dao.updateBoard(board);
		
		if(filename!=null) {
			//새파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, 
					             db_board.getReq_filename());
		}
		
		return "redirect:/board/detail.do?board_num="+board_num;
	}
}
