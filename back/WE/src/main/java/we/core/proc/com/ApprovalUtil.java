package we.core.proc.com;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.core.auth.AuthGrantorType;
import we.core.dto.ProcessDto;
import we.core.service.DataSourceUpdateService;
import we.website.model.DataSourceColumnModel;
import we.website.model.DataSourceInfoModel;
import we.website.model.DepartmentInfoModel;
import we.website.model.PositionInfoModel;
import we.website.service.DataSourceColumnService;
import we.website.service.DataSourceInfoService;
import we.website.service.DepartmentInfoService;
import we.website.service.PositionInfoService;

/**
 * 申请/审批流程相关工具类
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class ApprovalUtil {
	
	@Autowired
	private PositionInfoService positionInfoService;
	
	@Autowired
	private DepartmentInfoService departmentInfoService;
	
	@Autowired
	private DataSourceInfoService dataSourceInfoService;
	
	@Autowired
	private DataSourceColumnService dataSourceColumnService;

	@Autowired
	private DataSourceUpdateService dataSourceUpdateService;
	
	/**
	 * 取得流程需要设定的审批人
	 * @param authType
	 * @param authValue
	 * @param approvalDepartmentId
	 * @return approvalAssignees
	 * @throws Exception
	 */
	public List<String> getApprovalAssignees(String authType, String authValue, String approvalDepartmentId) {
		List<String> approvalAssignees = new ArrayList<String>();

		// 指定用户审批
		if (CommonUtil.equals(authType, "A01")) {
			// 审批者设定
			for (String approvalId : authValue.split(",")) {
				approvalAssignees.add(approvalId);
			}

		}

		// 指定部门审批
		if (CommonUtil.equals(authType, "A02")) {
			// 部门
			for (String approvalId : authValue.split(",")) {
				approvalAssignees.add(AuthGrantorType.USER_DEPARTMENT + ":" + approvalId);
			}
		}

		// 指定部门阶层审批
		if (CommonUtil.equals(authType, "A03")) {
			// 部门阶层
			for (String approvalId : authValue.split(",")) {
				DepartmentInfoModel param = new DepartmentInfoModel();
				param.setDepartmentRank(approvalId);
				List<DepartmentInfoModel> departmentList = departmentInfoService.selectDepartmentInfo(param);

				// 部门
				for (DepartmentInfoModel departmentInfo : departmentList) {
					approvalAssignees.add(AuthGrantorType.USER_DEPARTMENT + ":" + departmentInfo.getDepartmentId());
				}
			}
		}

		// 指定职位审批
		if (CommonUtil.equals(authType, "A04")) {
			// 职位
			for (String approvalId : authValue.split(",")) {
				approvalAssignees.add(AuthGrantorType.USER_POSITION + ":" + approvalId);
			}
		}

		// 指定职位阶层审批
		if (CommonUtil.equals(authType, "A05")) {
			// 职位阶层
			for (String approvalId : authValue.split(",")) {
				PositionInfoModel param = new PositionInfoModel();
				param.setPositionRank(approvalId);
				List<PositionInfoModel> positionList = positionInfoService.selectPositionInfo(param);

				// 职位
				for (PositionInfoModel positionInfo : positionList) {
					approvalAssignees.add(AuthGrantorType.USER_POSITION + ":" + positionInfo.getPositionId());
				}
			}
		}

		// 指定部门职位审批
		if (CommonUtil.equals(authType, "A06")) {
			// 部门职位
			for (String approvalId : authValue.split(",")) {
				approvalAssignees.add(AuthGrantorType.USER_DEPARTMENT_POSITION + ":" + approvalId);
			}
		}

		// 指定角色审批
		if (CommonUtil.equals(authType, "A07")) {
			// 角色
			for (String approvalId : authValue.split(",")) {
				approvalAssignees.add(AuthGrantorType.USER_ROLE + ":" + approvalId);
			}
		}

		// 部门管理者审批（审批部门）
		if (CommonUtil.equals(authType, "B01") || CommonUtil.equals(authType, "C01") || CommonUtil.equals(authType, "D01")) {
			// 功能
			for (String approvalId : authValue.split(",")) {
				approvalAssignees.add(AuthGrantorType.USER_MANAGE_DEPARTMENT + ":" + approvalId + ":" + approvalDepartmentId);
			}
		}

		// 指定职位审批（审批部门）
		if (CommonUtil.equals(authType, "B02") || CommonUtil.equals(authType, "C02") || CommonUtil.equals(authType, "D02")) {
			// 职位
			for (String approvalId : authValue.split(",")) {
				approvalAssignees.add(AuthGrantorType.USER_DEPARTMENT_POSITION + ":" + approvalDepartmentId + ":" + approvalId);
			}
		}

		// 指定职位阶层审批（审批部门）
		if (CommonUtil.equals(authType, "B03") || CommonUtil.equals(authType, "C03") || CommonUtil.equals(authType, "D03")) {
			// 职位阶层
			for (String approvalId : authValue.split(",")) {
				PositionInfoModel param = new PositionInfoModel();
				param.setPositionRank(approvalId);
				List<PositionInfoModel> positionList = positionInfoService.selectPositionInfo(param);

				// 部门职位
				for (PositionInfoModel positionInfo : positionList) {
					approvalAssignees.add(AuthGrantorType.USER_DEPARTMENT_POSITION + ":" + approvalDepartmentId + ":" + positionInfo.getPositionId());
				}
			}
		}

		// 指定角色审批（审批部门）
		if (CommonUtil.equals(authType, "B04") || CommonUtil.equals(authType, "C04") || CommonUtil.equals(authType, "D04")) {
			// 角色
			for (String approvalId : authValue.split(",")) {
				approvalAssignees.add(AuthGrantorType.USER_DEPARTMENT_ROLE + ":" + approvalDepartmentId  + ":" + approvalId);
			}
		}

		return approvalAssignees;
	}

	/**
	 * 生成审批历史中的数据
	 * @param task
	 * @param proceeDto
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public int createFlowHisData(ProcessDto proceeDto, String instanceId, String taskId, int stepIndex, String stepName, String message, String status) throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		// 数据源信息
		DataSourceInfoModel dataSourceInfo = dataSourceInfoService.findDataSource("	"
				+ "+");
		if (dataSourceInfo == null) {
			throw new FacadeException("数据源信息未找到。");
		}
		
		// 数据源列信息
		DataSourceColumnModel dataSourceColumnParam = new DataSourceColumnModel();
		dataSourceColumnParam.setDatasourceId(dataSourceInfo.getDatasourceId());
		List<DataSourceColumnModel> columnInfoList = dataSourceColumnService.searchDataSourceColumn(dataSourceColumnParam);
		
		//suc3 ID_ 是在数据源中配置 _auto_ ,在suc4中配置放在 页面配置的数据源中获取比较麻烦，
		param.put("ID_", UUID.randomUUID());
		//流程实例ID
		param.put("PROC_INST_ID_", instanceId);
		//任务ID
		param.put("TASK_ID_", taskId);
		param.put("STEP_ID_", "STEP"+ stepIndex);
		//获取步骤名称
		param.put("STEP_NAME_", stepName);
		
		// 操作者ID
		String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
		param.put("USER_ID_", loginName);
		
		//设置日期格式
		//获取系统当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		param.put("TIME_", df.format(new Date()));
		
		if(StringUtils.hasText(message)) {
			param.put("MESSAGE_", message);
		}
		else {
			param.put("MESSAGE_", proceeDto.getStringData2());
		}
		
		if(StringUtils.hasText(status)) {
			param.put("MESSAGE_", message);
		}
		else {
			Map<String,Object> dataMap = proceeDto.getObjData1();
			
			if (CommonUtil.equals(CommonUtil.toString(dataMap.get("approval_result")), "0")) {
				status = "0";
			} else if (CommonUtil.equals(CommonUtil.toString(dataMap.get("approval_result")), "1")) {
				status = "9";
			} else if (CommonUtil.equals(CommonUtil.toString(dataMap.get("approval_result")), "9")) {
				status = "7";
			} else if (CommonUtil.equals(CommonUtil.toString(dataMap.get("approval_result")), "5")) {
				status = "1";
			} 
			
			param.put("STATUS_", status);
		}
		
		//插入操作	
		return dataSourceUpdateService.insertData(dataSourceInfo, columnInfoList, param);
	}
	
	public int createFlowHisData(ProcessDto proceeDto, String instanceId, String taskId, int stepIndex, String stepName) throws Exception 
	{
		return createFlowHisData(proceeDto, instanceId, taskId, stepIndex, stepName, "", "");
	}
}
