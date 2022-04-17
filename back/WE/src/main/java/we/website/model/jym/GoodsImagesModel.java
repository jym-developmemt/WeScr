package we.website.model.jym;

/**
 * 商品图片
 * 
 * @author guan
 *
 */
public class GoodsImagesModel {

	// 外部商品ID
	private String externalGoodsId;

	// 图片ID
	private String imageId;

	// 商品图片URL
	private String imageUrl;

	// 备注
	private String note;

	public String getExternalGoodsId() {
		return externalGoodsId;
	}

	public void setExternalGoodsId(String externalGoodsId) {
		this.externalGoodsId = externalGoodsId;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "GoodsImagesModel [externalGoodsId=" + externalGoodsId + ", imageId=" + imageId + ", imageUrl="
				+ imageUrl + ", note=" + note + "]";
	}

}
