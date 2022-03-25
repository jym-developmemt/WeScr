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
import we.core.CoreProperties;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.wechat.payment.WXPay;
import we.wechat.payment.WXPayConfig;

/**
 * 用户追加
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class RefundProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CoreProperties websiteProp;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		Map<String, Object> orderInfo = proceeDto.getObjData1();

		// 订单ID
		String orderId = CommonUtil.toString(orderInfo.get("order_id"));
		if (StringUtils.isEmpty(orderId)) {
			logger.error("订单ID不能为空");
			throw new FacadeException("s.common.error.param");
		}

		// 缴费金额
		String totalFee = CommonUtil.toString(orderInfo.get("order_price"));
		if (StringUtils.isEmpty(totalFee)) {
			logger.error("缴费不能为空");
			throw new FacadeException("s.common.error.param");
		}

		WXPayConfig config = new WXPayConfig();
		config.setAppID(websiteProp.getQyapiCorpid());
		config.setMchID(websiteProp.getQyapiMchid());
		config.setKey(websiteProp.getQyapiKey());
		config.setCertPath(websiteProp.getQyapiCertPath());

		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("out_trade_no", orderId);
		reqData.put("out_refund_no", orderId);
		reqData.put("total_fee", CommonUtil.toString(Double.valueOf((Double.valueOf(totalFee) * 100)).longValue()));
		reqData.put("refund_fee", CommonUtil.toString(Double.valueOf((Double.valueOf(totalFee) * 100)).longValue()));
		reqData.put("notify_url", websiteProp.getApiServerUrl() + "/wxpay/refund");

		try {
			WXPay wxPayt = new WXPay(config);
			Map<String, String> result = wxPayt.refund(reqData);
			if (CommonUtil.equals(result.get("return_code"), "SUCCESS") == false) {
				logger.error("退款申请失败:" + result.get("return_msg"));
				throw new FacadeException("s.common.error.process");
			}
			if (CommonUtil.equals(result.get("result_code"), "SUCCESS") == false) {
				logger.error("退款申请失败:" + result.get("err_code") + result.get("err_code_des"));
				throw new FacadeException("s.common.error.process");
			}
		} catch (Exception e) {
			logger.error("退款申请失败", e);
			throw new FacadeException("s.common.error.process");
		}

		return null;
	}
}
