package we.website.model.jym;

/**
  * 卖家属性master
 * 
 * @author guan
 *
 */
public class PropertySellerModel {

	// cID
	private String cid;

	// 属性ID
	private String propertyId;

	// 属性名
	private String propertyName;
	
	// 类型
	private String type;

	// 属性值
	private String value;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
