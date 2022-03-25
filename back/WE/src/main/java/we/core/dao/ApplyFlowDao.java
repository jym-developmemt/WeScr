/********************************************************************************
 * Copyright (c) 2017 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.dao;

import java.util.List;

import we.core.model.ApplyFlowModel;
import we.core.model.ApplyInfoModel;
import we.core.model.ConditionInfoModel;
import we.core.model.FlowConditionModel;

/**
 * 流程数据查询Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface ApplyFlowDao {

	/**
	 * 申请基本信息取得
	 */
	public ApplyInfoModel findApplyInfo(ApplyInfoModel applyInfo);

	/**
	 * 申请基本信息检索
	 */
	public List<ApplyInfoModel> searchApplyInfo(ApplyInfoModel param);

	/**
	 * 流程条件信息检索
	 */
	public List<ConditionInfoModel> searchConditionInfo(ApplyInfoModel applyInfo);

	/**
	 * 审批流程条件检索
	 */
	public List<FlowConditionModel> searchFlowCondition(ApplyInfoModel applyInfo);

	/**
	 * 审批流程信息检索
	 */
	public List<ApplyFlowModel> searchApplyFlow(ApplyFlowModel applyFlow);
}
