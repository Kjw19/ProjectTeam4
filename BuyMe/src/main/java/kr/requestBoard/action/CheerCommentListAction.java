package kr.requestBoard.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.requestBoard.dao.RequestCheerCommentDAO;
import kr.requestBoard.vo.RequestCheerCommentVO;
import kr.util.PagingUtil;

public class CheerCommentListAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) {
			pageNum = "1";
		}
		
		int cheer_num = Integer.parseInt(request.getParameter("cheer_num"));
		
		RequestCheerCommentDAO dao = RequestCheerCommentDAO.getInstance();
		int count = dao.getCheerCountBoard(cheer_num);
		
		int rowCount = 5;
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, rowCount, 1, null);
		
		List<RequestCheerCommentVO> list = null;
		if(count>0) {
			list = dao.getListCheerCommentBoard(page.getStartRow(), page.getEndRow(), cheer_num);
		}else {
			list = Collections.emptyList();
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