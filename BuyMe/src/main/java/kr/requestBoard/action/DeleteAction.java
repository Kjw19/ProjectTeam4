package kr.requestBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.controller.Action;
import kr.requestBoard.dao.RequestBoardDAO;
import kr.requestBoard.vo.RequestBoardVO;
import kr.util.FileUtil;
  
public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		int req_num = Integer.parseInt(
				       request.getParameter("req_num"));
		RequestBoardDAO dao = RequestBoardDAO.getInstance();
		RequestBoardVO db_board = dao.getBoard(req_num);
		if(user_num != db_board.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		dao.deleteBoard(req_num);
		//파일 삭제
		FileUtil.removeFile(request, db_board.getReq_filename());
		
		return "redirect:/requestBoard/list.do";
	}

}
