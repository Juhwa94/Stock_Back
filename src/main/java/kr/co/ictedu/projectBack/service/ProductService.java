package kr.co.ictedu.projectBack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.projectBack.dao.ProductDao;
import kr.co.ictedu.projectBack.vo.OrderItemVO;
import kr.co.ictedu.projectBack.vo.ProductFormVO;
import kr.co.ictedu.projectBack.vo.ProductItemVO;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Transactional
	public void addProduct(ProductFormVO pfvo) {
		productDao.addProductForm(pfvo);
		
		int index = 0;
		int fk = productDao.fkSelect1();
		
		List<ProductItemVO> list = pfvo.getProductItem();
		for (ProductItemVO itemvo : list) {
			int price = itemvo.getPiprice();
			int amount = itemvo.getPiamount();
			int sumprice = price * amount;
			itemvo.setPisumprice(sumprice);
			
			itemvo.setPfnum(fk);
			productDao.addProductItem(list.get(index));
			index++;
		}
	}
}
