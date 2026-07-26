package kr.co.ictedu.projectBack.controller;

import java.io.File;
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
import kr.co.ictedu.projectBack.service.NoticeService;
import kr.co.ictedu.projectBack.vo.NoticeVO;
import kr.co.ictedu.projectBack.vo.PageVO;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
//	댓글자리
	

	@Autowired
	private PagingService pagingService;
	
	// application.properties에서 확인가능
	@Value("${spring.servlet.multipart.location}")
	private String filePath;
	
	@GetMapping("/getPath")
	public String getMethodName() {
		System.out.println("Path  : " + filePath);
		return filePath;
	}
	
//	@PostMapping("/noticeAdd")
//	public ResponseEntity<?> noticeAdd(NoticeVO vo, HttpServletRequest request) {
		
		// VO에 클라이언트의 아이피를 저장
//		vo.setReip(request.getRemoteAddr());
//		System.out.println("writer : " + vo.getWriter());
//		System.out.println("title : " + vo.getTitle());
//		System.out.println("content : " + vo.getContent());
//		System.out.println("Reip : " + request.getRemoteAddr());
//		System.out.println("========================");
		
		
//		MultipartFile mf = vo.getMfile();
//		String oriFn = mf.getOriginalFilename();
//		System.out.println("파일 이름 : " + oriFn);
//		// ------------------------------------------
//		StringBuilder path = new StringBuilder();
//		path.append(filePath).append("\\");
//		path.append(oriFn);
//		System.out.println("FullPath : " + path);
		// ------------------------------------------
	//	File f = new File(path.toString());
		// f에 저장된 파일객체를 사용해서 파일의 내용을 읽어와서 한 바이트씩 f에서 잡은 경로로 작성
		// 개념 InputStream read() -> while -> BufferedOutputStream write(f)
		// transferTo() : MultpartFile 를 사용해서 파일을 복제한다.
	//	try {
	//		mf.transferTo(f); // imgfile
			// 업로드 된 파일의 이름을 vo에 저장한다
			//vo.setCimgn(oriFn);
			// Service를 통해서 Mapper로 vo 주소 값을 보낸다
	//		communityService.add(vo);
	//	} catch (Exception e) {
	//		e.printStackTrace();
	//	} 
	//	return ResponseEntity.ok().body("업보드 업로드 성공!");
	//	}
	
	
	@RequestMapping("/noticeList")
	public Map<String, Object> noticeList(
			@RequestParam Map<String, String> paramMap, 
			HttpServletRequest request
			) {
		// 현재 페이지에 따라 페이지 공식에 의해서 begin , end를 구해서
		// 페이징 처리되어서 반환 받은 데이터
		
		String cPage = paramMap.get("cPage");
		System.out.println("searchType : " + paramMap.get("searchType"));
		System.out.println("searchValue : " + paramMap.get("searchValue"));
		System.out.println("*************************");
		int totalCnt = noticeService.totalCount(paramMap);
		PageVO pageVO = pagingService.makePage(totalCnt, cPage);
		
		// Json으로 응답 처리 - 페이징 처리된 결과 리스트와 정보
		Map<String, String> map = new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<NoticeVO> list = noticeService.list(map);
		

		
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
	public NoticeVO noticeDetail(@RequestParam("num") int num) {
		
		return noticeService.detail(num);
	}
	
//	// @RequestBody - Json으로 입력받아서 vo에 저장
//	@PostMapping("/upcommAdd")
//	public ResponseEntity<?> upBoardComm(@RequestBody UpBoardCommVO vo, HttpServletRequest request) {
//		
//		System.out.println("vo : " + vo.getUcode());
//		System.out.println("vo : " + vo.getUwriter());
//		System.out.println("vo : " + vo.getUcontent());
//		vo.setReip(request.getRemoteAddr());
//		System.out.println("vo : " + vo.getReip());
//		
//		upBoardCommService.addComment(vo);
//		
//		return ResponseEntity.ok().body("업로드 성공!");
//	}
//	
//	@RequestMapping("/upcommList")
//	public List<UpBoardCommVO> listBoardComm(@RequestParam("num") int num) {
//		
//		return upBoardCommService.listComment(num);
//	}
	
	
	
	@DeleteMapping("/delete")
	public String noticeDelete(@RequestParam("num") int num) {
		noticeService.del(num);
		return "삭제 완료";
	}
	
}
