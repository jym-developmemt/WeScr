package we.website.service.jym.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.util.CommonUtil;
import we.website.dao.JymGoodsPropertyDao;
import we.website.model.jym.GoodsPropertyModel;
import we.website.service.jym.GoodsPropertyService;

@Service
public class GoodsPropertyServiceImp implements GoodsPropertyService {

	protected static Logger logger = LoggerFactory.getLogger(GoodsPropertyServiceImp.class);

	@Autowired
	private JymGoodsPropertyDao jymGoodsPropertyDao;

	@Override
	public boolean insertData(List<Map<String, Object>> goodsPropertyList) {

		if (goodsPropertyList != null) {
			for (Map<String, Object> tmpData : goodsPropertyList) {

				GoodsPropertyModel gpm = new GoodsPropertyModel();
				
				gpm.setExternalGoodsId(CommonUtil.toString(tmpData.get("external_goods_id")));
				
				gpm.setPropertyId(CommonUtil.toString(tmpData.get("property_id")));
				if (CommonUtil.toString(tmpData.get("parent_id")).equals("root")) {
					gpm.setParentId(CommonUtil.toString(tmpData.get("property_id")));
				}else {
					gpm.setParentId(CommonUtil.toString(tmpData.get("parent_id")));
				}
				gpm.setValue(CommonUtil.toString(tmpData.get("value")));
				
				jymGoodsPropertyDao.insertData(gpm);
			}
		}
			
		return true;
	}
}
