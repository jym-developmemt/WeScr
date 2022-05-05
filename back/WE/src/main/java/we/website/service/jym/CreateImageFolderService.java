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
public interface CreateImageFolderService {

	// 执行未发送商品查询
	public List<String> getNotSendData();
}
