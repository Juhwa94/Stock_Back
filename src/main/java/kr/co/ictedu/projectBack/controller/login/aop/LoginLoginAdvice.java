package kr.co.ictedu.projectBack.controller.login.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import kr.co.ictedu.projectBack.dao.member.LogDao;
import kr.co.ictedu.projectBack.vo.LoginLoggerVO;
import kr.co.ictedu.projectBack.vo.MemberVO;

@Aspect
@Component
public class LoginLoginAdvice {
	@Autowired
	private LogDao logDao;
	
	private void createLoggin(String methodName, Object[] fd, ProceedingJoinPoint jp, String status) {
        LoginLoggerVO lvo = new LoginLoggerVO();
        System.out.println("매개변수 출력 해보기");
        System.out.println("첫번째 매개변수 HttpSession :" + (fd[0] instanceof HttpSession));
        System.out.println("두번째 매개변수 HttpServletRequest :" + (fd[1] instanceof HttpServletRequest));
        if (fd[0] instanceof HttpSession && fd[1] instanceof HttpServletRequest) {
            HttpSession session = (HttpSession) fd[0];
            HttpServletRequest request = (HttpServletRequest) fd[1];
            MemberVO vo = (MemberVO) session.getAttribute("loginMember");
            
            if (vo != null) { 
                lvo.setMemberemail(vo.getEmail()); 
                lvo.setStatus(status);     
                lvo.setReip(request.getRemoteAddr()); 
                
                // 브라우저 및 운영체제 정보 파싱 후 세팅
                String userAgent = request.getHeader("User-Agent");
                String parsedAgent = UserAgentUtils.parseAgent(userAgent);
                lvo.setUagent(parsedAgent);
                
                // 디버깅을 위한 콘솔 로그 출력
                System.out.println("로그인 기록 : " + lvo);
                System.out.println("memberemail : " + lvo.getMemberemail());
                System.out.println("Agent : " + lvo.getUagent());
                System.out.println("reip: " + lvo.getReip());
                System.out.println("status: " + lvo.getStatus());
                
                System.out.println("----------------------------------------");

                logDao.addLoginLogging(lvo);
            }
        }
    }

    @Around("execution(* kr.co.ictedu.projectBack.controller.member.LoginController.doLog*(..))")
    public String loginLogger(ProceedingJoinPoint jp) {
        Object[] fd = jp.getArgs();
        String rpath = null;
        String methodName = jp.getSignature().getName();
        
        try {
            if (methodName.equals("doLogin")) {
                rpath = (String) jp.proceed();
                createLoggin(methodName, fd, jp, "login");
            } else if (methodName.equals("doLogout")) {
                createLoggin(methodName, fd, jp, "logout");
                rpath = (String) jp.proceed();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
        System.out.println("Aop 동작 테스트:" + rpath);
        return rpath;
    }
}


