package we.website.dao;

import java.util.Map;

public interface OrderNumberDao {
	/**
	 * 单号取得
	 */
	public String getOrderNoInsert(Map<String,String> map);

}
