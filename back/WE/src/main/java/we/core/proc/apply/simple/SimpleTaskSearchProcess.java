/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply.simple;

import java.util.List;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricIdentityLinkLog;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.util.CommonUtil;
import we.core.auth.AuthorizationManager;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;

/**
 * 任务状态取得
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class SimpleTaskSearchProcess implements IProcess {

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private AuthorizationManager authorizationManager;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 任务ID
		String taskId = proceeDto.getStringData1();
		if (StringUtils.hasText(taskId) == false) {
			return 9;
		}

		// 任务
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		// 任务存在校验
		if (task == null) {
			HistoricTaskInstance histtoryTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
			if (hasAuthority(histtoryTask) == false) {
				return 3;
			}

			return 1;
		} else {
			// 任务权限校验
			if (hasAuthority(task) == false) {
				return 2;
			}
		}


		return 0;
	}

	/**
	 * 权限校验
	 *
	 * @param task 任务
	 * @return
	 */
	private boolean hasAuthority(Task task) throws Exception {
		// 操作者
		Authentication loginUser = SecurityContextHolder.getContext().getAuthentication();

		// 用户校验
		String assignee = task.getAssignee();
		if (StringUtils.hasText(assignee)) {
			if (CommonUtil.equals(assignee, loginUser.getName())) {
				return true;
			}
		}

		// 部门校验
		List<IdentityLink> identityList = taskService.getIdentityLinksForTask(task.getId());
		for (IdentityLink link : identityList) {
			if (StringUtils.hasText(link.getUserId())) {
				if (CommonUtil.equals(link.getUserId(), loginUser.getName())) {
					return true;
				}
			}
			if (StringUtils.hasText(link.getGroupId())) {
				String[] authInfo = link.getGroupId().split(":", 2);
				return authorizationManager.hasAuthority(authInfo[0], authInfo[1], loginUser);
			}
		}

		return false;
	}

	/**
	 * 权限校验
	 *
	 * @param task 任务
	 * @return
	 */
	private boolean hasAuthority(HistoricTaskInstance task) throws Exception {
		// 操作者
		Authentication loginUser = SecurityContextHolder.getContext().getAuthentication();

		// 用户校验
		String assignee = task.getAssignee();
		if (StringUtils.hasText(assignee)) {
			if (CommonUtil.equals(assignee, loginUser.getName())) {
				return true;
			}
		}

		// 部门校验
		List<HistoricIdentityLinkLog> identityList = historyService.createHistoricIdentityLinkLogQuery().taskId(task.getId()).list();
		for (HistoricIdentityLinkLog link : identityList) {
			if (StringUtils.hasText(link.getUserId())) {
				if (CommonUtil.equals(link.getUserId(), loginUser.getName())) {
					return true;
				}
			}
			if (StringUtils.hasText(link.getGroupId())) {
				String[] authInfo = link.getGroupId().split(":", 2);
				return authorizationManager.hasAuthority(authInfo[0], authInfo[1], loginUser);
			}
		}

		return false;
	}
}
