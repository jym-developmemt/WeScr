/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.dao;

import java.util.List;

import we.core.model.UserDepartmentModel;
import we.core.model.UserDepartmentPositionModel;
import we.core.model.UserPositionModel;
import we.website.model.UserInfoModel;

/**
 * 用户部门职位信息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface UserPositionDao {
	/**
	 * 用户部门信息一览取得
	 */
	public List<UserDepartmentModel> searchUserDepartment(UserInfoModel param);

	/**
	 * 用户职位信息一览取得
	 */
	public List<UserPositionModel> searchUserPosition(UserInfoModel param);

	/**
	 * 用户部门职位信息一览取得
	 */
	public List<UserDepartmentPositionModel> searchUserDepartmentPosition(UserInfoModel param);

	/**
	 * 部门用户一览取得
	 */
	public List<UserInfoModel> searchDepartmentUser(UserDepartmentModel param);

	/**
	 * 职位用户一览取得
	 */
	public List<UserInfoModel> searchPositionUser(UserPositionModel param);

	/**
	 * 部门职位用户一览取得
	 */
	public List<UserInfoModel> searchDepartmentPositionUser(UserDepartmentPositionModel param);
}
