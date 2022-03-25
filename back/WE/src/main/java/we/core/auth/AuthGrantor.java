/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.auth;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import we.core.model.UserGrantedAuthorityModel;

/**
 * 权限校验器
 *
 * @author cp0
 * @since 0.0
 */
@Component
public interface AuthGrantor {

	/**
	 * 取得用户权限类型
	 */
	public String getAuthType();

	/**
	 * 检查用户是否有该权限
	 */
	public boolean hasAuthority(Authentication userInfo, String args) throws Exception;

	/**
	 * 取得用户权限
	 */
	public List<UserGrantedAuthorityModel> searchUserGranted(Authentication userInfo) throws Exception;

	/**
	 * 取得权限用户
	 */
	public List<String> searchUserInfo(String args) throws Exception;

}
