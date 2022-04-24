package we.website.dao;

import we.website.model.jym.BatchDtlModel;

/**
 * 批处理明细记录Dao
 * 
 * @author guan
 *
 */
public interface JymBatchDtlDao {

	/** 批量插入批处理明细表 */
	public void insertBatchDtl(BatchDtlModel batchDtl);
	
	/** 批量更新批处理明细表 */
	public void updateBatchDtl(BatchDtlModel batchDtl);
	
	/** 批量删除批处理明细表 */
	public void deleteBatchDtl(BatchDtlModel batchDtl);
}
