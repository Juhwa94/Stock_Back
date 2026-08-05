package kr.co.ictedu.projectBack.controller.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.co.ictedu.projectBack.service.member.LoginService;
import kr.co.ictedu.projectBack.vo.MemberVO;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "https://stockfront-production.up.railway.app")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/dologin")
    public ResponseEntity<?> doLogin(
            HttpSession session, 
            HttpServletRequest req,
            HttpServletResponse resp,
            @RequestHeader(value = "User-Agent", required = false) String userAgent, 
            @RequestBody MemberVO vo
    ) {     
        System.out.println("dologin 호출됨 - UserAgent: " + userAgent);
        Map<String, Object> result = loginService.loginCheck(vo);
        System.out.println("result : " + result);

        Map<String, Object> responseMap = new HashMap<>();

        if (result != null) {
            System.out.println("세션 처리 완료!");
            MemberVO loginMember = new MemberVO();

            // NullPointerException 방지
            if (result.get("MNUM") != null) {
                loginMember.setMnum(((Number) result.get("MNUM")).intValue());
            }
            loginMember.setName(result.get("NAME") != null ? result.get("NAME").toString() : "");
            loginMember.setEmail(result.get("EMAIL") != null ? result.get("EMAIL").toString() : "");
            loginMember.setNick(result.get("NICK") != null ? result.get("NICK").toString() : "");
            loginMember.setMphone(result.get("MPHONE") != null ? result.get("MPHONE").toString() : "");
            loginMember.setGrade(result.get("GRADE") != null ? result.get("GRADE").toString() : "");
            loginMember.setStoreaddr(result.get("STOREADDR") != null ? result.get("STOREADDR").toString() : "");
            loginMember.setAuthority(result.get("AUTHORITY") != null ? result.get("AUTHORITY").toString() : "");

            // 세션 저장
            session.setAttribute("loginMember", loginMember);

            // 💡 크로스 도메인(Railway) 환경 세션 쿠키(JSESSIONID) 설정
            ResponseCookie cookie = ResponseCookie.from("JSESSIONID", session.getId())
                    .path("/")
                    .secure(true)       // HTTPS 필수
                    .sameSite("None")   // 서드파티 쿠키 허용
                    .httpOnly(true)
                    .build();
            resp.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            responseMap.put("status", "success");
            responseMap.put("member", loginMember);

            return ResponseEntity.ok(responseMap);
        }

        responseMap.put("status", "fail");
        responseMap.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");

        return ResponseEntity.status(401).body(responseMap);
    }

    @GetMapping("/dologout")
    public ResponseEntity<?> doLogout(
            HttpSession session, 
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestHeader(value = "User-Agent", required = false) String userAgent
    ) {
        System.out.println("로그아웃 처리 완료!");
        session.invalidate();

        // 쿠키 삭제 처리
        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", "")
                .path("/")
                .maxAge(0)
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("status", "logout");

        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/session")
    public ResponseEntity<?> session(HttpSession session) {
        System.out.println("★★★★★ session 호출 ★★★★★");
        System.out.println("session id = " + session.getId());

        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        System.out.println("loginMember = " + loginMember);

        if (loginMember == null) {
            System.out.println("세션 없음");
            return ResponseEntity.status(401).body(null);
        }

        System.out.println("세션 있음");
        return ResponseEntity.ok(loginMember);
    }
}