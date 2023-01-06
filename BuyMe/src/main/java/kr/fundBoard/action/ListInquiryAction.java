package kr.fundBoard.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import kr.controller.Action;
import kr.fundBoard.dao.FundInquiryDAO;
import kr.fundBoard.vo.FundInquiryVO;
import kr.util.PagingUtil;

//펀딩문의게시판 문의목록
public class ListInquiryAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		
		FundInquiryDAO dao = FundInquiryDAO.getInstance();
		int count = dao.getFundInquCount();
		
		int fund_num = Integer.parseInt(
		         request.getParameter("fund_num"));
		
		//페이지 처리
		PagingUtil page = 
				new PagingUtil(Integer.parseInt(pageNum),count,20,10,"list.do");
		
		List<FundInquiryVO> list = null;
		if(count > 0) {
			list = dao.getListFundInquiry(page.getStartRow(), page.getEndRow(), fund_num);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/board/list.jsp";
	}
}

