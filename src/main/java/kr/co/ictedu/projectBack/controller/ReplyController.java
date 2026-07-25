package kr.co.ictedu.projectBack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.projectBack.service.ReplyService;
import kr.co.ictedu.projectBack.vo.ReplyVO;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {

	    @Autowired
	    private ReplyService replyService;

	    @PostMapping("/add")
	    public void addComment(@RequestBody ReplyVO comment) {
	        replyService.addComment(comment);
	    }

	    @GetMapping("/list")
	    public List<ReplyVO> listReply(@RequestParam("num") int num){
	        return replyService.listReply(num);
	    }
	}
