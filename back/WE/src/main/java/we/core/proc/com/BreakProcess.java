/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.com;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.util.CommonUtil;
import we.base.util.DataUtil;
import we.base.util.VersionHolder;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;

/**
 * 中断处理
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class BreakProcess implements IProcess {

	@Autowired
	private VersionHolder versionHolder;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		if (StringUtils.hasText(proceeDto.getStringData1())) {
			// 判断条件
			String fieldKey = proceeDto.getStringData1();
			// 目标值
			String value = proceeDto.getStringData2();
			// 判断方式
			String type = proceeDto.getStringData3();

			// 校验
			boolean checkResult = checkValue(fieldKey, proceeDto, resultList, value, type);
			if (checkResult) {
				return true;
			}
		}

		if (proceeDto.getListData1() != null && proceeDto.getListData1().size() > 0) {
			for (Map<String, Object> dataMap : proceeDto.getListData1()) {
				// 判断条件
				String fieldKey = CommonUtil.toString(dataMap.get("field"));
				// 目标值
				String value = CommonUtil.toString(dataMap.get("value"));
				// 判断方式
				String type = CommonUtil.toString(dataMap.get("type"));

				// 校验
				boolean checkResult = checkValue(fieldKey, proceeDto, resultList, value, type);
				if (checkResult) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 校验实行
	 */
	private boolean checkValue(String fieldKey, ProcessDto proceeDto, List<Object> resultList, String value, String type) throws Exception {
		if (StringUtils.isEmpty(type)) {
			type = "0";
		}

		// 字段值
		String fieldValue = DataUtil.getProceeValue(fieldKey, proceeDto, resultList, versionHolder.getLastVersion());
		if (fieldValue == null) {
			fieldValue = "";
		}

		switch (type) {
		case "0":
			// 相等
			return CommonUtil.equals(fieldValue, value);
		case "1":
			// 部分匹配
			return fieldValue.indexOf(value) > -1;
		case "2":
			// 前匹配
			return fieldValue.startsWith(value);
		case "3":
			// 不等
			return !CommonUtil.equals(fieldValue, value);
		}

		return false;
	}
}
