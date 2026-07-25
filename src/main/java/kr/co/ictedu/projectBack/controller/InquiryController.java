package kr.co.ictedu.projectBack.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import kr.co.ictedu.projectBack.vo.ReplyVO;

@RestController
@RequestMapping("/api/inquiry")
public class InquiryController {
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private PagingService paginService;
	
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
	
	@Autowired
	private ReplyService replyService;
	
	@PostMapping("/replyAdd")
	public ResponseEntity<?> upBoardComm(@RequestBody ReplyVO vo){
		replyService.addComment(vo);
		return ResponseEntity.ok().body(1);
	}
//		//댓글 출력 -> upcommList
//		public _______________ listBoardComm(__________){
//			// 서비스에 listComment()호출해서 반환 
//			
//			return __________________
//		}
	@GetMapping("/replyList")
	public List<ReplyVO> listReply(@RequestParam("rnum") int num){
		return replyService.listReply(num);
	}
}