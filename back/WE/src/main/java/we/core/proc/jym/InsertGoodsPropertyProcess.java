package we.core.proc.jym;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.jym.GoodsPropertyService;

@Component()
public class InsertGoodsPropertyProcess implements IProcess {

	@Autowired
	private GoodsPropertyService jymGoodsPropertyService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		if (proceeDto.getListData1() != null) {
			
			// parent_id和property_id不同时为root，做校验
			for (Map<String, Object> tmpData : proceeDto.getListData1()) {
				
				if (CommonUtil.toString(tmpData.get("parent_id")).equals("root")) {
					if (CommonUtil.toString(tmpData.get("property_id")).equals("root")) {
						throw new FacadeException(CommonUtil.toString(tmpData.get("value")) + "的项目1，请从新选择一下。");
					}
				}
			}
		
			return jymGoodsPropertyService.insertData(proceeDto.getListData1());
		}
		
		return true;
	}

}
