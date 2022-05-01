package we.core.proc.jym;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.jym.StautsBatchQService;

@Component()
public class StatusBacthQProcess implements IProcess {

	@Autowired
	private StautsBatchQService jymBatchService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 检索条件
		List<String> goodsIds = new ArrayList<String>();

		if (proceeDto.getListData1() != null) {
			for (Map<String, Object> dataMap : proceeDto.getListData1()) {
				if (CommonUtil.toString(dataMap.get("goods_id")).isEmpty()) {
					continue;
				}
				
				goodsIds.add(CommonUtil.toString(dataMap.get("goods_id")));
			}
		}
		
		if (goodsIds.size() > 0) {
			jymBatchService.execStatusBatch(goodsIds);
		}
		
		return 0; 
	}
}
