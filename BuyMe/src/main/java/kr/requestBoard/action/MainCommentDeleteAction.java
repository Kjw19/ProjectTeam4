package kr.requestBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.requestBoard.dao.RequestMainCommentDAO;

public class MainCommentDeleteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		// 회원제 서비스 : 로그인 된 상태에서 처리
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		// 로그인 된 경우
		int mainComment_num = Integer.parseInt(request.getParameter("mainComment_num"));
		RequestMainCommentDAO dao = RequestMainCommentDAO.getInstance();
		RequestMainCommentVO db_mainComment = dao.getMainCommentDetailBoard(mainComment_num);
		if(user_num!=db_mainComment.getMem_num()) {
			// 로그인 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/requestboard/mainCommentDetailList.jsp";
		}
		// 로그인한 회원번호와 작성자 회원번호가 일치
		 */
		
		int mainComment_num = Integer.parseInt(request.getParameter("mainComment_num"));
		RequestMainCommentDAO dao = RequestMainCommentDAO.getInstance();
		dao.MainCommentDeleteBoard(mainComment_num);
		
		return "redirect:/requestBoard/mainCommentDetailList.do";
	}
}