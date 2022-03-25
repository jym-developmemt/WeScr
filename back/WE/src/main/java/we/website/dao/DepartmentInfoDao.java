/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import java.util.List;

import we.website.model.DepartmentInfoModel;

/**
 * 部门信息Dao
 *
 * @author cp0
 * @since 3.0
 */
public interface DepartmentInfoDao {
	/**
	 * 部门信息取得
	 */
	public DepartmentInfoModel findDepartmentInfo(DepartmentInfoModel departmentInfo);

	/**
	 * 部门信息取得
	 */
	public List<DepartmentInfoModel> selectDepartmentInfo(DepartmentInfoModel departmentInfo);
}
