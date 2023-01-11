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

public class WriteFavAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");

		if (user_num == null) {
			mapAjax.put("result", "logout");
		} else {
			request.setCharacterEncoding("utf-8");

			int noti_num = Integer.parseInt(request.getParameter("noti_num"));

			NoticeBoardFavsVO favsVO = new NoticeBoardFavsVO();
			favsVO.setNoti_num(noti_num);
			favsVO.setMem_num(user_num);

			NoticeBoardFavDAO dao = NoticeBoardFavDAO.getInstance();
			//좋아요 등록 여부 체크
			NoticeBoardFavsVO db_fav = dao.selectNoticeFav(favsVO);

			if (db_fav != null) {
				//좋아요 삭제
				dao.deleteNoticeFav(db_fav.getLike_num());
				mapAjax.put("result", "success");
				mapAjax.put("status", "noFav");
				mapAjax.put("count", dao.selectNoticeFavCount(noti_num));
			}
			else {
				//좋아요 등록
				dao.insertNoticeFav(favsVO);
				mapAjax.put("result", "success");
				mapAjax.put("status", "yesFav");
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
