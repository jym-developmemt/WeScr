package we.website.service.jym.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.website.dao.JymGoodsImagesDao;
import we.website.model.jym.GoodsImagesModel;
import we.website.service.jym.CreateImageDataService;

/**
 * 
 * @author dell
 *
 */
@Service
public class CreateImageDataServiceImp extends BaseService implements CreateImageDataService {

	protected static Logger logger = LoggerFactory.getLogger(CreateImageDataServiceImp.class);

	@Autowired
	private JymGoodsImagesDao jymGoodsImagesDao;
	
	@Override
	public boolean createImageData(List<Map<String, String>> externalGoodsIds) {
		String beforId = "";
		
		try {
			for (Map<String, String> tmpMap : externalGoodsIds) {
				String externalGoodsId = tmpMap.get("external_goods_id");
				
				GoodsImagesModel gi = new GoodsImagesModel();
				gi.setExternalGoodsId(externalGoodsId);
				
				// 已有数据先清空
				if (externalGoodsId != beforId) {
					jymGoodsImagesDao.deleteGoodsImage(gi);
				}
				
				gi.setImageUrl(tmpMap.get("image_url"));
				
				jymGoodsImagesDao.insertGoodsImage(gi);
				
				beforId = externalGoodsId;
			}
		} catch (Exception e) {	
			return false;
		}
		return true;
	}

}
