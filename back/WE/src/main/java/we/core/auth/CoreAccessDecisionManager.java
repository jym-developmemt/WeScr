/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.auth;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;
import we.core.CoreProperties;
import we.website.model.PageBaseInfoModel;
import we.website.service.PageBaseInfoService;

/**
 * 权限决策管理器
 *
 * @author cp0
 * @since 5.0
 */
@Component
public class CoreAccessDecisionManager implements AccessDecisionManager {

	private final String[] IGNORE_URIS = new String[] { "/siteInfo/find", "/siteInfo/message", "/siteInfo/menu",
			"/pageInfo/group", "/userInfo/find" };

	private final String URI_SEARCH_PAGE = "/pageInfo/find";

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CoreProperties coreProperties;

	@Autowired
	private PageBaseInfoService pageInfoService;

	@Autowired
	private AuthorizationManager authorizationManager;

	/**
	 * 页面权限决策
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		// URI校验
		try {
			FilterInvocation invocation = (FilterInvocation) object;
			HttpServletRequest request = invocation.getRequest();
			String requestUri = request.getRequestURI();
			if (StringUtils.hasText(requestUri)) {
				for (String ignoreUri : IGNORE_URIS) {
					if (CommonUtil.equals(request.getContextPath() + ignoreUri, requestUri)) {
						return;
					}
				}
			}

			String groupId = null;
			String pageId = null;

			if (CommonUtil.equals(request.getContextPath() + URI_SEARCH_PAGE, requestUri)) {
				groupId = request.getParameter("groupId");
				pageId = request.getParameter("pageId");
			}

			if (StringUtils.isEmpty(groupId) || StringUtils.isEmpty(pageId)) {
				String referer = request.getHeader("Referer");
				String[] urlInfo = referer.split("[?]")[0].split("/");
				if (urlInfo.length >= 2) {
					groupId = urlInfo[urlInfo.length - 2];
					pageId = urlInfo[urlInfo.length - 1];
				}
			}

			if (StringUtils.hasText(groupId) && StringUtils.hasText(pageId)) {

				PageBaseInfoModel param = new PageBaseInfoModel();
				param.setGroupId(EncryptorUtil.decryptString(groupId, coreProperties.getDesSalt()));
				param.setPageId(EncryptorUtil.decryptString(pageId, coreProperties.getDesSalt()));

				PageBaseInfoModel pageInfo = pageInfoService.findPageBaseInfo(param);
				if (pageInfo == null || StringUtils.isEmpty(pageInfo.getAuthType())) {
					return;
				}

				if (authorizationManager.hasAuthority(pageInfo.getAuthType(), pageInfo.getAuthValue(), authentication)) {
					return;
				}
			}
		} catch (Exception e) {
			logger.error("权限校验失败", e);
		}

		throw new InsufficientAuthenticationException("Access Denied.");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
