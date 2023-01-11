package kr.members.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.members.dao.MemberDAO;
import kr.members.vo.MemberVO;

public class ModifyMyInfoDetailAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인 되지 않은 경우
			return "redirect:/members/loginForm.do";
		}
		// 로그인 된 경우
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		// 자바빈(VO)를 생성, 전송된 데이터를 저장
		MemberVO member = new MemberVO();
		member.setMem_num(user_num); // 회원번호
		member.setName(request.getParameter("name"));
		member.setEmail(request.getParameter("email"));
		member.setId(request.getParameter("id"));
		member.setPasswd(request.getParameter("passwd"));
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.updateMember(member);
		
		return "/WEB-INF/views/members/modifyMyInfoDetail.jsp";
	}
}