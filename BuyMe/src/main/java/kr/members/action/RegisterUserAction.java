package kr.members.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.members.dao.MemberDAO;
import kr.members.vo.MemberVO;

public class RegisterUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		// 자바빈(VO) 생성, 전송된 데이터 저장 ∵ 통합적 객체 관리
		MemberVO member = new MemberVO();
		member.setEmail(request.getParameter("email"));
		member.setName(request.getParameter("name"));
		member.setId(request.getParameter("id"));
		member.setPasswd(request.getParameter("passwd"));
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.insertMember(member);
		
		// JSP 경로 지정 : 정상적으로 처리될 시, registerUser.jsp로 이동
		return "/WEB-INF/views/members/registerUser.jsp";
	}
}