package kr.co.ictedu.projectBack.controller.survey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.projectBack.service.survey.SurveyService;
import kr.co.ictedu.projectBack.vo.SurveyResultVO;
import kr.co.ictedu.projectBack.vo.SurveyVO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

	@Autowired
	private SurveyService service;
	
	/**
	 * @param SurveyVO
	 * @detail 
	 */
	@PostMapping("/addSurvey")
	public ResponseEntity<String> insertSurvey(@RequestBody SurveyVO svo) {
		service.addSurvey(svo);
		return ResponseEntity.ok("success");
	}
	
	/**
	 * @param vo
	 * @detail
	 */
	@PostMapping("/addResult")
	public ResponseEntity<String> addResult(@RequestBody List<SurveyResultVO> srlist) {
		
		service.addResult(srlist);
		return ResponseEntity.ok("success");
		
	}
	
	/**
	 * @return
	 * @detail
	 */
	@GetMapping("/selectSurvey")
	public Map<String, Object> getSurvey() {
		Map<String, Object> surveyDataMap = service.selectSurvey();
		return surveyDataMap;
	}
	
	@GetMapping("/getSdate")
	public List<Map<String,Object>> getAllSdate() {
		return service.getAllSdate();
	}
	
	@GetMapping("/getAvgs")
	public Map<String, Object> getAvgs(@RequestParam(name = "svnum", required = false, defaultValue = "0") int svnum) {
		Map<String, Object> avgMap = service.getAvgs(svnum);
		return avgMap;
	}

	@GetMapping("/getRequest")
	public List<Map<String, Object>> getAllRequest() {
		List<Map<String, Object>> reqMap = service.getAllRequest();
		return reqMap;
	}
}
