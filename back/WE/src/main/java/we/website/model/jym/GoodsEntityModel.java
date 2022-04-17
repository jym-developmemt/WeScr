package we.website.model.jym;

import java.util.Date;

/**
 * 商品详细
 * 
 * @author guan
 *
 */
public class GoodsEntityModel {

	// 外部商品ID
	private String externalGoodsId;

	// 商品标题
	private String title;

	// 价格
	private String price;

	// 库存
	private long storage;

	// 商品描述
	private String description;;

	// 服务器关联信息ID
	private String serverInfoId;

	// 是否支持找回包赔
	private boolean supportRetrieveCompensation;

	// 是否支持议价
	private boolean canBargain;

	// 创建时间
	private Date createAt;

	// 更新时间
	private Date updateAt;

	// 商品平台信息
	private ServerInfoModel serverInfoModel;

	public String getExternalGoodsId() {
		return externalGoodsId;
	}

	public void setExternalGoodsId(String externalGoodsId) {
		this.externalGoodsId = externalGoodsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public long getStorage() {
		return storage;
	}

	public void setStorage(long storage) {
		this.storage = storage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServerInfoId() {
		return serverInfoId;
	}

	public void setServerInfoId(String serverInfoId) {
		this.serverInfoId = serverInfoId;
	}

	public boolean isSupportRetrieveCompensation() {
		return supportRetrieveCompensation;
	}

	public void setSupportRetrieveCompensation(boolean supportRetrieveCompensation) {
		this.supportRetrieveCompensation = supportRetrieveCompensation;
	}

	public boolean isCanBargain() {
		return canBargain;
	}

	public void setCanBargain(boolean canBargain) {
		this.canBargain = canBargain;
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

	public ServerInfoModel getServerInfoModel() {
		return serverInfoModel;
	}

	public void setServerInfoModel(ServerInfoModel serverInfoModel) {
		this.serverInfoModel = serverInfoModel;
	}

	@Override
	public String toString() {
		return "GoodsEntityModel [externalGoodsId=" + externalGoodsId + ", title=" + title + ", price=" + price
				+ ", storage=" + storage + ", description=" + description + ", serverInfoId=" + serverInfoId
				+ ", supportRetrieveCompensation=" + supportRetrieveCompensation + ", canBargain=" + canBargain
				+ ", createAt=" + createAt + ", updateAt=" + updateAt + ", serverInfoModel=" + serverInfoModel + "]";
	}

}
