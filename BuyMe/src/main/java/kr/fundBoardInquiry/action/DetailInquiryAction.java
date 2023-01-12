package kr.fundBoardInquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.fundBoard.vo.FundBoardVO;
import kr.fundBoardInquiry.dao.FundInquiryDAO;
import kr.fundBoardInquiry.vo.FundInquiryVO;

public class DetailInquiryAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//글번호 반환
		int fund_num = Integer.parseInt(
				       request.getParameter("fund_num"));
		
		FundInquiryDAO dao = FundInquiryDAO.getInstance();
		
		FundBoardVO fund = dao.getFund(fund_num);
		
		request.setAttribute("fund", fund);
		
		return "/WEB-INF/views/fundBoard/fundInquiry.jsp";
	}

}
