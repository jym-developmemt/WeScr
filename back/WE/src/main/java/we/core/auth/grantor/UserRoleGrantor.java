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
import we.core.dao.UserRoleDao;
import we.core.model.UserGrantedAuthorityModel;
import we.core.model.UserRoleModel;
import we.core.service.UserGrantedAuthorityService;
import we.website.model.UserInfoModel;

/**
 * 用户角色校验器
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class UserRoleGrantor implements AuthGrantor {

	@Autowired
	@Qualifier("userRoleService")
	private UserGrantedAuthorityService userRoleService;

	@Autowired
	private VersionHolder versionHolder;

	@Autowired
	private UserRoleDao userRoleDao;

	/**
	 * 权限类型
	 */
	@Override
	public String getAuthType() {
		return AuthGrantorType.USER_ROLE;
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
		List<UserGrantedAuthorityModel> userRoleList = userRoleService.searchUserGrantedAuthority(param);

		for (UserGrantedAuthorityModel grantedAuthority : userRoleList) {

			// 用户角色表达式
			String expression = args;
			for (String roleExpression : expression.split(",")) {
				// 转为正则表达式进行判断
				String regex = roleExpression.replaceAll("%", "\\\\w*").replaceAll("_", "\\\\w");
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

		// 用户角色
		UserInfoModel param = new UserInfoModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setUserId(userInfo.getName());
		return userRoleService.searchUserGrantedAuthority(param);
	}

	/**
	 * 取得权限用户
	 */
	public List<String> searchUserInfo(String args) throws Exception {
		UserRoleModel param = new UserRoleModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setRoleId(args);
		List<UserInfoModel> userList = userRoleDao.searchRoleUser(param);
		List<String> rtnList = new ArrayList<String>();
		for (UserInfoModel userInfo : userList) {
			rtnList.add(userInfo.getUserId());
		}

		userList = userRoleDao.searchDepartmentRoleUser(param);
		for (UserInfoModel userInfo : userList) {
			rtnList.add(userInfo.getUserId());
		}

		userList = userRoleDao.searchPositionRoleUser(param);
		for (UserInfoModel userInfo : userList) {
			rtnList.add(userInfo.getUserId());
		}

		return rtnList;
	}
}
