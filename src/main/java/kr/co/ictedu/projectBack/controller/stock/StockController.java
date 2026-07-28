package kr.co.ictedu.projectBack.controller.stock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.ictedu.projectBack.common.PagingService;
import kr.co.ictedu.projectBack.service.stock.StockService;
import kr.co.ictedu.projectBack.vo.PageVO;
import kr.co.ictedu.projectBack.vo.StockImagesVO;
import kr.co.ictedu.projectBack.vo.StockVO;

@RestController
@RequestMapping("/api/stock")
public class StockController {
	@Autowired
	private StockService stockService;
	
	@Value("${spring.servlet.multipart.location}")
	private String filePath;
	
	@Autowired
	private PagingService pagingService;
	
	@PostMapping("/addStock")
	public ResponseEntity<?> addGallery(
			StockVO galleryVO,
			@RequestParam("images") MultipartFile[] images,
			HttpServletRequest request
			) {
		
		List<StockImagesVO> imageList = new ArrayList<>();
		
		File folder = new File(filePath, "stock");

		if (!folder.exists()) {
		    folder.mkdirs();
		}
		
		for(MultipartFile file : images) {
			if(!file.isEmpty()) {
				String oriFn = file.getOriginalFilename();
				File f = new File(filePath+"/stock/",oriFn);
				try {
					file.transferTo(f);
					StockImagesVO imageVO = new StockImagesVO(); // 이미지 관리 객체 생성
					imageVO.setStockimage(oriFn); // 이미지들이 이름을 vo에 저장
					imageList.add(imageVO); // 리스트에 저장
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		galleryVO.setGetimglist(imageList);
		
		try {
			stockService.transationProcess(galleryVO, imageList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Rollback");
		}
		return ResponseEntity.ok("재고 등록 성공");
	}
	
	@RequestMapping("/stockList")
	public Map<String, Object> stockList(
			@RequestParam Map<String, String> paramMap,
			HttpServletRequest request
			) {
		String cPage = paramMap.get("cPage");
		int totalCnt = stockService.totalCount(paramMap);
		PageVO pageVO = pagingService.makePage(totalCnt, cPage);
		
		// Json으로 응답 처리 - 페이징 처리된 결과 리스트와 정보
		Map<String, String> map = new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<Map<String, Object>> list = stockService.stockList(map);
		

		
		Map<String, Object> response = new HashMap<>();
		response.put("data", list); // 페이징 처리가 완료된 리스트를 저장한 데이터
		response.put("totalItems", pageVO.getTotalRecord()); // 전체 게시물의 count
		response.put("totalPages", pageVO.getTotalPage()); // 전체 페이지
		response.put("currentPage", pageVO.getNowPage()); // 현재 페이지
		response.put("startPage", pageVO.getStartPage()); // 블록의 시작
		response.put("endPage", pageVO.getEndPage()); // 블록의 끝
		
		return response;
	}
	
	// 상세보기 구현
	@GetMapping("/stockDetail")
	public ResponseEntity<?> detail(
	        @RequestParam("snum") int snum) {

	    List<Map<String,Object>> stock = stockService.stockDetail(snum);

	    return ResponseEntity.ok(stock);
	}
	
	@RequestMapping("/myStockList")
	public Map<String, Object> myStockList(
			@RequestParam Map<String, String> paramMap,
			HttpServletRequest request
			) {
		String cPage = paramMap.get("cPage");
		int totalCnt = stockService.myStockTotalCount(paramMap);
		PageVO pageVO = pagingService.makePage(totalCnt, cPage);
		
		// 세션에서 membernum 가져오기
		Integer membernum = (Integer) request.getSession().getAttribute("membernum");
		
		// Json으로 응답 처리 - 페이징 처리된 결과 리스트와 정보
		Map<String, String> map = new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<Map<String, Object>> list = stockService.myStockList(map);
		
		

		
		Map<String, Object> response = new HashMap<>();
		response.put("data", list); // 페이징 처리가 완료된 리스트를 저장한 데이터
		response.put("totalItems", pageVO.getTotalRecord()); // 전체 게시물의 count
		response.put("totalPages", pageVO.getTotalPage()); // 전체 페이지
		response.put("currentPage", pageVO.getNowPage()); // 현재 페이지
		response.put("startPage", pageVO.getStartPage()); // 블록의 시작
		response.put("endPage", pageVO.getEndPage()); // 블록의 끝
		
		return response;
	}
	
	@PutMapping("/updateStock")
	public ResponseEntity<?> updateStock(StockVO vo) {

	    int result = stockService.updateStock(vo);

	    return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/deleteStock")
	public ResponseEntity<?> deleteStock(@RequestParam("snum") int num) {
		stockService.deleteStock(num);
		return ResponseEntity.ok("재고 삭제 성공");
	}
}
