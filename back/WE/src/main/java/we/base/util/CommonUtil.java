/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 通用工具类
 *
 * @author cp0
 * @since 0.0
 */
public class CommonUtil {

	/** 日志 */
	protected static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 取得Web服务URL
	 *
	 * @return Web服务URL
	 */
	public static String getWebServerUrl() {
		HttpServletRequest request = getRequest();
		StringBuffer url = new StringBuffer();
		url.append(request.getScheme());
		url.append("://");
		url.append(request.getServerName());
		url.append(":");
		url.append(request.getServerPort());
		url.append(request.getContextPath());
		url.append("/");
		return url.toString();
	}

	/**
	 * 取得Request对象
	 *
	 * @return Request对象
	 */
	public static HttpServletRequest getRequest() {
		// Http Request
		HttpServletRequest request = null;

		// Spring Request
		try {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			if (request != null) {
				return request;
			}
		} catch (Exception e) {
		}

		logger.error("Could not found HttpServletRequest.");
		return request;
	}

	/**
	 * 取得Request对象
	 *
	 * @return Request对象
	 */
	public static HttpServletResponse getResponse() {
		// Http Response
		HttpServletResponse response = null;

		// Spring Response
		try {
			response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
			if (response != null) {
				return response;
			}
		} catch (Exception e) {
		}

		logger.error("Could not found HttpServletResponse.");
		return response;
	}

	/**
	 * 取得ClassPath
	 *
	 * @return ClassPath
	 */
	public static String getClassPath() {
		String classPath = CommonUtil.class.getResource("/").getFile();
		if (StringUtils.hasText(classPath) == false) {
			ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			classPath = servletContext.getRealPath("/") + "WEB-INF\\classes\\";
		}

		try {
			classPath = URLDecoder.decode(classPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}

		return classPath;
	}

	/**
	 * 取得根目录
	 *
	 * @return 根目录
	 */
	public static String getRootPath() {
		File rootFile = new File(CommonUtil.getClassPath()).getParentFile().getParentFile();
		return rootFile.getAbsolutePath();
	}

	/**
	 * 取得系统时间
	 *
	 * @return 系统时间
	 */
	public static String getSystemDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	}

	/**
	 * 生成主键(16位)
	 *
	 * @return 主键
	 */
	public static String generateKey() {
		return generateKey(16);
	}

	/**
	 * 生成主键
	 *
	 * @param length 长度
	 * @return 主键
	 */
	public static String generateKey(int length) {
		if (length <= 0) {
			return "";
		}
		String ctm = String.valueOf(System.currentTimeMillis());
		if (ctm.length() == length) {
			return ctm;
		} else if (ctm.length() > length) {
			return ctm.substring(ctm.length() - length, ctm.length());
		} else {
			return ctm + generateDummyKey(length - ctm.length());
		}
	}

	/**
	 * 生成随机码
	 *
	 * @param length 长度
	 * @return 随机码
	 */
	public static String generateDummyKey(int length) {
		Random r = new Random(new Date().getTime());
		StringBuffer strKey = new StringBuffer();
		for (int i=0;i<length;i++) {
			strKey.append(r.nextInt(10));
		}
		return strKey.toString();
	}

	/**
	 * 生成随机码
	 *
	 * @param length 长度
	 * @return 随机码
	 */
	public static String getRandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * NULL变换
	 *
	 * @param s 原文字
	 * @return 变换后文字
	 */
	public static String nvl(String s) {
		if (s == null) {
			s = "";
		}
		return s;
	}

	/**
	 * 相等判断
	 */
	public static boolean equals(String value1, String value2) {
		if (value1 == null) {
			value1 = "";
		} else {
			value1 = value1.trim();
		}
		if (value2 == null) {
			value2 = "";
		} else {
			value2 = value2.trim();
		}
		return value1.equals(value2);
	}

