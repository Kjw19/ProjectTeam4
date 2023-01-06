package kr.requestBoard.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.requestBoard.dao.RequestBoardDAO;
import kr.requestBoard.vo.RequestBoardVO;
import kr.util.PagingUtil;
  
public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		RequestBoardDAO dao = RequestBoardDAO.getInstance();
		int count = dao.getBoardCount(keyfield, keyword);
		
		
		PagingUtil page = 
				new PagingUtil(keyfield,keyword,
						      Integer.parseInt(pageNum),
						          count,20,10,"list.do");
		List<RequestBoardVO> list = null;
		if(count > 0) {
			list = dao.getListBoard(page.getStartRow(),
					                page.getEndRow(),
					                keyfield,keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/board/list.jsp";
	}		
}
