package kr.co.ictedu.projectBack.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.projectBack.vo.CommunityVO;

//kr.co.ictedu.projectBack.dao.CommunityDao
@Mapper
public interface CommunityDao {
	void add(CommunityVO vo);
	List<CommunityVO> list(Map<String, String> map);
	int totalCount(Map<String, String> map);
	void hit(int num);
	CommunityVO detail(int num);
	void del(int num);
	void update(CommunityVO vo);
}
