/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.core.dto.ProcessDto;
import we.core.model.DataSourceDataModel;
import we.core.proc.IProcess;
import we.website.service.CallProcedureService;

/**
 * 任务实例检索
 *
 * @author cp0
 * @since 0.0
 */
@Component()
public class CallProcedureProcess implements IProcess {

	@Autowired
	private CallProcedureService callProcedureService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 存储过程名称
		String procedureName = proceeDto.getStringData1();

		// 超长字符串区分
		String maxStringFlg = proceeDto.getStringData3();

		// 输入参数
		Map<String, Object> condition = proceeDto.getObjData1();

		Map<String, String> dataParam = new HashMap<String, String>();

		List<Object> resuList = new ArrayList<Object>();
		try {

			if (condition!=null && condition.size() > 0) {

				for (String key : condition.keySet()) {

					String value = condition.get(key).toString();
					dataParam.put(key, value);
				}
			}

			DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();

			dataSourceDataModel.setTableName(procedureName);

			dataSourceDataModel.setDataParam(dataParam);

			List<Map<String, Object>> list = callProcedureService.checkProcedure(dataSourceDataModel);
			if (list.size() > 0) {
				if (StringUtils.hasText(maxStringFlg)) {
					String strMax = "";
					for (int i = 0; i < list.size(); i++) {
						for (Entry<String, Object> entry : list.get(i).entrySet()) {
							strMax += entry.getValue();
							if (strMax != null) {
								break;
							}
						}
					}
					Map<String, Object> resuMap = new HashMap<String, Object>();
					resuMap.put("resultValue", strMax);
					resuList.add(resuMap);
				} else {
					resuList.add(list.get(0));
				}
			}

		} catch (Exception e) {
			throw new FacadeException("存储过程执行错误。" + e);
		}

		return resuList;
	}
}
