package we.core.proc.jym;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.jym.GoodsEntityService;

@Component()
public class uploadImageDataProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
			
	@Autowired
	private GoodsEntityService jymGoodsEntityService;
	
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 检索条件
		List<String> externalGoodsId = new ArrayList<String>();

		if (proceeDto.getListData1() != null) {
			for (Map<String, Object> dataMap : proceeDto.getListData1()) {

				externalGoodsId.add(CommonUtil.toString(dataMap.get("external_goods_id")));
			}
		}
						
		if (externalGoodsId.size() > 0) {
			jymGoodsEntityService.uploadImageData(externalGoodsId);
		}
		
		return 0; 
	}

}
