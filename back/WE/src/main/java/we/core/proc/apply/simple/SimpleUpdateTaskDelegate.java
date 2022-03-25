/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply.simple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;
import we.base.util.TokenUtils;
import we.core.CoreProperties;
import we.core.model.ApplyInfoModel;
import we.core.service.ApplyFlowService;
import we.core.service.DataSourceUpdateService;
import we.website.model.DataSourceColumnModel;
import we.website.model.DataSourceInfoModel;
import we.website.service.DataSourceColumnService;
import we.website.service.DataSourceInfoService;

/**
 * 最终业务更新（流程任务）
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class SimpleUpdateTaskDelegate implements JavaDelegate {

	private final static String DBID_NOTICE_INFO = "_noticeInfo";

	private final static String DBID_NOTICE_USER = "_noticeUser";

	@Autowired
	private CoreProperties coreProperties;

	@Autowired
	private ApplyFlowService applyFlowService;

	@Autowired
	private DataSourceInfoService dataSourceInfoService;

	@Autowired
	private DataSourceColumnService dataSourceColumnService;

	@Autowired
	private DataSourceUpdateService dataSourceUpdateService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		// 申请ID
		String applyId = CommonUtil.toString(execution.getVariable("apply_id"));

		// 申请基本信息取得
		ApplyInfoModel applyInfo = applyFlowService.findApplyInfo(applyId);

		// 数据更新
		if (StringUtils.hasText(applyInfo.getUpdateDatasourceId())) {
			updateData(execution, applyInfo);
		}

		// 发送通知
		String noticeId = insertNoticeInfo(execution, applyInfo);
		insertNoticeUser(execution, noticeId);

		// 变量更新
		updateVariable(execution);
	}

	/**
	 * 变量更新
	 */
	private void updateVariable(DelegateExecution execution) {

		// 审批结束步骤更新
		String approvalResult = CommonUtil.toString(execution.getVariable("approval_result"));
		if (CommonUtil.equals(approvalResult, "0")) {
			int stepIndex = Integer.parseInt(CommonUtil.toString(execution.getVariable("step_index")));
			execution.setVariable("step_index", stepIndex + 1);
			execution.setVariable("approval_result", "5");
		} else if (CommonUtil.equals(approvalResult, "1")) {
			execution.setVariable("approval_status", "1");
		}

		// 清空步骤变量
		execution.removeVariable("EMPTY_ARRAY");
		execution.removeVariable("approval_assignees");
		execution.removeVariable("auth_type");
		execution.removeVariable("approval_type");
		execution.removeVariable("step_name");
		execution.removeVariable("auth_name");
	}

	/**
	 * 业务数据更新
	 */
	private void updateData(DelegateExecution execution, ApplyInfoModel applyInfo) throws Exception {
		// 申请实例ID
		String instanceId = CommonUtil.toString(execution.getProcessInstanceId());
		// 审批结果
		String approvalResult = CommonUtil.toString(execution.getVariable("approval_result"));

		Map<String, Object> updateData = new HashMap<String, Object>();
		updateData.put("updated_date", CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss"));
		updateData.put("updated_by", TokenUtils.createBatchAuthentication().getName());

		Map<String, Object> conditionData = new HashMap<String, Object>();

		// 数据源
		DataSourceInfoModel dataSourceInfo = dataSourceInfoService.findDataSource(applyInfo.getUpdateDatasourceId());

		// 数据源
		DataSourceColumnModel param = new DataSourceColumnModel();
		param.setDatasourceId(applyInfo.getUpdateDatasourceId());
		List<DataSourceColumnModel> columnInfoList = dataSourceColumnService.searchDataSourceColumn(param);

		for (DataSourceColumnModel columnInfo : columnInfoList) {
			// 申请实例
			if (CommonUtil.equals(columnInfo.getColumnIndex(), applyInfo.getInstanceIdColumnIndex())) {
				conditionData.put(columnInfo.getColumnId(), instanceId);
			}
			// 申请状态
			if (CommonUtil.equals(columnInfo.getColumnIndex(), applyInfo.getStatusColumnIndex())) {
				if (CommonUtil.equals(approvalResult, "0")) {
					// 申请通过
					approvalResult = "8";
				} else if (CommonUtil.equals(approvalResult, "1")) {
					// 申请驳回
					approvalResult = "9";
				} else if (CommonUtil.equals(approvalResult, "9")) {
					// 申请撤销
					approvalResult = "7";
				}
				updateData.put(columnInfo.getColumnId(), approvalResult);
			}
		}

		// 项目数校验
		if (conditionData.size() != 1 || updateData.size() != 3) {
			throw new Exception("更新校验错误!");
		}

		// 状态更新
		dataSourceUpdateService.updateData(dataSourceInfo, columnInfoList, updateData, conditionData);
	}

	/**
	 * 追加通知基本信息
	 */
	private String insertNoticeInfo(DelegateExecution execution, ApplyInfoModel applyInfo) throws Exception {
		// 申请名称
		String applyName = CommonUtil.toString(execution.getVariable("apply_name"));
		// 审批结果
		String approvalResult = CommonUtil.toString(execution.getVariable("approval_result"));
		// 通知ID
		String noticeId = CommonUtil.generateKey();

		// 数据源
		DataSourceInfoModel dataSourceInfo = dataSourceInfoService.findDataSource(DBID_NOTICE_INFO);

		// 数据源
		DataSourceColumnModel param = new DataSourceColumnModel();
		param.setDatasourceId(DBID_NOTICE_INFO);
		List<DataSourceColumnModel> columnInfoList = dataSourceColumnService.searchDataSourceColumn(param);

		Map<String, Object> insertData = new HashMap<String, Object>();
		insertData.put("notice_id", noticeId);
		insertData.put("notice_title", applyName);

		StringBuffer noticeContent = new StringBuffer();
		if (CommonUtil.equals(approvalResult, "0")) {
			// 申请通过
			insertData.put("icon_type", "2");
			noticeContent.append("您的申请已通过");
		} else if (CommonUtil.equals(approvalResult, "1")) {
			// 申请驳回
			insertData.put("icon_type", "4");
			noticeContent.append("您的申请被驳回");
		} else if (CommonUtil.equals(approvalResult, "9")) {
			// 申请撤销
			insertData.put("icon_type", "3");
			noticeContent.append("您的申请已撤销");
		} else {
			insertData.put("icon_type", "0");
			noticeContent.append("您的申请已完成");
		}
		if (execution.hasVariable("approval_reason")) {
			noticeContent.append("\r\n");
			noticeContent.append(execution.getVariable("approval_reason"));
		}
		insertData.put("notice_content", noticeContent.toString());

		insertData.put("notice_type", applyInfo.getApplyId());
		insertData.put("notice_datetime", CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss"));
		if (StringUtils.hasText(applyInfo.getApprovalPageId())) {
			insertData.put("notice_link", getApprovalUrl(applyInfo.getApprovalPageId(), execution.getProcessInstanceId()));
		}
		insertData.put("send_user_id", TokenUtils.createBatchAuthentication().getName());
		insertData.put("site_notice", "0");
		insertData.put("created_date", CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss"));
		insertData.put("created_by", TokenUtils.createBatchAuthentication().getName());
		insertData.put("updated_date", CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss"));
		insertData.put("updated_by", TokenUtils.createBatchAuthentication().getName());

		// 通知插入
		dataSourceUpdateService.insertData(dataSourceInfo, columnInfoList, insertData);

		return noticeId;
	}

	/**
	 * 追加通知用户信息
	 */
	private void insertNoticeUser(DelegateExecution execution, String noticeId) throws Exception {
		// 申请名称
		String applyUser = CommonUtil.toString(execution.getVariable("apply_user"));

		// 数据源
		DataSourceInfoModel dataSourceInfo = dataSourceInfoService.findDataSource(DBID_NOTICE_USER);

		// 数据源
		DataSourceColumnModel param = new DataSourceColumnModel();
		param.setDatasourceId(DBID_NOTICE_USER);
		List<DataSourceColumnModel> columnInfoList = dataSourceColumnService.searchDataSourceColumn(param);

		Map<String, Object> insertData = new HashMap<String, Object>();
		insertData.put("notice_id", noticeId);
		insertData.put("user_id", applyUser);
		insertData.put("receive_state", "0");
		insertData.put("delete_flg", "0");
		insertData.put("created_date", CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss"));
		insertData.put("created_by", TokenUtils.createBatchAuthentication().getName());
		insertData.put("updated_date", CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss"));
		insertData.put("updated_by", TokenUtils.createBatchAuthentication().getName());

		// 通知插入
		dataSourceUpdateService.insertData(dataSourceInfo, columnInfoList, insertData);
	}

	// 生成审批URL
	private String getApprovalUrl(String approvalPageId, String instanceId) {
		String[] defaultPageInfo = approvalPageId.replaceAll("/", " ").trim().split(" ");
		StringBuffer strPageInfo = new StringBuffer();
		strPageInfo.append("/");
		strPageInfo.append(EncryptorUtil.encryptString(defaultPageInfo[0], coreProperties.getDesSalt()));
		strPageInfo.append("/");
		strPageInfo.append(EncryptorUtil.encryptString(defaultPageInfo[1], coreProperties.getDesSalt()));
		strPageInfo.append("?instanceId=");
		strPageInfo.append(instanceId);
		return strPageInfo.toString();
	}
}
