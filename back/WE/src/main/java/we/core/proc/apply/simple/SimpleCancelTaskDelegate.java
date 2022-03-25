/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
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
 * 申请撤销（流程任务）
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class SimpleCancelTaskDelegate implements JavaDelegate {

	private final static String DBID_NOTICE_INFO = "_noticeInfo";

	private final static String DBID_NOTICE_USER = "_noticeUser";

	@Autowired
	private CoreProperties coreProperties;

	@Autowired
	private HistoryService historyService;

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
		// 审批流程信息
		ApplyInfoModel applyInfo = applyFlowService.findApplyInfo(applyId);

		// 申请撤销
		execution.setVariable("approval_result", "9");
		execution.setVariable("approval_status", "9");

		List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery().processInstanceId(execution.getProcessInstanceId())
				.list();

		List<String> userIdList = new ArrayList<String>();

		for (HistoricTaskInstance taskInfo : taskList) {
			if (StringUtils.isEmpty(taskInfo.getAssignee())) {
				continue;
			}

			if (userIdList.contains(taskInfo.getAssignee()) == false) {
				userIdList.add(taskInfo.getAssignee());
			}
		}

		// 发送通知
		if (userIdList.size() > 0) {
			String noticeId = insertNoticeInfo(execution, applyInfo);
			for (String userId : userIdList) {
				insertNoticeUser(userId, noticeId);
			}
		}
	}

	/**
	 * 追加通知基本信息
	 */
	private String insertNoticeInfo(DelegateExecution execution, ApplyInfoModel applyInfo) throws Exception {
		// 申请名称
		String applyName = CommonUtil.toString(execution.getVariable("apply_name"));
		// 申请说明
		String applyComment = CommonUtil.toString(execution.getVariable("apply_comment"));
		// 通知ID
		String noticeId = CommonUtil.generateKey();

		// 数据源
		DataSourceInfoModel dataSourceInfo = dataSourceInfoService.findDataSource(DBID_NOTICE_INFO);

		// 数据源
		DataSourceColumnModel param = new DataSourceColumnModel();
		param.setDatasourceId(dataSourceInfo.getDatasourceId());
		List<DataSourceColumnModel> columnInfoList = dataSourceColumnService.searchDataSourceColumn(param);

		Map<String, Object> insertData = new HashMap<String, Object>();
		insertData.put("notice_id", noticeId);
		insertData.put("icon_type", "3");
		insertData.put("notice_title", applyName + "已撤销");
		if (StringUtils.hasText(applyComment)) {
			insertData.put("notice_content", applyComment);
		}
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
	private void insertNoticeUser(String userId, String noticeId) throws Exception {
		// 数据源
		DataSourceInfoModel dataSourceInfo = dataSourceInfoService.findDataSource(DBID_NOTICE_USER);

		// 数据源
		DataSourceColumnModel param = new DataSourceColumnModel();
		param.setDatasourceId(DBID_NOTICE_USER);
		List<DataSourceColumnModel> columnInfoList = dataSourceColumnService.searchDataSourceColumn(param);

		Map<String, Object> insertData = new HashMap<String, Object>();
		insertData.put("notice_id", noticeId);
		insertData.put("user_id", userId);
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
