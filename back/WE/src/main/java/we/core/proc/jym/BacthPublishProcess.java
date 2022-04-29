package we.core.proc.jym;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.jym.BacthPublishService;

@Component()
public class BacthPublishProcess implements IProcess {

	@Autowired
	private BacthPublishService jymBatchService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 发布商品
		List<String> externalGoodsIds = new ArrayList<String>();
		
		// 发布商品现有external_batch_id时删除用
		List<String> externalBatchIds = new ArrayList<String>();

		if (proceeDto.getListData1() != null) {
			for (Map<String, Object> dataMap : proceeDto.getListData1()) {
				externalGoodsIds.add(CommonUtil.toString(dataMap.get("external_goods_id")));
				
				externalBatchIds.add(CommonUtil.toString(dataMap.get("external_batch_id")));
			}
		}
		
		jymBatchService.execGoodsPublish(externalGoodsIds, externalBatchIds);
		
		return 0; 
	}
}
