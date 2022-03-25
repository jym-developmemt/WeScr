/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 工程属性配置
 *
 * @author cp0
 * @since 0.0
 */
@ConfigurationProperties(prefix = "core")
public class CoreProperties {

	// 默认站点ID
	private String defaultSiteId;

	// 版本刷新时间(分)
	private int versionRefreshTime;

	// 管理员ID
	private String adminId;

	// 管理员密码
	private String adminPassword;

	// 管理员菜单ID
	private String adminMenuId;

	// 更新用户最后登陆时间
	private boolean updateLoginDate;

	// DES加密盐值
	private String desSalt;

	// 上传路径
	private String uploadPath;
	
	// 上传路径
	private String uploadStaticPicPath;
	
	//上传资源
	private Map<String, String> uploadTypePath = new HashMap<String, String>();
	
	// 上传路径
	private String uploadIpPath;

	// 每次批量插入处理的最大件数
	private int batchInsertMaxCount;

	// OAuth2: 站点认证ClientId
	private String clientIdSite;

	// OAuth2: 站点认证ClientSecret
	private String clientSecretSite;

	// OAuth2: 手机认证ClientId
	private String clientIdMobile;

	// OAuth2: 手机认证ClientSecret
	private String clientSecretMobile;

	// OAuth2: 企业微信认证ClientId
	private String clientIdQywx;

	// OAuth2: 企业微信认证ClientSecret
	private String clientSecretQywx;

	// OAuth2: Windows认证ClientId
	private String clientIdSpnego;

	// OAuth2: Windows认证ClientSecret
	private String clientSecretSpnego;

	// OAuth2: 钉钉认证ClientId
	private String clientIdDing;

	// OAuth2: 钉钉微信认证ClientSecret
	private String clientSecretDing;

	// OAuth2: scope
	private String scope;

	// Token超时时间
	private int tokenTimeout;

	// 启动AD认证
	private boolean adAuthority;

	// Spnego服务器地址
	private String spnegoUrl;

	// 启动企业微信认证
	private boolean qyapiAuthority;

	// 企业微信:企业ID
	private String qyapiCorpid;

	// 企业微信:应用ID
	private String qyapiAgentid;

	// 企业微信:应用的凭证密钥
	private String qyapiCorpsecret;

	// 企业微信:商户号
	private String qyapiMchid;

	// 企业微信:API证书地址
	private String qyapiCertPath;

	// 企业微信:API密钥
	private String qyapiKey;

	// 服务器外网地址
	private String apiServerUrl;

	// 权限选择器
	private Map<String, String> authSelecter = new HashMap<String, String>();

	// 功能定义
	private Map<String, String> functions = new HashMap<String, String>();

	/**
	 * Getting method of defaultSiteId
	 *
	 * @return defaultSiteId
	 */
	public String getDefaultSiteId() {
		return defaultSiteId;
	}

	/**
	 * Setting method of defaultSiteId
	 *
	 * @param defaultSiteId
	 */
	public void setDefaultSiteId(String defaultSiteId) {
		this.defaultSiteId = defaultSiteId;
	}

	/**
	 * Getting method of versionRefreshTime
	 *
	 * @return versionRefreshTime
	 */
	public int getVersionRefreshTime() {
		return versionRefreshTime;
	}

	/**
	 * Setting method of versionRefreshTime
	 *
	 * @param versionRefreshTime
	 */
	public void setVersionRefreshTime(int versionRefreshTime) {
		this.versionRefreshTime = versionRefreshTime;
	}

	/**
	 * Getting method of adminId
	 *
	 * @return adminId
	 */
	public String getAdminId() {
		return adminId;
	}

	/**
	 * Setting method of adminId
	 *
	 * @param adminId
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * Getting method of adminPassword
	 *
	 * @return adminPassword
	 */
	public String getAdminPassword() {
		return adminPassword;
	}

	/**
	 * Setting method of adminPassword
	 *
	 * @param adminPassword
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	/**
	 * Getting method of adminMenuId
	 *
	 * @return adminMenuId
	 */
	public String getAdminMenuId() {
		return adminMenuId;
	}

