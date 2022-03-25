/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.facade;

import java.util.List;

import we.core.dto.ProcessDto;

/**
 * 处理请求Facade
 *
 * @author cp0
 * @since 0.0
 */
public interface ProcessFacade {
	/** 处理实行 */
	public List<Object> execute(List<ProcessDto> processList) throws Exception;
}
