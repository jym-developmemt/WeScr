package we.website.service.jym;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface BatchOnsaleService {

	public boolean execGoodsOnsale(List<Map<String, String>> goodsIdList);
}
