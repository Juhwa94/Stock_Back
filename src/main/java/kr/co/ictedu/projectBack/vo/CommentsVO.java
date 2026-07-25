package kr.co.ictedu.projectBack.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Alias("tomivo")
@Getter
@Setter
public class CommentsVO {
private int cnum;
private int communitynum;
private String cwriter;
private String ccontent;
private String cregdate;
private MultipartFile mfile;

}
