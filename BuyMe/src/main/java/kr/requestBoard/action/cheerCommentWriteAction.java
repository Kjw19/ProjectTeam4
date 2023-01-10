package kr.requestBoard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.requestBoard.dao.RequestCheerCommentDAO;
import kr.requestBoard.vo.RequestCheerCommentVO;
import kr.util.FileUtil;

public class cheerCommentWriteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();

		// 로그인 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else { // 로그인이 된 경우
			request.setCharacterEncoding("utf-8"); // 전송된 데이터 인코딩 처리
			
			MultipartRequest multi = FileUtil.createFile(request);
			RequestCheerCommentVO cheerComment = new RequestCheerCommentVO();
			cheerComment.setMem_num(user_num); // 회원번호(댓글 작성자)
			cheerComment.setCheerComm_title(multi.getParameter("cheerComm_title"));
			cheerComment.setCheerComm_content(multi.getParameter("cheerComm_content"));
			cheerComment.setCheerComm_filename(multi.getFilesystemName("cheerComm_filename"));
			cheerComment.setCheerComm_ip(request.getRemoteAddr());
			cheerComment.setCheer_num(Integer.parseInt(request.getParameter("cheer_num")));
			
			RequestCheerCommentDAO dao = RequestCheerCommentDAO.getInstance();
			dao.insertCheerCommentBoard(cheerComment);

			mapAjax.put("result", "success"); // 정상적으로 처리될 시
		}

		// JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);	

		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}