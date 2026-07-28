package kr.co.ictedu.projectBack.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.projectBack.vo.RevenueVO;

@Mapper
public interface RevenueDao {
//List<Map<String, Object>>
	void addRevenue(RevenueVO rvo);
	List<Map<String, Object>> getRevenueByMonth(String rmonth);
	
	List<Map<String, Object>> shareChartData(String rmonth);
	
	int checkRevenueExists(String rmonth);
	
	RevenueVO selectAllRevenue(String rmonth);
}
