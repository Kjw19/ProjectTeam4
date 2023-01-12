package kr.fundBoardInquiry.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.fundBoardInquiry.dao.FundInquiryDAO;
import kr.fundBoardInquiry.vo.FundInquiryVO;


// 펀딩 문의 글 삭제
public class DeleteInquiryAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int inquiry_num = Integer.parseInt(
				request.getParameter("inquiry_num"));
		
		Map<String,String> mapAjax = 
				            new HashMap<String,String>();
		FundInquiryDAO dao = FundInquiryDAO.getInstance();
		//작성자 회원번호 구하기
		FundInquiryVO db_reply = dao.getFundInquiry(inquiry_num);
		
		HttpSession session = request.getSession();
		Integer user_num= 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num!=null 
				&& user_num == db_reply.getMem_num()) {
			//로그인이 되어 있고 로그인 한 회원번호와 작성자 회원번호가
			//일치
			dao.deleteFundInquiry(inquiry_num);
			
			mapAjax.put("result", "success");
		}else {
			//로그인이 되어 있고 회원번호와 작성자 회원번호가 불일치
			mapAjax.put("result","wrongAccess");
		}
		
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
				
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
