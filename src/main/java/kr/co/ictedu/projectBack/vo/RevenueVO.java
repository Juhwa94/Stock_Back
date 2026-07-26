package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("rvo")
@Setter
@Getter
public class RevenueVO {

	private String rmonth;		// 매출 기준 월
	private int rtotalqty;		// 총 판매 수량
	private int rtotalsales;	// 총 매출액
	private int rtotalcost;		// 총 원가
	private int rtotalmargin;	// 총 마진
	private int pnum;
}
