package we.website.service.jym;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 批处理
 * 
 * @author dell
 *
 */
@Service
public interface CreateImageDataService {

	public boolean createImageData(List<Map<String, String>> externalGoodsId);
}
