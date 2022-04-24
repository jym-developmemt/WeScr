package we.website.dao;

import java.util.List;

import we.website.model.jym.BatchHdModel;

/**
 * 批处理记录Dao
 * 
 * @author guan
 *
 */
public interface JymBatchHdDao {

	/** 检索批处理列表 */
	public List<BatchHdModel> selectBatchAll();

	/** 批量插入批处理表 */
	public void insertBatchHd(BatchHdModel batchHd);
}
