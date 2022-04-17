package we.core.proc.jym;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.jym.BacthPublishService;

@Component()
public class BacthPublishProcess implements IProcess {

	@Autowired
	private BacthPublishService jymBatchService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		Map<String, Object> selectedGoods = proceeDto.getObjData1();
		List dataList = (List) selectedGoods.get("dataList");

		// 检索条件
		List<String> externalGoodsIds = new ArrayList<String>();

		// 获取外部商品ID
		for (int i = 0; i < dataList.size(); i++) {

			Map<String, String> goodsMap = (Map<String, String>) dataList.get(i);

			// 添加检索条件
			externalGoodsIds.add(goodsMap.get("external_goods_id"));
		}

		return jymBatchService.execGoodsPublish(externalGoodsIds);
	}
}
