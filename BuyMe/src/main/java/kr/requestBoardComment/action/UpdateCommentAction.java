package kr.requestBoardComment.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.requestBoard.dao.RequestCommentDAO;
import kr.requestBoard.vo.RequestCommentVO;

public class UpdateCommentAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		// 댓글 번호
		int comm_num = Integer.parseInt(request.getParameter("comm_num"));
		
		RequestCommentDAO dao = RequestCommentDAO.getInstance();
		// 작성자의 회원번호 구하기
		RequestCommentVO db_comment = dao.getRequestComment(comm_num);
		
		// 로그인 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		if(user_num==null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_num==db_comment.getMem_num()) {
			// 로그인이 되어 있고, 로그인한 회원번호와 작성자 회원번호가 일치
			RequestCommentVO comment = new RequestCommentVO();
			comment.setComment_num(comm_num);
			comment.setComm_content(request.getParameter("comm_content"));
			
			// 댓글 수정
			dao.updateRequestComment(comment);
			
			mapAjax.put("result", "success");
		}else { // 로그인이 되어 있고, 로그인한 회원번호와 작성자 회원번호가 불일치
			mapAjax.put("result", "wrongAccess");
		}
		
		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}