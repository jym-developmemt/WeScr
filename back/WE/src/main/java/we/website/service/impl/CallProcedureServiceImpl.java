/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.core.model.DataSourceDataModel;
import we.website.dao.CallProcedureDao;
import we.website.dao.DataSourceColumnDao;
import we.website.model.DataSourceColumnModel;
import we.website.service.CallProcedureService;
import we.website.service.DataSourceColumnService;

/**
 * 数据源列信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class CallProcedureServiceImpl extends BaseService implements CallProcedureService {

	// 数据源列信息Dao
	@Autowired
	private CallProcedureDao callProcedureDao;

	@Override
	public List<Map<String, Object>> checkProcedure(DataSourceDataModel DataSourceDataModel) {
		
		return callProcedureDao.checkProcedure(DataSourceDataModel);
	}

	
	
}
