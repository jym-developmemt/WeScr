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
import we.base.util.DataUtil;
import we.base.util.EncryptorUtil;
import we.base.util.VersionHolder;
import we.core.CoreProperties;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;

/**
 * 用户追加
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class PayRequestProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String PARAM_APP_ID = "appId";

	private static final String PARAM_TIMESTAMP = "timeStamp";

	private static final String PARAM_NONCESTR = "nonceStr";

	private static final String PARAM_PACKAGE = "package";

	private static final String PARAM_SIGNTYPE = "signType";

	private static final String PARAM_SIGNTYPE_MD5 = "MD5";

	private static final String PARAM_KEY = "key";

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private VersionHolder versionHolder;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 预支付交易会话标识
		String prepayId = proceeDto.getStringData1();
		if (StringUtils.isEmpty(prepayId)) {
			logger.error("预支付交易ID不能为空");
			throw new FacadeException("s.common.error.param");
		}
		prepayId = DataUtil.getProceeValue(prepayId, proceeDto, resultList, versionHolder.getLastVersion());

		// 随机字符串
		String noncestr = CommonUtil.getRandomString(10);
		// 时间戳
		long timestamp = System.currentTimeMillis();

		// string1
		StringBuffer string1 = new StringBuffer();
		string1.append(PARAM_APP_ID);
		string1.append("=");
		string1.append(websiteProp.getQyapiCorpid());
		string1.append("&");
		string1.append(PARAM_NONCESTR);
		string1.append("=");
		string1.append(noncestr);
		string1.append("&");
		string1.append(PARAM_PACKAGE);
		string1.append("=prepay_id=");
		string1.append(prepayId);
		string1.append("&");
		string1.append(PARAM_SIGNTYPE);
		string1.append("=");
		string1.append(PARAM_SIGNTYPE_MD5);
		string1.append("&");
		string1.append(PARAM_TIMESTAMP);
		string1.append("=");
		string1.append(timestamp);
		string1.append("&");
		string1.append(PARAM_KEY);
		string1.append("=");
		string1.append(websiteProp.getQyapiKey());

		String signature = EncryptorUtil.MD5(string1.toString());
		if (StringUtils.isEmpty(signature)) {
			logger.error("签名失败");
			throw new FacadeException("s.common.error.process");
		}
		signature = signature.toUpperCase();

		// 权限验证配置
		Map<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("appId", websiteProp.getQyapiCorpid());
		rtnData.put("timeStamp", timestamp);
		rtnData.put("nonceStr", noncestr);
		rtnData.put("package", "prepay_id=" + prepayId);
		rtnData.put("signType", PARAM_SIGNTYPE_MD5);
		rtnData.put("paySign", signature);
		return rtnData;
	}
}
