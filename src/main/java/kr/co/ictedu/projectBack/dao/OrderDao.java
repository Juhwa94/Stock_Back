package kr.co.ictedu.projectBack.dao;

//kr.co.ictedu.projectBack.dao.OrderDao
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.projectBack.vo.OrderFormVO;
import kr.co.ictedu.projectBack.vo.OrderItemVO;

@Mapper
public interface OrderDao {

	void addOrderForm(OrderFormVO ofvo);
	void addOrderItem(List<OrderItemVO> oivo);

}
