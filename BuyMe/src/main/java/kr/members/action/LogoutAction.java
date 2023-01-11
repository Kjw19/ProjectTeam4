package kr.members.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class LogoutAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.invalidate(); // 로그아웃 처리
		
		// JSP 경로 반환
		return "redirect:/main/main.do";
	}
}