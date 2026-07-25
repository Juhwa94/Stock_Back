package kr.co.ictedu.projectBack.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Alias("ofvo")
@Setter
@Getter
public class OrderFormVO {

	private int ofnum;
	private String oname;		// 대표자 이름
	private String oaddr;		// 주소
	private String ophone;		// 연락처
	private int mnum;
	private String oimg;		// 서명 이미지
	private String ofcompany;	// 상호명
	// 이미지 파일 저장
	private MultipartFile ofile;
	private List<OrderItemVO> orderItem;
}
