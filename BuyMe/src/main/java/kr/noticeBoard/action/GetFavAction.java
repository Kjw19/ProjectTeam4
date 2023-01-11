package kr.noticeBoard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;

import kr.noticeBoard.vo.NoticeBoardFavsVO;
import kr.noticeBoardFav.dao.NoticeBoardFavDAO;

public class GetFavAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		// 전송된 데이터 반환
		int noti_num = Integer.parseInt(request.getParameter("noti_num"));
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");

		NoticeBoardFavDAO dao = NoticeBoardFavDAO.getInstance();

		if (user_num == null) {
			mapAjax.put("status", "noFav");
			mapAjax.put("count", dao.selectNoticeFavCount(noti_num));
		} else {
			NoticeBoardFavsVO favsVO = new NoticeBoardFavsVO();
			favsVO.setNoti_num(noti_num);
			favsVO.setMem_num(user_num);

			NoticeBoardFavsVO boardFav = dao.selectNoticeFav(favsVO);
			if (boardFav != null) {
				mapAjax.put("status", "yesFav");
				mapAjax.put("count", dao.selectNoticeFavCount(noti_num));
			} else {
				mapAjax.put("status", "noFav");
				mapAjax.put("count", dao.selectNoticeFavCount(noti_num));

			}
		}
		// JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
