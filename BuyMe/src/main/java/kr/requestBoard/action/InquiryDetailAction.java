package kr.requestBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.requestBoard.dao.RequestInquiryDAO;
import kr.requestBoard.vo.RequestInquiryVO;
import kr.util.StringUtil;

public class InquiryDetailAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 내 문의 번호 반환
		int inquiry_num = Integer.parseInt(request.getParameter("inquiry_num"));
		
		RequestInquiryDAO dao = RequestInquiryDAO.getInstance();
		RequestInquiryVO inquiry = dao.getDetailInquiryBoard(inquiry_num);
		// HTML 태그를 허용하지 않음
		inquiry.setInqu_title(StringUtil.useNoHtml(inquiry.getInqu_title()));
		// HTML 태그를 허용하지 않으면서 줄바꿈 처리
		inquiry.setInqu_content(StringUtil.useBrNoHtml(inquiry.getInqu_content()));
		
		request.setAttribute("inquiry", inquiry);
		
		return "/WEB-INF/views/testRequestBoard/inquiryDetail.jsp";
	}
}