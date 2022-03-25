/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志拦截器
 *
 * @author cp0
 * @since 0.0
 */
@Aspect
@Component
public class LogAspect {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 日志输出
	 */
	@Around("execution(* we.*.controller.*Controller.*(..))")
	public Object logPrint(ProceedingJoinPoint point) throws Throwable {
		String method = point.getStaticPart().toString();
		logger.debug("处理开始：" + method);
		try {
			return point.proceed();
		} finally {
			logger.debug("处理结束：" + method);
		}
	}
}
