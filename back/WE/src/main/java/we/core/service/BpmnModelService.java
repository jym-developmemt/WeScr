/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service;

import org.springframework.stereotype.Service;

import we.core.model.BpmnModel;

/**
 * 模型信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public interface BpmnModelService {

	/**
	 * 模型信息取得
	 */
	public BpmnModel findBpmnModel(String modelId);

}
