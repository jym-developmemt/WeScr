/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.model;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;

import we.base.base.BaseModel;
import we.base.util.CommonUtil;

/**
 * 人员权限信息模型
 *
 * @author cp0
 * @since 0.0
 */
public abstract class UserGrantedAuthorityModel extends BaseModel implements GrantedAuthority {

	/** 权限值 */
	public abstract String getValue();

	/** 权限类型 */
	public abstract String getType();

	@Override
	public String getAuthority() {
		return getType() + ":" + getValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserGrantedAuthorityModel) {
			UserGrantedAuthorityModel target = (UserGrantedAuthorityModel) obj;
			if (CommonUtil.equals(getType(), target.getType())
					&& CommonUtil.equals(getValue(), target.getValue())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAuthority());
	}
}
