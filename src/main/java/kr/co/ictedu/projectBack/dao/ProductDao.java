package kr.co.ictedu.projectBack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.projectBack.vo.ProductFormVO;
import kr.co.ictedu.projectBack.vo.ProductItemVO;


@Mapper
public interface ProductDao {

	void addProductForm(ProductFormVO pfvo);
	void addProductItem(List<ProductItemVO> plist);	
}
