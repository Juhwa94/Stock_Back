package kr.co.ictedu.projectBack.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RevenueDao {

	void addRevenue(String rmonth);
}