	/**
	 * Setting method of adminMenuId
	 *
	 * @param adminMenuId
	 */
	public void setAdminMenuId(String adminMenuId) {
		this.adminMenuId = adminMenuId;
	}

	/**
	 * Getting method of updateLoginDate
	 *
	 * @return updateLoginDate
	 */
	public boolean isUpdateLoginDate() {
		return updateLoginDate;
	}

	/**
	 * Setting method of updateLoginDate
	 *
	 * @param updateLoginDate
	 */
	public void setUpdateLoginDate(boolean updateLoginDate) {
		this.updateLoginDate = updateLoginDate;
	}

	/**
	 * Getting method of desSalt
	 *
	 * @return desSalt
	 */
	public String getDesSalt() {
		return desSalt;
	}

	/**
	 * Setting method of desSalt
	 *
	 * @param desSalt
	 */
	public void setDesSalt(String desSalt) {
		this.desSalt = desSalt;
	}

	/**
	 * Getting method of uploadPath
	 *
	 * @return uploadPath
	 */
	public String getUploadPath() {
		return uploadPath;
	}

	/**
	 * Setting method of uploadPath
	 *
	 * @param uploadPath
	 */
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
	/**
	 * @return the uploadStaticPicPath
	 */
	public String getUploadStaticPicPath() {
		return uploadStaticPicPath;
	}

	/**
	 * @param uploadStaticPicPath the uploadStaticPicPath to set
	 */
	public void setUploadStaticPicPath(String uploadStaticPicPath) {
		this.uploadStaticPicPath = uploadStaticPicPath;
	}
	
	/**
	 * @return the uploadTypePath
	 */
	public Map<String, String> getUploadTypePath() {
		return uploadTypePath;
	}

	/**
	 * @param uploadTypePath the uploadTypePath to set
	 */
	public void setUploadTypePath(Map<String, String> uploadTypePath) {
		this.uploadTypePath = uploadTypePath;
	}

	/**
	 * @return the uploadIpPath
	 */
	public String getUploadIpPath() {
		return uploadIpPath;
	}

	/**
	 * @param uploadIpPath the uploadIpPath to set
	 */
	public void setUploadIpPath(String uploadIpPath) {
		this.uploadIpPath = uploadIpPath;
	}

	/**
	 * Getting method of batchInsertMaxCount
	 *
	 * @return batchInsertMaxCount
	 */
	public int getBatchInsertMaxCount() {
		return batchInsertMaxCount;
	}

	/**
	 * Setting method of batchInsertMaxCount
	 *
	 * @param batchInsertMaxCount
	 */
	public void setBatchInsertMaxCount(int batchInsertMaxCount) {
		this.batchInsertMaxCount = batchInsertMaxCount;
	}

	/**
	 * Getting method of clientIdSite
	 *
	 * @return clientIdSite
	 */
	public String getClientIdSite() {
		return clientIdSite;
	}

	/**
	 * Setting method of clientIdSite
	 *
	 * @param clientIdSite
	 */
	public void setClientIdSite(String clientIdSite) {
		this.clientIdSite = clientIdSite;
	}

	/**
	 * Getting method of clientSecretSite
	 *
	 * @return clientSecretSite
	 */
	public String getClientSecretSite() {
		return clientSecretSite;
	}

	/**
	 * Setting method of clientSecretSite
	 *
	 * @param clientSecretSite
	 */
	public void setClientSecretSite(String clientSecretSite) {
		this.clientSecretSite = clientSecretSite;
	}

	/**
	 * Getting method of clientIdMobile
	 *
	 * @return clientIdMobile
	 */
	public String getClientIdMobile() {
		return clientIdMobile;
	}

	/**
	 * Setting method of clientIdMobile
	 *
	 * @param clientIdMobile
	 */
	public void setClientIdMobile(String clientIdMobile) {
		this.clientIdMobile = clientIdMobile;
	}

