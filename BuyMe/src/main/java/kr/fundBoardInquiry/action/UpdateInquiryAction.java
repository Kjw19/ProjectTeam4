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

public class UpdateInquiryAction implements Action{

	//펀딩문의 수정
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//댓글 번호
		int inquiry_num = Integer.parseInt(
				          request.getParameter("inquiry_num"));
		
		FundInquiryDAO dao = FundInquiryDAO.getInstance();
		//작성자의 회원번호 구하기
		FundInquiryVO db_reply = dao.getFundInquiry(inquiry_num);
		
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		
		Map<String,String> mapAjax = 
				           new HashMap<String,String>();
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num!=null && 
				      user_num == db_reply.getMem_num()) {
			//로그인이 되어 있고 로그인한 회원번호와 작성자 회원번호 일치
			FundInquiryVO inqu = new FundInquiryVO();
			inqu.setInquiry_num(inquiry_num);
			inqu.setInqu_content(
					request.getParameter("inqu_content"));
			
			//댓글 수정
			dao.updateFundInquiry(inqu);
			
			mapAjax.put("result", "success");
		}else {//로그인이 되어 있고 로그인한 회원번호와 작성자 회원번호 불일치
			mapAjax.put("result", "wrongAccess");			
		}
		
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}


}
