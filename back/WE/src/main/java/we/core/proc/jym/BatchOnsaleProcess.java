package we.core.proc.jym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.jym.BatchOnsaleService;

@Component()
public class BatchOnsaleProcess implements IProcess {

	@Autowired
	private BatchOnsaleService jymBatchOnsaleService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 检索条件
		List<Map<String, String>> goodsIds = new ArrayList<Map<String, String>>();

		if (proceeDto.getListData1() != null) {
			for (Map<String, Object> dataMap : proceeDto.getListData1()) {

				if (CommonUtil.toString(dataMap.get("goods_id")).isEmpty()) {
					continue;
				}

				Map<String, String> tmpId = new HashMap<>();

				tmpId.put("external_goods_id", CommonUtil.toString(dataMap.get("external_goods_id")));
				tmpId.put("goods_id", CommonUtil.toString(dataMap.get("goods_id")));
				goodsIds.add(tmpId);
			}
		}

		if (goodsIds.size() > 0) {
			jymBatchOnsaleService.execGoodsOnsale(goodsIds);
		}
		
		return 0;
	}

}
