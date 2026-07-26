package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("mvo")
@Getter
@Setter
public class MemberVO {
	private int mnum;
	private String id;
	private String pwd;
	private String name;
	private String nick;
	private String email;
	private String verified;
	private String mphone;
	private String grade;
	private String storecode;
	private String storeaddr;
	private String logintype;
	private String authority;
	private String regdate;
}
