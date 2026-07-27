package kr.co.ictedu.projectBack.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("svo")
@Getter
@Setter
public class StockVO {
	private Integer snum;
	private String sisbn;
	private String sname;
	private String spublisher;
	private String sauthor;
	private int samount;
	private int sprice;
	private String scategory;
	private String sdate;
	private int membernum;
	// 1:N 즉 collection관계
	private List<StockImagesVO> getimglist;
}
