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

public class CommentWriteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();

		// 로그인 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else { // 로그인이 된 경우
			request.setCharacterEncoding("utf-8"); // 전송된 데이터 인코딩 처리

			RequestCommentVO comment = new RequestCommentVO();
			comment.setMem_num(user_num); // 회원번호(댓글 작성자)
			comment.setComm_content(request.getParameter("comm_content"));
			comment.setComm_ip(request.getRemoteAddr());
			comment.setReq_num(Integer.parseInt(request.getParameter("req_num")));

			RequestCommentDAO dao = RequestCommentDAO.getInstance();
			dao.insertCommentBoard(comment);

			mapAjax.put("result", "success"); // 정상적으로 처리될 시
		}

		// JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);	

		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}