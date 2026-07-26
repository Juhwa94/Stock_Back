package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Alias("invo")
@Getter
@Setter
public class InquiryVO {

	private int inum;
	private String ititle;
	private String iwriter;
	private String icontent;
	private String idate;
	private int membernum;
	private String imgn;
	private MultipartFile mfile;
}
