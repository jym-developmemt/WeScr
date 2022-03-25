/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade;

import java.util.Map;

import we.base.exception.FacadeException;
import we.website.dto.UserInfoDto;

/**
 * 用户信息Facade
 *
 * @author cp0
 * @since 3.0
 */
public interface UserInfoFacade {
	/**
	 * 用户信息取得
	 */
	public Map<String, Object> findUserInfo(UserInfoDto userInfoDto) throws FacadeException;

	/**
	 * 用户前回登陆时间更新
	 */
	public void updateLoginDate(String userId);


	/**
	 * 更新用户信息
	 */
	public int updateUserInfo(UserInfoDto userInfoDto) throws Exception;
}
