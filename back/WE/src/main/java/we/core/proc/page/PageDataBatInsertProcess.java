/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.EncryptorUtil;
import we.core.CoreProperties;
import we.core.auth.AuthorizationManager;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.core.service.PageDataUpdateService;
import we.core.service.SqlSessionService;
import we.website.model.PageBaseInfoModel;
import we.website.model.PageDataSourceColumnModel;
import we.website.model.PageDataSourceModel;
import we.website.service.PageBaseInfoService;
import we.website.service.PageDataSourceService;

/**
 * 页面数据插入（批量）
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class PageDataBatInsertProcess implements IProcess {

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
	private PageDataUpdateService pageDataUpdateService;

	@Autowired
	private SqlSessionService sqlSessionService;

	/**
	 * 处理实行
	 */
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
			if (authorizationManager.hasAuthority(pageInfo.getAuthType(), pageInfo.getAuthValue(), loginUser) == false) {
				logger.error("操作权限不足");
				throw new FacadeException("s.common.error.auth");
			}
		}

		// 页面数据源信息
		PageDataSourceModel pageDataSourceParam = new PageDataSourceModel();
		pageDataSourceParam.setGroupId(pageGroupId);
		pageDataSourceParam.setPageId(pageId);
		pageDataSourceParam.setDatasourceIndex(datasourceIndex);
		PageDataSourceModel dataSourceInfo = pageDataSourceService.findPageDataSource(pageDataSourceParam);
		if (dataSourceInfo == null) {
			logger.error("页面数据源信息未找到。");
			throw new FacadeException("s.common.error.none");
		}

		// 数据源列信息
		PageDataSourceColumnModel dataSourceColumnParam = new PageDataSourceColumnModel();
		dataSourceColumnParam.setGroupId(pageGroupId);
		dataSourceColumnParam.setPageId(pageId);
		dataSourceColumnParam.setDatasourceIndex(datasourceIndex);
		List<PageDataSourceColumnModel> columnInfoList = pageDataSourceService.searchPageDataSourceColumn(dataSourceColumnParam);

		// 单条插入
		if (proceeDto.getObjData1() != null) {
			List<Map<String, Object>> dataMapList = new ArrayList<Map<String, Object>>();
			dataMapList.add(proceeDto.getObjData1());
			return insertData(dataSourceInfo, columnInfoList, dataMapList, proceeDto, resultList);
		}

		// 列表插入
		if (proceeDto.getListData1() != null) {
			int result = 0;
			for (Map<String, Object> dataMap : proceeDto.getListData1()) {
				// 序号
				int index = proceeDto.getListData1().indexOf(dataMap);
				dataMap.put("_index_", index);
			}
			result += insertData(dataSourceInfo, columnInfoList, proceeDto.getListData1(), proceeDto, resultList);
			return result;
		}

		return 0;
	}

	/**
	 * 数据插入处理
	 */
	private int insertData(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			List<Map<String, Object>> dataMapList, ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 数据源
		if ("1".equals(dataSourceInfo.getDatasourceType())) {

			for (Map<String, Object> dataMap : dataMapList) {
				// 保留项目清除
				dataMap.put("created_date", null);
				dataMap.put("created_by", null);
				dataMap.put("updated_date", null);
				dataMap.put("updated_by", null);
			}

			// 数据插入
			return pageDataUpdateService.batchInsertData(dataSourceInfo, columnInfoList, dataMapList, proceeDto, resultList);
		}

		// SQL语句
		if ("2".equals(dataSourceInfo.getDatasourceType())) {
			return sqlSessionService.batchInsert(dataSourceInfo.getDatasourceId(), dataMapList);
		}

		logger.error("查询类型未识别。");
		throw new FacadeException("s.common.error.funtype");
	}
}
