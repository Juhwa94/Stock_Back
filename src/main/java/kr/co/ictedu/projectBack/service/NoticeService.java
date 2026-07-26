package kr.co.ictedu.projectBack.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.projectBack.dao.NoticeDao;
import kr.co.ictedu.projectBack.vo.NoticeVO;

@Service
public class NoticeService {

	@Autowired
	private NoticeDao noticeDao;
	
	public void add(NoticeVO vo) {
		noticeDao.add(vo);
	}
	
	public List<NoticeVO> list(Map<String, String> map) {
		return noticeDao.list(map);
	}
	
	public int totalCount(Map<String, String> map) {
		return noticeDao.totalCount(map);
	}
	
	public void hit(int num) {
		noticeDao.hit(num);
	}
	
	public NoticeVO detail(int num) {
		hit(num);
		return noticeDao.detail(num);
	}
	
	public void del(int num) {
		noticeDao.del(num);
	}
}
