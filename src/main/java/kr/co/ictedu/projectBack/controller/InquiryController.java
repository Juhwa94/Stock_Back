package kr.co.ictedu.projectBack.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.ictedu.projectBack.common.PagingService;
import kr.co.ictedu.projectBack.service.InquiryService;
import kr.co.ictedu.projectBack.service.ReplyService;
import kr.co.ictedu.projectBack.vo.InquiryVO;
import kr.co.ictedu.projectBack.vo.PageVO;
import kr.co.ictedu.projectBack.vo.ReplyVO;

@RestController
@RequestMapping("/api/inquiry")
public class InquiryController {

	@Autowired
	private InquiryService inquiryService;

	@Autowired
	private PagingService pagingService;

	@Value("${spring.servlet.multipart.location}")
	private String filePath;

	@GetMapping("/getPath")
	public String getPathTest() {
		System.out.println("Path:" + filePath);
		return filePath;
	}

	@PostMapping("/inquiryAdd")
	public ResponseEntity<?> inquiryadd(InquiryVO vo, HttpServletRequest request) {

		System.out.println("iwriter:" + vo.getIwriter());
		System.out.println("ititle:" + vo.getItitle());
		System.out.println("icontent:" + vo.getIcontent());

		System.out.println("============================");

		MultipartFile mf = vo.getMfile();
		String oriFn = mf.getOriginalFilename();
		System.out.println("파일 이름 :" + oriFn);

		StringBuilder path = new StringBuilder();
		path.append(filePath).append("\\");
		path.append(oriFn);
		System.out.println("FullPath : " + path);

		File f = new File(path.toString());
		try {
			mf.transferTo(f);
			vo.setImgn(oriFn);
			inquiryService.add(vo);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("업로드 성공!");
	}

//	@GetMapping("/inquiryList")
//	public List<InquiryVO> inquiryList() {
//		// 페이지 처리를 위한 테스트
//		Map<String, String> map = new HashMap<>();
//		map.put("begin", String.valueOf(1));
//		map.put("end", String.valueOf(10));
//		return inquiryService.list(map);
//	}

	@RequestMapping("/inquiryList")
	public Map<String, Object> inquiryList(@RequestParam Map<String, String> paramMap, HttpServletRequest request) {
		// 현재 페이지에 따라 페이지 공식에 의해서 begin , end를 구해서
		// 페이징 처리되어서 반환 받은 데이터

		String cPage = paramMap.get("cPage");
		System.out.println("searchType : " + paramMap.get("searchType"));
		System.out.println("searchValue : " + paramMap.get("searchValue"));
		System.out.println("*************************");
		int totalCnt = inquiryService.totalCount(paramMap);
		PageVO pageVO = pagingService.makePage(totalCnt, cPage);

		// Json으로 응답 처리 - 페이징 처리된 결과 리스트와 정보
		Map<String, String> map = new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<InquiryVO> list = inquiryService.list(map);

		Map<String, Object> response = new HashMap<>();
		response.put("data", list); // 페이징 처리가 완료된 리스트를 저장한 데이터
		response.put("totalItems", pageVO.getTotalRecord()); // 전체 게시물의 count
		response.put("totalPages", pageVO.getTotalPage()); // 전체 페이지
		response.put("currentPage", pageVO.getNowPage()); // 현재 페이지
		response.put("startPage", pageVO.getStartPage()); // 블록의 시작
		response.put("endPage", pageVO.getEndPage()); // 블록의 끝
		return response;
	}

	@GetMapping("/detail")
	public InquiryVO detail(@RequestParam("num") int num) {
		return inquiryService.detail(num);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam("num") int num) {
		try {
			inquiryService.delete(num);
			return ResponseEntity.ok().body("삭제 성공");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}

	@Autowired
	private ReplyService replyService;

	@PostMapping("/replyAdd")
	public ResponseEntity<?> upBoardComm(@RequestBody ReplyVO vo) {
		replyService.addComment(vo);
		return ResponseEntity.ok().body(1);
	}

	@GetMapping("/replyList")
	public List<ReplyVO> listReply(@RequestParam("rnum") int num) {
		return replyService.listReply(num);
	}
}