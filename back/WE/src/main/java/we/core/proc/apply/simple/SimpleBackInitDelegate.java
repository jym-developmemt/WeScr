package we.core.proc.apply.simple;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import we.base.util.CommonUtil;
import we.core.model.ApplyFlowModel;
import we.core.proc.com.ApprovalUtil;
import we.core.service.ApplyFlowService;
import we.website.model.DepartmentInfoModel;
import we.website.service.DepartmentInfoService;

/**
 * 审批信息设定（退回时流程任务）
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class SimpleBackInitDelegate implements JavaDelegate {

	@Autowired
	private ApplyFlowService applyFlowService;
	
	@Autowired
	private DepartmentInfoService departmentInfoService;
	
	@Autowired
	private ApprovalUtil approvalUtil;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		// 申请ID
		String applyId = CommonUtil.toString(execution.getVariable("apply_id"));
		// 流程番号
		int flowIndex = Integer.parseInt(CommonUtil.toString(execution.getVariable("flow_index")));
		// 当前步骤番号
		int stepIndex = Integer.parseInt(CommonUtil.toString(execution.getVariable("step_index")));
		// 申请部门ID
		String applyDepartmentId = CommonUtil.toString(execution.getVariable("apply_department_id"));
		// 审批部门ID
		String approvalDepartmentId = CommonUtil.toString(execution.getVariable("approval_department_id"));
		
		/**
		 * 流程状态查询画面里，退回也作为待审核处理，所以重置审批结果 3->0
		 */
		execution.setVariable("approval_result", "0");		
		
		// 审批流程信息检索
		ApplyFlowModel applyFlow = new ApplyFlowModel();
		applyFlow.setApplyId(applyId);
		applyFlow.setFlowIndex(flowIndex);
		applyFlow.setStepIndex(stepIndex - 1);
		List<ApplyFlowModel> applyFlowList = applyFlowService.searchApplyFlow(applyFlow);
		if (applyFlowList.size() != 1) {
			throw new Exception("审批步骤错误！");
		}

		// 审批步骤
		applyFlow = applyFlowList.get(0);
		String authType = applyFlow.getAuthType();
		String authValue = applyFlow.getAuthValue();

		// 审批部门
		if (authType.startsWith("B")) {
			// 申请部门
			approvalDepartmentId = applyDepartmentId;
		} else if (authType.startsWith("C")) {
			// 取得上级部门
			DepartmentInfoModel param = new DepartmentInfoModel();
			param.setDepartmentId(approvalDepartmentId);
			DepartmentInfoModel departmentInfo = departmentInfoService.findDepartmentInfo(param);
			if (departmentInfo != null && StringUtils.hasText(departmentInfo.getParentId())) {
				// 保存审批部门
				approvalDepartmentId = departmentInfo.getParentId();
				execution.setVariable("approval_department_id", approvalDepartmentId);
			}
		}

		// 审批者
		List<String> approvalAssignees = approvalUtil.getApprovalAssignees(authType, authValue, approvalDepartmentId);
		execution.setVariable("approval_assignees", approvalAssignees);

		if (CommonUtil.equals(authType, "A01")) {
			execution.setVariable("assignee_type", "user");
		} else {
			execution.setVariable("assignee_type", "group");
		}
		// 审批权限类型:A01:用户 A02:部门
		execution.setVariable("auth_type", authType);
		// 审批方式:0:会签 1:或签
		execution.setVariable("approval_type", applyFlow.getApprovalType());

		// 当前步骤更新
		execution.setVariable("step_index", stepIndex - 1);
		execution.setVariable("step_name", applyFlow.getStepName());
		execution.setVariable("auth_name", applyFlow.getAuthName());
		
		// 当前超时审批的更新
		// 如果不做超时审批通过的话，默认为超时拒绝（时效180天）
		if (CommonUtil.equals(applyFlow.getTimeoutFlag(), "0")) {
			execution.setVariable("timeout_type", "1");
			execution.setVariable("duetime", "P180Y");
		} else {
			execution.setVariable("timeout_type", applyFlow.getTimeoutFlag());
			execution.setVariable("duetime", applyFlow.getTimeoutDuetime());
		}
	}



}
