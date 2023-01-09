package kr.fundBoardInquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.controller.Action;
import kr.fundBoardComment.dao.FundInquiryDAO;
import kr.fundBoardComment.vo.FundInquiryVO;

public class UpdateInquiryAction implements Action{

	//펀딩문의 수정
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		int inquiry_num = Integer.parseInt(request.getParameter("inquiry_num"));
		
		FundInquiryDAO dao = FundInquiryDAO.getInstance();
		//수정전 데이터 호출
		FundInquiryVO fund_inquiry = dao.getFundInquiry(inquiry_num);
		if(user_num != fund_inquiry.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		FundInquiryVO inquiry = new FundInquiryVO();
		inquiry.setInquiry_num(inquiry_num);
		inquiry.setInqu_title(request.getParameter("title"));
		inquiry.setInqu_content(request.getParameter("content"));
		inquiry.setRe_inqu_is_ok(request.getParameter("re_inqu_is_ok"));
		dao.updateFundInquiry(inquiry);
		
		
		return "redirect:/board/detail.do?board_num";
	}

}
