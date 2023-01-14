package kr.requestBoard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.requestBoard.dao.RequestCommentDAO;
import kr.requestBoard.vo.RequestCommentVO;

public class CommentDeleteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// js에서 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		// 전송된 데이터 반환 : comment_num을 받으면 로그인 회원번호와 작성자 회원번호를 확인할 수 있다.
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		RequestCommentDAO dao = RequestCommentDAO.getInstance();
		// 작성자 회원번호 구하기
		RequestCommentVO db_comment = dao.getRequestComment(comment_num);
		
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num==null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_auth==5) {
			// 로그인이 되어 있고, 회번번호가 관리자 회원번호일 경우
			dao.deleteRequestComment(comment_num);
			
			mapAjax.put("result", "success");
		}

		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		// JSP에 전달
		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}