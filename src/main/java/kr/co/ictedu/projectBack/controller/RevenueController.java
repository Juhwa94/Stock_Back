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
	
	@CrossOrigin(origins = "https://stockfront-production.up.railway.app")
    @GetMapping("/revenueDataSet")
    public RevenueVO revenueDataSet(@RequestParam("rmonth") String rmonth) {
        // 프론트에서 큰따옴표("")가 포함되어 들어오는 경우 제거
        if (rmonth != null) {
            rmonth = rmonth.replace("\"", "");
        }
        System.out.println("수익 데이터 조회 rmonth: " + rmonth);
        
        RevenueVO rvo = revenueService.returnRevenue(rmonth);   
        return rvo;
    }
    
    @CrossOrigin(origins = "https://stockfront-production.up.railway.app")
    @GetMapping("/shareChartDataSet")
    public List<Map<String, Object>> shareChartDataSet(@RequestParam("rmonth") String rmonth) {
        // 프론트에서 큰따옴표("")가 포함되어 들어오는 경우 제거
        if (rmonth != null) {
            rmonth = rmonth.replace("\"", "");
        }
        System.out.println("원형차트 데이터셋 추출기 생성 rmonth: " + rmonth);
        
        return revenueService.returnShareChartData(rmonth);
    }
