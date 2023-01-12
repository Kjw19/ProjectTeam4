package kr.members.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.members.dao.MemberDAO;
import kr.members.vo.MemberVO;

public class DeleteUserFormAction implements Action{ 
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 탈퇴 역시 회원제 서비스
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인이 되지 않은 경우
			return "redirect:/members/loginForm.jsp";
		}
		// 로그인된 경우
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.getMember(user_num);
				
		request.setAttribute("member", member); // 자바빈 저장
		
		// 로그인 된 경우
		return "/WEB-INF/views/members/deleteUserForm.jsp";
	}
}