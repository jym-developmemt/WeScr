/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.auth;

import org.springframework.stereotype.Component;

/**
 * 权限类型
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class AuthGrantorType {

	/** 登录用户 */
	public static final String LOGIN_USER = "A00";

	/** 用户ID */
	public static final String USER_ID = "A01";

	/** TODO 用户属性 */
	public static final String USER_ATTRIBUTE = "A02";

	/** 用户部门信息 */
	public static final String USER_DEPARTMENT = "A10";

	/** 用户职位信息 */
	public static final String USER_POSITION = "A11";

	/** 用户部门职位信息 */
	public static final String USER_DEPARTMENT_POSITION = "A12";

	/** 用户管理部门信息 */
	public static final String USER_MANAGE_DEPARTMENT = "A13";

	/** 用户角色信息 */
	public static final String USER_ROLE = "A20";

	/** 用户部门角色信息 */
	public static final String USER_DEPARTMENT_ROLE = "A21";

	/** 用户功能信息 */
	public static final String USER_FUNCTION = "A30";

	/** TODO 系统设定信息（文字项目） */
	public static final String SETTING_VARCHAR = "B01";

	/** TODO 系统设定信息（日期项目） */
	public static final String SETTING_DATE = "B02";

}
