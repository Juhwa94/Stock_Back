package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("reply")
@Getter
@Setter
public class ReplyVO {

	private int rnum;
	private int rcode;
	private String rwriter;
	private String rcontent;
	private String rdate;
}
