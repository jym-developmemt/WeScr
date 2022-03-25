/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import we.base.base.BaseFacade;
import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;
import we.base.util.VersionHolder;
import we.core.CoreProperties;
import we.core.auth.AuthorizationManager;
import we.website.dto.SiteInfoDto;
import we.website.facade.MenuInfoFacade;
import we.website.model.MenuInfoModel;
import we.website.model.SiteInfoModel;
import we.website.service.MenuInfoService;
import we.website.service.SiteInfoService;

/**
 * 站点信息Facade
 *
 * @author cp0
 * @since 2.0
 */
@Service
public class MenuInfoFacadeImpl extends BaseFacade implements MenuInfoFacade {

	@Autowired
	private CoreProperties coreProperties;

	@Autowired
	private VersionHolder versionHolder;

	@Autowired
	private AuthorizationManager authorizationManager;

	@Autowired
	private SiteInfoService siteInfoService;

	@Autowired
	private MenuInfoService menuInfoService;

	/** 菜单信息取得 */
	@Override
	public List<MenuInfoModel> searchMenuInfo(SiteInfoDto processInfo) throws Exception {
		// 站点ID校验
		if (StringUtils.hasText(processInfo.getSiteId()) == false) {
			logger.error("站点ID未设定");
			throw new FacadeException("s.common.error.param");
		}

		// 菜单类型校验
		if (StringUtils.hasText(processInfo.getMenuType()) == false) {
			logger.error("菜单类型未设定");
			throw new FacadeException("s.common.error.param");
		}

		SiteInfoModel siteInfo = new SiteInfoModel();
		siteInfo.setVersionId(versionHolder.getLastVersion());
		siteInfo.setSiteId(processInfo.getSiteId());

		// 站点校验
		siteInfo = siteInfoService.findSiteInfo(siteInfo);
		if (siteInfo == null) {
			logger.error("站点信息未找到");
			throw new FacadeException("s.common.error.none");
		}

		String menuId = null;
		if (CommonUtil.equals(processInfo.getMenuType(), "1")) {
			menuId = siteInfo.getPortalMenuId();
		} else if (CommonUtil.equals(processInfo.getMenuType(), "2")) {
			menuId = siteInfo.getConsoleMenuId();
		} else if (CommonUtil.equals(processInfo.getMenuType(), "3")) {
			menuId = siteInfo.getMobileMenuId();
		}

		// 菜单ID校验
		if (StringUtils.hasText(menuId) == false) {
			logger.error("菜单ID未设定");
			throw new FacadeException("s.common.error.param");
		}
		// 登陆者ID
		Authentication loginUser = SecurityContextHolder.getContext().getAuthentication();

		List<MenuInfoModel> totalMenuInfoList = new ArrayList<MenuInfoModel>();

		// 取得管理菜单
		if (CommonUtil.equals(processInfo.getMenuType(), "2")) {
			if (CommonUtil.equals(loginUser.getName(), coreProperties.getAdminId())) {
				totalMenuInfoList.addAll(searchSubMenuList(coreProperties.getAdminMenuId(), loginUser));
			}
		}

		// 子菜单树取得
		totalMenuInfoList.addAll(searchSubMenuList(menuId, loginUser));

		return totalMenuInfoList;
	}

	/** 子菜单取得 */
	private List<MenuInfoModel> searchSubMenuList(String menuId, Authentication loginUser) throws Exception {
		// 返回菜单列表
		List<MenuInfoModel> rtnMenuList = new ArrayList<MenuInfoModel>();

		// 取得菜单列表
		List<MenuInfoModel> srcMenuList = menuInfoService.searchMenuInfoList(menuId);
		for (MenuInfoModel menuInfo : srcMenuList) {

			// 忽略节点
			if (CommonUtil.equals(menuInfo.getExtractFlg(), "2")) {
				continue;
			}

			// 权限校验
			String authType = menuInfo.getAuthType();
			String authValue = menuInfo.getAuthValue();
			if (StringUtils.hasText(authType)
					&& authorizationManager.hasAuthority(authType, authValue, loginUser) == false) {
				continue;
			}

			// 取得子菜单列表
			List<MenuInfoModel> subMenuList = searchSubMenuList(menuInfo.getMenuId(), loginUser);

			// 提取子节点
			if (CommonUtil.equals(menuInfo.getExtractFlg(), "1")) {
				// 父节点ID设定
				for (MenuInfoModel subMenuInfo : subMenuList) {
					subMenuInfo.setParentId(menuInfo.getParentId());
					rtnMenuList.add(subMenuInfo);
				}
				continue;
			}

			// 页面地址加密
			if (CommonUtil.equals(menuInfo.getMenuType(), "0")) {
				// 页面组ID
				String groupId = menuInfo.getGroupId();
				menuInfo.setGroupId(EncryptorUtil.encryptString(groupId, coreProperties.getDesSalt()));
				// 面面ID
				String pageId = menuInfo.getPageId();
				menuInfo.setPageId(EncryptorUtil.encryptString(pageId, coreProperties.getDesSalt()));
			}

			// 保存子菜单
			menuInfo.setChildMenuList(subMenuList);

			// 保存当前节点
			rtnMenuList.add(menuInfo);
		}

		return rtnMenuList;
	}
}
