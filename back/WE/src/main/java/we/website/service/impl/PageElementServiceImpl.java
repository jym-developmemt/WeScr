/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import we.base.base.BaseService;
import we.base.util.CommonUtil;
import we.core.auth.AuthorizationManager;
import we.website.dao.PageElementDao;
import we.website.model.PageElementBaseModel;
import we.website.model.PageElementCellModel;
import we.website.model.PageElementDetailModel;
import we.website.model.PageElementRowModel;
import we.website.model.PageElementSetModel;
import we.website.model.PageElementSetRowModel;
import we.website.service.PageElementService;

/**
 * 页面项目信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class PageElementServiceImpl extends BaseService implements PageElementService {

	// 页面项目信息Dao
	@Autowired
	private PageElementDao pageElementDao;

	@Autowired
	private AuthorizationManager authorizationManager;

	/**
	 * 页面项目信息取得
	 */
	@Override
	public List<PageElementSetRowModel> searchPageElementSetRow(PageElementSetRowModel elementParam) throws Exception {

		// 取得页面项目组信息
		List<PageElementSetModel> elementSetList = pageElementDao.searchPageElementSet(elementParam);

		// 取得页面项目信息
		List<PageElementBaseModel> elementBaseList = pageElementDao.searchPageElementBase(elementParam);

		// 取得页面项目明细信息
		List<PageElementDetailModel> elementDetailList = pageElementDao.searchPageElementDetail(elementParam);


		List<PageElementSetRowModel> rtnList = new ArrayList<PageElementSetRowModel>();

		Map<String, PageElementSetModel> elementSetMap = new HashMap<String, PageElementSetModel>();

		// 前回项目组行信息
		PageElementSetRowModel preSetRow = null;

		for (PageElementSetModel elementSet : elementSetList) {

			// 权限校验
			if (StringUtils.hasText(elementSet.getAuthType())
					&& authorizationManager.hasAuthority(elementSet.getAuthType(), elementSet.getAuthValue()) == false) {
				continue;
			}

			// 取得页面项目组行合并
			if (preSetRow == null
					|| CommonUtil.equals(preSetRow.getSetType(), elementSet.getSetType()) == false
					|| CommonUtil.equals(preSetRow.getRowIndex(), elementSet.getRowIndex()) == false) {
				preSetRow = new PageElementSetRowModel();
				BeanUtils.copyProperties(preSetRow, elementSet);
				rtnList.add(preSetRow);
			} else if (preSetRow.getRowProp() == null) {
				preSetRow.setRowProp(elementSet.getRowProp());
			}
			elementSetMap.put(elementSet.getSetId(), elementSet);
			preSetRow.getElementSetList().add(elementSet);
		}

		Map<String, PageElementBaseModel> elementBaseMap = new HashMap<String, PageElementBaseModel>();

		PageElementRowModel preElementRow = null;
		PageElementCellModel preElementCell = null;

		for (PageElementBaseModel elementBase : elementBaseList) {
			// 父节点存在校验
			PageElementSetModel elementSet = elementSetMap.get(elementBase.getSetId());
			if (elementSet == null) {
				continue;
			}

			// 权限校验
			if (StringUtils.hasText(elementBase.getAuthType())
					&& authorizationManager.hasAuthority(elementBase.getAuthType(), elementBase.getAuthValue()) == false) {
				continue;
			}

			// 取得页面项目行合并
			if (preElementRow == null
					|| CommonUtil.equals(preElementRow.getSetId(), elementBase.getSetId()) == false
					|| CommonUtil.equals(preElementRow.getRowIndex(), elementBase.getRowIndex()) == false) {
				preElementRow = new PageElementRowModel();
				BeanUtils.copyProperties(preElementRow, elementBase);
				elementSet.getElementRowList().add(preElementRow);
				preElementCell = null;
			} else if (preElementRow.getRowProp() == null) {
				preElementRow.setRowProp(elementBase.getRowProp());
			}

			// 取得页面项目列合并
			if (preElementCell == null || CommonUtil.equals(preElementCell.getColIndex(), elementBase.getColIndex()) == false) {
				preElementCell = new PageElementCellModel();
				BeanUtils.copyProperties(preElementCell, elementBase);
				preElementRow.getCellList().add(preElementCell);
			} else if (preElementCell.getColProp() == null) {
				preElementCell.setColProp(elementBase.getColProp());
			}
			elementBaseMap.put(elementBase.getElementIndex(), elementBase);
			preElementCell.getElementList().add(elementBase);
		}

		for (PageElementDetailModel elementDetail : elementDetailList) {
			// 父节点存在校验
			PageElementBaseModel elementBase = elementBaseMap.get(elementDetail.getElementIndex());
			if (elementBase == null) {
				continue;
			}

			// 权限校验
			if (StringUtils.hasText(elementDetail.getAuthType())
					&& authorizationManager.hasAuthority(elementDetail.getAuthType(), elementDetail.getAuthValue()) == false) {
				continue;
			}

			elementBase.getDetailList().add(elementDetail);
		}

		return rtnList;
	}
}
