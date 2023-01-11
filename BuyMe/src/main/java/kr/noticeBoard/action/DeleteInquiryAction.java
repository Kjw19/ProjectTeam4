package kr.noticeBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.noticeBoard.vo.NoticeInquiryVO;
import kr.noticeInquiry.dao.NoticeInquiryDAO;

public class DeleteInquiryAction implements Action{
//공지사항 문의 삭제
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된경우
		int inquiry_num = Integer.parseInt(request.getParameter("inquiry_num"));
		
		NoticeInquiryDAO dao = NoticeInquiryDAO.getInstance();
		NoticeInquiryVO db_inquiry = dao.getNoticeInquiry(inquiry_num);
		
		if(user_num!=db_inquiry.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		//로그인한 회원번호와 작성자 회원번호가 일치
		dao.deleteNoticeInquiry(inquiry_num);
		return "redirect:/board/list.do";
	}

}
