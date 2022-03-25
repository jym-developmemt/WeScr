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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.core.CoreProperties;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.model.UserInfoModel;
import we.website.service.UserInfoService;
import we.wechat.payment.WXPay;
import we.wechat.payment.WXPayConfig;

/**
 * 统一下单接口
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class UnifiedOrderProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private CoreProperties websiteProp;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		Map<String, Object> orderInfo = proceeDto.getObjData1();
		Map<String, Object> chargeInfo = proceeDto.getObjData2();

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

		// 缴费类型
		String chargeName = CommonUtil.toString(chargeInfo.get("charge_name"));
		if (StringUtils.isEmpty(totalFee)) {
			logger.error("缴费类型不能为空");
			throw new FacadeException("s.common.error.param");
		}

		// 缴费说明
		String chargeComment = CommonUtil.toString(chargeInfo.get("charge_comment"));
		if (StringUtils.isEmpty(totalFee)) {
			logger.error("缴费说明不能为空");
			throw new FacadeException("s.common.error.param");
		}

		// 企业微信用户Openid取得
		UserInfoModel userInfo = new UserInfoModel();
		userInfo.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
		userInfo = userInfoService.findUserInfo(userInfo);

		WXPayConfig config = new WXPayConfig();
		config.setAppID(websiteProp.getQyapiCorpid());
		config.setMchID(websiteProp.getQyapiMchid());
		config.setKey(websiteProp.getQyapiKey());
		config.setCertPath(websiteProp.getQyapiCertPath());

		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("trade_type", "JSAPI");
		reqData.put("body", "缴费支付-" + chargeName);
		reqData.put("detail", chargeComment);
		reqData.put("out_trade_no", orderId);
		reqData.put("total_fee", CommonUtil.toString(Double.valueOf((Double.valueOf(totalFee) * 100)).longValue()));
		reqData.put("notify_url", websiteProp.getApiServerUrl() + "/wxpay/order");
		reqData.put("openid", userInfo.getQywxOpenId());

		try {
			WXPay wxPayt = new WXPay(config);
			Map<String, String> result = wxPayt.unifiedOrder(reqData);
			if (CommonUtil.equals(result.get("return_code"), "SUCCESS") == false) {
				logger.error("订单创建失败:" + result.get("return_msg"));
				throw new FacadeException("s.common.error.process");
			}
			if (CommonUtil.equals(result.get("result_code"), "SUCCESS") == false) {
				logger.error("订单创建失败:" + result.get("err_code") + result.get("err_code_des"));
				throw new FacadeException("s.common.error.process");
			}

			// 预支付交易会话标识
			return result.get("prepay_id");
		} catch (Exception e) {
			logger.error("订单创建失败", e);
			throw new FacadeException("s.common.error.process");
		}
	}
}
