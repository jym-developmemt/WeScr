/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.page;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;
import we.core.CoreProperties;
import we.core.auth.AuthorizationManager;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.core.service.PageDataSearchService;
import we.core.service.SqlSessionService;
import we.website.model.PageBaseInfoModel;
import we.website.model.PageDataSourceColumnModel;
import we.website.model.PageDataSourceModel;
import we.website.service.PageBaseInfoService;
import we.website.service.PageDataSourceService;

/**
 * 页面数据检索
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class PageDataSearchProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private AuthorizationManager authorizationManager;

	@Autowired
	private PageBaseInfoService pageBaseInfoService;

	@Autowired
	private PageDataSourceService pageDataSourceService;

	@Autowired
	private PageDataSearchService pageDataSearchService;

	@Autowired
	private SqlSessionService sqlSessionService;

	/**
	 * 处理实行
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 画面组ID
		String pageGroupId = EncryptorUtil.decryptString(proceeDto.getStringData1(), websiteProp.getDesSalt());
		if (StringUtils.hasText(pageGroupId) == false) {
			logger.error("画面组ID未输入。");
			throw new FacadeException("s.common.error.param");
		}

		// 画面ID
		String pageId = EncryptorUtil.decryptString(proceeDto.getStringData2(), websiteProp.getDesSalt());
		if (StringUtils.hasText(pageId) == false) {
			logger.error("画面ID未输入。");
			throw new FacadeException("s.common.error.param");
		}

		// 数据源番号
		String datasourceIndex = proceeDto.getStringData3();
		if (StringUtils.hasText(datasourceIndex) == false) {
			logger.error("数据源番号未输入。");
			throw new FacadeException("s.common.error.param");
		}

		// 页面基本信息
		PageBaseInfoModel pageParam = new PageBaseInfoModel();
		pageParam.setGroupId(pageGroupId);
		pageParam.setPageId(pageId);
		PageBaseInfoModel pageInfo = pageBaseInfoService.findPageBaseInfo(pageParam);
		if (pageInfo == null) {
			logger.error("页面信息未找到。");
			throw new FacadeException("s.common.error.none");
		}

		if (StringUtils.hasText(pageInfo.getAuthType())) {
			// 登陆者
			Authentication loginUser = SecurityContextHolder.getContext().getAuthentication();

			// 用户权限检查
			if (authorizationManager.hasAuthority(pageInfo.getAuthType(), pageInfo.getAuthValue(),
					loginUser) == false) {
				logger.error("操作权限不足");
				throw new FacadeException("s.common.error.auth");
			}
		}

		// 页面数据源信息
		PageDataSourceModel pageDataSourceParam = new PageDataSourceModel();
		pageDataSourceParam.setGroupId(pageGroupId);
		pageDataSourceParam.setPageId(pageId);
		pageDataSourceParam.setDatasourceIndex(datasourceIndex);
		PageDataSourceModel pageDataSourceInfo = pageDataSourceService.findPageDataSource(pageDataSourceParam);
		if (pageDataSourceInfo == null) {
			logger.error("页面数据源信息未找到。");
			throw new FacadeException("s.common.error.none");
		}

		// 数据源附加信息
		Map<String, Object> addonMap = pageDataSourceInfo.getDatasourceAddon();
		if (proceeDto.getObjData3() != null) {
			addonMap.putAll(proceeDto.getObjData3());
		}

		// 数据源初期条件
		if (StringUtils.hasText(pageDataSourceInfo.getSearchCondition())
				&& (addonMap.containsKey("init") == false
						|| !CommonUtil.equals(CommonUtil.toString(addonMap.get("init")), "0"))) {
			if (proceeDto.getObjData1() == null) {
				proceeDto.setObjData1(new HashMap<String, Object>());
			}

			JSONObject initCondition = new JSONObject(pageDataSourceInfo.getSearchCondition());
			Iterator names = initCondition.keys();
			while (names.hasNext()) {
				String name = CommonUtil.toString(names.next());
				if (proceeDto.getObjData1().containsKey(name)) {
					continue;
				}
				proceeDto.getObjData1().put(name, initCondition.get(name));
			}
		}

		// 数据源列信息
		PageDataSourceColumnModel dataSourceColumnParam = new PageDataSourceColumnModel();
		dataSourceColumnParam.setGroupId(pageGroupId);
		dataSourceColumnParam.setPageId(pageId);
		dataSourceColumnParam.setDatasourceIndex(datasourceIndex);
		List<PageDataSourceColumnModel> columnInfoList = pageDataSourceService
				.searchPageDataSourceColumn(dataSourceColumnParam);

		// 数据检索
		return searchData(pageDataSourceInfo, columnInfoList, proceeDto.getObjData1(), proceeDto.getObjData2(),
				addonMap, proceeDto, resultList);
	}

	public Object searchData(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> searchCondition, Map<String, Object> pagingParam, Map<String, Object> addonMap,
			ProcessDto proceeDto, List<Object> resultList) throws FacadeException {

		try {

			// 数据源
			if ("1".equals(dataSourceInfo.getDatasourceType())) {
				// 单条检索
				if ("0".equals(dataSourceInfo.getSearchType())) {
					return pageDataSearchService.findData(dataSourceInfo, columnInfoList, searchCondition, addonMap,
							proceeDto, resultList);
				}

				// 全检索
				else if ("1".equals(dataSourceInfo.getSearchType())) {
					return pageDataSearchService.searchDataList(dataSourceInfo, columnInfoList, searchCondition, addonMap,
							proceeDto, resultList);
				}

				// 分页检索
				else if ("2".equals(dataSourceInfo.getSearchType())) {
					return pageDataSearchService.pagingDataList(dataSourceInfo, columnInfoList, pagingParam,
							searchCondition, addonMap, proceeDto, resultList);
				}

				// 树检索
				else if ("3".equals(dataSourceInfo.getSearchType())) {
					return pageDataSearchService.searchDataTree(dataSourceInfo, columnInfoList, searchCondition, addonMap,
							proceeDto, resultList);
				}
			}

			// SQL语句
			if ("2".equals(dataSourceInfo.getDatasourceType())) {
				// 单条检索
				if ("0".equals(dataSourceInfo.getSearchType())) {
					return sqlSessionService.find(dataSourceInfo.getDatasourceId(), searchCondition);
				}

				// 全检索
				if ("1".equals(dataSourceInfo.getSearchType())) {
					return sqlSessionService.search(dataSourceInfo.getDatasourceId(), searchCondition);
				}
			}

			logger.error("查询类型未识别。");
			throw new FacadeException("s.common.error.funtype");
		} catch (Exception e) {
			logger.error("查询实行错误。", e);
			throw new FacadeException("s.common.error.process");
		}
	}
}
