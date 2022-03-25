/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.user;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.model.UserInfoModel;
import we.website.service.UserInfoService;

/**
 * 用户信息更新
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class UserUpdPasswordProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 用户ID
		String userId = proceeDto.getStringData1();
		if (StringUtils.isEmpty(userId)) {
			logger.error("用户ID不能为空");
			throw new FacadeException("s.common.error.param");
		}

		// 用户存在检查
		UserInfoModel userInfo = new UserInfoModel();
		userInfo.setUserId(userId);
		userInfo = userInfoService.findUserInfo(userInfo);
		if (userInfo == null) {
			logger.error("用户信息不存在");
			throw new FacadeException("s.common.error.none");
		}

		// 原密码
		String oldPassword = proceeDto.getStringData2();
		if (StringUtils.isEmpty(oldPassword)) {
			logger.error("原密码不能为空");
			throw new FacadeException("s.common.error.param");
		}

		if (!CommonUtil.equals(EncryptorUtil.MD5(oldPassword), userInfo.getPassword())) {
			logger.error("原密码不正确");
			throw new FacadeException("s.common.error.param");
		}

		// 新密码
		String newPassword = proceeDto.getStringData3();
		if (StringUtils.isEmpty(newPassword)) {
			logger.error("新密码不能为空");
			throw new FacadeException("s.common.error.param");
		}

		// BeanUtil处理时间格式
		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(new String[] { "yyyy/M/d HH:m:s", "yyyy/M/d", "yyyy/M/d HH:m", "yyyy-M-d HH:m:s", "yyyy-M-d", "yyyy-M-d HH:m" });
		ConvertUtils.register(dateConverter, Date.class);

		// 用户更新
		userInfo = new UserInfoModel();
		userInfo.setUserId(userId);
		userInfo.setPassword(newPassword);
		userInfoService.updateUserInfo(userInfo);

		return null;
	}

}
