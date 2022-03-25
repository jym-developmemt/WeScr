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
import we.core.dao.UserPositionDao;
import we.core.dao.UserRoleDao;
import we.core.model.UserDepartmentModel;
import we.core.model.UserDepartmentRoleModel;
import we.core.model.UserGrantedAuthorityModel;
import we.core.model.UserRoleModel;
import we.core.service.UserGrantedAuthorityService;
import we.website.model.UserInfoModel;

/**
 * 用户部门职位校验器
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class UserDepartRoleGrantor implements AuthGrantor {

	@Autowired
	@Qualifier("userRoleService")
	private UserGrantedAuthorityService userRoleService;

	@Autowired
	@Qualifier("userDepartmentPositionService")
	private UserGrantedAuthorityService userDepartmentPositionService;

	@Autowired
	private VersionHolder versionHolder;

	@Autowired
	private UserPositionDao userPositionDao;

	@Autowired
	private UserRoleDao userRoleDao;

	/**
	 * 权限类型
	 */
	@Override
	public String getAuthType() {
		return AuthGrantorType.USER_DEPARTMENT_ROLE;
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
		List<UserGrantedAuthorityModel> departmentPositionList = userDepartmentPositionService.searchUserGrantedAuthority(param);
		List<UserGrantedAuthorityModel> userRoleList = userRoleService.searchUserGrantedAuthority(param);

		for (String departRoleExpression : args.split(",")) {
			boolean result = false;

			String departmentId = departRoleExpression.split(":")[0];

			for (UserGrantedAuthorityModel userGrantedAuthorityModel : departmentPositionList) {
				// 转为正则表达式进行判断
				String regex = departmentId.replaceAll("%", "\\\\w*").replaceAll("_", "\\\\w");
				regex = "^" + regex + "$";
				if (Pattern.matches(regex, userGrantedAuthorityModel.getValue())) {
					result = true;
					break;
				}
			}
			if (result == false) {
				continue;

			}

			String roleId = departRoleExpression.split(":")[1];
			for (UserGrantedAuthorityModel userGrantedAuthorityModel : userRoleList) {
				// 转为正则表达式进行判断
				String regex = roleId.replaceAll("%", "\\\\w*").replaceAll("_", "\\\\w");
				regex = "^" + regex + "$";
				if (Pattern.matches(regex, userGrantedAuthorityModel.getValue())) {
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
		List<UserGrantedAuthorityModel> rtnList = new ArrayList<UserGrantedAuthorityModel>();

		UserInfoModel param = new UserInfoModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setUserId(userInfo.getName());
		List<UserGrantedAuthorityModel> departmentPositionList = userDepartmentPositionService.searchUserGrantedAuthority(param);
		List<UserGrantedAuthorityModel> userRoleList = userRoleService.searchUserGrantedAuthority(param);

		for (UserGrantedAuthorityModel department : departmentPositionList) {
			for (UserGrantedAuthorityModel userRole : userRoleList) {
				UserDepartmentRoleModel departmentRole = new UserDepartmentRoleModel();
				departmentRole.setDepartmentId(department.getValue());
				departmentRole.setRoleId(userRole.getValue());
				rtnList.add(departmentRole);
			}
		}

		return rtnList;
	}

	/**
	 * 取得权限用户
	 */
	public List<String> searchUserInfo(String args) throws Exception {
		String[] departRole = args.split(":");

		UserDepartmentModel param1 = new UserDepartmentModel();
		param1.setVersionId(versionHolder.getLastVersion());
		param1.setDepartmentId(departRole[0]);
		List<UserInfoModel> userInfoList = userPositionDao.searchDepartmentUser(param1);

		List<String> userList = new ArrayList<String>();
		for (UserInfoModel userInfo : userInfoList) {
			userList.add(userInfo.getUserId());
		}

		UserRoleModel param2 = new UserRoleModel();
		param2.setVersionId(versionHolder.getLastVersion());
		param2.setRoleId(args);
		userInfoList = userRoleDao.searchRoleUser(param2);
		List<String> rtnList = new ArrayList<String>();
		for (UserInfoModel userInfo : userInfoList) {
			if (userList.contains(userInfo.getUserId())) {
				rtnList.add(userInfo.getUserId());
			}
		}

		userInfoList = userRoleDao.searchDepartmentRoleUser(param2);
		for (UserInfoModel userInfo : userInfoList) {
			if (userList.contains(userInfo.getUserId())) {
				rtnList.add(userInfo.getUserId());
			}
		}

		userInfoList = userRoleDao.searchPositionRoleUser(param2);
		for (UserInfoModel userInfo : userInfoList) {
			if (userList.contains(userInfo.getUserId())) {
				rtnList.add(userInfo.getUserId());
			}
		}
		return rtnList;
	}
}
