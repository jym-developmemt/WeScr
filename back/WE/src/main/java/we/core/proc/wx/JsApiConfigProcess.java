/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.wx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;
import we.core.CoreProperties;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.wechat.JSApiTickerManager;

/**
 * 用户追加
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class JsApiConfigProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String PARAM_JSAPI_TICKET = "jsapi_ticket";

	private static final String PARAM_NONCESTR = "noncestr";

	private static final String PARAM_TIMESTAMP = "timestamp";

	private static final String PARAM_URL = "url";

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private JSApiTickerManager jsApiTickerManager;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// URL
		String url = proceeDto.getStringData1();
		if (StringUtils.isEmpty(url)) {
			logger.error("URL不能为空");
			throw new FacadeException("s.common.error.param");
		}

		// URL
		String apiList = proceeDto.getStringData2();
		if (StringUtils.isEmpty(apiList)) {
			logger.error("JS接口列表不能为空");
			throw new FacadeException("s.common.error.param");
		}

		// 票据
		String jsapiTicket = jsApiTickerManager.getTicker();
		// 随机字符串
		String noncestr = CommonUtil.getRandomString(10);
		// 时间戳
		long timestamp = System.currentTimeMillis();

		// string1
		StringBuffer string1 = new StringBuffer();
		string1.append(PARAM_JSAPI_TICKET);
		string1.append("=");
		string1.append(jsapiTicket);
		string1.append("&");
		string1.append(PARAM_NONCESTR);
		string1.append("=");
		string1.append(noncestr);
		string1.append("&");
		string1.append(PARAM_TIMESTAMP);
		string1.append("=");
		string1.append(timestamp);
		string1.append("&");
		string1.append(PARAM_URL);
		string1.append("=");
		string1.append(url);

		String signature = EncryptorUtil.shaEncode(string1.toString());
		if (StringUtils.isEmpty(signature)) {
			logger.error("签名失败");
			throw new FacadeException("s.common.error.process");
		}

		// 权限验证配置
		Map<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("beta", true);
		rtnData.put("debug", false);
		rtnData.put("appId", websiteProp.getQyapiCorpid());
		rtnData.put("timestamp", timestamp);
		rtnData.put("nonceStr", noncestr);
		rtnData.put("signature", signature);
		rtnData.put("jsApiList", apiList.split(","));
		return rtnData;
	}
}
