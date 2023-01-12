package kr.members.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.members.dao.MemberDAO;
import kr.members.vo.MemberVO;
import kr.util.FileUtil;

public class DeleteUserAction implements Action{ 
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인이 되지 않은 경우
			return "redirect:/members/loginForm.jsp";
		}
		
		// 로그인 된 경우
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		// 전송된 데이터 반환
		String id = request.getParameter("id"); // 현재 아이디
		String passwd = request.getParameter("passwd"); // 현재 비밀번호
		String name = request.getParameter("name");
		
		String user_id = (String)session.getAttribute("user_id"); // 로그인한 아이디
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(id);
		boolean check = false;
		
		// 사용자가 입력한 아이디가 존재하고 로그인한 아이디와 일치하는지 체크
		if(member!=null && id.equals(user_id)) {
			// 비밀번호 일치 여부 체크
			check = member.isCheckedPassword(passwd); // member 지정된 비밀번호, passwd 입력한 비밀번호
		}
		
		if(check) { // 인증 성공
			// 회원정보 삭제
			dao.deleteMember(user_num); // pk
			// 프로필 사진 삭제 ← 프로필 작업 후 코드 작성
			FileUtil.removeFile(request, member.getPhoto());
			// 로그아웃
			session.invalidate();
		}
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/members/deleteUser.jsp";
	}
}