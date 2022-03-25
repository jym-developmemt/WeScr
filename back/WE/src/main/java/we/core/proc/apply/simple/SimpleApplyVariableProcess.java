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
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.DataUtil;
import we.base.util.VersionHolder;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;

/**
 * 将流程变量存入对应数据中
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class SimpleApplyVariableProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private VersionHolder versionHolder;

	/**
	 * 处理实行
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 数据来源
		String dataPath = proceeDto.getStringData1();
		if (StringUtils.hasText(dataPath) == false) {
			logger.error("数据来源未输入。");
			throw new FacadeException("s.common.error.param");
		}
		// 数据类型(0:List 1:Map)
		String dataType = proceeDto.getStringData2();
		if (StringUtils.hasText(dataType) == false) {
			dataType = "0";
		}
		// 流程Key
		String instanceColumn = proceeDto.getStringData3();
		if (StringUtils.hasText(instanceColumn) == false) {
			instanceColumn = "instance_id";
		}

		// 返回数据列表
		if (CommonUtil.equals(dataType, "0")) {
			List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();

			// 数据取得
			List<Map<String, Object>> dataList = DataUtil.getProceeValue("$r." + dataPath, proceeDto, resultList,
					List.class, versionHolder.getLastVersion());
			if (dataList == null) {
				logger.error("数据不存在或已被删除。");
				throw new FacadeException("s.common.error.none");
			}

			for (Map<String, Object> dataInfo : dataList) {
				// 审批实例ID
				String instanceId = CommonUtil.toString(dataInfo.get(instanceColumn));

				ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId);
				ProcessInstance processInstance = query.singleResult();

				Map<String, Object> processVariable;
				if (processInstance != null) {
					processVariable = runtimeService.getVariables(processInstance.getId());
				} else {
					processVariable = new HashMap<String, Object>();

					List<HistoricVariableInstance> varList = historyService.createHistoricVariableInstanceQuery().processInstanceId(instanceId)
							.list();
					for (HistoricVariableInstance var : varList) {
						processVariable.put(var.getName(), var.getValue());
					}

				}
				processVariable.putAll(dataInfo);
				rtnList.add(processVariable);
			}

			return rtnList;
		}

		// 返回数据对象
		if (CommonUtil.equals(dataType, "1")) {
			// 数据取得
			Map<String, Object> dataInfo = DataUtil.getProceeValue("$r." + dataPath, proceeDto, resultList, Map.class,
					versionHolder.getLastVersion());
			if (dataInfo == null) {
				logger.error("数据不存在或已被删除。");
				throw new FacadeException("s.common.error.none");
			}

			// 审批实例ID
			String instanceId = CommonUtil.toString(dataInfo.get(instanceColumn));

			ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId);
			ProcessInstance processInstance = query.singleResult();

			Map<String, Object> rtnMap;
			if (processInstance != null) {
				rtnMap = runtimeService.getVariables(processInstance.getId());
			} else {
				rtnMap = new HashMap<String, Object>();

				List<HistoricVariableInstance> varList = historyService.createHistoricVariableInstanceQuery().processInstanceId(instanceId)
						.list();
				for (HistoricVariableInstance var : varList) {
					rtnMap.put(var.getName(), var.getValue());
				}
			}
			rtnMap.putAll(dataInfo);
			return rtnMap;
		}

		return null;
	}
}
