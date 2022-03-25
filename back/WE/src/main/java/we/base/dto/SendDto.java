/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.dto;

/**
 * 送信Dto
 *
 * @author cp0
 * @since 0.0
 */
public class SendDto {

	/** 结果 */
	private int result = 0;

	/** 错误消息 */
	private String errorCode;

	/** 送信数据 */
	private Object data;

	/**
	 * Getting method of result
	 *
	 * @return result
	 */
	public int getResult() {
		return result;
	}

	/**
	 * Setting method of result
	 *
	 * @param result
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * Getting method of errorMessage
	 *
	 * @return errorMessage
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Setting method of errorMessage
	 *
	 * @param errorMessage
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Getting method of data
	 *
	 * @return data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Setting method of data
	 *
	 * @param data
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
