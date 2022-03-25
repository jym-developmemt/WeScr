/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import we.core.dto.ProcessDto;

/**
 * 数据工具类
 *
 * @author cp0
 * @since 3.0
 */
public class DataUtil {

	/**
	 * 将默认值保留文字转换为实际值
	 *
	 * @return 默认值的实际值
	 */
	public static String convertDefaultValue(String key, Map<String, Object> dataMap) {

		// 登陆者ID
		if (CommonUtil.equals(key, "_auto_")) {
			return CommonUtil.generateKey();
		}

		// 登陆者ID
		if (CommonUtil.equals(key, "_loginuser_")) {
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}

		// 系统时间
		if (CommonUtil.equals(key, "_sysdate_")) {
			return CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss");
		}

		// 序号
		if (CommonUtil.equals(key, "_index_")) {
			return CommonUtil.toString(dataMap.get(key));
		}



		return key;
	}

	/**
	 * 变量表达式转换为实际值
	 * 格式: $s1, $o1.key, $l2.0.key, $r.1.key
	 *
	 * @return 处理间变量的实际值
	 */
	public static String getProceeValue(String fieldKey, ProcessDto proceeDto, List<Object> resultList, int version) {
		Object dataValue = getProceeValue(fieldKey, proceeDto, resultList, Object.class, version);
		if (dataValue == null) {
			return null;
		}
		return CommonUtil.toString(dataValue);
	}

	/**
	 * 变量表达式转换为实际值
	 * 格式: $s1, $o1.key, $l2.0.key, $r.1.key
	 *
	 * @return 处理间变量的实际值
	 */
	public static <T> T getProceeValue(String fieldKey, ProcessDto proceeDto, List<Object> resultList, Class<T> type, int version) {
		if (StringUtils.isEmpty(fieldKey)) {
			return null;
		}

		if (fieldKey.startsWith("$") == false || proceeDto == null) {
			return type.cast(fieldKey);
		}

		String[] fieldKeys = fieldKey.substring(1).split("[.]");
		String scope = fieldKeys[0];
		Object rtnValue;

		switch (scope) {
		case "loginuser":
			rtnValue = SecurityContextHolder.getContext().getAuthentication().getName();
			break;
		case "version":
			rtnValue = version;
			break;
		case "sysdate":
			rtnValue = CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss");
			break;
		case "s1":
			rtnValue = proceeDto.getStringData1();
			break;
		case "s2":
			rtnValue = proceeDto.getStringData2();
			break;
		case "s3":
			rtnValue = proceeDto.getStringData3();
			break;
		case "s4":
			rtnValue = proceeDto.getStringData4();
			break;
		case "s5":
			rtnValue = proceeDto.getStringData5();
			break;
		case "s6":
			rtnValue = proceeDto.getStringData6();
			break;
		case "s7":
			rtnValue = proceeDto.getStringData7();
			break;
		case "s8":
			rtnValue = proceeDto.getStringData8();
			break;
		case "s9":
			rtnValue = proceeDto.getStringData9();
			break;
		case "s10":
			rtnValue = proceeDto.getStringData10();
			break;
		case "o1":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getObjData1());
			break;
		case "o2":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getObjData2());
			break;
		case "o3":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getObjData3());
			break;
		case "o4":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getObjData4());
			break;
		case "o5":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getObjData5());
			break;
		case "o6":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getObjData6());
			break;
		case "o7":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getObjData7());
			break;
		case "o8":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getObjData8());
			break;
		case "o9":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getObjData9());
			break;
		case "o10":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getObjData10());
			break;
		case "l1":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getListData1());
			break;
		case "l2":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getListData1());
			break;
		case "l3":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getListData1());
			break;
		case "l4":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getListData1());
			break;
		case "l5":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getListData1());
			break;
		case "l6":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getListData1());
			break;
		case "l7":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getListData1());
			break;
		case "l8":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getListData1());
			break;
		case "l9":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getListData1());
			break;
		case "l10":
			rtnValue = getProceeValue(fieldKeys, 1, proceeDto.getListData1());
			break;
		case "r":
			rtnValue = getProceeValue(fieldKeys, 1, resultList);
			break;
		default:
			rtnValue = fieldKey;
		}

		if (StringUtils.isEmpty(rtnValue)) {
			return null;
		}
		return type.cast(rtnValue);
	}

	@SuppressWarnings("rawtypes")
	private static Object getProceeValue(String[] fieldKeys, int keyIndex, Object dataMap) {
		if (dataMap == null || keyIndex >= fieldKeys.length) {
			return dataMap;
		}

		String fieldKey = fieldKeys[keyIndex];

		Object rtnValue;
		if (dataMap instanceof Map) {
			rtnValue = ((Map) dataMap).get(fieldKey);
		} else if (dataMap instanceof List) {
			try {
				rtnValue = ((List) dataMap).get(Integer.valueOf(fieldKey));
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				rtnValue = PropertyUtils.getProperty(dataMap, fieldKey);
			} catch (Exception e) {
				return null;
			}
		}

		return getProceeValue(fieldKeys, keyIndex + 1, rtnValue);
	}

	@SuppressWarnings("rawtypes")
	private static Object getProceeValue(String[] fieldKeys, int keyIndex, List dataMapList) {
		if (keyIndex >= fieldKeys.length) {
			return dataMapList;
		}

		int fieldIndex = Integer.valueOf(fieldKeys[keyIndex]);
		return getProceeValue(fieldKeys, keyIndex + 1, dataMapList.get(fieldIndex));
	}

	/**
	 * 组装路径文字
	 *
	 * @return 默认值的实际值
	 */
	public static String convertPathValue(String key, Map<String, Object> dataMap) {
		if (StringUtils.isEmpty(key)) {
			return "";
		}

		Pattern pattern = Pattern.compile("\\$\\{.+?\\}");
		Matcher matcher = pattern.matcher(key);
		int start = 0;

		while (matcher.find(start)) {

			String item = matcher.group();
			String[] itemInfo = item.substring(2, item.length() - 1).split("[.]");
			String itemValue = "<error>";

			try {
				if (itemInfo.length == 1) {
					itemValue = CommonUtil.toString(dataMap.get(itemInfo[0]));
				} else if (itemInfo.length == 2) {
					Object data = dataMap.get(itemInfo[0]);
					if (data != null) {
						itemValue = BeanUtils.getProperty(data, itemInfo[1]);
					}
				}
			} catch (Exception e) {
			}

			if (itemValue == null) {
				itemValue = "";
			}

			key = key.replace(item, itemValue);

			start = matcher.end();
		}
		return key;
	}
}
