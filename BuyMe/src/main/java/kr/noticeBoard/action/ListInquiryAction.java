package kr.noticeBoard.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import kr.controller.Action;
import kr.noticeBoard.vo.NoticeInquiryVO;
import kr.noticeInquiry.dao.NoticeInquiryDAO;
import kr.util.PagingUtil;

//펀딩문의게시판 문의목록
public class ListInquiryAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		
		NoticeInquiryDAO dao = NoticeInquiryDAO.getInstance();
		int count = dao.getNoticeInquCount();
		
		int notice_num = Integer.parseInt(
		         request.getParameter("notice_num"));
		
		//페이지 처리
		PagingUtil page = 
				new PagingUtil(Integer.parseInt(pageNum),count,20,10,"fundInquiry.do");
		
		List<NoticeInquiryVO> list = null;
		if(count > 0) {
			list = dao.getListNoticeInquiry(page.getStartRow(), page.getEndRow(), notice_num);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/fundBoard/fundInquiry.jsp";
	}
}