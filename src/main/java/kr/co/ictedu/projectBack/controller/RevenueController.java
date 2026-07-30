package kr.co.ictedu.projectBack.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.projectBack.service.RevenueService;
import kr.co.ictedu.projectBack.vo.RevenueVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/revenue")
public class RevenueController {

	@Autowired
	private RevenueService revenueService;
	
	@GetMapping("/revenueDataSet")
	public RevenueVO revenueDataSet(@RequestParam("rmonth") String rmonth) {
		System.out.println(rmonth);
		RevenueVO rvo = revenueService.returnRevenue(rmonth);	
		// "MARGIN" : 마진 , 나머지 : 컬럼명
		return rvo;
	}
	
	@GetMapping("/shareChartDataSet")
	public List<Map<String, Object>> shareChartDataSet(@RequestParam("rmonth") String rmonth) {
		System.out.println("원형차트 데이터셋 추출기 생성");
		return revenueService.returnShareChartData(rmonth);
	}
	
}
