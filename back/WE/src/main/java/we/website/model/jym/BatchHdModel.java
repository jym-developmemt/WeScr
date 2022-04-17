package we.website.model.jym;

import java.util.Date;

/**
 * 批处理记录模型
 * 
 * @author guan
 *
 */
public class BatchHdModel {
	/** 外部批次ID */
	private String externalBatchId;

	/** 商品发布批次ID */
	private String batchId;

	/** 请求批处理结果 */
	private boolean succeed;

	/** 发布商品件数 */
	private int productCnt;

	/** 请求返回状态码 */
	private String stateCode;

	/** 返回错误信息 */
	private String extraErrMsg;

	/** API接口ID */
	private String methodId;

	/** 任务状态 */
	private int status;

	/** 原因 */
	private int reason;

	/** 创建时间 */
	private Date createAt;

	/** 更新时间 */
	private Date updateAt;

	public String getExternalBatchId() {
		return externalBatchId;
	}

	public void setExternalBatchId(String externalBatchId) {
		this.externalBatchId = externalBatchId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public boolean getSucceed() {
		return succeed;
	}

	public void isSucceed(boolean succeed) {
		this.succeed = succeed;
	}

	public int getProductCnt() {
		return productCnt;
	}

	public void setProductCnt(int productCnt) {
		this.productCnt = productCnt;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getExtraErrMsg() {
		return extraErrMsg;
	}

	public void setExtraErrMsg(String extraErrMsg) {
		this.extraErrMsg = extraErrMsg;
	}

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getReason() {
		return reason;
	}

	public void setReason(int reason) {
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

}
