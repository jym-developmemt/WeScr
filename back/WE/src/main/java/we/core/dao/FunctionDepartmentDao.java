/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.dao;

import java.util.List;

import we.core.model.FunctionDepartmentModel;
import we.website.model.UserInfoModel;

/**
 * 用户管理部门职位信息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface FunctionDepartmentDao {
	/**
	 * 角色管理部门信息一览取得
	 */
	public List<FunctionDepartmentModel> searchRoleFunctionDepartment(FunctionDepartmentModel param);

	/**
	 * 用户管理部门信息一览取得
	 */
	public List<FunctionDepartmentModel> searchUserFunctionDepartment(UserInfoModel param);

	/**
	 * 管理部门角色信息一览取得
	 */
	public List<FunctionDepartmentModel> searchFunctionDepartmentRole(FunctionDepartmentModel param);

	/**
	 * 管理部门用户信息一览取得
	 */
	public List<UserInfoModel> searchFunctionDepartmentUser(FunctionDepartmentModel param);
}
