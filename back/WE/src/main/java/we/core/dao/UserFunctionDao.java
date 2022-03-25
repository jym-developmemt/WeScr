/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.dao;

import java.util.List;

import we.core.model.UserFunctionModel;
import we.website.model.UserInfoModel;

/**
 * 用户功能信息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface UserFunctionDao {
	/**
	 * 用户功能信息一览取得
	 */
	public List<UserFunctionModel> searchUserFunction(UserInfoModel param);

	/**
	 * 用户部门功能信息一览取得
	 */
	public List<UserFunctionModel> searchUserDepartmentFunction(UserInfoModel param);

	/**
	 * 用户职位功能信息一览取得
	 */
	public List<UserFunctionModel> searchUserPositionFunction(UserInfoModel param);

	/**
	 * 功能用户信息一览取得
	 */
	public List<UserInfoModel> searchFunctionUser(UserFunctionModel param);

	/**
	 * 部门功能用户信息一览取得
	 */
	public List<UserInfoModel> searchDepartmentFunctionUser(UserFunctionModel param);

	/**
	 * 职位功能用户信息一览取得
	 */
	public List<UserInfoModel> searchPositionFunctionUser(UserFunctionModel param);
}
