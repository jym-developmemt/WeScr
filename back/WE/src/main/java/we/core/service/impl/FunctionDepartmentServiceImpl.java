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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.core.dao.FunctionDepartmentDao;
import we.core.model.FunctionDepartmentModel;
import we.core.model.UserGrantedAuthorityModel;
import we.core.service.UserGrantedAuthorityService;
import we.website.model.UserInfoModel;

/**
 * 用户角色信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service("functionDepartmentService")
public class FunctionDepartmentServiceImpl extends BaseService implements UserGrantedAuthorityService {

	// 管理部门信息Dao
	@Autowired
	private FunctionDepartmentDao functionDepartmentDao;

	@Autowired
	@Qualifier("userRoleService")
	private UserGrantedAuthorityService userRoleService;

	/**
	 * 用户角色信息一览取得
	 */
	@Override
	public List<UserGrantedAuthorityModel> searchUserGrantedAuthority(UserInfoModel userInfo) {

		List<UserGrantedAuthorityModel> tempList = new ArrayList<UserGrantedAuthorityModel>();

		List<UserGrantedAuthorityModel> roleList = userRoleService.searchUserGrantedAuthority(userInfo);
		for (UserGrantedAuthorityModel roleInfo : roleList) {
			// 用户角色信息一览取得
			FunctionDepartmentModel param = new FunctionDepartmentModel();
			param.setRoleId(roleInfo.getValue());
			tempList.addAll(functionDepartmentDao.searchRoleFunctionDepartment(param));
		}

		// 用户部门角色信息一览取得
		tempList.addAll(functionDepartmentDao.searchUserFunctionDepartment(userInfo));

		return tempList.stream().distinct().collect(Collectors.toList());
	}
}
