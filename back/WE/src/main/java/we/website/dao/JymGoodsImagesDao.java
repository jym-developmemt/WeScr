package we.website.dao;

import java.util.List;

import we.website.model.jym.GoodsImagesModel;

/**
 * 商品图片
 * 
 * @author dell
 *
 */
public interface JymGoodsImagesDao {

	public List<GoodsImagesModel> searchGoodsImagesById(List<String> externalGoodsId);
	
	public void insertGoodsImage(GoodsImagesModel goodsImages);
	
	public void deleteGoodsImage(GoodsImagesModel goodsImages);
}
