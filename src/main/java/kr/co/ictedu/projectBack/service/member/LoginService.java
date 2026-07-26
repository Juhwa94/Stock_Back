package kr.co.ictedu.projectBack.service.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.projectBack.dao.member.LoginDao;
import kr.co.ictedu.projectBack.vo.MemberVO;

@Service
public class LoginService {
	@Autowired
	private LoginDao loginDao;
	
	public Map<String, Object> loginCheck(MemberVO vo){
		return loginDao.loginCheck(vo);
	}
	
}
