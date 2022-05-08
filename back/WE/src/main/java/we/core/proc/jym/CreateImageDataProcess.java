package we.core.proc.jym;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.jym.CreateImageDataService;

@Component()
public class CreateImageDataProcess implements IProcess {

	@Value("${jym.local_netdisk_path}")
	private String jymLocalNetdrivePath;
	
	@Value("${jym.netdisk_url}")
	private String jymNetdisk_url;
	
	@Autowired
	private CreateImageDataService jymCreateImageDataService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 商品Id
		List<Map<String, String>> externalGoodsIds = new ArrayList<Map<String, String>>();
		
		String localNetdrivePath = jymLocalNetdrivePath;
		String netdiskUrl = jymNetdisk_url;
		
		if (!jymNetdisk_url.endsWith("/")) {
			netdiskUrl = jymNetdisk_url +"/";
		}
		
		File localPath = new File(localNetdrivePath);
		
		// 网盘路径失效时，不做任何处理
		if (!localPath.exists()) {
			throw new FacadeException("上传图片路径有误！");
		}
		
		if (proceeDto.getListData1() != null) {
			for (Map<String, Object> dataMap : proceeDto.getListData1()) {
				String externalGoodsId =CommonUtil.toString(dataMap.get("external_goods_id"));
				String imgUrlPath = netdiskUrl + externalGoodsId + "/";
				
				File imgFolder = new File(FilenameUtils.concat(localNetdrivePath, externalGoodsId));
				File[] imgFiles = imgFolder.listFiles();
				
				if(imgFiles != null) {	
					for (File tmpFile : imgFiles) {
						Map<String, String> tmpIds = new HashMap<>();
						
						tmpIds.put("external_goods_id", externalGoodsId);
						tmpIds.put("image_url", imgUrlPath + tmpFile.getName());
						externalGoodsIds.add(tmpIds);
					}
				}
			}
		}
		
		if (externalGoodsIds.size() > 0) {
			return jymCreateImageDataService.createImageData(externalGoodsIds);
		}
		
		return 0; 
	}
}
