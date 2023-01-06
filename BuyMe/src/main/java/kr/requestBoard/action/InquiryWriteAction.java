package kr.requestBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.requestBoard.dao.RequestInquiryDAO;
import kr.requestBoard.vo.RequestInquiryVO;
import kr.util.FileUtil;

public class InquiryWriteAction implements Action{
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
		myInquiry.setInqu_filename(multi.getFilesystemName("inqu_filename"));
		myInquiry.setInqu_ip(request.getRemoteAddr());
		
		//테스트용으로 사용하다가 연동하는 프로그램 구현히 제거할 것
		myInquiry.setReq_num(200);
		//myInquiry.setReq_num(Integer.parseInt(multi.getParameter("req_num")));
		
		//테스트용으로 사용하다가 연동하는 프로그램 구현히 제거할 것
		myInquiry.setMem_num(100);
		//myInquiry.setMem_num(user_num); // 세션에서 가져온 데이터
		
		RequestInquiryDAO dao = RequestInquiryDAO.getInstance();
		dao.insertInquiryBoard(myInquiry);
		
		return "/WEB-INF/views/testRequestBoard/inquiryWrite.jsp";
	}
}