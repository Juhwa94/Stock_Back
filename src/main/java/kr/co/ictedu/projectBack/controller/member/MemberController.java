package kr.co.ictedu.projectBack.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.projectBack.service.member.MemberService;
import kr.co.ictedu.projectBack.vo.MemberVO;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/admin/signup")
	public ResponseEntity<?> adminSignup(
	        @RequestBody MemberVO vo
	) {
	    vo.setAuthority("ADMIN");
	    memberService.create(vo);
	    return ResponseEntity.ok("관리자 생성 완료");
	}
	@PostMapping("/signup")
	public ResponseEntity<?> memberjoin(@RequestBody MemberVO memberDTO) {
	    System.out.println("회원가입 요청 들어옴");
	    //memberDTO.setAuthority("MEMBER");
	    memberService.create(memberDTO);
	    return ResponseEntity.ok("회원가입이 완료되었습니다.");
	}
	
	@GetMapping("/mypage")
	public MemberVO getMyInfo(@RequestParam("email") String email) {
	    MemberVO vo = memberService.getMemberByEmail(email);
	    if (vo != null) {
	        vo.setPwd(null);
	    }
	    return vo;
	}
	@PostMapping("/update")
	public int updateMyInfo(@RequestBody MemberVO vo) {

	    System.out.println("받은 nick = " + vo.getNick());
	    System.out.println("받은 email = " + vo.getEmail());

	    return memberService.updateMember(vo);
	}
	@DeleteMapping("/withdraw")
	public String memberWithdraw(
	        @RequestParam("num") int num,
	        HttpSession session
	) { System.out.println("탈퇴 요청 num = " + num);
	    memberService.withdrawMember(num);
	    session.invalidate();
	    return "탈퇴 완료";
	}
  }
	
	








