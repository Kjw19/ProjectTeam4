package kr.requestBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.requestBoard.dao.RequestCheerDAO;
import kr.requestBoard.vo.RequestCheerVO;

public class CheerDetailAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int cheer_num = Integer.parseInt(request.getParameter("cheer_num"));
		RequestCheerDAO dao = RequestCheerDAO.getInstance();
		RequestCheerVO cheerBoardVO = dao.getCheerBoard(cheer_num);
		
		//자바빈(VO)를 request에 저장
		request.setAttribute("cheerBoardVO", cheerBoardVO);
		
		//JSP 경로 반환
		return "/WEB-INF/views/requestboard/cheerDetail.jsp";
	}
}