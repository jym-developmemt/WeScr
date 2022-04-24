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
public interface BatchTaskQService {

	// 执行商品批量查询
	public boolean execBatchTask(List<Map<String,String>> batchId);
}
