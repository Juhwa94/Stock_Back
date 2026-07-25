package kr.co.ictedu.projectBack.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.ictedu.projectBack.common.PagingService;
import kr.co.ictedu.projectBack.service.CommentsService;
import kr.co.ictedu.projectBack.vo.CommentsVO;
import kr.co.ictedu.projectBack.vo.NoticeVO;
import kr.co.ictedu.projectBack.vo.PageVO;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/comments")
public class CommentsController {


    @Autowired
    private CommentsService tomi;
    @Autowired
    private PagingService pagingService;

    
    // 댓글 등록
	
    
    @PostMapping("/toAdd")
  	public ResponseEntity<?> toAdd(CommentsVO vo) {
    	
    	tomi.add(vo);
    	return ResponseEntity.ok("댓글 등록 성공");
  		
  	}	
  	
    // 댓글 목록
    @GetMapping("/toList")
    public Map<String, Object> list(@RequestParam Map<String, String> paramMap) {

        String cPage = paramMap.getOrDefault("cPage", "1");

        int totalCnt = tomi.totalCount(paramMap);

        PageVO pageVO = pagingService.makePage(totalCnt, cPage);

        Map<String, String> map = new HashMap<>(paramMap);
        map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
        map.put("end", String.valueOf(pageVO.getEndPerPage()));

        List<CommentsVO> list = tomi.list(map);

        Map<String, Object> response = new HashMap<>();
        response.put("data", list);
        response.put("totalItems", pageVO.getTotalRecord());
        response.put("totalPages", pageVO.getTotalPage());
        response.put("currentPage", pageVO.getNowPage());
        response.put("startPage", pageVO.getStartPage());
        response.put("endPage", pageVO.getEndPage());

        return response;
    }

//    댓글 상세
//    @GetMapping("/toDetail")
//    public CommentsVO toDetail(@RequestParam("num") int num) {
//        return tomi.detail(num);
//    }

    //댓글 수정
    @PostMapping("toUpdate")
  	public ResponseEntity<?> update(CommentsVO vo){	
  		tomi.update(vo);
  		return ResponseEntity.ok("수정 완료");
      }
  		
    
    
    // 댓글 삭제
    @DeleteMapping("/toDelete")
    public ResponseEntity<?> delete(@RequestParam("num") int num) {

        tomi.del(num);

        return ResponseEntity.ok("삭제 완료");
    }
}