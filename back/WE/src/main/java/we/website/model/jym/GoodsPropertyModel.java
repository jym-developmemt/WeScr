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

	// 属性值ID
	private String valueId;

	// 属性值
	private String value;

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

	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "GoodsPropertyModel [externalGoodsId=" + externalGoodsId + ", propertyId=" + propertyId + ", valueId="
				+ valueId + ", value=" + value + "]";
	}

}
