package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("oivo")
@Setter
@Getter
public class OrderItemVO {

	private int oinum;
	private String oiname;		// 도서명
	private String oipublisher; // 출판사
	private int oiprice;		// 단가
	private int oisumprice;		// 합계 금액
	private int oiamount;		// 수량
	private int ofnum;
}
