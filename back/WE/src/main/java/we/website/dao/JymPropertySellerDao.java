package we.website.dao;

import java.util.List;

import we.website.model.jym.PropertySellerModel;

/**
 * 卖家属性master
 * 
 * @author dell
 *
 */
public interface JymPropertySellerDao {

	public List<PropertySellerModel> searchData(PropertySellerModel propertySellerInfo);
}
