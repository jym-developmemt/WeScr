package we.website.service.jym;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface BatchOffsaleService {

	public boolean execGoodsOffsale(List<Map<String, String>> goodsIdList);
}
