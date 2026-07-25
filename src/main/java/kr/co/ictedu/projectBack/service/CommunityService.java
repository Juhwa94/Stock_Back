package kr.co.ictedu.projectBack.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.projectBack.dao.CommunityDao;
import kr.co.ictedu.projectBack.vo.CommunityVO;

// service 빈객체 관리 선언
@Service
public class CommunityService {
	
	@Autowired
	private CommunityDao communityDao;
	
	public void add(CommunityVO vo) {
		communityDao.add(vo);
		
	}
	
	public void update(CommunityVO vo) {
		communityDao.update(vo);
	}

	public List<CommunityVO> list(Map<String, String> map) {
		return communityDao.list(map);
	}
	
	public int totalCount(Map<String, String> map) {
		return communityDao.totalCount(map);
	}
	
	public void hit(int num) {
		communityDao.hit(num);
	}

	// 상세보기 하기 전에 한번 조회수를 증가 시키기
	public CommunityVO detail(int num) {
		hit(num);
		return communityDao.detail(num);
	}
	public CommunityVO get(int num) {
		return communityDao.detail(num);
	}


	public void del(int num) {
		communityDao.del(num);
		
	}
}
