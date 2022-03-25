/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import we.base.base.BaseModel;
import we.base.util.CommonUtil;
import we.core.model.UserGrantedAuthorityModel;

/**
 * 人员信息模型
 *
 * @author cp0
 * @since 3.0
 */
public class UserInfoModel extends BaseModel implements UserDetails {

	/** 用户ID */
	@JsonIgnore
	private String userId;

	/** 用户名 */
	private String userName;

	/** 用户登录用ID */
	private String loginUserId;

	/** 登陆密码 */
	@JsonIgnore
	private String password;

	/** 默认言语设定番号 */
	@JsonIgnore
	private String localeSettingIndex;

	/** 企业微信OPENID */
	@JsonIgnore
	private String qywxOpenId;

	/** 企业微信用户ID */
	@JsonIgnore
	private String qywxUserId;

	/** 钉钉OPENID */
	@JsonIgnore
	private String dingOpenId;

	/** 钉钉UNIONID */
	@JsonIgnore
	private String dingUnionId;

	/** Windows认证userid */
	@JsonIgnore
	private String spnegoUserId;

	/** 用户邮箱 */
	@JsonIgnore
	private String mail;

	/** 用户手机号 */
	@JsonIgnore
	private String mobile;

	/** 有效开始时间 */
	@JsonIgnore
	private String startDate;

	/** 有效结束时间 */
	@JsonIgnore
	private String endDate;

	/** 前回登陆时间 */
	private String lastLoginDate;

	/** 前回密码修改时间 */
	@JsonIgnore
	private String lastChangePassword;

	/** 版本ID */
	@JsonIgnore
	private int versionId;

	/** 部门ID */
	@JsonIgnore
	private String departmentId;

	/** 职位ID */
	@JsonIgnore
	private String positionId;

	/** 用户权限一览 */
	@JsonIgnore
	private List<UserGrantedAuthorityModel> userGrantedAuthorityList;

	/** 管理员区分 */
	@JsonIgnore
	private boolean sysadmin = false;

	/**
	 * 权限一览
	 */
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userGrantedAuthorityList;
	}

	/**
	 * 用户登陆ID
	 */
	@JsonIgnore
	@Override
	public String getUsername() {
		return userId;
	}

	/**
	 * 用户登陆密码
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * 用户过期
	 */
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		if (CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss").compareTo(startDate) < 0) {
			return false;
		}

		if (endDate != null && CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss").compareTo(endDate) > 0) {
			return false;
		}
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Getting method of userId
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Setting method of userId
	 *
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Getting method of userName
	 *
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setting method of userName
	 *
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Getting method of loginUserId
	 *
	 * @return loginUserId
	 */
	public String getLoginUserId() {
		return loginUserId;
	}

	/**
	 * Setting method of loginUserId
	 *
	 * @param loginUserId
	 */
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	/**
	 * Getting method of localeSettingIndex
	 *
	 * @return localeSettingIndex
	 */
	public String getLocaleSettingIndex() {
		return localeSettingIndex;
	}

	/**
	 * Setting method of localeSettingIndex
	 *
	 * @param localeSettingIndex
	 */
	public void setLocaleSettingIndex(String localeSettingIndex) {
		this.localeSettingIndex = localeSettingIndex;
	}

	/**
	 * Getting method of qywxUserId
	 *
	 * @return qywxUserId
	 */
	public String getQywxUserId() {
		return qywxUserId;
	}

	/**
	 * Setting method of qywxUserId
	 *
	 * @param qywxUserId
	 */
	public void setQywxUserId(String qywxUserId) {
		this.qywxUserId = qywxUserId;
	}

	/**
	 * Getting method of dingOpenId
	 *
	 * @return dingOpenId
	 */
	public String getDingOpenId() {
		return dingOpenId;
	}

	/**
	 * Setting method of dingOpenId
	 *
	 * @param dingOpenId
	 */
	public void setDingOpenId(String dingOpenId) {
		this.dingOpenId = dingOpenId;
	}

	/**
	 * Getting method of dingUnionId
	 *
	 * @return dingUnionId
	 */
	public String getDingUnionId() {
		return dingUnionId;
	}

	/**
	 * Setting method of dingUnionId
	 *
	 * @param dingUnionId
	 */
	public void setDingUnionId(String dingUnionId) {
		this.dingUnionId = dingUnionId;
	}

	/**
	 * Getting method of spnegoUserId
	 *
	 * @return spnegoUserId
	 */
	public String getSpnegoUserId() {
		return spnegoUserId;
	}

	/**
	 * Setting method of spnegoUserId
	 *
	 * @param spnegoUserId
	 */
	public void setSpnegoUserId(String spnegoUserId) {
		this.spnegoUserId = spnegoUserId;
	}

	/**
	 * Getting method of mail
	 *
	 * @return mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Setting method of mail
	 *
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Getting method of mobile
	 *
	 * @return mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Setting method of mobile
	 *
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * Getting method of startDate
	 *
	 * @return startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Setting method of startDate
	 *
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getting method of endDate
	 *
	 * @return endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Setting method of endDate
	 *
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * Getting method of lastLoginDate
	 *
	 * @return lastLoginDate
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * Setting method of lastLoginDate
	 *
	 * @param lastLoginDate
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * Getting method of lastChangePassword
	 *
	 * @return lastChangePassword
	 */
	public String getLastChangePassword() {
		return lastChangePassword;
	}

	/**
	 * Setting method of lastChangePassword
	 *
	 * @param lastChangePassword
	 */
	public void setLastChangePassword(String lastChangePassword) {
		this.lastChangePassword = lastChangePassword;
	}

	/**
	 * Getting method of qywxOpenId
	 *
	 * @return qywxOpenId
	 */
	public String getQywxOpenId() {
		return qywxOpenId;
	}

	/**
	 * Setting method of qywxOpenId
	 *
	 * @param qywxOpenId
	 */
	public void setQywxOpenId(String qywxOpenId) {
		this.qywxOpenId = qywxOpenId;
	}

	/**
	 * Getting method of versionId
	 *
	 * @return versionId
	 */
	public int getVersionId() {
		return versionId;
	}

	/**
	 * Setting method of versionId
	 *
	 * @param versionId
	 */
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	/**
	 * Getting method of departmentId
	 *
	 * @return departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * Setting method of departmentId
	 *
	 * @param departmentId
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * Getting method of positionId
	 *
	 * @return positionId
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * Setting method of positionId
	 *
	 * @param positionId
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	/**
	 * Setting method of password
	 *
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getting method of userGrantedAuthorityList
	 *
	 * @return userGrantedAuthorityList
	 */
	public List<UserGrantedAuthorityModel> getUserGrantedAuthorityList() {
		return userGrantedAuthorityList;
	}

	/**
	 * Setting method of userGrantedAuthorityList
	 *
	 * @param userGrantedAuthorityList
	 */
	public void setUserGrantedAuthorityList(List<UserGrantedAuthorityModel> userGrantedAuthorityList) {
		this.userGrantedAuthorityList = userGrantedAuthorityList;
	}

	/**
	 * Getting method of sysadmin
	 *
	 * @return sysadmin
	 */
	public boolean isSysadmin() {
		return sysadmin;
	}

	/**
	 * Setting method of sysadmin
	 *
	 * @param sysadmin
	 */
	public void setSysadmin(boolean sysadmin) {
		this.sysadmin = sysadmin;
	}
}
