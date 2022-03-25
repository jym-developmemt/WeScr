/********************************************************************************
 * Copyright (c) 2017 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.base.util.VersionHolder;
import we.core.dao.ApplyFlowDao;
import we.core.model.ApplyFlowModel;
import we.core.model.ApplyInfoModel;
import we.core.model.ConditionInfoModel;
import we.core.model.FlowConditionModel;
import we.core.service.ApplyFlowService;

/**
 * 流程数据查询Service实现类
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class ApplyFlowServiceImpl extends BaseService implements ApplyFlowService {

	@Autowired
	private ApplyFlowDao applyFlowDao;

	@Autowired
	private VersionHolder versionHolder;

	/**
	 * 申请基本信息取得
	 */
	public ApplyInfoModel findApplyInfo(String applyId) throws Exception {
		ApplyInfoModel param = new ApplyInfoModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setApplyId(applyId);
		return applyFlowDao.findApplyInfo(param);
	}

	/**
	 * 申请基本信息检索
	 */
	public List<ApplyInfoModel> searchApplyInfo(ApplyInfoModel param) throws Exception {
		param.setVersionId(versionHolder.getLastVersion());
		return applyFlowDao.searchApplyInfo(param);
	}

	/**
	 * 流程条件信息检索
	 */
	@Override
	public List<ConditionInfoModel> searchConditionInfo(String applyId) throws Exception {
		ApplyInfoModel param = new ApplyInfoModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setApplyId(applyId);
		return applyFlowDao.searchConditionInfo(param);
	}

	/**
	 * 审批流程条件检索
	 */
	@Override
	public List<FlowConditionModel> searchFlowCondition(String applyId) throws Exception {
		ApplyInfoModel param = new ApplyInfoModel();
		param.setVersionId(versionHolder.getLastVersion());
		param.setApplyId(applyId);
		return applyFlowDao.searchFlowCondition(param);
	}

	/**
	 * 审批流程信息检索
	 */
	@Override
	public List<ApplyFlowModel> searchApplyFlow(ApplyFlowModel applyFlow) throws Exception {
		applyFlow.setVersionId(versionHolder.getLastVersion());
		return applyFlowDao.searchApplyFlow(applyFlow);
	}
}
