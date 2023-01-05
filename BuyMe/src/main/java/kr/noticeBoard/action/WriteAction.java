package kr.noticeBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.noticeBoard.dao.NoticeBoardDAO;
import kr.noticeBoard.vo.NoticeBoardVO;
import kr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//HttpSession session = request.getSession();
		//Integer user_num = 
				//(Integer)session.getAttribute("user_num");
		//if(user_num==null) {//로그인이 되지 않은 경우
			//return "redirect:/member/loginForm.do";
		//}
		
		//Integer user_auth = 
				//(Integer)session.getAttribute("user_auth");
		//if(user_auth<5) {//관리자로 로그인하지 않은 경우
			//return "/WEB-INF/views/common/notice.jsp";
		//}
		
		//테스트 해보려고 세션 주석 달아놨어요(관리자 회원가입 하고나서 수정할게요!)
		
		//로그인 된 경우
		MultipartRequest multi = 
				             FileUtil.createFile(request);
		NoticeBoardVO noticeboard = new NoticeBoardVO();
		noticeboard.setNoti_title(multi.getParameter("title"));
		noticeboard.setNoti_content(multi.getParameter("content"));
		noticeboard.setNoti_ip(request.getRemoteAddr());
		noticeboard.setNoti_filename(multi.getFilesystemName("filename"));
		//noticeboard.setMem_num(user_num);
		
		NoticeBoardDAO dao = NoticeBoardDAO.getInstance();
		dao.insertNoticeBoard(noticeboard);
		
		return "/WEB-INF/views/noticeboard/write.jsp";
	}

}
