package kr.co.ictedu.projectBack.dao.member;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.projectBack.vo.LoginLoggerVO;


@Mapper
public interface LogDao {
	@Insert("insert into loginlog values(loginlog_seq.nextVal,"
			+ "#{memberemail},#{reip},#{uagent},sysdate,sysdate,#{status})")
	public void addLoginLogging(LoginLoggerVO vo);
}

