package kr.co.ictedu.projectBack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.projectBack.vo.ReplyVO;

@Mapper
public interface ReplyDao {
	void addComment(ReplyVO reply);
	List<ReplyVO> listReply(int num);
}
