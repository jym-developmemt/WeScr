/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 分页模型
 *
 * @author cp0
 * @since 2.0
 */
public class PaginationModel extends BaseModel {

	/** 页码 */
	private int pageNo = 1;

	/** 每页显示数 */
	private int pageSize = 10;

	/** 数据总件数 */
	private int itemCount = 0;

	/**
	 * 取得开始记录番号
	 *
	 * @return 开始记录番号
	 */
	@JsonIgnore
	public int getStartNum() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 取得结束记录番号
	 *
	 * @return 结束记录番号
	 */
	@JsonIgnore
	public int getEndNum() {
		return pageNo * pageSize;
	}

	/**
	 * 取得总页数
	 *
	 * @return 总页数
	 */
	@JsonIgnore
	public int getPageTotalCount() {
		if (itemCount % pageSize == 0) {
			return itemCount / pageSize;
		} else {
			return (itemCount / pageSize) + 1;
		}
	}

	/**
	 * 取得当前页码
	 *
	 * @return 当前页码
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设定当前页码
	 *
	 * @author pageNo 当前页码
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 取得每页显示数
	 *
	 * @return 每页显示数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设定每页显示数
	 *
	 * @param pageSize 每页显示数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 取得数据总件数
	 *
	 * @return 数据总件数
	 */
	public int getItemCount() {
		return itemCount;
	}

	/**
	 * 设定数据总件数
	 *
	 * @param itemCount 数据总件数
	 */
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
}
