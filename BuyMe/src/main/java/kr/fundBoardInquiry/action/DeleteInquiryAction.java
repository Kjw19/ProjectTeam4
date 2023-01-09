package kr.fundBoardInquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.controller.Action;
import kr.fundBoardComment.dao.FundInquiryDAO;
import kr.fundBoardComment.vo.FundInquiryVO;


// 펀딩 문의 글 삭제
public class DeleteInquiryAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		int inquiry_num = Integer.parseInt(request.getParameter("inquiry_num"));
		
		FundInquiryDAO dao = FundInquiryDAO.getInstance();
		FundInquiryVO db_inquiry = dao.getFundInquiry(inquiry_num);
		
		if(user_num != db_inquiry.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		dao.deleteFundInquiry(inquiry_num);
		
		return "redirect:/board/list.do";
	}
}
