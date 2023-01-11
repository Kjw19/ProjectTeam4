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

public class WriteLikeAction implements	Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,Object> mapAjax = 
	             new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Integer user_num = 
			(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
		mapAjax.put("result", "logout");
		}else {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int fund_num = Integer.parseInt(
				    request.getParameter("fund_num"));
		
		FundBoardLikeVO likeVO = new FundBoardLikeVO();
		likeVO.setFund_num(fund_num);
		likeVO.setMem_num(user_num);
		
		FundBoardLikeDAO dao = FundBoardLikeDAO.getInstance();
		//좋아요 등록 여부 체크
		FundBoardLikeVO db_like = dao.selectLike(likeVO);
		if(db_like!=null) {//좋아요 등록 O
			//좋아요 삭제
			dao.deleteLike(db_like.getLike_num());
			mapAjax.put("result", "success");
			mapAjax.put("status", "noFav");
			mapAjax.put("count", dao.selectLikeCount(fund_num));
		}else {//좋아요 등록 X
			//좋아요 등록
			dao.insertLike(likeVO);
			mapAjax.put("result", "success");
			mapAjax.put("status", "yesFav");
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