	/**
	 * Getting method of clientSecretMobile
	 *
	 * @return clientSecretMobile
	 */
	public String getClientSecretMobile() {
		return clientSecretMobile;
	}

	/**
	 * Setting method of clientSecretMobile
	 *
	 * @param clientSecretMobile
	 */
	public void setClientSecretMobile(String clientSecretMobile) {
		this.clientSecretMobile = clientSecretMobile;
	}

	/**
	 * Getting method of clientIdQywx
	 *
	 * @return clientIdQywx
	 */
	public String getClientIdQywx() {
		return clientIdQywx;
	}

	/**
	 * Setting method of clientIdQywx
	 *
	 * @param clientIdQywx
	 */
	public void setClientIdQywx(String clientIdQywx) {
		this.clientIdQywx = clientIdQywx;
	}

	/**
	 * Getting method of clientSecretQywx
	 *
	 * @return clientSecretQywx
	 */
	public String getClientSecretQywx() {
		return clientSecretQywx;
	}

	/**
	 * Setting method of clientSecretQywx
	 *
	 * @param clientSecretQywx
	 */
	public void setClientSecretQywx(String clientSecretQywx) {
		this.clientSecretQywx = clientSecretQywx;
	}

	/**
	 * Getting method of clientIdSpnego
	 *
	 * @return clientIdSpnego
	 */
	public String getClientIdSpnego() {
		return clientIdSpnego;
	}

	/**
	 * Setting method of clientIdSpnego
	 *
	 * @param clientIdSpnego
	 */
	public void setClientIdSpnego(String clientIdSpnego) {
		this.clientIdSpnego = clientIdSpnego;
	}

	/**
	 * Getting method of clientSecretSpnego
	 *
	 * @return clientSecretSpnego
	 */
	public String getClientSecretSpnego() {
		return clientSecretSpnego;
	}

	/**
	 * Setting method of clientSecretSpnego
	 *
	 * @param clientSecretSpnego
	 */
	public void setClientSecretSpnego(String clientSecretSpnego) {
		this.clientSecretSpnego = clientSecretSpnego;
	}

	/**
	 * Getting method of clientIdDing
	 *
	 * @return clientIdDing
	 */
	public String getClientIdDing() {
		return clientIdDing;
	}

	/**
	 * Setting method of clientIdDing
	 *
	 * @param clientIdDing
	 */
	public void setClientIdDing(String clientIdDing) {
		this.clientIdDing = clientIdDing;
	}

	/**
	 * Getting method of clientSecretDing
	 *
	 * @return clientSecretDing
	 */
	public String getClientSecretDing() {
		return clientSecretDing;
	}

	/**
	 * Setting method of clientSecretDing
	 *
	 * @param clientSecretDing
	 */
	public void setClientSecretDing(String clientSecretDing) {
		this.clientSecretDing = clientSecretDing;
	}

	/**
	 * Getting method of scope
	 *
	 * @return scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Setting method of scope
	 *
	 * @param scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * Getting method of tokenTimeout
	 *
	 * @return tokenTimeout
	 */
	public int getTokenTimeout() {
		return tokenTimeout;
	}

	/**
	 * Setting method of tokenTimeout
	 *
	 * @param tokenTimeout
	 */
	public void setTokenTimeout(int tokenTimeout) {
		this.tokenTimeout = tokenTimeout;
	}

	/**
	 * Getting method of adAuthority
	 *
	 * @return adAuthority
	 */
	public boolean isAdAuthority() {
		return adAuthority;
	}

	/**
	 * Setting method of adAuthority
	 *
	 * @param adAuthority
	 */
	public void setAdAuthority(boolean adAuthority) {
		this.adAuthority = adAuthority;
	}

	/**
	 * Getting method of spnegoUrl
	 *
	 * @return spnegoUrl
	 */
	public String getSpnegoUrl() {
		return spnegoUrl;
	}

	/**
	 * Setting method of spnegoUrl
	 *
	 * @param spnegoUrl
	 */
	public void setSpnegoUrl(String spnegoUrl) {
		this.spnegoUrl = spnegoUrl;
	}

