package we.core.proc.jym;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.jym.CreateImageFolderService;

@Component()
public class CreateImageFolderProcess implements IProcess {

	@Value("${jym.local_netdisk_path}")
	private String jymLocalNetdrivePath;
	
	@Autowired
	private CreateImageFolderService jymCreateImgFolderService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 商品id
		String externalGoodsId = proceeDto.getStringData1();
		
		List<String> externalGoodsIds = new ArrayList<String>();
		
		if (!externalGoodsId.isEmpty()) {
			
			externalGoodsIds.add(externalGoodsId);
			
			this.createFolder(externalGoodsIds);
		}
		return 0; 
	}
	
	/**
	 * 定时作成商品文件夹
	 */
	@Scheduled(cron = "5 * * * * ?")
	public void autoCreate() {
		List<String> externalGoodsIds = jymCreateImgFolderService.getNotSendData();
		
		if (externalGoodsIds.size()> 0) {
			this.createFolder(externalGoodsIds);
		}
	}

	public void createFolder(List<String> externalGoodsIds) {
		String localNetdrivePath = jymLocalNetdrivePath;
		
		File localPath = new File(localNetdrivePath);
		
		// 网盘路径失效时，不做任何处理
		if (!localPath.exists()) {
			return;
		}
		
		if (externalGoodsIds.size() >0 ) {
			for (String externalGoodsId :externalGoodsIds) {
				String folderPath =FilenameUtils.concat(localNetdrivePath, externalGoodsId);
				
				File productFile = new File(folderPath);
				
				if (!productFile.exists()) {
					productFile.mkdir();
				}
			}
		}
	}
}
