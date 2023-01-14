package kr.fundBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.fundBoard.dao.FundBoardDAO;
import kr.fundBoard.vo.FundBoardVO;
import kr.util.FileUtil;

public class UpdateAction implements Action{

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
		int fund_num = Integer.parseInt(
				          multi.getParameter("fund_num"));
		String Fund_filename = multi.getFilesystemName("Fund_filename");
		
		FundBoardDAO dao = FundBoardDAO.getInstance();
		//수정전 데이터 호출
		FundBoardVO db_board = dao.getFundBoard(fund_num);
		if(user_num != db_board.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			FileUtil.removeFile(request, Fund_filename);//업로드된 파일이 있으면 파일 삭제
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		FundBoardVO fund = new FundBoardVO();
		fund.setFund_num(fund_num);
		fund.setCategory_num(Integer.parseInt(multi.getParameter("category_num")));
		fund.setFund_title(multi.getParameter("fund_title"));
		fund.setFund_content(multi.getParameter("fund_content"));
		fund.setFund_ip(request.getRemoteAddr());
		fund.setFund_filename(Fund_filename);
		
		dao.updateBoard(fund);
		
		if(Fund_filename!=null) {
			//새파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, 
					             db_board.getFund_filename());
		}
		
		return "redirect:/fundBoard/detail.do?fund_num="+fund_num;
	}

}
