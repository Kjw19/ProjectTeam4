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

public class DeleteCommentAction implements Action {
 @Override
public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 
	 request.setCharacterEncoding("utf-8");
	 
	 int comment_num = Integer.parseInt(request.getParameter("comment_num"));
	 
	 Map<String,String> mapAjax = new HashMap<String,String>();
	 
	 NoticeBoardCommentDAO dao = NoticeBoardCommentDAO.getInstance();
	 NoticeBoardCommentVO db_comment = dao.getNoticeBoardComment(comment_num);
	 
	 HttpSession session = request.getSession();
	 Integer user_num = (Integer)session.getAttribute("user_num");
	 
	 if(user_num == null) {
		 mapAjax.put("result", "logout");
	 }
	 else if (user_num != null && user_num == db_comment.getMem_num()) {
		 dao.deleteNoticeBoardComment(comment_num);
		 
		 mapAjax.put("result", "success");
	 }
	 else {
		 mapAjax.put("result", "wrongAccess");
	 }
	 ObjectMapper mapper = new ObjectMapper();
	 String ajaxData = mapper.writeValueAsString(mapAjax);
	 
	 request.setAttribute("ajaxData", ajaxData);
	 
	return "/WEB-INF/views/common/ajax_view.jsp";
}
}
