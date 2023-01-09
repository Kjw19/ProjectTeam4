package kr.fundBoardComment.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;


import kr.controller.Action;
import kr.fundBoardComment.dao.FundCommentDAO;
import kr.fundBoardComment.vo.FundCommentVO;

//Fund게시판 댓글 삭제
public class DeleteCommentAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int comment_num = Integer.parseInt(
				request.getParameter("comment_num"));
		
		Map<String,String> mapAjax = 
				            new HashMap<String,String>();
		FundCommentDAO dao = FundCommentDAO.getInstance();
		//작성자 회원번호 구하기
		FundCommentVO db_reply = dao.getFundComment(comment_num);
		
		HttpSession session = request.getSession();
		Integer user_num= (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
			
		}else if(user_num!=null && user_num == db_reply.getMem_num()) {
			//로그인이 되어 있고 로그인 한 회원번호와 작성자 회원번호가 일치하는 경우
			dao.deleteFundComment(comment_num);
			
			mapAjax.put("result", "success");
		}else {
			
			//로그인이 되어 있고 회원번호와 작성자 회원번호가 불일치하는 경우
			mapAjax.put("result","wrongAccess");
		}
		
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
				
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
