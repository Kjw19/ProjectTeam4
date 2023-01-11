package kr.noticeBoard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;

import kr.noticeBoard.vo.NoticeBoardCommentVO;

import kr.noticeBoardComment.dao.NoticeBoardCommentDAO;

public class UpdateCommentAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
	
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
	

		NoticeBoardCommentDAO dao = NoticeBoardCommentDAO.getInstance();
		//작성자의 회원번호 구하기 
		NoticeBoardCommentVO db_comment = dao.getNoticeBoardComment(comment_num);
	

		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("user_num");

		Map<String,String> mapAjax = new HashMap<String,String>();

		if (user_num == null) {
			mapAjax.put("result", "logout");
		} else if (user_num != null && user_num == db_comment.getMem_num()) {
			
			NoticeBoardCommentVO comment = new NoticeBoardCommentVO();
			comment.setCommnet_num(comment_num);
			comment.setComm_content(request.getParameter("comm_content"));

			// 댓글 수정
			dao.updateNoticeBoardComment(comment);

			mapAjax.put("result", "success");
		} else {
			mapAjax.put("result", "wrongAccess");
		}

		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_jsp";
	}
}
