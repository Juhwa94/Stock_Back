package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("sivo")
@Getter
@Setter
public class StockImagesVO {
	private int stocksnum;
	private String stockimage;
}
