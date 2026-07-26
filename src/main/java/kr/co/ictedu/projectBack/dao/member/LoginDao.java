package kr.co.ictedu.projectBack.dao.member;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.projectBack.vo.MemberVO;


@Mapper
public interface LoginDao {
	@Select("SELECT MNUM, ID, NAME, NICK, EMAIL, VERIFIED, MPHONE, GRADE, STORECODE, STOREADDR, LOGINTYPE, AUTHORITY, REGDATE FROM MEMBER WHERE EMAIL=#{email} AND PWD=#{pwd}")
	Map<String, Object> loginCheck(MemberVO vo);
}