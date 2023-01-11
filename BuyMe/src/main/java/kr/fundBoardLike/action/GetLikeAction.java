package kr.fundBoardLike.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.fundBoard.dao.FundBoardLikeDAO;
import kr.fundBoard.vo.FundBoardLikeVO;

public class GetLikeAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int fund_num = Integer.parseInt(
				      request.getParameter("like_num"));
		
		Map<String,Object> mapAjax =
				           new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		FundBoardLikeDAO dao = FundBoardLikeDAO.getInstance();
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("status", "noFav");
			mapAjax.put("count", dao.selectLikeCount(fund_num));
		}else {//로그인 된 경우
			FundBoardLikeVO favVO = new FundBoardLikeVO();
			favVO.setFund_num(fund_num);
			favVO.setMem_num(user_num);
			//좋아요 선택 여부 체크
			FundBoardLikeVO fundboardLike = dao.selectLike(favVO);
			if(fundboardLike!=null) {//좋아요가 등록되어 있음
				mapAjax.put("status", "yesFav");
				mapAjax.put("count", dao.selectLikeCount(fund_num));
			}else {//좋아요가 등록되지 않음
				mapAjax.put("status", "noFav");
				mapAjax.put("count", dao.selectLikeCount(fund_num));
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = 
				    mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
