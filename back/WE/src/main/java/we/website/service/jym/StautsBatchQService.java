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
public interface StautsBatchQService {

	// 执行商品状态查询
	public boolean execStatusBatch(List<String> goodsIds);
}
