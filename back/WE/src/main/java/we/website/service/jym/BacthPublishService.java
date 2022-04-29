package we.website.service.jym;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * 批处理
 * 
 * @author dell
 *
 */
@Service
public interface BacthPublishService {

	// 执行商品发布
	public boolean execGoodsPublish(List<String> externalGoodsId, List<String> externalBatchIds);
}
