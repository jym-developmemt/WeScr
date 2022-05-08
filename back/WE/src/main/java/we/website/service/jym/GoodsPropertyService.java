package we.website.service.jym;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;


@Service
public interface GoodsPropertyService {

	public boolean insertData(List<Map<String, Object>> goodsPropertyList);
}
