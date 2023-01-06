package kr.requestBoardComment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.requestBoard.dao.RequestBoardDAO;
import kr.requestBoard.vo.RequestBoardVO;
import kr.util.StringUtil;

public class CommentWriteFormAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 글번호 반환
		int req_num = Integer.parseInt(request.getParameter("req_num"));

		RequestBoardDAO dao = RequestBoardDAO.getInstance();
		RequestBoardVO req = dao.getBoard(req_num);

		// HTML 태그를 허용하지 않음
		req.setReq_title(StringUtil.useNoHtml(req.getReq_title()));
		// HTML 태그를 허용하지 않으면서 줄바꿈 처리
		req.setReq_content(StringUtil.useBrNoHtml(req.getReq_content()));
		
		return "/WEB-INF/views/requestBoardComment/writeCommentForm.jsp";
	}
}