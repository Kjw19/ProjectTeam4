package kr.requestBoard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.requestBoard.dao.RequestCheerCommentDAO;
import kr.requestBoard.vo.RequestCheerCommentVO;

public class CheerCommentUpdateAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		int cheerComment_num = Integer.parseInt(request.getParameter("cheerComment_num"));

		RequestCheerCommentDAO dao = RequestCheerCommentDAO.getInstance();
		// 작성자의 회원번호 구하기
		RequestCheerCommentVO db_cheerComment = dao.getCheerCommentDetailBoard(cheerComment_num);

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");

		Map<String, String> mapAjax = new HashMap<String, String>();
		if(user_num==null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_num==db_cheerComment.getMem_num()) {
			// 로그인이 되어 있고, 로그인 회원번호와 작성자 회원번호가 일치
			RequestCheerCommentVO cheerComment = new RequestCheerCommentVO();
			cheerComment.setCheerComment_num(cheerComment_num);
			cheerComment.setCheerComm_title(request.getParameter("cheerComm_title"));
			cheerComment.setCheerComm_content(request.getParameter("cheerComm_content"));
			cheerComment.setCheerComm_filename(request.getParameter("cheerComm_filename"));

			// 댓글 수정
			dao.updateCheerCommentBoard(cheerComment);

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