	/**
	 * 数组转文字
	 */
	public static String join(Object[] array, String flag) {
		StringBuffer strBuff = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				strBuff.append(flag);
			}
			strBuff.append(String.valueOf(array[i]));
		}
		return strBuff.toString();
	}
	
	/**
	 * 文字转数组
	 */
	public static String[] split(String text, String flag) {
		if (StringUtils.hasText(text) == false) {
			return new String[0];
		}

		List<String> rtnList = new ArrayList<String>();
		for (String singleValue : text.split(flag)) {
			if (StringUtils.hasText(singleValue)) {
				rtnList.add(singleValue.trim());
			}
		}
		return rtnList.toArray(new String[rtnList.size()]);
	}

	/**
	 * 对象转字符串
	 */
	public static String toString(Object o) {
		if (o == null) {
			return "";
		}
		return o.toString();
	}

	/**
	 * Map型的key，aa_bbb_ccc 转 aaBbbCcc
	 */
	public static Map<String, Object> convertMapKey(Map<String, Object> dataMap) {
		if (dataMap == null) {
			return dataMap;
		}

		Map<String, Object> rtnMap = new HashMap<String, Object>();

		for (Entry<String,Object> entry : dataMap.entrySet()) {

			Object entryValue = entry.getValue();
			if ("".equals(entryValue)) {
				entryValue = null;
			}

			if (entry.getKey().indexOf("_") < 0) {
				rtnMap.put(entry.getKey(), entryValue);
				continue;
			}

			// 生成新key
			StringBuffer strKey = new StringBuffer();
			String[] keyInfos = entry.getKey().split("_");
			for (String keyInfo : keyInfos) {
				if (strKey.length() == 0) {
					strKey.append(keyInfo);
				} else {
					strKey.append(keyInfo.substring(0, 1).toUpperCase() + keyInfo.substring(1));
				}
			}

			// 数据转移
			rtnMap.put(strKey.toString(), entryValue);
		}
		return rtnMap;
	}

	/**
	 * 相对路径转绝对路径
	 */
	public static String convertToAbsolutePath(String path) {
		String uploadAbsolutePath;

		String[] uploadInfo = path.split(":", 2);
		if (CommonUtil.equals(uploadInfo[0], "classpath")) {
			uploadAbsolutePath = FilenameUtils.concat(CommonUtil.getClassPath(), uploadInfo[1]);
		} else if (CommonUtil.equals(uploadInfo[0], "file")) {
			uploadAbsolutePath = uploadInfo[1];
		} else {
			uploadAbsolutePath = path;
		}
		return (new File(uploadAbsolutePath)).getAbsolutePath();
	}

	/**
	 * 发送GET请求，并取得JSON返回值
	 */
	public static JSONObject sendGetRequest(String strUrl) throws Exception {
		HttpURLConnection conn = null;
		InputStream is = null;

		try {

			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			//设置连接方式
			conn.setRequestMethod("GET");
			//设置主机连接时间超时时间3000毫秒
			conn.setConnectTimeout(3000);
			//设置读取远程返回数据的时间3000毫秒
			conn.setReadTimeout(3000);

			conn.connect();

			is = conn.getInputStream();

			//封装输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			//接收读取数据
			StringBuffer sb = new StringBuffer();

			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			br.close();

			return new JSONObject(sb.toString());
		} finally {
			if (is != null) {
				is.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	/**
	 * 发送POST请求，并取得JSON返回值
	 * 参数为JSon格式
	 */
	public static JSONObject sendPostRequest(String strUrl, JSONObject params) throws Exception {
		HttpURLConnection conn = null;
		OutputStreamWriter out = null;
		InputStream is = null;

		try {

			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			//设置连接方式
			conn.setRequestMethod("POST");
			//设置主机连接时间超时时间3000毫秒
			conn.setConnectTimeout(3000);
			//设置读取远程返回数据的时间3000毫秒
			conn.setReadTimeout(3000);
			conn.setDoInput(true);
			if (params != null) {
				conn.setDoOutput(true);
			}
			conn.connect();

			if (params != null) {
				out = new OutputStreamWriter(conn.getOutputStream());
				out.write(params.toString());
				out.flush();
				out.close();
			}

			is = conn.getInputStream();

			//封装输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			//接收读取数据
			StringBuffer sb = new StringBuffer();

			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			br.close();

			return new JSONObject(sb.toString());
		} finally {
			if (out != null) {
				out.close();
			}
			if (is != null) {
				is.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	/**
	 * 相对路径转绝对路径
	 */
	public static Map<String, Object> jsonToMap(String json) throws Exception {
		if (StringUtils.isEmpty(json)) {
			return null;
		}

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, new TypeReference<HashMap<String, Object>>(){});
	}
	
	/**
	 * objToStringList
	 */
	public static List<String> objToStringList(Object obj) throws Exception {
		List<String> result  = new ArrayList<String>();
		
		if (obj instanceof ArrayList<?>) {
			for (Object o : (List<?>) obj) {
				result.add(String.class.cast(o));
			}
		}
		
		return result;
	}
}
