package we.core.proc.jym;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.EncryptorUtil;
import we.core.CoreProperties;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.jym.GoodsEntityService;

@Component()
public class InsertGoodsEntityProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CoreProperties websiteProp;
		
	@Autowired
	private GoodsEntityService jymGoodsEntityService;
	
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 画面组ID
		String pageGroupId = EncryptorUtil.decryptString(proceeDto.getStringData1(), websiteProp.getDesSalt());
		if (StringUtils.hasText(pageGroupId) == false) {
			logger.error("画面组ID未输入。");
			throw new FacadeException("s.common.error.param");
		}

		// 画面ID
		String pageId = EncryptorUtil.decryptString(proceeDto.getStringData2(), websiteProp.getDesSalt());
		if (StringUtils.hasText(pageId) == false) {
			logger.error("画面ID未输入。");
			throw new FacadeException("s.common.error.param");
		}
				
		if (proceeDto.getObjData1() != null) {
			
			jymGoodsEntityService.insertData(proceeDto);
		}
		
		return true;
	}

}
