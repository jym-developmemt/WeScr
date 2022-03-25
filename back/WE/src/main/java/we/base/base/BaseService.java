/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

/**
 * 基础Service类
 *
 * @author cp0
 * @since 0.0
 */
@Scope("prototype")
public class BaseService {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
