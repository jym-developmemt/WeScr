package we.website.service.jym;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface BatchOnsaleService {

	public boolean execGoodsOnsale(List<String> externalGoodsId);
}
