/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.website.dao.ResourceInfoDao;
import we.website.model.ResourceInfoModel;
import we.website.service.ResourceInfoService;

/**
 * 资源信息Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class ResourceInfoServiceImpl extends BaseService implements ResourceInfoService {

	// 资源信息Dao
	@Autowired
	private ResourceInfoDao resourceInfoDao;

	/**
	 * 资源信息取得
	 */
	@Override
	public ResourceInfoModel findResourceInfo(String resourceId) {
		return resourceInfoDao.findResourceInfo(resourceId);
	}

	/**
	 * 资源信息追加
	 */
	@Override
	public int addResourceInfo(ResourceInfoModel resourceInfo) {
		resourceInfo.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return resourceInfoDao.addResourceInfo(resourceInfo);
	}

	/**
	 * 资源信息更新
	 */
	@Override
	public int updateResourceInfo(ResourceInfoModel resourceInfo) {
		resourceInfo.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return resourceInfoDao.updateResourceInfo(resourceInfo);
	}

	/**
	 * 资源信息删除
	 */
	@Override
	public int deleteResourceInfo(String resourceId) {
		return resourceInfoDao.deleteResourceInfo(resourceId);
	}

	/**
	 * 取得过期的临时资源
	 */
	@Override
	public List<ResourceInfoModel> searchExpiredResource() {
		return resourceInfoDao.searchExpiredResource();
	}

}
