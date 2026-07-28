package kr.co.ictedu.projectBack.dao.stock;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.projectBack.vo.StockImagesVO;
import kr.co.ictedu.projectBack.vo.StockVO;

// kr.co.ictedu.projectBack.dao.stock.StockDao
@Mapper
public interface StockDao {
	void addStock(StockVO svo);

	void addStockImg(List<StockImagesVO> sivo);

	// sqlsession.selectList()
	// select id="list" resultType="map" parameterType="map">
	// resultType="map" 이기 때문에 List(Map<String, String> map);
	List<Map<String, Object>> stockList(Map<String, String> map);

	int totalCount(Map<String, String> map);

	// 회원용 재고 조회
	List<Map<String, Object>> myStockList(Map<String, String> map);

	int myStockTotalCount(Map<String, String> map);

	List<Map<String,Object>> stockDetail(int snum);
	
	// 재고 수정
	int updateStock(StockVO vo);
	
	// 재고 삭제
	void deleteStock(int snum);

}
