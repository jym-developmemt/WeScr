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
import we.core.model.UserDepartmentPositionModel;
import we.core.model.UserGrantedAuthorityModel;
import we.core.service.UserGrantedAuthorityService;
import we.website.model.UserInfoModel;

/**
 * 用户部门职位校验器
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class UserDepartPositionGrantor implements AuthGrantor {

	@Autowired
	@Qualifier("userDepartmentPositionService")
	private UserGrantedAuthorityService userDepartmentPositionService;

	@Autowired
	private VersionHolder versionHolder;

	@Autowired
	private UserPositionDao userPositionDao;

	/**
	 * 权限类型
	 */
	@Override
	public String getAuthType() {
		return AuthGrantorType.USER_DEPARTMENT_POSITION;
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

		for (UserGrantedAuthorityModel grantedAuthority : departmentPositionList) {

			// 用户部门职位表达式
			String expression = args;
			for (String departPositionExpression : expression.split(",")) {
				// 转为正则表达式进行判断
				String regex = departPositionExpression.replaceAll("%", "\\\\w*").replaceAll("_", "\\\\w");
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

		// 用户部门职位
		UserInfoModel param = new UserInfoModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setUserId(userInfo.getName());
		return userDepartmentPositionService.searchUserGrantedAuthority(param);
	}

	/**
	 * 取得权限用户
	 */
	public List<String> searchUserInfo(String args) throws Exception {
		String[] departPosition = args.split(":");

		UserDepartmentPositionModel param = new UserDepartmentPositionModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setDepartmentId(departPosition[0]);
		param.setPositionId(departPosition[1]);
		List<UserInfoModel> userList = userPositionDao.searchDepartmentPositionUser(param);

		List<String> rtnList = new ArrayList<String>();
		for (UserInfoModel userInfo : userList) {
			rtnList.add(userInfo.getUserId());
		}
		return rtnList;
	}
}
