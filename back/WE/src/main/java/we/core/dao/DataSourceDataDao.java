/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.dao;

import java.util.List;
import java.util.Map;

import we.core.model.DataSourceDataModel;

/**
 * 数据源数据Dao
 *
 * @author cp0
 * @since 3.0
 */
public interface DataSourceDataDao
{
  /**
   * 取得数据源数据信息
   */
  public Map<String, Object> findDataSourceData(DataSourceDataModel dataSourceDataModel);

  /**
   * 数据源数据检索
   */
  public List<Map<String, Object>> searchDataSourceData(DataSourceDataModel dataSourceDataModel);

  /**
   * 取得数据源数据件数
   */
  public int countDataSourceData(DataSourceDataModel dataSourceDataModel);

  /**
   * 数据源数据分页检索
   */
  public List<Map<String, Object>> pagingDataSourceData(DataSourceDataModel dataSourceDataModel);

  /**
   * 登录数据源数据
   */
  public int insertDataSourceData(DataSourceDataModel dataSourceDataModel);

  /**
   * 批量登录数据源数据
   */
  public int batchInsertDataSourceData(DataSourceDataModel dataSourceDataModel);

  /**
   * 更新数据源数据
   */
  public int updateDataSourceData(DataSourceDataModel dataSourceDataModel);

  /**
   * 删除数据源数据
   */
  public int deleteDataSourceData(DataSourceDataModel dataSourceDataModel);
}
