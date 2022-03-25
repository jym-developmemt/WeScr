/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.model.UserInfoModel;
import we.website.service.UserInfoService;

/**
 * 用户追加
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class UserAddProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 用户信息
		Map<String, Object> userInfoMap = proceeDto.getObjData1();

		// 用户ID
		String userId = CommonUtil.toString(userInfoMap.get("user_id"));
		if (StringUtils.isEmpty(userId)) {
			userId = CommonUtil.generateKey(32);
		}

		// 用户ID存在检查
		UserInfoModel userInfo = new UserInfoModel();
		userInfo.setUserId(userId);
		userInfo = userInfoService.findUserInfo(userInfo);
		if (userInfo != null) {
			logger.error("用户ID重复，请修改用户ID");
			throw new FacadeException("s.common.error.duplication");
		}

		// 用户登陆ID
		String loginUserId = CommonUtil.toString(userInfoMap.get("login_user_id"));
		if (StringUtils.hasText(loginUserId)) {
			userInfo = new UserInfoModel();
			userInfo.setLoginUserId(loginUserId);
			userInfo = userInfoService.findUserInfo(userInfo);
			if (userInfo != null) {
				logger.error("用户登陆ID重复，请修改用户登陆ID");
				throw new FacadeException("s.common.error.duplication");
			}
		}

		// BeanUtil处理时间格式
		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(new String[] { "yyyy/M/d HH:m:s", "yyyy/M/d", "yyyy/M/d HH:m", "yyyy-M-d HH:m:s", "yyyy-M-d", "yyyy-M-d HH:m" });
		ConvertUtils.register(dateConverter, Date.class);

		// 用户追加
		userInfo = new UserInfoModel();
		BeanUtils.populate(userInfo, CommonUtil.convertMapKey(userInfoMap));
		userInfo.setUserId(userId);
		userInfoService.addUserInfo(userInfo);

		return userId;
	}

}
