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
						       request.getParameter("board_num"));
				
		FundBoardDAO dao = FundBoardDAO.getInstance();
		//조회수 증가
		dao.updateReadcount(fund_num);
				
		FundBoardVO board = dao.getFundBoard(fund_num);
				
		//HTML 태그를 허용하지 않음
		board.setFund_title(StringUtil.useNoHtml(board.getFund_title()));
		//HTML 태그를 허용하지 않으면서 줄바꿈 처리
		board.setFund_content(
					StringUtil.useBrNoHtml(board.getFund_content()));
			
		request.setAttribute("board", board);
				
		return "/WEB-INF/views/fundboard/detail.jsp";
	}

}
