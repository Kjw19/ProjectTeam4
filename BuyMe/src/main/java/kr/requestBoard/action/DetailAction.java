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
				int board_num = Integer.parseInt(
						       request.getParameter("board_num"));
				
				RequestBoardDAO dao = RequestBoardDAO.getInstance();
				//조회수 증가
				dao.updateReadcount(board_num);
				
				RequestBoardVO board = dao.getBoard(board_num);
				
				//HTML 태그를 허용하지 않음
				board.setReq_title(StringUtil.useNoHtml(board.getReq_title()));
				//HTML 태그를 허용하지 않으면서 줄바꿈 처리
				board.setReq_content(
						StringUtil.useBrNoHtml(board.getReq_content()));
			
				request.setAttribute("board", board);
				
				return "/WEB-INF/views/board/detail.jsp";
		
	}
}
