package kr.co.ictedu.projectBack.dao.survey;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.projectBack.vo.SurveyQuestionsVO;
import kr.co.ictedu.projectBack.vo.SurveyResultVO;
import kr.co.ictedu.projectBack.vo.SurveyVO;

@Mapper
public interface SurveyDao {
	
	 void insertSurvey(SurveyVO vo);
	 
	 void insertQuestions(List<SurveyQuestionsVO> qlist);
	 
	 void insertResult(List<SurveyResultVO> rlist);
	 
	 void deleteSurvey();
	 
	 SurveyVO selectLatestSurvey();
	 
	 SurveyVO selectSurvey(int svnum);
	 
	 List<SurveyQuestionsVO> selectQuestions(int svnum);
	 
	 List<Map<String, Object>> selectAvg(int svnum);
	 
	 List<Map<String, Object>> selectSdate();

	 List<Map<String, Object>> selectAllRequest();
	 
}
