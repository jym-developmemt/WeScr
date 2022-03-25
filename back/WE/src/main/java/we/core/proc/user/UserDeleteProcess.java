/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.user;

import java.util.List;
import java.util.Map;

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
 * 用户信息更新
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class UserDeleteProcess implements IProcess {

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

		// 用户更新
		userInfo = new UserInfoModel();
		userInfo.setUserId(userId);
		userInfoService.deleteUserInfo(userInfo);

		return null;
	}

}
