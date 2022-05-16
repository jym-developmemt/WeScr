package we.website.dao;

import java.util.List;

import we.website.model.jym.GoodsEntityModel;

/**
 * 商品基本信息
 * 
 * @author dell
 *
 */
public interface JymGoodsEntityDao {

	public List<GoodsEntityModel> searchGoodsListById(List<String> externalGoodsId);
	
	public List<GoodsEntityModel> selectNotSendData();
	
	public void insertData(GoodsEntityModel goodEntityData);
	
	public void updateData(GoodsEntityModel goodEntityData);
}
