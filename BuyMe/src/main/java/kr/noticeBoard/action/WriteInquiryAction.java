package kr.noticeBoard.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import kr.controller.Action;
import kr.noticeBoard.vo.NoticeInquiryVO;
import kr.noticeInquiry.dao.NoticeInquiryDAO;


public class WriteInquiryAction implements Action{
	@Override
	//공지사항 문의사항 작성
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
			request.setCharacterEncoding("utf-8");
			
			NoticeInquiryVO inquiry = new NoticeInquiryVO();
			inquiry.setInqu_title(request.getParameter("title"));			
			inquiry.setInqu_content(request.getParameter("content"));
			inquiry.setRe_inqu_is_ok(request.getParameter("re_inqu_is_ok"));
			inquiry.setMem_num(user_num);
			inquiry.setInquiry_num(Integer.parseInt(request.getParameter("notice_num")));
			
			NoticeInquiryDAO dao = NoticeInquiryDAO.getInstance();
			dao.insertNoticeInquiry(inquiry);
			
			return "/WEB-INF/views/inquiry/write.jsp";
}
		
	}
