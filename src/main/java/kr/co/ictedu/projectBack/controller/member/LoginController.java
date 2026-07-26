package kr.co.ictedu.projectBack.controller.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import kr.co.ictedu.projectBack.service.member.LoginService;
import kr.co.ictedu.projectBack.vo.MemberVO;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/dologin")
	public String doLogin (HttpSession session, HttpServletRequest req,
			@RequestHeader("User-Agent") String userAgent, @RequestBody MemberVO vo
			) {		
		System.out.println("dologin");
		Map<String, Object> result = loginService.loginCheck(vo);
		System.out.println("result :"+result);
		
		if(result != null) {
		    System.out.println("세션 처리 완료!");
		    MemberVO loginMember = new MemberVO();
		    loginMember.setMnum(((Number)result.get("MNUM")).intValue());
		    loginMember.setId(result.get("ID").toString());
		    loginMember.setName(result.get("NAME").toString());
		    loginMember.setEmail(result.get("EMAIL").toString());
		    loginMember.setNick(result.get("NICK").toString());
		    loginMember.setMphone(result.get("MPHONE").toString());
		    loginMember.setGrade(result.get("GRADE").toString());
		    loginMember.setStorecode(result.get("STORECODE").toString());
		    loginMember.setStoreaddr(result.get("STOREADDR").toString());
		    loginMember.setAuthority(result.get("AUTHORITY").toString());
		    session.setAttribute("loginMember", loginMember);
		    return "success";
		}
		return "fail";
	   
	}
	@GetMapping("/dologout")
	public String doLogout(HttpSession session, HttpServletRequest request,
			@RequestHeader("User-Agent") String userAgent) {
		System.out.println("로그아웃 처리 완료!");
		session.invalidate();
		return "logout";
	}
	
	@GetMapping("/session")
	public MemberVO session(HttpSession session) {

	    System.out.println("★★★★★ session 호출 ★★★★★");
	    System.out.println("session id = " + session.getId());

	    MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
	    System.out.println("loginMember = " + loginMember);
	    if(loginMember == null) {
	        System.out.println("세션 없음");
	        return null;
	    }
	    System.out.println("세션 있음");
	    return loginMember;
	}				
}

