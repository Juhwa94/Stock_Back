package kr.co.ictedu.projectBack.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.projectBack.dao.InquiryDao;
import kr.co.ictedu.projectBack.vo.InquiryVO;

@Service
public class InquiryService {
	
	@Autowired
	private InquiryDao inquiryDao;
	
	public void add (InquiryVO vo) {
		inquiryDao.add(vo);
	}
	public List<InquiryVO> list(Map<String,String> map) {
		return inquiryDao.list(map);
	}
	public int totalCount(Map<String, String> map) {
		return inquiryDao.totalCount(map);
	}
	public InquiryVO detail(int num) {
		return inquiryDao.detail(num);
	}
	public void delete(int num) {
		inquiryDao.delete(num);
	}
}
