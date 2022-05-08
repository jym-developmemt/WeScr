package we.website.model.jym;

/**
 * 商品属性
 * 
 * @author guan
 *
 */
public class GoodsPropertyModel {

	// 外部商品ID
	private String externalGoodsId;

	// 属性ID
	private String propertyId;

	// 父属性值ID
	private String parentId;

	// 属性值
	private String value;
	
	// 属性类型
	private String type;

	public String getExternalGoodsId() {
		return externalGoodsId;
	}

	public void setExternalGoodsId(String externalGoodsId) {
		this.externalGoodsId = externalGoodsId;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "GoodsPropertyModel [externalGoodsId=" + externalGoodsId + ", propertyId=" + propertyId + ", valueId="
				+ parentId + ", value=" + value + "]";
	}

}
