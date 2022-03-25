/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.dao;

import we.core.model.BpmnModel;

/**
 * 模型信息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface BpmnModelDao {

	/**
	 * 模型信息取得
	 */
	public BpmnModel findBpmnModel(String modelId);

}
