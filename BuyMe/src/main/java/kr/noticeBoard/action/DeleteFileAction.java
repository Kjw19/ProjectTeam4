package kr.noticeBoard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.noticeBoard.dao.NoticeBoardDAO;
import kr.noticeBoard.vo.NoticeBoardVO;
import kr.util.FileUtil;

public class DeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			int noti_num = Integer.parseInt(request.getParameter("noti_num"));
		
			NoticeBoardDAO dao = NoticeBoardDAO.getInstance();
			NoticeBoardVO db_noticeboard = dao.getNoticeBoard(noti_num);
			if(user_num != db_noticeboard.getMem_num()) {
				//로그인한 사람과 작성자가 불일치한 경우
				mapAjax.put("result", "wrongAccess");
			}else {
				//로그인한 사람과 작성자 일치
				dao.deleteNoticeFile(noti_num);
				//파일 삭제
				FileUtil.removeFile(request,db_noticeboard.getNoti_filename());
				mapAjax.put("result", "success");
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
