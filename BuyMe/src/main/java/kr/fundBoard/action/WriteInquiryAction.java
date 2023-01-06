package kr.fundBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.controller.Action;
import kr.fundBoard.dao.FundInquiryDAO;
import kr.fundBoard.vo.FundInquiryVO;

public class WriteInquiryAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		FundInquiryVO inquiry = new FundInquiryVO();
		inquiry.setInqu_title(request.getParameter("title"));
		inquiry.setInqu_content(request.getParameter("content"));
		inquiry.setRe_inqu_is_ok(request.getParameter("re_inqu_is_ok"));
		inquiry.setMem_num(user_num);
		inquiry.setFund_num(Integer.parseInt("fund_num"));
		
		FundInquiryDAO dao = FundInquiryDAO.getInstance();
		dao.insertFundInquiry(inquiry);
		
		return "/WEB-INF/views/inquiry/write.jsp";
	}

	}
