/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.base;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 基础模型
 *
 * @author cp0
 * @since 0.0
 */
public class BaseModel {

	/** 作成日 */
	@JsonIgnore
	private Date createdDate;

	/** 作成者 */
	@JsonIgnore
	private String createdBy;

	/** 更新日 */
	@JsonIgnore
	private Date updatedDate;

	/** 更新者 */
	@JsonIgnore
	private String updatedBy;

	/** 操作用户 */
	@JsonIgnore
	private String loginUser;

	/** 删除标志 */
	@JsonIgnore
	private String delFlg;

	/**
	 * Getting method of createdDate
	 *
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Setting method of createdDate
	 *
	 * @param createdDate
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Getting method of createdBy
	 *
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Setting method of createdBy
	 *
	 * @param createdBy
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Getting method of updatedDate
	 *
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * Setting method of updatedDate
	 *
	 * @param updatedDate
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * Getting method of updatedBy
	 *
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Setting method of updatedBy
	 *
	 * @param updatedBy
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Getting method of loginUser
	 *
	 * @return the loginUser
	 */
	public String getLoginUser() {
		if (StringUtils.isEmpty(loginUser) && SecurityContextHolder.getContext().getAuthentication() != null) {
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}
		return loginUser;
	}

	/**
	 * Setting method of loginUser
	 *
	 * @param loginUser
	 */
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	/**
	 * Getting method of delFlg
	 *
	 * @return the delFlg
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * Setting method of delFlg
	 *
	 * @param delFlg
	 */
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
}
