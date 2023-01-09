package kr.noticeBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.noticeBoard.dao.NoticeBoardDAO;
import kr.noticeBoard.vo.NoticeBoardVO;
import kr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		int noti_num = Integer.parseInt(multi.getParameter("noti_num"));
		String noti_filename = multi.getFilesystemName("noti_filename");
		
		NoticeBoardDAO dao = NoticeBoardDAO.getInstance();
		//수정전 데이터 호출
		NoticeBoardVO db_noticeboard = dao.getNoticeBoard(noti_num);
		if(user_num != db_noticeboard.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			FileUtil.removeFile(request, noti_filename);//업로드된 파일이 있으면 파일 삭제
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		NoticeBoardVO noticeboard = new NoticeBoardVO();
		noticeboard.setNoti_num(noti_num);
		noticeboard.setNoti_title(multi.getParameter("noti_title"));
		noticeboard.setNoti_content(multi.getParameter("noti_content"));
		noticeboard.setNoti_ip(request.getRemoteAddr());
		noticeboard.setNoti_filename(noti_filename);
		
		dao.updateNoticeBoard(noticeboard);
		
		if(noti_filename!=null) {
			//새파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request,db_noticeboard.getNoti_filename());
		}
		
		return "redirect:/noticeboard/detail.do?noti_num="+noti_num;
	}

}
