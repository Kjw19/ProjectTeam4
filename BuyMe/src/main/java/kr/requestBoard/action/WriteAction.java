package kr.requestBoard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import kr.controller.Action;
import kr.requestBoard.dao.RequestBoardDAO;
import kr.requestBoard.vo.RequestBoardVO;
import kr.util.FileUtil;
  
public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		MultipartRequest multi = 
				             FileUtil.createFile(request);
		RequestBoardVO board = new RequestBoardVO();
		board.setReq_title(multi.getParameter("title"));
		board.setReq_content(multi.getParameter("content"));
		board.setReq_ip(request.getRemoteAddr());
		board.setReq_filename(multi.getFilesystemName("filename"));
		board.setMem_num(user_num);
		
		RequestBoardDAO dao = RequestBoardDAO.getInstance();
		dao.insertBoard(board);
		
		return "/WEB-INF/views/board/write.jsp";
	
	}
}
