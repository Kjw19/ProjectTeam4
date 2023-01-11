package kr.fundBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.fundBoard.dao.FundBoardDAO;
import kr.fundBoard.vo.FundBoardVO;
import kr.util.FileUtil;

public class WriteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		MultipartRequest multi = 
				             FileUtil.createFile(request);
		FundBoardVO board = new FundBoardVO();
		board.setFund_title(multi.getParameter("fund_title"));
		board.setFund_content(multi.getParameter("fund_content"));
		board.setFund_ip(request.getRemoteAddr());
		board.setFund_filename(multi.getFilesystemName("fund_filename"));
		board.setMem_num(user_num);
		
		FundBoardDAO dao = FundBoardDAO.getInstance();
		dao.insertBoard(board);
		
		return "/WEB-INF/views/fundboard/write.jsp";
	}
}
