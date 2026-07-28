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
		
//		int index = 0;
//		int fk = orderDao.fkSelect();
						
		List<OrderItemVO> list = ofvo.getOrderItem();
		for (OrderItemVO itemvo : list) {
			int price = itemvo.getOiprice();
			int amount = itemvo.getOiamount();
			int sumprice = price * amount;
			itemvo.setOisumprice(sumprice);
		}
		orderDao.addOrderItem(list);
	}
}
