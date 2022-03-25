/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.website.dao.DepartmentInfoDao;
import we.website.model.DepartmentInfoModel;
import we.website.service.DepartmentInfoService;

/**
 * 部门信息Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class DepartmentInfoServiceImpl extends BaseService implements DepartmentInfoService {

	@Autowired
	private DepartmentInfoDao departmentInfoDao;

	/**
	 * 部门信息取得
	 */
	public DepartmentInfoModel findDepartmentInfo(DepartmentInfoModel departmentInfo) {
		return departmentInfoDao.findDepartmentInfo(departmentInfo);
	}

	/**
	 * 部门信息取得
	 */
	public List<DepartmentInfoModel> selectDepartmentInfo(DepartmentInfoModel departmentInfo) {
		return departmentInfoDao.selectDepartmentInfo(departmentInfo);
	}
}
