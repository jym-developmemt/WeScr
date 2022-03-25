/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import we.core.model.DataSourceDataModel;

/**
 * 模型信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public interface CallProcedureService {

	public List<Map<String, Object>> checkProcedure(DataSourceDataModel DataSourceDataModel);

}
