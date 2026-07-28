package kr.co.ictedu.projectBack.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.projectBack.dao.RevenueDao;
import kr.co.ictedu.projectBack.vo.RevenueVO;

@Service
public class RevenueService {

	@Autowired
	private RevenueDao revenueDao;

	@Transactional(rollbackFor = Exception.class)
	public RevenueVO returnRevenue(String rmonth) {
		String cleanMonth = rmonth.replace("\"", "");
		RevenueVO rvo = new RevenueVO();
		// 2. 자바 메모리에서 전체 총합을 누적할 변수들 초기화
		int totalQty = 0;
		int totalSales = 0;
		int totalCost = 0;
		int totalMargin = 0;

		int count = revenueDao.checkRevenueExists(cleanMonth);

		if (count == 0) {

			// 1. 해당 월의 원가, 수량, 판매금액 등의 원본 데이터를 리스트로 조회
			List<Map<String, Object>> firstRow = revenueDao.getRevenueByMonth(cleanMonth);

			// 3. 루프를 돌며 카테고리 분리 없이 전체 총합 연산 처리
			for (Map<String, Object> rows : firstRow) {
				int qty = ((Number) rows.get("RTOTALQTY")).intValue();
				int sales = ((Number) rows.get("RTOTALSALES")).intValue();
				int cost = ((Number) rows.get("RTOTALCOST")).intValue();
				int rowMargin = sales - cost; // 각 행의 마진

				totalQty += qty;
				totalSales += sales;
				totalCost += cost;
				totalMargin += rowMargin;
			}

			// 4. REVENUE 테이블(rmonth가 PK인 1:1 구조)에 넣을 VO 객체 생성 및 최종값 세팅

			String mon = cleanMonth + "-01";
			System.out.println("리베뉴 pk 입력값 : " + mon);
			System.out.println("리베뉴 totalQty 입력값 : " + totalQty);
			System.out.println("리베뉴 totalSales 입력값 : " + totalSales);
			System.out.println("리베뉴 totalCost 입력값 : " + totalCost);
			System.out.println("리베뉴 totalMargin 입력값 : " + totalMargin);
			rvo.setRmonth(mon);
			rvo.setRtotalqty(totalQty);
			rvo.setRtotalsales(totalSales);
			rvo.setRtotalcost(totalCost);
			rvo.setRtotalmargin(totalMargin);
			revenueDao.addRevenue(rvo);
			return rvo;
		} else {
			rvo = revenueDao.selectAllRevenue(cleanMonth);
			return rvo;
		}	
	}

	public List<Map<String, Object>> returnShareChartData(String rmonth) {
		return revenueDao.shareChartData(rmonth);
	}

}
