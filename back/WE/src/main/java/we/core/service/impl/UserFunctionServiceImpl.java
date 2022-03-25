/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.core.dao.UserFunctionDao;
import we.core.model.UserGrantedAuthorityModel;
import we.core.service.UserGrantedAuthorityService;
import we.website.model.UserInfoModel;

/**
 * 用户功能信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service("userFunctionService")
public class UserFunctionServiceImpl extends BaseService implements UserGrantedAuthorityService {

	// 用户功能信息Dao
	@Autowired
	private UserFunctionDao userFunctionDao;

	/**
	 * 用户角色信息一览取得
	 */
	@Override
	public List<UserGrantedAuthorityModel> searchUserGrantedAuthority(UserInfoModel userInfo) {

		List<UserGrantedAuthorityModel> tempList = new ArrayList<UserGrantedAuthorityModel>();

		// 用户功能信息一览取得
		tempList.addAll(userFunctionDao.searchUserFunction(userInfo));

		// 用户部门功能信息一览取得
		tempList.addAll(userFunctionDao.searchUserDepartmentFunction(userInfo));

		// 用户职位功能信息一览取得
		tempList.addAll(userFunctionDao.searchUserPositionFunction(userInfo));

		return tempList.stream().distinct().collect(Collectors.toList());
	}
}
