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

public class WriteCommentAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) { //로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			//인코딩 처리
			request.setCharacterEncoding("utf-8");
			
			//데이터 기입
			FundCommentVO comm = new FundCommentVO();
			comm.setMem_num(user_num);
			comm.setComm_content(request.getParameter("comm_content"));
			comm.setFund_num(Integer.parseInt(request.getParameter("fund_num")));
			
			FundCommentDAO dao = FundCommentDAO.getInstance();
			dao.insertFundComment(comm);
			
			//성공시 success 전송
			mapAjax.put("result", "success");
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = 
				mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
