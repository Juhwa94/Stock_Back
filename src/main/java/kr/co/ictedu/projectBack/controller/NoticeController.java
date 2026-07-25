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
import kr.co.ictedu.projectBack.vo.CommunityVO;
import kr.co.ictedu.projectBack.vo.NoticeVO;
import kr.co.ictedu.projectBack.vo.PageVO;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noti;

    @Autowired
    private PagingService pagingService;

	@Value("${spring.servlet.multipart.location}")
	private String filePath;
	
	@GetMapping("/noPath")
	public String getMethodName() {
		System.out.println("Path  : " + filePath);
		return filePath;
    
	}
    // 공지 등록
    @PostMapping("/noAdd")
	public ResponseEntity<?> noAdd(NoticeVO vo, HttpServletRequest request) {
		
		
		MultipartFile mf = vo.getMfile();
		
	
	    if(mf != null && !mf.isEmpty()) {
	    	
//		String oriFn = mf.getOriginalFilename();
//		System.out.println("파일 이름 : " + oriFn);
		// ------------------------------------------
		StringBuilder path = new StringBuilder();
		path.append(filePath).append("\\");
//		path.append(oriFn);
		
		System.out.println("FullPath : " + path);
		// ------------------------------------------
		File f = new File(path.toString());
	
		try {
			mf.transferTo(f); 
//			vo.setCimgn(oriFn);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");
		} 
	
	}
	    noti.add(vo);
		
	return ResponseEntity.ok().body("게시글 등록 성공!");
	}
    // 공지 목록
    @RequestMapping("/noList")
    public Map<String, Object> noticeList(
            @RequestParam Map<String, String> paramMap,
            HttpServletRequest request) {

        String cPage = paramMap.get("cPage");

        int totalCnt = noti.totalCount(paramMap);
        PageVO pageVO = pagingService.makePage(totalCnt, cPage);

        Map<String, String> map = new HashMap<>(paramMap);
        map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
        map.put("end", String.valueOf(pageVO.getEndPerPage()));

        List<NoticeVO> list = noti.list(map);

        Map<String, Object> response = new HashMap<>();
        response.put("data", list);
        response.put("totalItems", pageVO.getTotalRecord());
        response.put("totalPages", pageVO.getTotalPage());
        response.put("currentPage", pageVO.getNowPage());
        response.put("startPage", pageVO.getStartPage());
        response.put("endPage", pageVO.getEndPage());

        return response;
    }

    // 상세보기
    @GetMapping("/noDetail")
    public NoticeVO noticeDetail(@RequestParam("num") int num) {
        return noti.detail(num);
    }

    @PostMapping("noUpdate")
	public ResponseEntity<?> update(NoticeVO vo){
		
		MultipartFile mf = vo.getMfile();
		
		if(mf != null && !mf.isEmpty()) {
			String oriFn = mf.getOriginalFilename();
			File file = new File(filePath, oriFn);
			
			try {
				mf.transferTo(file);
//				vo.setCimgn(oriFn);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 수정 실패");
				
			}
		}
		noti.update(vo);
		return ResponseEntity.ok("수정 완료");
    }
		

    // 삭제
    @DeleteMapping("/noDelete")
    public ResponseEntity<?> noticeDelete(@RequestParam("num") int num) {

        noti.del(num);

        return ResponseEntity.ok("삭제 완료");
    }
}