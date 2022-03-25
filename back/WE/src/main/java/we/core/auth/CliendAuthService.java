/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户认证Service(ClientID区分)
 *
 * @author cp0
 * @since 5.0
 */
public interface CliendAuthService {

	/**
	 * ClientID支持校验
	 *
	 * @param clientId
	 */
	boolean supports(String clientId);

	/**
	 * 是否需要校验密码
	 *
	 * @param clientId
	 */
	boolean checkPassword();

	/**
	 * 取得用户信息
	 *
	 * @param username
	 */
	UserDetails loadUserByUsername(String username) throws AuthenticationException;
}
