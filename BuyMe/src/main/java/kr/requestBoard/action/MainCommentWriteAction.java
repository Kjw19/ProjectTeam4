package kr.requestBoard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.requestBoard.dao.RequestMainCommentDAO;
import kr.requestBoard.vo.RequestMainCommentVO;

public class MainCommentWriteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		// 댓글·응원 게시판 이용 : 회원제 서비스
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else { // 로그인이 된 경우
			// 전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			
			RequestMainCommentVO mainComment = new RequestMainCommentVO();
			mainComment.setMem_num(user_num); // 회원번호(댓글 작성자)
			mainComment.setMainComm_content(request.getParameter("mainComm_content"));
			mainComment.setMainComm_ip(request.getRemoteAddr());
			mainComment.setMainComment_num(Integer.parseInt(request.getParameter("mainComment_num")));
			
			RequestMainCommentDAO dao = RequestMainCommentDAO.getInstance();
			dao.insertMainCommentBoard(mainComment);
			
			// 정상적으로 처리될 시
			mapAjax.put("result", "success");
		}
		
		// JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		*/
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		//HttpSession session = request.getSession();
		//Integer user_num = (Integer)session.getAttribute("user_num");
		
		RequestMainCommentVO mainComment = new RequestMainCommentVO();
		//테스트용으로 사용하다가 연동하는 프로그램 구현히 제거할 것
		mainComment.setMem_num(100);
		//mainComment.setMem_num(user_num); // 회원번호(댓글 작성자)
		mainComment.setMainComm_content(request.getParameter("mainComm_content"));
		mainComment.setMainComm_ip(request.getRemoteAddr());
		//mainComment.setMainComment_num(Integer.parseInt(request.getParameter("mainComment_num")));
		
		RequestMainCommentDAO dao = RequestMainCommentDAO.getInstance();
		dao.insertMainCommentBoard(mainComment);	
		
		mapAjax.put("result", "success");
		
		// 나중에 경로 ajax_view로 변경
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}