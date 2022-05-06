package we.website.service.jym.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import we.base.util.CommonUtil;
import we.website.dao.JymGoodsEntityDao;
import we.website.dao.JymPropertySellerDao;
import we.website.dao.JymSellerGoodsPropertyDao;
import we.website.model.jym.GoodsEntityModel;
import we.website.model.jym.PropertySellerModel;
import we.website.model.jym.SellerGoodsPropertyModel;
import we.website.service.jym.SellerGoodsPropertyService;

@Service
public class SellerGoodsPropertyServiceImp implements  SellerGoodsPropertyService {

	protected static Logger logger = LoggerFactory.getLogger(SellerGoodsPropertyServiceImp.class);

	@Autowired
	private JymGoodsEntityDao jymGoodsEntityDao;

	@Autowired
	private JymPropertySellerDao jymPropertySellerDao;
	
	@Autowired
	private JymSellerGoodsPropertyDao jymSellerGoodsPropertyDao;

	@Override
	public boolean createData(List<String> externalGoodsIds) {

		// 查询商品基本信息
		List<GoodsEntityModel> goodsList = jymGoodsEntityDao.searchGoodsListById(externalGoodsIds);
		
		if (goodsList.size() >0) {
			this.createDataByGoodsEntity(goodsList);
		}
	
		return true;
	}
	
	@Override
	public boolean createDataByGoodsEntity(List<GoodsEntityModel> externalGoodsIds) {
		
		for (GoodsEntityModel tmpGoods : externalGoodsIds) {
			SellerGoodsPropertyModel sgp = new SellerGoodsPropertyModel();
			String tmpValue = CommonUtil.getRandomString(10);
			
			// 查询卖家属性
			PropertySellerModel psCondition = new PropertySellerModel();
			psCondition.setCid(tmpGoods.getServerInfoModel().getCid());
			List<PropertySellerModel> ps = jymPropertySellerDao.searchData(psCondition);
			
			if (ps.size() >0) {
				// 删除游戏属性卖家属性关联表
				sgp.setExternalGoodsId(tmpGoods.getExternalGoodsId());
				jymSellerGoodsPropertyDao.deleteData(sgp);
			}
			
			for (PropertySellerModel tmpPs : ps) {
				
				// 添加游戏属性卖家属性关联表
				sgp = new SellerGoodsPropertyModel();				
				sgp.setExternalGoodsId(tmpGoods.getExternalGoodsId());
				sgp.setPropertyId(tmpPs.getPropertyId());
				if (StringUtils.hasText(tmpPs.getValue())){
					sgp.setValue(tmpPs.getValue());
				}else {
					sgp.setValue(tmpValue);
				}
				
				jymSellerGoodsPropertyDao.insertData(sgp);
			}
			
		}
		
		return true;
	}
}
