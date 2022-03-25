/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import java.util.List;

import org.springframework.stereotype.Service;

import we.website.model.DepartmentInfoModel;

/**
 * 部门信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public interface DepartmentInfoService {
	/**
	 * 部门信息取得
	 */
	public DepartmentInfoModel findDepartmentInfo(DepartmentInfoModel departmentInfo);

	/**
	 * 部门信息取得
	 */
	public List<DepartmentInfoModel> selectDepartmentInfo(DepartmentInfoModel departmentInfo);
}
