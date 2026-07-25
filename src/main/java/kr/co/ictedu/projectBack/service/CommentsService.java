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
    private CommentsDao comtDao;

    public void add(CommentsVO vo) {
        comtDao.add(vo);
    }
    
	public void update(CommentsVO vo) {
		comtDao.update(vo);
	}

    public List<CommentsVO> list(Map<String, String> map) {
        return comtDao.list(map);
    }

    public int totalCount(Map<String, String> map) {
        return comtDao.totalCount(map);
    }

//    public CommentsVO detail(int num) {
//        return comtDao.detail(num);
//    }

    public void del(int num) {
    	comtDao.del(num);
    }
}