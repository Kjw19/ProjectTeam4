package kr.requestBoardComment.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.requestBoard.dao.RequestCommentDAO;
import kr.requestBoard.vo.RequestCommentVO;
import kr.util.PagingUtil;

public class CommentListAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리 : post 방식
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) {
			pageNum = "1";
		}
		
		int req_num = Integer.parseInt(request.getParameter("req_num"));
		
		RequestCommentDAO dao = RequestCommentDAO.getInstance();
		int count = dao.getRequestCommentCount(req_num);
		
		/*
		 	ajax 방식으로 목록을 표시하기 때문에 PagingUtil은 페이지수 표시가 목적이 아니라,
		 	목록 데이터 페이지 처리를 위한 rownum 번호를 구하는 것이 목적이다.
		 */
		// (currentPage, count, rowCount, pageCount, url)
		int rowCount = 5;
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, rowCount, 1, null);
		
		List<RequestCommentVO> list = null;
		if(count>0) {
			list = dao.getListRequestComment(page.getStartRow(), page.getEndRow(), req_num);
		}else { // null일 때 = 댓글이 없는 경우
			list = Collections.emptyList(); // 빈 List 생성 = 비어있는 배열로 인식
		}
		
		// 로그인 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list", list);
		// 로그인한 사람이 작성자인지 체크하기 위해서 전송
		mapAjax.put("user_num", user_num);
		
		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		// JSP에 전달
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}