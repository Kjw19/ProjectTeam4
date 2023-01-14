package kr.fundBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.fundBoard.dao.FundBoardDAO;
import kr.fundBoard.vo.FundBoardVO;
import kr.util.StringUtil;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//글번호 반환
		int fund_num = Integer.parseInt(
						       request.getParameter("fund_num"));
				
		FundBoardDAO dao = FundBoardDAO.getInstance();
		//조회수 증가
		dao.updateReadcount(fund_num);
				
		FundBoardVO fund = dao.getFundBoard(fund_num);
				
		//HTML 태그를 허용하지 않음
		fund.setFund_title(StringUtil.useNoHtml(fund.getFund_title()));
		//HTML 태그를 허용하지 않으면서 줄바꿈 처리
		fund.setFund_content(
					StringUtil.useBrNoHtml(fund.getFund_content()));
			
		request.setAttribute("fund", fund);
				
		return "/WEB-INF/views/fundBoard/detail.jsp";
	}

}
