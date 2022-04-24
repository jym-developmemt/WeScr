package we.core.proc.jym;

import java.util.ArrayList;
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
		List<String> externalGoodsIds = new ArrayList<String>();

		// sql条件做成
		if (proceeDto.getListData1() != null) {
			for (Map<String, Object> dataMap : proceeDto.getListData1()) {
				externalGoodsIds.add(CommonUtil.toString(dataMap.get("external_goods_id")));
			}
		}

		boolean onsaleSucceed = jymBatchOnsaleService.execGoodsOnsale(externalGoodsIds);

		return onsaleSucceed;
	}

}
