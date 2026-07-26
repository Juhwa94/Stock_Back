package kr.co.ictedu.projectBack.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.ictedu.projectBack.common.PagingService;
import kr.co.ictedu.projectBack.service.member.MemberService;
import kr.co.ictedu.projectBack.vo.MemberVO;
import kr.co.ictedu.projectBack.vo.PageVO;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@PostMapping("/admin/signup")
	public ResponseEntity<?> adminSignup(@RequestBody MemberVO vo) {
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

	@GetMapping("/checkId")
	public ResponseEntity<Integer> checkId(@RequestParam("id") String id) {
		int count = memberService.checkId(id);
		return ResponseEntity.ok(count); // 0이면 사용 가능, 1 이상이면 중복
	}// http://192.168.0.19/projectBack/api/member/mypage

	@GetMapping("/mypage")
	public MemberVO getMyInfo(@RequestParam("id") String id) {
		MemberVO vo = memberService.getMemberById(id);
		if (vo != null) {
			vo.setPwd(null);
		}
		return vo;
	}

	@PostMapping("/update")
	public int updateMyInfo(@RequestBody MemberVO vo) {
		return memberService.updateMember(vo);
	}

	@DeleteMapping("/withdraw")
	public String memberWithdraw(@RequestParam("num") int num) {
		memberService.withdrawMember(num);
		return "탈퇴 완료";
	}

	// 페이징 처리 서비스 의존성 주입
	@Autowired
	private PagingService pagingService;

	// 회원 전체 조회
	@RequestMapping("/memberList")
	public Map<String, Object> memberList(@RequestParam Map<String, String> paramMap, HttpServletRequest request) {

		String cPage = paramMap.get("cPage");
		int totalCnt = memberService.totalCount(paramMap);
		PageVO pageVO = pagingService.makePage(totalCnt, cPage);

		// Json으로 응답 처리 - 페이징 처리된 결과 리스트와 정보
		Map<String, String> map = new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<MemberVO> list = memberService.list(map);

		Map<String, Object> response = new HashMap<>();
		response.put("data", list); // 페이징 처리가 완료된 리스트를 저장한 데이터
		response.put("totalItems", pageVO.getTotalRecord()); // 전체 게시물의 count
		response.put("totalPages", pageVO.getTotalPage()); // 전체 페이지
		response.put("currentPage", pageVO.getNowPage()); // 현재 페이지
		response.put("startPage", pageVO.getStartPage()); // 블록의 시작
		response.put("endPage", pageVO.getEndPage()); // 블록의 끝

		return response;
	}

	// 선택된 회원 등급 변경
	@PutMapping("/updateGrade")
	public ResponseEntity<?> updateGrade(@RequestBody Map<String, Object> param) {

	    memberService.updateGrade(param);

	    return ResponseEntity.ok("등급이 변경되었습니다.");
	}
}
