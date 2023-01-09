package kr.fundBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.fundBoard.dao.FundCommentDAO;
import kr.fundBoard.vo.FundCommentVO;

public class DetailCommentAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//글번호 반환
		int fund_num = Integer.parseInt(
				       request.getParameter("fund_num"));
		
		FundCommentDAO dao = FundCommentDAO.getInstance();
		
		FundCommentVO fund = dao.getFundComment(fund_num);
		
		request.setAttribute("fund", fund);
		
		return "/WEB-INF/views/fundBoard/fundComment.jsp";
	}

}
