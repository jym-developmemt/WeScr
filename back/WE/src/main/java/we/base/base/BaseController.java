/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;

import we.base.dto.SendDto;
import we.base.util.CommonUtil;
import we.website.model.ResourceInfoModel;

/**
 * 基础Controller类
 *
 * @author cp0
 * @since 0.0
 */
@Scope("prototype")
public class BaseController {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/** Spring上下文 */
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 取得Spring上下文
	 */
	protected WebApplicationContext getApplicationContext() {
		return (WebApplicationContext) applicationContext;
	}

	/**
	 * 传输正常数据
	 *
	 * @param transferData 数据
	 * @return 通信Dto
	 */
	protected SendDto successResult(Object transferData) {
		SendDto dto = new SendDto();
		dto.setResult(0);
		dto.setData(transferData);
		return dto;
	}

	/**
	 * 传输错误消息
	 *
	 * @param transferData 数据
	 * @return 通信Dto
	 */
	protected SendDto errorResult(String errorCode) {
		SendDto dto = new SendDto();
		dto.setResult(9);
		dto.setErrorCode(errorCode);
		return dto;
	}

	/**
	 * 传输资源数据
	 *
	 * @param resourceInfo 资源信息
	 * @return 通信Dto
	 */
	protected SendDto resourceResult(ResourceInfoModel resourceInfo) {
		Resource resource = applicationContext.getResource(resourceInfo.getResourcePath());
		if (resource.exists() == false) {
			return errorResult("s.common.error.none");
		}


		InputStream in = null;
		OutputStream out = null;

		try {
			in = resource.getInputStream();

			CommonUtil.getResponse().setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(resourceInfo.getResourceName(), "UTF-8"));
			CommonUtil.getResponse().setHeader("Content-Length", String.valueOf(resource.contentLength()));

			out = CommonUtil.getResponse().getOutputStream();
			IOUtils.copy(in, out);
		} catch (Exception e) {
			logger.error("资源输出失败", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) { }
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) { }
			}
		}
		return null;
	}
}
