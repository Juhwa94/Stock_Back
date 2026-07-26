package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("srvo")
public class SurveyResultVO {

	private MemberVO member;
	
	private Integer mnum;
	private Integer svnum;
	private Integer questions_id;
	private Integer rating;
	private String request;
	private String resdate;
	
}
