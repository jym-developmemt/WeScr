package we.website.dao;

import java.util.List;

import we.website.model.jym.SellerGoodsPropertyModel;

/**
 * 卖家账号信息商品属性
 * 
 * @author dell
 *
 */
public interface JymSellerGoodsPropertyDao {

	public List<SellerGoodsPropertyModel> searchSellerPropertyById(List<String> externalGoodsId);
}
