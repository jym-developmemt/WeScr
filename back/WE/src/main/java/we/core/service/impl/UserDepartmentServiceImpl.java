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
import we.core.dao.UserPositionDao;
import we.core.model.UserGrantedAuthorityModel;
import we.core.service.UserGrantedAuthorityService;
import we.website.model.UserInfoModel;

/**
 * 用户部门信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service("userDepartmentService")
public class UserDepartmentServiceImpl extends BaseService implements UserGrantedAuthorityService {

	// 用户部门职位信息Dao
	@Autowired
	private UserPositionDao userPositionDao;

	/**
	 * 用户职位信息一览取得
	 */
	@Override
	public List<UserGrantedAuthorityModel> searchUserGrantedAuthority(UserInfoModel userInfo) {

		List<UserGrantedAuthorityModel> tempList = new ArrayList<UserGrantedAuthorityModel>();

		// 用户部门信息一览取得
		tempList.addAll(userPositionDao.searchUserDepartment(userInfo));

		return tempList.stream().distinct().collect(Collectors.toList());
	}
}
