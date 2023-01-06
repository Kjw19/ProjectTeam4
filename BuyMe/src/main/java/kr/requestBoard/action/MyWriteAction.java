package kr.requestBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.requestBoard.dao.RequestInquiryDAO;
import kr.requestBoard.vo.RequestInquiryVO;
import kr.util.FileUtil;

public class MyWriteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		// 로그인 체크 : 문의를 남기는 것은 회원제 서비스
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		*/
		
		// 로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		RequestInquiryVO myInquiry = new RequestInquiryVO();
		myInquiry.setInqu_title(multi.getParameter("inqu_title"));
		myInquiry.setInqu_content(multi.getParameter("inqu_content"));
		myInquiry.setInqu_ip(request.getRemoteAddr());
		myInquiry.setInqu_filename(multi.getFilesystemName("inqu_filename"));
		//myInquiry.setMem_num(user_num); // 세션에서 가져온 데이터
		
		RequestInquiryDAO dao = RequestInquiryDAO.getInstance();
		dao.insertMyInquiryBoard(myInquiry);
		
		return "/WEB-INF/views/testRequestBoard/myWrite.jsp";
	}
}