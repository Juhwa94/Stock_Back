package kr.co.ictedu.projectBack.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.projectBack.dao.OrderDao;
import kr.co.ictedu.projectBack.vo.OrderFormVO;
import kr.co.ictedu.projectBack.vo.OrderItemVO;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;

	@Transactional
	public void addOrder(OrderFormVO ofvo) {
		orderDao.addOrderForm(ofvo);
		
		int index = 0;
		int fk = orderDao.fkSelect();
						
		List<OrderItemVO> list = ofvo.getOrderItem();
		for (OrderItemVO itemvo : list) {
			int price = itemvo.getOiprice();
			int amount = itemvo.getOiamount();
			int sumprice = price * amount;
			itemvo.setOisumprice(sumprice);
			/*
				fk를 자식테이블에 할당하는 비지니스 로직
				1. 부모 테이블의 Vo에서 직접 fk를 추출하여 자식 테이블에 fk에 해당되는 프로퍼티에 값을 바인딩
				insert all문에서는 반드시 fk를 select key 속성을 통해 fk를 추출해야 작동됨을 확인 
			*/
			itemvo.setOfnum(fk);
			orderDao.addOrderItem(list.get(index));
			index++;
		}
		//ofvo.setMnum(5);
	}
}
