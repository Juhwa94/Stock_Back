package kr.co.ictedu.projectBack.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.projectBack.vo.InquiryVO;

@Mapper
public interface InquiryDao {
	void add(InquiryVO vo);
	List<InquiryVO> list(Map<String, String> map);
	int totalCount(Map<String, String> map);
	
	InquiryVO detail(int num);
	void delete(int num);
}