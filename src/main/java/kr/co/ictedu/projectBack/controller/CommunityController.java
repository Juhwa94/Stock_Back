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
import kr.co.ictedu.projectBack.service.CommentsService;
import kr.co.ictedu.projectBack.service.CommunityService;
import kr.co.ictedu.projectBack.vo.CommentsVO;
import kr.co.ictedu.projectBack.vo.CommunityVO;
import kr.co.ictedu.projectBack.vo.PageVO;

@RestController
@RequestMapping("/api/community")
public class CommunityController {
	
	@Autowired
	private CommunityService comm;
	
//	댓글자리
//	@PostMapping("commAdd")
	//json으로 받겠
//	public ResponseEntity<?> comm(CommunityVO vo) {
//	comm.add(vo);
//		System.out.println("vo:"+vo.getUcode());
//		System.out.println("vo:"+vo.getCwirter());
//	    System.out.println("vo:"+vo.getCcontent());
//		System.out.println("vo:"+vo.getCregdate());
//		return ResponseEntity.ok("등록 성공");
//	}

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
	
	@PostMapping("/communityAdd")
	public ResponseEntity<?> communityAdd(CommunityVO vo, HttpServletRequest request) {
		
		
		MultipartFile mf = vo.getMfile();
		
	
	    if(mf != null && !mf.isEmpty()) {
	    	
		String oriFn = mf.getOriginalFilename();
		System.out.println("파일 이름 : " + oriFn);
		// ------------------------------------------
		StringBuilder path = new StringBuilder();
		path.append(filePath).append("\\");
		path.append(oriFn);
		
		System.out.println("FullPath : " + path);
		// ------------------------------------------
		File f = new File(path.toString());
	
		try {
			mf.transferTo(f); 
			vo.setCimgn(oriFn);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");
		} 
	
	}
	    comm.add(vo);
		
	return ResponseEntity.ok().body("게시글 등록 성공!");
	}
	
	@RequestMapping("/communityList")
	public Map<String, Object> upCommunityList(
			@RequestParam Map<String, String> paramMap, 
			HttpServletRequest request
			) {
		// 현재 페이지에 따라 페이지 공식에 의해서 begin , end를 구해서
		// 페이징 처리되어서 반환 받은 데이터
		
		String cPage = paramMap.get("cPage");
		System.out.println("searchType : " + paramMap.get("searchType"));
		System.out.println("searchValue : " + paramMap.get("searchValue"));
		System.out.println("*************************");
		int totalCnt = comm.totalCount(paramMap);
		PageVO pageVO = pagingService.makePage(totalCnt, cPage);
		
		// Json으로 응답 처리 - 페이징 처리된 결과 리스트와 정보
		Map<String, String> map = new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<CommunityVO> list = comm.list(map);
		

		
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
	public CommunityVO communityDetail(@RequestParam("num") int num) {
		return comm.detail(num);
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
	@PostMapping("update")
	public ResponseEntity<?> update(CommunityVO vo){
		
		MultipartFile mf = vo.getMfile();
		
		if(mf != null && !mf.isEmpty()) {
			String oriFn = mf.getOriginalFilename();
			File file = new File(filePath, oriFn);
			
			try {
				mf.transferTo(file);
				vo.setCimgn(oriFn);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 수정 실패");
				
			}
		}
		
		
		
		
		
		comm.update(vo);
		return ResponseEntity.ok("수정 완료");
	}
	
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> communityDelete(@RequestParam("num") int num) {
		
		CommunityVO vo = comm.get(num);
		
		if(vo.getCimgn() != null) {
			File file = new File(filePath, vo.getCimgn());
			
			if (file.exists()) {
				file.delete();
			}
		}
		comm.del(num);
		
		
		
		return ResponseEntity.ok("삭제 완료");
	}
	
}
