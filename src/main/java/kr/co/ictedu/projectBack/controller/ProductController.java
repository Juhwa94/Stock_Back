package kr.co.ictedu.projectBack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.projectBack.service.ProductService;
import kr.co.ictedu.projectBack.vo.ProductFormVO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/productForm")
	public void handleProductSubmit(@RequestBody ProductFormVO productFormVO) {
		productService.addProduct(productFormVO);
	}
}
