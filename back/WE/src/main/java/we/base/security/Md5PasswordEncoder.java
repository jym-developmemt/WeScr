/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;

/**
 * 用户密码加密类
 *
 * @author cp0
 * @since 0.0
 */
public class Md5PasswordEncoder implements PasswordEncoder {

	/**
	 * 密码加密
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return EncryptorUtil.MD5(rawPassword.toString());
	}

	/**
	 * 密码校验
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return CommonUtil.equals(EncryptorUtil.MD5(rawPassword.toString()), encodedPassword);
	}

}
