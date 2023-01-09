package kr.noticeBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.noticeBoard.dao.NoticeBoardDAO;
import kr.noticeBoard.vo.NoticeBoardVO;
import kr.util.StringUtil;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//글번호 반환
		int noti_num = Integer.parseInt(request.getParameter("noti_num"));
				
		NoticeBoardDAO dao = NoticeBoardDAO.getInstance();
		//조회수 증가
		dao.updateNoticeReadCount(noti_num);
		
		NoticeBoardVO noticeboard = dao.getNoticeBoard(noti_num);
		
		//HTML 태그를 허용하지 않음
		noticeboard.setNoti_title(StringUtil.useNoHtml(noticeboard.getNoti_title()));
		//HTML 태그를 허용하지 않으면서 줄바꿈 처리
		noticeboard.setNoti_content(StringUtil.useBrNoHtml(noticeboard.getNoti_content()));
			
		request.setAttribute("noticeboard", noticeboard);
				
		return "/WEB-INF/views/noticeboard/detail.jsp";
	}
}
