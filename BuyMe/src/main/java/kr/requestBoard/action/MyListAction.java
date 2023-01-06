package kr.requestBoard.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.requestBoard.dao.RequestInquiryDAO;
import kr.requestBoard.vo.RequestInquiryVO;
import kr.util.PagingUtil;

public class MyListAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		// 검색창
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		RequestInquiryDAO dao = RequestInquiryDAO.getInstance();
		int count = dao.getRequestInquiryCount(keyfield, keyword);
		
		// 페이지 처리
		// (keyfield, keyword, currentPage, count, rowCount, pageCount, url)
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 20, 10, "list.do");
		List<RequestInquiryVO> list = null;
		if(count>0) {
			list = dao.getListRequestInquiry(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/testRequestBoard/myList.jsp";
	}
}