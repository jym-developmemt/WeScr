package we.core.proc.jym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.jym.BatchTaskQService;;

@Component()
public class BacthTaskQProcess implements IProcess {

	@Autowired
	private BatchTaskQService jymBatchService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 检索条件
		Set<Map<String, String>> batchIds = new HashSet<>();

		if (proceeDto.getListData1() != null) {
			for (Map<String, Object> dataMap : proceeDto.getListData1()) {
				
				if (CommonUtil.toString(dataMap.get("batch_id")).isEmpty()) {
					continue;
				}
				
				Map<String, String> tmpIds = new HashMap<>();
				
				tmpIds.put("external_batch_id", CommonUtil.toString(dataMap.get("external_batch_id")));
				tmpIds.put("batch_id", CommonUtil.toString(dataMap.get("batch_id")));
				batchIds.add(tmpIds);
			}
		}
		
		List<Map<String, String>> batchIdList = new ArrayList<Map<String, String>>(batchIds);
				
		if (batchIdList.size() > 0) {
			jymBatchService.execBatchTask(batchIdList);
		}
		
		return 0; 
	}
}
