/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

/**
 * 基础Facade类
 *
 * @author cp0
 * @since 0.0
 */
@Scope("prototype")
public class BaseFacade {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/** Spring上下文 */
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 取得Spring上下文
	 */
	protected WebApplicationContext getApplicationContext() {
		return (WebApplicationContext) applicationContext;
	}

}
