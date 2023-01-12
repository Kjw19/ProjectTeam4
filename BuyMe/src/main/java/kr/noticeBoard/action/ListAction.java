package kr.noticeBoard.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.noticeBoard.dao.NoticeBoardDAO;
import kr.noticeBoard.vo.NoticeBoardVO;
import kr.util.PagingUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_auth=(Integer)session.getAttribute("user_auth");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		NoticeBoardDAO dao = NoticeBoardDAO.getInstance();
		int count = dao.getNoticeBoardCount(keyfield, keyword);
		
		//페이지 처리
		//keyfield,keyword,currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(keyfield,keyword,Integer.parseInt(pageNum),count,20,10,"list.do");
		List<NoticeBoardVO> list = null;
		if(count > 0) {
			list = dao.getListNoticeBoard(page.getStartRow(), page.getEndRow(), keyfield,keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/noticeboard/list.jsp";
	}

}
