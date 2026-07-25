package kr.co.ictedu.projectBack.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.ictedu.projectBack.service.OrderService;
import kr.co.ictedu.projectBack.vo.OrderFormVO;
import kr.co.ictedu.projectBack.vo.OrderItemVO;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Value("${spring.servlet.multipart.location}")
	private String filePath;

	@PostMapping("/orderForm")
	public void handleOrderSubmit(
		@RequestPart("orderData") OrderFormVO orderFormVO, // JSON 문자열로 넘어온 order 
		@RequestParam("signature") MultipartFile file // signature로 넘어온 파일
	) {
		if (!file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			File f = new File(filePath + "/signature/", originalFilename);
			try {
				file.transferTo(f);// 업로드 완료
				
		        String pureName = originalFilename;
		        if (originalFilename != null && originalFilename.contains(".")) {
		            pureName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
		        }
		        orderFormVO.setOimg(pureName); 
				orderFormVO.setOfile(file); // 이미지들의 이름을 vo저장
				
				//=========검증영역
				System.out.println(orderFormVO.getOfile()+ " : " + orderFormVO.getOimg());
				System.out.println(orderFormVO.getOname());
				
				List<OrderItemVO> volist =  orderFormVO.getOrderItem();
				
				for (OrderItemVO vo : volist) {
					System.out.println(vo.getOfnum());
					System.out.println(vo.getOiname());
					System.out.println(vo.getOinum());
				}
				
				orderService.addOrder(orderFormVO);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

		}
	}

}
