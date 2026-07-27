package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("sqvo")
public class SurveyQuestionsVO {
	
	private SurveyVO survey;
	
	private Integer svnum;
	private Integer questions_id;
	private String questions_text;
	
}
