package kr.noticeBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.noticeBoard.vo.NoticeBoardCommentVO;
import kr.noticeBoardComment.dao.NoticeBoardCommentDAO;
import kr.util.PagingUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ListCommentAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		
		if(pageNum == null ) {
			pageNum = "1";
		}
		 
		int noti_num = Integer.parseInt(request.getParameter("noti_num"));
		
		NoticeBoardCommentDAO dao = NoticeBoardCommentDAO.getInstance();
		int count = dao.getNoticeBoardCommentCount(noti_num);
		
		/*
		 * ajax 방식으로 목록을 표시하기 때문에 PagingUtil은 페이지수
		 * 표시가 목적이 아니라 목록 데이터 페이지 처리를 위한 rownum
		 * 번호를 구하는 것이 목적임
		 */
		
		int rowCount = 10;
		//currentPage, count, rowCount, pageCount, url
		
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, rowCount, 1, null);
		
		List<NoticeBoardCommentVO> list = null;
		if(count > 0) {
			list = dao.getListNoticeBoardComment(page.getStartRow(), page.getEndRow(), noti_num);
		}
		else {
			list = Collections.emptyList(); //빈 List 생성
		}
		
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list", list);
		//로그인한 사람이 작성자인지 체크하기 위해 전송
		mapAjax.put("user_num", user_num);
		
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
