package kr.fundBoard.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.fundBoard.dao.FundBoardDAO;
import kr.fundBoard.vo.FundBoardVO;
import kr.util.FileUtil;
import kr.util.PagingUtil;

public class ListAction implements Action{


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		FundBoardDAO dao = FundBoardDAO.getInstance();
		int count = dao.getFundBoardCount(keyfield, keyword);
		
		//페이지 처리
		//keyfield,keyword,currentPage,count,rowCount,
		//pageCount,url
		PagingUtil page = 
				new PagingUtil(keyfield,keyword,
						      Integer.parseInt(pageNum),
						          count,20,10,"list.do");
		List<FundBoardVO> list = null;
		if(count > 0) {
			list = dao.getListFundBoard(page.getStartRow(),
					                page.getEndRow(),
					                keyfield,keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/fundboard/list.jsp";
	}

}
