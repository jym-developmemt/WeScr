/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.com;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import we.core.service.SqlSessionService;

/**
 * SqlSession实行处理
 *
 * @author cp0
 * @since 5.0
 */
@Component
public class SqlSessionProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SqlSessionService sqlSessionService;

	@Autowired
	private VersionHolder versionHolder;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// SQL ID
		String statement = proceeDto.getStringData1();
		if (StringUtils.hasText(statement) == false) {
			logger.error("ID未输入。");
			throw new FacadeException("s.common.error.param");
		}

		// 执行方式
		String type = proceeDto.getStringData2();
		if (StringUtils.hasText(type) == false) {
			type = "0";
		}

		// 参数
		Map<String, Object> parameter = proceeDto.getObjData1();
		if (parameter != null) {
			convertValue(parameter, proceeDto, resultList);
		}

		if (CommonUtil.equals(type, "0")) {
			return sqlSessionService.search(statement, parameter);
		} else if (CommonUtil.equals(type, "1")) {
			return sqlSessionService.find(statement, parameter);
		} else if (CommonUtil.equals(type, "2")) {
			return sqlSessionService.insert(statement, parameter);
		} else if (CommonUtil.equals(type, "3")) {
			return sqlSessionService.update(statement, parameter);
		} else if (CommonUtil.equals(type, "4")) {
			return sqlSessionService.delete(statement, parameter);
		}

		return false;
	}

	/**
	 * 值转换
	 */
	@SuppressWarnings("unchecked")
	private void convertValue(Map<String, Object> parameter, ProcessDto proceeDto, List<Object> resultList)
			throws Exception {
		if (parameter == null) {
			return;
		}

		for (Entry<String, Object> entry : parameter.entrySet()) {
			if (entry.getValue() instanceof String) {
				String convertValue = DataUtil.getProceeValue((String) entry.getValue(), proceeDto, resultList,
						versionHolder.getLastVersion());
				entry.setValue(convertValue);
			} else if (entry.getValue() instanceof Map) {
				convertValue((Map<String, Object>) entry.getValue(), proceeDto, resultList);
			}
		}
	}
}
