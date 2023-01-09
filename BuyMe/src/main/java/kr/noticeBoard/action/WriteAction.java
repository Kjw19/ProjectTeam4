package kr.noticeBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.noticeBoard.dao.NoticeBoardDAO;
import kr.noticeBoard.vo.NoticeBoardVO;
import kr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth<5) {//관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		
		//로그인 된 경우
		MultipartRequest multi = 
				             FileUtil.createFile(request);
		NoticeBoardVO noticeboard = new NoticeBoardVO();
		noticeboard.setNoti_title(multi.getParameter("noti_title"));
		noticeboard.setNoti_content(multi.getParameter("noti_content"));
		noticeboard.setNoti_filename(multi.getFilesystemName("noti_filename"));
		noticeboard.setNoti_ip(request.getRemoteAddr());
		noticeboard.setMem_num(user_num);
		
		NoticeBoardDAO dao = NoticeBoardDAO.getInstance();
		dao.insertNoticeBoard(noticeboard);
		
		return "/WEB-INF/views/noticeboard/write.jsp";
	}

}
