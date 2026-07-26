package kr.co.ictedu.projectBack.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.projectBack.dao.CommentsDao;
import kr.co.ictedu.projectBack.vo.CommentsVO;
import kr.co.ictedu.projectBack.vo.CommunityVO;

@Service
public class CommentsService {

	@Autowired
	private CommentsDao commentsDao;
	
	public void add(CommentsVO vo) {
		commentsDao.add(vo);
		
	}

	public List<CommentsVO> list(Map<String, String> map) {
		return commentsDao.list(map);
	}
	
	public int totalCount(Map<String, String> map) {
		return commentsDao.totalCount(map);
	}
	
	public void hit(int num) {
		commentsDao.hit(num);
	}

	// 상세보기 하기 전에 한번 조회수를 증가 시키기
	public CommentsVO detail(int num) {
		hit(num);
		return commentsDao.detail(num);
	}


	public void del(int num) {
		commentsDao.del(num);
		
	}
}



