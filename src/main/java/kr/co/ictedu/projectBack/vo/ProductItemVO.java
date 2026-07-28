package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("pivo")
@Setter
@Getter
public class ProductItemVO {

	private int pinum;
	private String piname;		// 도서명
	private String piisbn;		// 도서번호
	private int piprice;		// 단가
	private int piamount;		// 수량
	private int pisumprice;		// 1행의 합계 가격(가격 * 수량)
	private int piunitCost;		// 원가(도매단가)
	private int pfnum;			// pk
	private String picategory;  //카테고리
}