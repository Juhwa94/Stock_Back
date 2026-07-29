package kr.co.ictedu.projectBack.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.projectBack.service.CommentsService;
import kr.co.ictedu.projectBack.vo.CommentsVO;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentsService tomi;

    // 댓글 등록
    @PostMapping("/toAdd")
    public ResponseEntity<?> toAdd(CommentsVO vo) {

        tomi.add(vo);

        return ResponseEntity.ok("댓글 등록 성공");
    }

    // 댓글 목록 (댓글은 5개씩)
    @GetMapping("/toList")
    public Map<String, Object> list(@RequestParam Map<String, String> paramMap) {

        int currentPage = Integer.parseInt(paramMap.getOrDefault("cPage", "1"));

        // 댓글은 페이지당 5개
        int pageSize = 5;

        // 페이지 번호는 5개씩 출력
        int pageBlock = 5;

        int totalCnt = tomi.totalCount(paramMap);

        int totalPages = (int) Math.ceil((double) totalCnt / pageSize);

        int begin = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;

        int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;

        if (endPage > totalPages) {
            endPage = totalPages;
        }

        Map<String, String> map = new HashMap<>(paramMap);
        map.put("begin", String.valueOf(begin));
        map.put("end", String.valueOf(end));

        List<CommentsVO> list = tomi.list(map);

        Map<String, Object> response = new HashMap<>();
        response.put("data", list);
        response.put("totalItems", totalCnt);
        response.put("totalPages", totalPages);
        response.put("currentPage", currentPage);
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return response;
    }

    // 댓글 수정
    @PostMapping("/toUpdate")
    public ResponseEntity<?> update(CommentsVO vo) {

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