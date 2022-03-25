/********************************************************************************
 * Copyright (c) 2017 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service;

import java.util.List;

import we.core.model.ApplyFlowModel;
import we.core.model.ApplyInfoModel;
import we.core.model.ConditionInfoModel;
import we.core.model.FlowConditionModel;

/**
 * 流程数据查询Service
 *
 * @author cp0
 * @since 0.0
 */
public interface ApplyFlowService {

	/**
	 * 申请基本信息取得
	 */
	public ApplyInfoModel findApplyInfo(String applyId) throws Exception;

	/**
	 * 申请基本信息检索
	 */
	public List<ApplyInfoModel> searchApplyInfo(ApplyInfoModel param) throws Exception;

	/**
	 * 流程条件信息检索
	 */
	public List<ConditionInfoModel> searchConditionInfo(String applyId) throws Exception;

	/**
	 * 审批流程条件检索
	 */
	public List<FlowConditionModel> searchFlowCondition(String applyId) throws Exception;

	/**
	 * 审批流程信息检索
	 */
	public List<ApplyFlowModel> searchApplyFlow(ApplyFlowModel applyFlow) throws Exception;
}
