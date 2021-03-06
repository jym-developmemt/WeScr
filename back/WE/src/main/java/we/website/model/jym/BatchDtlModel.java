package we.website.model.jym;

import java.util.Date;

/**
 * 批处理明细记录模型
 * 
 * @author guan
 *
 */
public class BatchDtlModel {
	/** 外部批次ID */
	private String externalBatchId;

	/** 商品ID */
	private String externalGoodsId;

	/** 交易猫商品ID */
	private String goodsId;

	/** 商品状态 */
	private int goodsStatus;

	/** 任务状态 */
	private int status;

	/** 原因 */
	private String reason;

	/** 创建时间 */
	private Date createAt;

	/** 更新时间 */
	private Date updateAt;
	
	/** 搜索时用交易猫商品ID */
	private String whereGoodsId;

	public String getExternalBatchId() {
		return externalBatchId;
	}

	public void setExternalBatchId(String externalBatchId) {
		this.externalBatchId = externalBatchId;
	}

	public String getExternalGoodsId() {
		return externalGoodsId;
	}

	public void setExternalGoodsId(String externalGoodsId) {
		this.externalGoodsId = externalGoodsId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public int getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(int goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getWhereGoodsId() {
		return whereGoodsId;
	}

	public void setWhereGoodsId(String whereGoodsId) {
		this.whereGoodsId = whereGoodsId;
	}

}
