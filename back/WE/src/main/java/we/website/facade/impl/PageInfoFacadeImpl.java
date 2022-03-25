/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import we.base.base.BaseFacade;
import we.base.exception.FacadeException;
import we.base.util.EncryptorUtil;
import we.core.CoreProperties;
import we.website.dto.PageInfoDto;
import we.website.facade.PageInfoFacade;
import we.website.model.PageBaseInfoModel;
import we.website.model.PageDataSourceModel;
import we.website.model.PageElementSetRowModel;
import we.website.model.PageGroupInfoModel;
import we.website.service.PageBaseInfoService;
import we.website.service.PageDataSourceService;
import we.website.service.PageElementService;
import we.website.service.PageGroupInfoService;

/**
 * 页面信息Facade
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class PageInfoFacadeImpl extends BaseFacade implements PageInfoFacade {

	@Autowired
	private CoreProperties coreProperties;

	@Autowired
	private PageGroupInfoService pageGroupInfoService;

	@Autowired
	private PageBaseInfoService pageBaseInfoService;

	@Autowired
	private PageElementService pageElementService;

	@Autowired
	private PageDataSourceService pageDataSourceService;

	/**
	 * 页面组信息取得
	 */
	@Override
	public PageGroupInfoModel findPageGroupInfo(PageInfoDto pageInfoDto) throws Exception {
		// 页面组ID解密
		String groupId = EncryptorUtil.decryptString(pageInfoDto.getGroupId(), coreProperties.getDesSalt());
		if (StringUtils.isEmpty(groupId)) {
			logger.error("页面组ID不合法");
			throw new FacadeException("s.common.error.param");
		}

		// 页面组信息取得
		PageGroupInfoModel pageGroupInfo = pageGroupInfoService.findPageGroupInfo(groupId);
		if (pageGroupInfo == null) {
			logger.error("页面组信息未找到");
			throw new FacadeException("s.common.error.none");
		}

		pageGroupInfo.setGroupId(EncryptorUtil.encryptString(pageGroupInfo.getGroupId(), coreProperties.getDesSalt()));
		pageGroupInfo.setDefaultPage(
				EncryptorUtil.encryptString(pageGroupInfo.getDefaultPage(), coreProperties.getDesSalt()));
		return pageGroupInfo;
	}

	/**
	 * 页面信息取得
	 */
	@Override
	public Map<String, Object> findPageInfo(PageInfoDto pageInfoDto) throws Exception {
		// 页面组ID解密
		String groupId = EncryptorUtil.decryptString(pageInfoDto.getGroupId(), coreProperties.getDesSalt());
		if (StringUtils.isEmpty(groupId)) {
			logger.error("页面组ID不合法");
			throw new FacadeException("s.common.error.param");
		}

		// 页面ID解密
		String pageId = EncryptorUtil.decryptString(pageInfoDto.getPageId(), coreProperties.getDesSalt());
		if (StringUtils.isEmpty(pageId)) {
			logger.error("页面ID不合法");
			throw new FacadeException("s.common.error.param");
		}

		// 页面信息取得
		PageBaseInfoModel pageParam = new PageBaseInfoModel();
		pageParam.setGroupId(groupId);
		pageParam.setPageId(pageId);
		PageBaseInfoModel pageInfo = pageBaseInfoService.findPageBaseInfo(pageParam);
		if (pageInfo == null) {
			logger.error("页面信息未找到");
			throw new FacadeException("s.common.error.none");
		}

		// 页面ID加密
		pageInfo.setGroupId(EncryptorUtil.encryptString(groupId, coreProperties.getDesSalt()));
		pageInfo.setPageId(EncryptorUtil.encryptString(pageId, coreProperties.getDesSalt()));

		// 页面项目信息取得
		PageElementSetRowModel elementParam = new PageElementSetRowModel();
		elementParam.setGroupId(groupId);
		elementParam.setPageId(pageId);
		List<PageElementSetRowModel> elementList = pageElementService.searchPageElementSetRow(elementParam);

		// 页面数据源信息取得
		PageDataSourceModel datasourceParam = new PageDataSourceModel();
		datasourceParam.setGroupId(groupId);
		datasourceParam.setPageId(pageId);
		List<PageDataSourceModel> dataSourceList = pageDataSourceService.searchPageDataSource(datasourceParam);

		// 返回对象
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("pageInfo", pageInfo);
		rtnMap.put("elementSetRows", elementList);
		rtnMap.put("dataSourceList", dataSourceList);
		return rtnMap;
	}

}
