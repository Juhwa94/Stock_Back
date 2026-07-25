package kr.co.ictedu.projectBack.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("pfvo")
@Setter
@Getter
public class ProductFormVO {

	private int pfnum;
	private String pstartdate; // 시작인
	private String penddate;   // 마감일
	private List<ProductItemVO> productItem;
}
