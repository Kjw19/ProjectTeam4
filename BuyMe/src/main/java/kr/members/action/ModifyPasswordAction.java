package kr.members.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.members.dao.MemberDAO;
import kr.members.vo.MemberVO;

public class ModifyPasswordAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인이 되지 않은 경우
			return "redirect:/member/loginForm.jsp";
		}
		// 로그인 된 경우
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		// 전송된 데이터 반환 : 입력한 데이터
		String email = request.getParameter("email"); // 이메일
		String id = request.getParameter("id"); // 아이디
		String origin_passwd = request.getParameter("origin_passwd"); // 현재 비밀번호
		String passwd = request.getParameter("passwd"); // 새 비밀번호
		
		// 현재 로그인한 아이디 : 세션 내 아이디
		String user_id = (String)session.getAttribute("user_id");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(id);
		boolean check = false;
		
		// 사용자가 입력한 아이디가 존재하고 로그인한 아이디와 일치하는지 체크
		if(member!=null && id.equals(user_id)) {
			// 비밀번호 일치 여부 체크
			check = member.isCheckedPassword(origin_passwd);
		}
		
		if(check) { // 인증 성공
			// 비밀번호 변경
			dao.updatePassword(passwd, user_num); // 새 비밀번호, pk
		}
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/members/modifyPassword.jsp";
	}
}