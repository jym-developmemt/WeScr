/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import java.util.List;
import java.util.Map;

import we.core.model.DataSourceDataModel;

/**
 * 模型信息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface CallProcedureDao {

	public List<Map<String, Object>> checkProcedure(DataSourceDataModel DataSourceDataModel);

}
