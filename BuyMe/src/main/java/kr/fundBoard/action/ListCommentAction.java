package kr.fundBoard.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.fundBoard.dao.FundCommentDAO;
import kr.fundBoard.vo.FundCommentVO;
import kr.util.PagingUtil;

//fund게시판 댓글 목록 불러오기
public class ListCommentAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int fund_num= Integer.parseInt(
				         request.getParameter("comment_num"));
		
		FundCommentDAO dao = FundCommentDAO.getInstance();
		int count = dao.getFundCommentCount(fund_num);
		
		/*
		 * ajax 방식으로 목록을 표시하기 때문에 PagingUtil은 페이지수
		 * 표시가 목적이 아니라 목록 데이터 페이지 처리를 위한 rownum
		 * 번호를 구하는 것이 목적임
		 */
		int rowCount = 10;

		//페이징 처리
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,rowCount,1,null);
		
		
		List<FundCommentVO> list = null;
		if(count > 0) {
			list = dao.getListFundComment(page.getStartRow(),
					                     page.getEndRow(),
					                     fund_num);
		}else {
			list = Collections.emptyList();//빈 List 생성
		}
		
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list", list);
		
		//로그인id가 작성자인지 체크
		mapAjax.put("user_num", user_num);
		
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";

	}

}
