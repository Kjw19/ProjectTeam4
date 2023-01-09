package kr.requestBoard.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.requestBoard.dao.RequestCheerDAO;
import kr.requestBoard.vo.RequestCheerVO;
import kr.util.PagingUtil;

public class CheerListAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 페이지 번호 반환
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1"; // 첫 목록 페이지(cheerList.do)

		RequestCheerDAO dao = RequestCheerDAO.getInstance();
		int count = dao.getCheerBoardCount();

		// 페이지 처리
		// (currentPage,count,rowCount,pageCount,url)
		// pageNum : 문자열
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,10,10,"cheerList.do");

		List<RequestCheerVO> list = null;
		if(count>0) {
			list = dao.getCheerList(page.getStartRow(), page.getEndRow());
		}

		request.setAttribute("list", list); // 전체 데이터를 가져옴
		request.setAttribute("count", count); // 조건 체크할 때 필요
		request.setAttribute("pagingHtml", page.getPage());

		// JSP 경로 반환
		return "/WEB-INF/views/requestboard/cheerList.jsp";
	}
}