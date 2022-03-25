/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import we.base.base.BaseController;
import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;
import we.base.util.TokenUtils;
import we.core.CoreProperties;
import we.core.dto.ProcessDto;
import we.core.proc.page.PageDataSearchProcess;
import we.core.proc.page.PageDataUpdateProcess;
import we.wechat.payment.WXPayUtil;

/**
 * 通知信息Controller
 *
 * @author cp0
 * @since 0.0
 */
@RestController
@RequestMapping("/wxpay")
public class WXNotifyController extends BaseController {

	@Autowired
	private PageDataSearchProcess searchProcess;

	@Autowired
	private PageDataUpdateProcess updateProcess;

	@Autowired
	private CoreProperties websiteProp;

	/**
	 * 通知一览取得
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/order", produces = { "application/xml; charset=UTF-8" })
	public Map<String, Object> order(@RequestBody Map<String, Object> notifyData) throws Exception {

		// 用户认证
		SecurityContextHolder.getContext().setAuthentication(TokenUtils.createBatchAuthentication());

		String orderId = CommonUtil.toString(notifyData.get("out_trade_no"));
		double payPrice = Double.valueOf(CommonUtil.toString(notifyData.get("total_fee")));

		ProcessDto proceeDto = new ProcessDto();
		proceeDto.setStringData1("4AB5B252708B79D4");
		proceeDto.setStringData2("3FE7E96149325FA6");
		proceeDto.setStringData3("11");

		Map<String, Object> condData = new HashMap<String, Object>();
		condData.put("order_id", orderId);
		proceeDto.setObjData1(condData);
		List<Object> resultList = new ArrayList<Object>();
		Map<String, Object> dataMap = (Map<String, Object>) searchProcess.execute(proceeDto, resultList);
		double orderPrice = Double.valueOf(CommonUtil.toString(dataMap.get("order_price"))) * 100;

		Map<String, Object> updData = new HashMap<String, Object>();
		if (payPrice == orderPrice) {
			updData.put("account_status", "1");
		} else {
			updData.put("account_status", "2");
		}
		proceeDto.setObjData1(updData);
		proceeDto.setObjData2(condData);
		updateProcess.execute(proceeDto, resultList);

		// 结果返回
		Map<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("return_code", "SUCCESS");
		return rtnData;
	}

	/**
	 * 通知一览取得
	 */
	@RequestMapping(value = "/refund", produces = { "application/xml; charset=UTF-8" })
	public Map<String, Object> refund(@RequestBody Map<String, Object> notifyData) throws Exception {

		// 用户认证
		SecurityContextHolder.getContext().setAuthentication(TokenUtils.createBatchAuthentication());

		String key = EncryptorUtil.MD5(websiteProp.getQyapiKey()).toLowerCase();
		String reqInfo = CommonUtil.toString(notifyData.get("req_info"));
		String xmlData = WXPayUtil.decryptAES256(reqInfo, key);

		Document document = DocumentHelper.parseText(xmlData);
		String orderId = document.getRootElement().element("out_trade_no").getText();

		ProcessDto proceeDto = new ProcessDto();
		proceeDto.setStringData1("4AB5B252708B79D4");
		proceeDto.setStringData2("3FE7E96149325FA6");
		proceeDto.setStringData3("11");

		Map<String, Object> condData = new HashMap<String, Object>();
		condData.put("order_id", orderId);

		Map<String, Object> updData = new HashMap<String, Object>();
		updData.put("refund_status", "2");

		proceeDto.setObjData1(updData);
		proceeDto.setObjData2(condData);

		List<Object> resultList = new ArrayList<Object>();
		updateProcess.execute(proceeDto, resultList);

		// 结果返回
		Map<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("return_code", "SUCCESS");
		return rtnData;
	}
}
