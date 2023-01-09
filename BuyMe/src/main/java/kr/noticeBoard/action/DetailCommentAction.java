package kr.noticeBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.noticeBoard.vo.NoticeBoardCommentVO;
import kr.noticeBoardComment.dao.NoticeBoardCommentDAO;

public class DetailCommentAction implements Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			int noti_num = Integer.parseInt(request.getParameter("noti_num"));
			
			NoticeBoardCommentDAO dao = NoticeBoardCommentDAO.getInstance();
			NoticeBoardCommentVO notice = dao.getNoticeBoardComment(noti_num);
			
			request.setAttribute("notice", notice);
			
		return "/WEB-INF/views/noticeboard/detail.jsp";
	}
}
