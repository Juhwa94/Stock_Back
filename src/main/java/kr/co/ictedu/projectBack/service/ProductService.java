package kr.co.ictedu.projectBack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.projectBack.dao.ProductDao;
import kr.co.ictedu.projectBack.vo.ProductFormVO;
import kr.co.ictedu.projectBack.vo.ProductItemVO;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Transactional
	public void addProduct(ProductFormVO pfvo) {
		productDao.addProductForm(pfvo);
		List<ProductItemVO> list = pfvo.getProductItem();
		productDao.addProductItem(list);
	}
}
