package kr.members.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.members.dao.MemberDAO;
import kr.members.vo.MemberVO;
import kr.util.FileUtil;

public class UpdateMyPhotoAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {
			mapAjax.put("result", "logout");
		}else {
			MemberDAO dao = MemberDAO.getInstance();
			MemberVO db_member = dao.getMember(user_num); // 이전 이미지 파일 정보 얻기 : 이전의 이미지는 지우고 교체하려는 이미지로 변경하려고
			
			// 전송된 파일 업로드 처리
			MultipartRequest multi = FileUtil.createFile(request);
			String photo = multi.getFilesystemName("photo"); // 서버에 저장된 파일명 반환
			dao.updateMyPhoto(photo, user_num); // (파일명, mem_num)
			
			session.setAttribute("user_photo", photo); // 세션에 저장된 프로필 사진 갱신
			FileUtil.removeFile(request, db_member.getPhoto()); // 이전 프로필 사진 삭제
			
			mapAjax.put("result", "success");
		}
		
		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}