package kr.co.ictedu.projectBack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.projectBack.dao.ReplyDao;
import kr.co.ictedu.projectBack.vo.ReplyVO;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyDao replyDao;
	public void addComment(ReplyVO comment) {
		replyDao.addComment(comment);
	}
	public List<ReplyVO> listReply(int num) {
		return replyDao.listReply(num);
	}
}
