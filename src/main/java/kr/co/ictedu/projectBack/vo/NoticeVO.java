package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
// 공지 사항 메인 페이지 상상도
@Alias("nomivo")
@Getter
@Setter

public class NoticeVO {
	
	private int nnum;
	private int nhit;
	private String ntitle;
	private String nwriter;
	private String ncontent;
	private String ndate;
	private MultipartFile mfile;
	
}
