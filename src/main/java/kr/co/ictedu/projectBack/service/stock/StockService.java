package kr.co.ictedu.projectBack.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.projectBack.dao.stock.StockDao;
import kr.co.ictedu.projectBack.vo.StockImagesVO;
import kr.co.ictedu.projectBack.vo.StockVO;

@Service
public class StockService {
	@Autowired
	private StockDao stockDao;
	
	// transactionProcess 메서드 안에서
	// galleryDao.add(gvo); // 갤러리 정보
	// galleryDao.addImg(gvoList); // 갤러리 정보에 대한 멀티 업로드 이미지, 를 하나의 단위로 처리 해주기 때문에
	// 만약 중간에 오류가 발생하면 모두 rollback 처리하고
	// 오류가 발생하지 않으면 하나의 단위 commit
	@Transactional
	public void transationProcess(StockVO svo, List<StockImagesVO> sivoList) {
		stockDao.addStock(svo);
		stockDao.addStockImg(sivoList);
	}


	public List<Map<String, Object>> stockList(Map<String, String> map) {
		
		return stockDao.stockList(map);
	}

	public int totalCount(Map<String, String> map) {
		
		return stockDao.totalCount(map);
	}
	

	
	
	public List<Map<String, Object>> myStockList(Map<String, String> map) {
		
		return stockDao.myStockList(map);
	}
	
	public int myStockTotalCount(Map<String, String> map) {
		
		return stockDao.myStockTotalCount(map);
	}
	
	public List<Map<String,Object>> stockDetail(int snum) {
		return stockDao.stockDetail(snum);
	};

	public int updateStock(StockVO vo) {
	    return stockDao.updateStock(vo);
	}
	
	public void deleteStock(int snum) {
		stockDao.deleteStock(snum);
	};

}
