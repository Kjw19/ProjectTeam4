package kr.requestBoard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.requestBoard.dao.RequestInquiryDAO;
import kr.requestBoard.vo.RequestInquiryVO;

public class InquiryUpdateAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// js에서 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		// 문의 번호
		int inquiry_num = Integer.parseInt(request.getParameter("inquiry_num"));

		RequestInquiryDAO dao = RequestInquiryDAO.getInstance();
		// 작성자의 회원번호 구하기
		RequestInquiryVO db_inquiry = dao.getRequestInquiry(inquiry_num);

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");

		Map<String, String> mapAjax = new HashMap<String, String>();
		if(user_num==null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_num==db_inquiry.getMem_num()) {
			// 로그인이 되어 있고, 로그인 회원번호와 작성자 회원번호가 일치
			RequestInquiryVO inquiry = new RequestInquiryVO();
			inquiry.setInquiry_num(inquiry_num);
			inquiry.setInqu_content(request.getParameter("inqu_content"));

			// 댓글 수정
			dao.updateRequestInquiry(inquiry);

			mapAjax.put("result", "success");
		}

		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		// JSP에 전달
		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}