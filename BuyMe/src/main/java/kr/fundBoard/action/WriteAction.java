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
		FundBoardVO fund = new FundBoardVO();
		fund.setFund_title(multi.getParameter("fund_title"));
		fund.setCategory_num(Integer.parseInt(multi.getParameter("category_num")));
		fund.setFund_content(multi.getParameter("fund_content"));
		fund.setFund_ip(request.getRemoteAddr());
		fund.setFund_filename(multi.getFilesystemName("fund_filename"));
		fund.setMem_num(user_num);
		
		FundBoardDAO dao = FundBoardDAO.getInstance();
		dao.insertBoard(fund);
		
		return "/WEB-INF/views/fundBoard/write.jsp";
	}
}
