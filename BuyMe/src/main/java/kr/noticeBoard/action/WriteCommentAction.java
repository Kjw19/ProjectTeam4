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

public class WriteCommentAction implements Action {
 @Override
public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	Map<String,String> mapAjax = new HashMap<String,String>();
	
	HttpSession session = request.getSession();
	Integer user_num = (Integer)session.getAttribute("user_num");
	
	if(user_num == null) {//로그인 되지 않은 경우
		mapAjax.put("result", "logout");
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		NoticeBoardCommentVO comment = new NoticeBoardCommentVO();
		comment.setMem_num(user_num);
		comment.setComm_content(request.getParameter("comm_content"));
		comment.setNotice_num(Integer.parseInt(request.getParameter("notice_num")));
		
		NoticeBoardCommentDAO dao = NoticeBoardCommentDAO.getInstance();
		dao.insertNoticeBoardComment(comment);
		
		mapAjax.put("result", "success");
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
	}
	
	
	return "/WEB-INF/views/common/ajax_view.jsp";
}
}
