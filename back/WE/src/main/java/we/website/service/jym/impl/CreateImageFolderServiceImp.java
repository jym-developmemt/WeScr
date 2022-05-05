package we.website.service.jym.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.website.dao.JymGoodsEntityDao;
import we.website.model.jym.GoodsEntityModel;
import we.website.service.jym.CreateImageFolderService;

/**
 * 
 * @author dell
 *
 */
@Service
public class CreateImageFolderServiceImp extends BaseService implements CreateImageFolderService {

	protected static Logger logger = LoggerFactory.getLogger(CreateImageFolderServiceImp.class);

	@Autowired
	private JymGoodsEntityDao jymGoodsEntityDao;
	
	@Override
	public List<String> getNotSendData() {
		
		List<String> rtnList = new ArrayList<String>();
		
		List<GoodsEntityModel> getData = jymGoodsEntityDao.selectNotSendData();
		
		for (GoodsEntityModel tmpData : getData) {
		
			rtnList.add(tmpData.getExternalGoodsId());
		}
		return rtnList;
	}

}
