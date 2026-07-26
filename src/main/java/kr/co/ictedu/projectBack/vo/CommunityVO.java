package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Alias("comivo")
@Getter
@Setter
public class CommunityVO {
	private int cnum;
	private int chit;
	private String ctitle;
	private String cwriter;
	private String ccontent;
	private String cimgn;	
	private String cdate;
	// 이미지 저장용
	private MultipartFile mfile;
}
