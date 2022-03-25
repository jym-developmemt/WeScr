/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.auth.grantor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.util.VersionHolder;
import we.core.auth.AuthGrantor;
import we.core.auth.AuthGrantorType;
import we.core.dao.FunctionDepartmentDao;
import we.core.dao.UserRoleDao;
import we.core.model.FunctionDepartmentModel;
import we.core.model.UserGrantedAuthorityModel;
import we.core.model.UserRoleModel;
import we.core.service.UserGrantedAuthorityService;
import we.website.model.UserInfoModel;

/**
 * 用户管理部门校验器
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class UserManageDepartGrantor implements AuthGrantor {

	@Autowired
	@Qualifier("functionDepartmentService")
	private UserGrantedAuthorityService functionDepartmentService;

	@Autowired
	private VersionHolder versionHolder;

	@Autowired
	private FunctionDepartmentDao functionDepartmentDao;

	@Autowired
	private UserRoleDao userRoleDao;

	/**
	 * 权限类型
	 */
	@Override
	public String getAuthType() {
		return AuthGrantorType.USER_MANAGE_DEPARTMENT;
	}

	/**
	 * 检查用户是否有该权限
	 */
	public boolean hasAuthority(Authentication userInfo, String args) throws Exception {
		// 匿名用户以外
		if (StringUtils.isEmpty(userInfo.getName()) || "anonymousUser".equals(userInfo.getName())) {
			return false;
		}

		UserInfoModel param = new UserInfoModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setUserId(userInfo.getName());
		List<UserGrantedAuthorityModel> functionDepartmentList = functionDepartmentService.searchUserGrantedAuthority(param);

		for (UserGrantedAuthorityModel grantedAuthority : functionDepartmentList) {

			// 用户部门表达式
			String expression = args;
			for (String departmentExpression : expression.split(",")) {
				// 转为正则表达式进行判断
				String regex = departmentExpression.replaceAll("%", "\\\\w*").replaceAll("_", "\\\\w");
				regex = "^" + regex + "$";
				if (Pattern.matches(regex, grantedAuthority.getValue())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 取得用户权限
	 */
	public List<UserGrantedAuthorityModel> searchUserGranted(Authentication userInfo) throws Exception {
		// 匿名用户以外
		if (StringUtils.isEmpty(userInfo.getName()) || "anonymousUser".equals(userInfo.getName())) {
			return new ArrayList<UserGrantedAuthorityModel>();
		}

		// 用户部门
		UserInfoModel param = new UserInfoModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setUserId(userInfo.getName());
		return functionDepartmentService.searchUserGrantedAuthority(param);
	}

	/**
	 * 取得权限用户
	 */
	public List<String> searchUserInfo(String args) throws Exception {
		String[] functionDepart = args.split(":");

		FunctionDepartmentModel param = new FunctionDepartmentModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setFunctionId(functionDepart[0]);
		param.setDepartmentId(functionDepart[1]);
		List<UserInfoModel> userList = functionDepartmentDao.searchFunctionDepartmentUser(param);

		List<String> rtnList = new ArrayList<String>();
		for (UserInfoModel userInfo : userList) {
			rtnList.add(userInfo.getUserId());
		}

		List<FunctionDepartmentModel> roleList = functionDepartmentDao.searchFunctionDepartmentRole(param);
		for (FunctionDepartmentModel roleInfo : roleList) {
			UserRoleModel userRole = new UserRoleModel();
			userRole.setVersionId(versionHolder.getLastVersion());
			userRole.setRoleId(roleInfo.getRoleId());
			List<UserInfoModel> roleUserList = userRoleDao.searchRoleUser(userRole);
			for (UserInfoModel userInfo : roleUserList) {
				rtnList.add(userInfo.getUserId());
			}
		}

		return rtnList;
	}
}
