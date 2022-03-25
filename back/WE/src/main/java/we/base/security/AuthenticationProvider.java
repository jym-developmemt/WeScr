/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.security;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;
import we.core.CoreProperties;
import we.core.auth.CliendAuthService;
import we.website.model.UserInfoModel;
import we.website.service.UserInfoService;
import we.wechat.WxUserManager;

/**
 * 用户密码认证模式
 *
 * @author cp0
 * @since 5.0
 */
@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CoreProperties websiteProp;

	// 用户信息Service
	@Autowired
	private List<CliendAuthService> userDetailsServiceList;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private WxUserManager wxUserManager;

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 用户查询
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		// 从请求体取得Cliend ID
		String clientId = CommonUtil.toString(((Map<String, Object>) authentication.getDetails()).get("client_id"));
		if (StringUtils.isEmpty(clientId)) {
			// 从请求头取得Cliend ID
			try {
				String[] authorization = CommonUtil.getRequest().getHeader("Authorization").split(" ");
				if ("Basic".equalsIgnoreCase(authorization[0])) {
					clientId = EncryptorUtil.decodeStringBase64(authorization[1]).split(":")[0];
				}
			} catch (Exception e) {
			}
		}
		if (StringUtils.isEmpty(clientId)) {
			throw new BadCredentialsException("No Client ID");
		}

		// 用户信息
		UserDetails userDetails = null;

		// 用户信息检索
		for (CliendAuthService cliendAuthService : userDetailsServiceList) {
			if (cliendAuthService.supports(clientId)) {
				// 查询用户信息
				userDetails = cliendAuthService.loadUserByUsername(username);

				// 密码校验
				if (userDetails != null && cliendAuthService.checkPassword()) {
					if (checkPassword(userDetails, authentication) == false) {
						continue;
					}
				}

				if (userDetails != null) {
					return userDetails;
				}
			}
		}

		// 用户信息未找到
		throw new InternalAuthenticationServiceException("s.common.error.usernotfound." + clientId);
	}

	/**
	 * 密码校验
	 */
	private boolean checkPassword(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		// 密码未输入
		if (authentication.getCredentials() == null) {
			return false;
		}

		// 密码匹配
		String presentedPassword = authentication.getCredentials().toString();
		if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			return false;
		}

		return true;
	}

	/**
	 * 附加处理
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		// 企业微信用户ID
		String qywxUserId = CommonUtil
				.toString(((Map<String, Object>) authentication.getDetails()).get("qywx_user_id"));
		// 企业微信公开ID
		String qywxOpenId = CommonUtil
				.toString(((Map<String, Object>) authentication.getDetails()).get("qywx_open_id"));

		if (StringUtils.hasText(qywxUserId) || StringUtils.hasText(qywxOpenId)) {
			// 用户ID转OpenID
			if (StringUtils.isEmpty(qywxOpenId)) {
				try {
					qywxOpenId = wxUserManager.convertToOpenid(qywxUserId);
				} catch (Exception e) {
					logger.warn("企业微信用户ID转OpenID时发生错误", e);
				}
			}

			// 保存企业微信用户信息
			UserInfoModel param = new UserInfoModel();
			param.setUserId(userDetails.getUsername());
			param.setQywxUserId(qywxUserId);
			param.setQywxOpenId(qywxOpenId);
			userInfoService.updateUserInfo(param);
		}

		// 更新用户登陆时间
		if (!CommonUtil.equals(websiteProp.getAdminId(), userDetails.getUsername())
				&& websiteProp.isUpdateLoginDate()) {
			userInfoService.updateLoginDate(userDetails.getUsername());
		}
	}
}
