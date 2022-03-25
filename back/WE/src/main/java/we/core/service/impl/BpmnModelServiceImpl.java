/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.core.dao.BpmnModelDao;
import we.core.model.BpmnModel;
import we.core.service.BpmnModelService;

/**
 * 模型信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class BpmnModelServiceImpl extends BaseService implements BpmnModelService {

	// 页面基本信息Dao
	@Autowired
	private BpmnModelDao bpmnModelDao;

	/**
	 * 模型信息取得
	 */
	@Override
	public BpmnModel findBpmnModel(String modelId) {
		return bpmnModelDao.findBpmnModel(modelId);
	}
}
