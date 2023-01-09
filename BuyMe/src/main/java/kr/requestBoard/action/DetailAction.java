package kr.requestBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.requestBoard.dao.RequestBoardDAO;
import kr.requestBoard.vo.RequestBoardVO;
import kr.util.StringUtil;
  
public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//글번호 반환
				int req_num = Integer.parseInt(
						       request.getParameter("req_num"));
				
				RequestBoardDAO dao = RequestBoardDAO.getInstance();
				//조회수 증가
				dao.updateReadcount(req_num);
				
				RequestBoardVO request_board = dao.getBoard(req_num);
				
				//HTML 태그를 허용하지 않음
				request_board.setReq_title(StringUtil.useNoHtml(request_board.getReq_title()));
				//HTML 태그를 허용하지 않으면서 줄바꿈 처리
				request_board.setReq_content(
						StringUtil.useBrNoHtml(request_board.getReq_content()));
			
				request.setAttribute("request_board", request_board);
				
				return "/WEB-INF/views/requestboard/detail.jsp";
		
	}
}
