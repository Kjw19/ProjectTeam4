package kr.requestBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.requestBoard.dao.RequestCheerDAO;
import kr.requestBoard.vo.RequestCheerVO;
import kr.util.FileUtil;

public class CheerWriteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		// 회원제 서비스 : 로그인 체크
		HttpSession session = request.getSession();

		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		// 로그인 된 경우
		*/
		
		MultipartRequest multi = FileUtil.createFile(request);
		RequestCheerVO cheerBoard = new RequestCheerVO();
		cheerBoard.setCheer_content(multi.getParameter("cheer_content"));
		//cheerBoard.setCheer_ip(multi.getParameter("cheer_ip"));
		cheerBoard.setCheer_filename(multi.getParameter("cheer_filename"));
		//cheerBoard.setMem_num(user_num); // 세션에서 가져온 데이터
		// 테스트용
		cheerBoard.setMem_num(100);
		
		// RequestCheerDAO 객체를 생성해서 자바빈의 데이터를 RequestCheerDAO의 insertCheerBoard()에 넘긴다.
		RequestCheerDAO dao = RequestCheerDAO.getInstance();
		dao.insertCheerBoard(cheerBoard);

		return "/WEB-INF/views/requestboard/cheerWrite.jsp";
	}
}