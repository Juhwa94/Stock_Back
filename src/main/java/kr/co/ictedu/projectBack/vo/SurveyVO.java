package kr.co.ictedu.projectBack.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("surveyvo")
public class SurveyVO {
	
	private List<SurveyQuestionsVO> questions;
	private List<SurveyResultVO> result;

	private Integer svnum;
	private Integer code;
	private String sub;
	private String sdate;
	
}
