package we.website.dao;

import java.util.List;

import we.website.model.jym.GoodsPropertyModel;

/**
 * 商品属性
 * 
 * @author dell
 *
 */
public interface JymGoodsPropertyDao {

	public List<GoodsPropertyModel> searchGoodsPropertiesById(List<String> externalGoodsId);
	
	public void insertData(GoodsPropertyModel externalGoodsId);
}
