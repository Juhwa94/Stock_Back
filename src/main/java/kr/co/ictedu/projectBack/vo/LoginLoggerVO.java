package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("lvo")
@Getter
@Setter 
public class LoginLoggerVO {
	private int lnum;
	private String memberemail;
	private String reip;
	private String uagent;
	private String sstime, eetime;
	private String status;
	
	
}
