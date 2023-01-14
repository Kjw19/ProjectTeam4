package kr.fundBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fundBoard.dao.FundBoardDAO;
import kr.fundBoard.vo.FundBoardVO;
import kr.util.StringUtil;

public class UpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		//글번호 반환
		int fund_num = Integer.parseInt(
				request.getParameter("fund_num"));

		FundBoardDAO dao = FundBoardDAO.getInstance();
		FundBoardVO fund = dao.getFundBoard(fund_num);
		if(user_num!=fund.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/common/notice.jsp";			
		}
		
		//로그인이 되어 있고 로그인한 회원번호와 작성자 회원번호가 일치
		
		//제목에 큰 따옴표가 있으면 input 태그에 데이터를 표시할 때
		//오동작이 일어나기 때문에 " -> &quot; 로 변환
		fund.setFund_title(StringUtil.parseQuot(
							fund.getFund_title()));
		
		request.setAttribute("fund", fund);

		return "/WEB-INF/views/fundBoard/updateForm.jsp";
	}

}
