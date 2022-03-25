/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.dao;

import java.util.List;

import we.core.model.UserRoleModel;
import we.website.model.UserInfoModel;

/**
 * 用户角色信息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface UserRoleDao {
	/**
	 * 用户角色信息一览取得
	 */
	public List<UserRoleModel> searchUserRole(UserInfoModel param);

	/**
	 * 用户部门角色信息一览取得
	 */
	public List<UserRoleModel> searchUserDepartmentRole(UserInfoModel param);

	/**
	 * 用户职位角色信息一览取得
	 */
	public List<UserRoleModel> searchUserPositionRole(UserInfoModel param);

	/**
	 * 角色用户信息一览取得
	 */
	public List<UserInfoModel> searchRoleUser(UserRoleModel param);

	/**
	 * 部门角色用户信息一览取得
	 */
	public List<UserInfoModel> searchDepartmentRoleUser(UserRoleModel param);

	/**
	 * 职位角色用户信息一览取得
	 */
	public List<UserInfoModel> searchPositionRoleUser(UserRoleModel param);
}
