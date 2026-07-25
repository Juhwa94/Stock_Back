package kr.co.ictedu.projectBack.dao.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import kr.co.ictedu.projectBack.vo.MemberVO;

//kr.co.ictedu.projectBack.dao.member.MemberDao
@Mapper
public interface MemberDao {
	void insertMember(MemberVO vo);
	
	int countByEmail(String email);
	int checkEmail(String email);
	int countByNick(String nick);
	MemberVO getMemberByEmail(String email);
    int updateMember(MemberVO vo);
	int deleteMember(int mnum);
	
	// 회원 전체 조회
	List<MemberVO> memberList(Map<String, String> map);
	int totalCount(Map<String, String> map);
	
	// 체크된 회원 등급 변경
	int updateGrade(Map<String, Object> param);
}

