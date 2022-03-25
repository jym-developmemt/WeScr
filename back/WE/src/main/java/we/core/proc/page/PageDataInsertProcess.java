/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.page;

import java.util.ArrayList;
import java.util.HashMap;
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
import we.base.util.CommonUtil;
import we.base.util.DataUtil;
import we.base.util.EncryptorUtil;
import we.core.CoreProperties;
import we.core.auth.AuthorizationManager;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.core.service.PageDataSearchService;
import we.core.service.PageDataUpdateService;
import we.core.service.SqlSessionService;
import we.website.dao.OrderNumberDao;
import we.website.dto.OrderNumberDto;
import we.website.model.PageBaseInfoModel;
import we.website.model.PageDataSourceColumnModel;
import we.website.model.PageDataSourceModel;
import we.website.service.PageBaseInfoService;
import we.website.service.PageDataSourceService;

/**
 * 页面数据插入
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class PageDataInsertProcess implements IProcess {

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
	private PageDataUpdateService pageDataUpdateService;

	@Autowired
	private SqlSessionService sqlSessionService;
	
	@Autowired
	private OrderNumberDao orderNumberDao;

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

		// 申请单号生成参数
		String codeparam = proceeDto.getStringData4();
		String codeparam2 = proceeDto.getStringData5();
		
		// 判断插入数据时是否需要申请单号
		if (!StringUtils.hasText(codeparam)) {
			// 单条插入
			if (proceeDto.getObjData1() != null) {
				return insertData(dataSourceInfo, columnInfoList, proceeDto.getObjData1(), proceeDto, resultList);
			}
			
			// 列表插入
			if (proceeDto.getListData1() != null) {
				int result = 0;
				for (Map<String, Object> dataMap : proceeDto.getListData1()) {
					// 序号
					int index = proceeDto.getListData1().indexOf(dataMap);
					dataMap.put("_index_", index);
					
					result += insertData(dataSourceInfo, columnInfoList, dataMap, proceeDto, resultList);
				}
				return result;
			}
		}else {
			OrderNumberDto orderNumberDto = new OrderNumberDto();
			orderNumberDto.setTableName(codeparam + codeparam2);
			String ordernumber = null;
			if (resultList.size() == 1) {
				ordernumber = getOrderNo(orderNumberDto);
			} else {
				ordernumber = String.valueOf(resultList.get(1));
			}
			
			// 单条插入
			if (proceeDto.getObjData1() != null) {
				proceeDto.getObjData1().put("order_no", ordernumber);
				insertData(dataSourceInfo, columnInfoList, proceeDto.getObjData1(), proceeDto, resultList);
				return ordernumber;	
			}
			
			// 列表插入
			if (proceeDto.getListData1() != null) {
				int result = 0;
				for (Map<String, Object> dataMap : proceeDto.getListData1()) {
					// 序号
					int index = proceeDto.getListData1().indexOf(dataMap);
					dataMap.put("_index_", index);
					dataMap.put("order_no", ordernumber);
					result += insertData(dataSourceInfo, columnInfoList, dataMap, proceeDto, resultList);
				}
				return result;
			}
		}

		return 0;
	}

	/**
	 * 数据插入处理
	 */
	private int insertData(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> dataMap, ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 数据源
		if ("1".equals(dataSourceInfo.getDatasourceType())) {
			// 附加信息
			Map<String, Object> addonMap = new HashMap<String, Object>();
			addonMap.put("alwaysEquals", "1");

			// 主键信息
			StringBuffer strPrimaryName = new StringBuffer();
			List<PageDataSourceColumnModel> primaryList = new ArrayList<PageDataSourceColumnModel>();
			for (PageDataSourceColumnModel columnInfo : columnInfoList) {
				if (CommonUtil.equals(columnInfo.getPrimaryKey(), "1")) {
					primaryList.add(columnInfo);

					if (strPrimaryName.length() > 0) {
						strPrimaryName.append(", ");
					}
					strPrimaryName.append(columnInfo.getDisplayName());

					// 默认值变换
					if (StringUtils.hasText(CommonUtil.toString(dataMap.get(columnInfo.getColumnId()))) == false) {
						if (StringUtils.hasText(columnInfo.getDefaultValue())) {
							dataMap.put(columnInfo.getColumnId(), DataUtil.convertDefaultValue(columnInfo.getDefaultValue(), dataMap));
						}
					}
				}
			}

			// 件数校验
			int cnt = pageDataSearchService.coundData(dataSourceInfo, primaryList, dataMap, addonMap, proceeDto, resultList);
			if (cnt > 0) {
				logger.error("数据重复，请更改 " + strPrimaryName + "。");
				throw new FacadeException("s.common.error.duplication");
			}

			// 保留项目清除
			dataMap.put("created_date", null);
			dataMap.put("created_by", null);
			dataMap.put("updated_date", null);
			dataMap.put("updated_by", null);

			// 数据插入
			return pageDataUpdateService.insertData(dataSourceInfo, columnInfoList, dataMap, proceeDto, resultList);
		}

		// SQL语句
		if ("2".equals(dataSourceInfo.getDatasourceType())) {
			return sqlSessionService.insert(dataSourceInfo.getDatasourceId(), dataMap);
		}

		logger.error("查询类型未识别。");
		throw new FacadeException("s.common.error.funtype");
	}
	
	/**
	 * 实行取申请单号的处理
	 */
	public String getOrderNo(OrderNumberDto orderNumberDto) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tabName", orderNumberDto.getTableName());
		return orderNumberDao.getOrderNoInsert(map);
	}
	
	
}
