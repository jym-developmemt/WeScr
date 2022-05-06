package we.website.service.jym;

import java.util.List;

import org.springframework.stereotype.Service;

import we.website.model.jym.GoodsEntityModel;

@Service
public interface SellerGoodsPropertyService {

	public boolean createData(List<String> externalGoodsIds);
	
	public boolean createDataByGoodsEntity(List<GoodsEntityModel> externalGoodsIds);
}
