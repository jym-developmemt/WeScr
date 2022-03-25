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
import we.core.model.UserGrantedAuthorityModel;
import we.core.model.UserPositionModel;
import we.core.service.UserGrantedAuthorityService;
import we.website.model.UserInfoModel;

/**
 * 职位校验器
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class UserPositionGrantor implements AuthGrantor {

	@Autowired
	@Qualifier("userPositionService")
	private UserGrantedAuthorityService userPositionService;

	@Autowired
	private VersionHolder versionHolder;

	@Autowired
	private UserPositionDao userPositionDao;

	/**
	 * 权限类型
	 */
	@Override
	public String getAuthType() {
		return AuthGrantorType.USER_POSITION;
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
		List<UserGrantedAuthorityModel> userPositionList = userPositionService.searchUserGrantedAuthority(param);

		for (UserGrantedAuthorityModel grantedAuthority : userPositionList) {

			// 用户职位表达式
			String expression = args;
			for (String positionExpression : expression.split(",")) {
				// 转为正则表达式进行判断
				String regex = positionExpression.replaceAll("%", "\\\\w*").replaceAll("_", "\\\\w");
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

		// 用户职位
		UserInfoModel param = new UserInfoModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setUserId(userInfo.getName());
		return userPositionService.searchUserGrantedAuthority(param);
	}

	/**
	 * 取得权限用户
	 */
	public List<String> searchUserInfo(String args) throws Exception {
		UserPositionModel param = new UserPositionModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setPositionId(args);
		List<UserInfoModel> userList = userPositionDao.searchPositionUser(param);

		List<String> rtnList = new ArrayList<String>();
		for (UserInfoModel userInfo : userList) {
			rtnList.add(userInfo.getUserId());
		}
		return rtnList;
	}
}
