package we.website.model.jym;

/**
 * 商品平台信息
 * 
 * @author dell
 *
 */
public class ServerInfoModel {

	private String id;

	// 系统
	private String system;

	// 商品类型
	private String goodType;

	private String cid;

	// 一级类目ID
	private String firstCategoryId;

	// 一级类目名称
	private String firstCategoryName;

	// 二级类目ID
	private String secondCategoryId;

	// 二级类目名称
	private String secondCategoryName;

	// 平台ID
	private String platformId;

	// 客户端ID
	private String clientId;

	// 游戏ID
	private String gameId;

	// 服务器ID
	private String serverId;

	// 服务器名称
	private String serverName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getGoodType() {
		return goodType;
	}

	public void setGoodType(String goodType) {
		this.goodType = goodType;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getFirstCategoryId() {
		return firstCategoryId;
	}

	public void setFirstCategoryId(String firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}

	public String getFirstCategoryName() {
		return firstCategoryName;
	}

	public void setFirstCategoryName(String firstCategoryName) {
		this.firstCategoryName = firstCategoryName;
	}

	public String getSecondCategoryId() {
		return secondCategoryId;
	}

	public void setSecondCategoryId(String secondCategoryId) {
		this.secondCategoryId = secondCategoryId;
	}

	public String getSecondCategoryName() {
		return secondCategoryName;
	}

	public void setSecondCategoryName(String secondCategoryName) {
		this.secondCategoryName = secondCategoryName;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public String toString() {
		return "ServerInfoModel [id=" + id + ", system=" + system + ", goodType=" + goodType + ", cid=" + cid
				+ ", firstCategoryId=" + firstCategoryId + ", firstCategoryName=" + firstCategoryName
				+ ", secondCategoryId=" + secondCategoryId + ", secondCategoryName=" + secondCategoryName
				+ ", platformId=" + platformId + ", clientId=" + clientId + ", gameId=" + gameId + ", serverId="
				+ serverId + ", serverName=" + serverName + "]";
	}

}
