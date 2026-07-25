package kr.co.ictedu.projectBack.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.projectBack.vo.CommentsVO;
import kr.co.ictedu.projectBack.vo.CommunityVO;

@Mapper
public interface CommentsDao {

    void add(CommentsVO  vo);

    List<CommentsVO> list(Map<String,String> map);

    int totalCount(Map<String,String> map);

//    CommentsVO detail(int num);

    void del(int num);

    void update(CommentsVO vo);
}