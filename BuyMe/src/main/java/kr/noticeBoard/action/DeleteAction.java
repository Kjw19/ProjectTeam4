package kr.noticeBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.noticeBoard.dao.NoticeBoardDAO;
import kr.noticeBoard.vo.NoticeBoardVO;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		int noti_num = Integer.parseInt(request.getParameter("noti_num"));
		NoticeBoardDAO dao = NoticeBoardDAO.getInstance();
		NoticeBoardVO db_noticeboard = dao.getNoticeBoard(noti_num);
		if(user_num != db_noticeboard.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		dao.deleteNoticeBoard(noti_num);
		//파일 삭제
		FileUtil.removeFile(request, db_noticeboard.getNoti_filename());
		
		return "redirect:/noticeboard/list.do";
	}

}
