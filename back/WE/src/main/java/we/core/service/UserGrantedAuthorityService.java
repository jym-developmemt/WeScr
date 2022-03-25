/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import we.core.model.UserGrantedAuthorityModel;
import we.website.model.UserInfoModel;

/**
 * 用户权限信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public interface UserGrantedAuthorityService {

	/**
	 * 用户权限信息一览取得
	 */
	public List<UserGrantedAuthorityModel> searchUserGrantedAuthority(UserInfoModel userInfo);

}