	/**
	 * Getting method of qyapiAuthority
	 *
	 * @return qyapiAuthority
	 */
	public boolean isQyapiAuthority() {
		return qyapiAuthority;
	}

	/**
	 * Setting method of qyapiAuthority
	 *
	 * @param qyapiAuthority
	 */
	public void setQyapiAuthority(boolean qyapiAuthority) {
		this.qyapiAuthority = qyapiAuthority;
	}

	/**
	 * Getting method of qyapiCorpid
	 *
	 * @return qyapiCorpid
	 */
	public String getQyapiCorpid() {
		return qyapiCorpid;
	}

	/**
	 * Setting method of qyapiCorpid
	 *
	 * @param qyapiCorpid
	 */
	public void setQyapiCorpid(String qyapiCorpid) {
		this.qyapiCorpid = qyapiCorpid;
	}

	/**
	 * Getting method of qyapiAgentid
	 *
	 * @return qyapiAgentid
	 */
	public String getQyapiAgentid() {
		return qyapiAgentid;
	}

	/**
	 * Setting method of qyapiAgentid
	 *
	 * @param qyapiAgentid
	 */
	public void setQyapiAgentid(String qyapiAgentid) {
		this.qyapiAgentid = qyapiAgentid;
	}

	/**
	 * Getting method of qyapiCorpsecret
	 *
	 * @return qyapiCorpsecret
	 */
	public String getQyapiCorpsecret() {
		return qyapiCorpsecret;
	}

	/**
	 * Setting method of qyapiCorpsecret
	 *
	 * @param qyapiCorpsecret
	 */
	public void setQyapiCorpsecret(String qyapiCorpsecret) {
		this.qyapiCorpsecret = qyapiCorpsecret;
	}

	/**
	 * Getting method of qyapiMchid
	 *
	 * @return qyapiMchid
	 */
	public String getQyapiMchid() {
		return qyapiMchid;
	}

	/**
	 * Setting method of qyapiMchid
	 *
	 * @param qyapiMchid
	 */
	public void setQyapiMchid(String qyapiMchid) {
		this.qyapiMchid = qyapiMchid;
	}

	/**
	 * Getting method of qyapiCertPath
	 *
	 * @return qyapiCertPath
	 */
	public String getQyapiCertPath() {
		return qyapiCertPath;
	}

	/**
	 * Setting method of qyapiCertPath
	 *
	 * @param qyapiCertPath
	 */
	public void setQyapiCertPath(String qyapiCertPath) {
		this.qyapiCertPath = qyapiCertPath;
	}

	/**
	 * Getting method of qyapiKey
	 *
	 * @return qyapiKey
	 */
	public String getQyapiKey() {
		return qyapiKey;
	}

	/**
	 * Setting method of qyapiKey
	 *
	 * @param qyapiKey
	 */
	public void setQyapiKey(String qyapiKey) {
		this.qyapiKey = qyapiKey;
	}

	/**
	 * Getting method of apiServerUrl
	 *
	 * @return apiServerUrl
	 */
	public String getApiServerUrl() {
		return apiServerUrl;
	}

	/**
	 * Setting method of apiServerUrl
	 *
	 * @param apiServerUrl
	 */
	public void setApiServerUrl(String apiServerUrl) {
		this.apiServerUrl = apiServerUrl;
	}

	/**
	 * Getting method of authSelecter
	 *
	 * @return authSelecter
	 */
	public Map<String, String> getAuthSelecter() {
		return authSelecter;
	}

	/**
	 * Setting method of authSelecter
	 *
	 * @param authSelecter
	 */
	public void setAuthSelecter(Map<String, String> authSelecter) {
		this.authSelecter = authSelecter;
	}

	/**
	 * Getting method of functions
	 *
	 * @return functions
	 */
	public Map<String, String> getFunctions() {
		return functions;
	}

	/**
	 * Setting method of functions
	 *
	 * @param functions
	 */
	public void setFunctions(Map<String, String> functions) {
		this.functions = functions;
	}


}
